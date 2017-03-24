package com.wzylibrary.tools.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 按长宽进行图片缩*/
public class BitMapUtils {
	public BitMapUtils() {
		// TODO Auto-generated constructor stub
	}

//	下面的代码非常简单地将任意一张图片压缩成100*100的缩略图，并在ImageView上展示。
	public static Bitmap decodeBitmap(byte[] data, int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inPurgeable = true;
		options.inInputShareable = false;
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				// 计算出实际宽高和目标宽高的比率
				// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
				// 一定都会大于等于目标的宽和高。
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
}
