package com.wzylibrary.tools.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步加载图片
 */
@SuppressLint("NewApi")
public class AsyncImageLoader {

	private static final int LOAD_SUCCESS = 1;
	private static final int LOAD_ERROR = 2;
	/** 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存 */
	private static LruCache<String, Bitmap> lruCache;
	/** 文件操作工具类 */
	private AsyncImageLoader_FileUtils utils;
	private static ThreadPoolExecutor executor;
	private static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(
			4);
	private static final int CORE_POOL_SIZE = 1;
	private static final int MAXIMUM_POOL_SIZE = 4;
	private static final int KEEP_ALIVE = 10;
	
	static {
		// 开启线程池 最小线程数
		executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
				KEEP_ALIVE, TimeUnit.SECONDS, queue,
				new ThreadPoolExecutor.DiscardOldestPolicy());
		// 获取系统分配给应用程序的最大内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int maxSize = maxMemory / 8;
		lruCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// 测量Bitmap的大小 默认返回图片数量
				return value.getRowBytes() * value.getHeight();
			}
		};
	}

	public AsyncImageLoader(Context context) {
		super();
		utils = new AsyncImageLoader_FileUtils(context);
	}

	/**
	 * 使用方法：
	 * 如果缓存中有这个图片，则不会下载就直接return了，所以必须设置iv.setImageBitmap(mLoader.downLoader()),如果缓存中没有，则在回调方法中再设置。
	 * 网络下载的回调为onLoadSuccess和onLoadError
	 *
     * @return 返回值为缓存的bitmap（file、lru中的缓存），如果缓存中没有，则返回为null
	 * noted by wzy (2016/8/2  16:32)
     */
	@SuppressLint("HandlerLeak")
	public Bitmap downLoader(final ImageView imageView,final ImageLoaderlistener loaderlistener) {
		final String url = (String) imageView.getTag();
		if (url != null) {
			final Bitmap bitmap = showCacheBitmap(url);
			if (bitmap != null) {
				return bitmap;
			} else {
				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						if (msg.what == LOAD_SUCCESS) {
							loaderlistener.onLoadSuccess((Bitmap) msg.obj, imageView);
						} else if (msg.what == LOAD_ERROR){  //2016-8-2 添加
							loaderlistener.onLoadError(imageView);
						}
					}
				};

				executor.execute(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = AsyncImageLoader_HttpUtils.getBitmapFormUrl(url);
						if (bitmap != null) {
							Message msg = handler.obtainMessage();
							msg.obj = bitmap;
							msg.what = LOAD_SUCCESS;
							handler.sendMessage(msg);
							try {
								utils.savaBitmap(url, bitmap);
							} catch (IOException e) {
								e.printStackTrace();
							}
							lruCache.put(url, bitmap);
						}else {     //2016-8-2  添加了如果bitmap为null的时候，回调返回onError方法
							Message msg = handler.obtainMessage();
							msg.obj = null;
							msg.what = LOAD_ERROR;
							handler.sendMessage(msg);
						}
					}
				});
			}
		}
		return null;
	}
	
	
	
	/**
	 * 移除image缓存，
	 * @param key 传递之前缓存图片的网址
	 */
	public void removeImageCache(String key) {
        if (key != null) {
            if (lruCache != null) {
                Bitmap bm = lruCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
        
        utils.deleteFile();
    }

	/**
	 * 
	 * @Title: showCacheBitmap
	 * @说 明: 获取bitmap对象 : 内存中没有就去sd卡中去找
	 * @参 数: @param url 图片地址
	 * @参 数: @return
	 * @return Bitmap 返回类型 图片
	 * @throws
	 */
	private Bitmap showCacheBitmap(String url) {
		Bitmap bitmap = getMemoryBitmap(url);
		if (bitmap != null) {
			return bitmap;
		} else if (utils.isFileExists(url) && utils.getFileSize(url) > 0) {
			bitmap = utils.getBitmap(url);
			if (url != null && bitmap != null) {
				lruCache.put(url, bitmap);
			}

			return bitmap;
		}
		return null;
	}

	/**
	 * 
	 * @Title: getMemoryBitmap
	 * @说 明:获取内存中的图片
	 * @参 数: @param url
	 * @参 数: @return
	 * @return Bitmap 返回类型
	 * @throws
	 */
	private Bitmap getMemoryBitmap(String url) {
		return lruCache.get(url);
	}

	public interface ImageLoaderlistener {
		/**
		 * 如果下载成功，下载的图片即为bitmap，imageView为带tag的View
		 * noted by wzy (2016/8/2  16:28)
		 */
		public void onLoadSuccess(Bitmap bitmap, ImageView imageView);
		/**
		 * 如果下载失败，即bitmap为空
		 * create by wzy (2016/8/2  16:28)
		*/
		public void onLoadError(ImageView imageView);
	}


	/**
	 * 
	 * @Title: cancelTask
	 * @说 明:停止所有下载线程
	 * @参 数:
	 * @return void 返回类型
	 * @throws
	 */
	public void cancelTask() {
		if (executor != null) {
			executor.shutdownNow();
		}
	}

}
