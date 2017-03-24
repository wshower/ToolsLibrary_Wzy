package com.wzylibrary.tools.ImageLoader;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsyncImageLoader_FileUtils {
	
	 //sd卡的根目录
	private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
	
	 //手机的缓存根目录
	private static String mDataRootPath = null;
	
	 //保存Image的目录名
	private static String FOLDER_NAME = "/Image/image";  //可在application中定制

	public AsyncImageLoader_FileUtils(Context context) {
		mDataRootPath = context.getCacheDir().getPath();
	}

	
	 //获取储存Image的目录
	
	private String getStorageDirectory() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
	}

	
	 //保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	
	public void savaBitmap(String url, Bitmap bitmap) throws IOException {
		if (bitmap == null) {
			return;
		}
		String path = getStorageDirectory();
		File folderFile = new File(path);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		File file = new File(path + File.separator + getFileName(url));
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
	}


	 // 从手机或者sd卡获取Bitmap
	
	public Bitmap getBitmap(String url) {
		return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + getFileName(url));
	}

	
	 // 判断文件是否存在
	
	public boolean isFileExists(String fileName) {
		return new File(getStorageDirectory() + File.separator + getFileName(fileName)).exists();
	}

	// 获取文件的大小

	public long getFileSize(String url) {
		return new File(getStorageDirectory() + File.separator + getFileName(url)).length();
	}

	
	 //删除SD卡或者手机的缓存图片和目�?
	
	public void deleteFile() {
		File dirFile = new File(getStorageDirectory());
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}

		dirFile.delete();
	}

	
	public String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

}