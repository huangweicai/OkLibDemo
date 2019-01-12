package com.oklib.camera.z_help;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.oklib.camera.AppPaths;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileUtil {

	private static final int TYPE_IMAGE = 1;
	private static final int TYPE_ADUIO = 2;
	private static final int TYPE_VIDEO = 3;

	/**
	 * {@link #TYPE_IMAGE}<br/>
	 * {@link #TYPE_ADUIO}<br/>
	 * {@link #TYPE_VIDEO} <br/>
	 * 
	 * @param type
	 * @return
	 */
	private static String getPublicFilePath(int type) {
		String fileDir = null;
		String fileSuffix = null;
		switch (type) {
		case TYPE_ADUIO:
			fileDir = AppPaths.getInstance().mExternalFilesVoicesDir;
			fileSuffix = ".mp3";
			break;
		case TYPE_VIDEO:
			fileDir = AppPaths.getInstance().mExternalFilesVideosDir;
			fileSuffix = ".mp4";
			break;
		case TYPE_IMAGE:
			fileDir = AppPaths.getInstance().mExternalFilesPicturesDir;
			fileSuffix = ".jpg";
			break;
		}
		if (fileDir == null) {
			return null;
		}
		File file = new File(fileDir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return null;
			}
		}
		return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
	}

	/**
	 * {@link #TYPE_ADUIO}<br/>
	 * {@link #TYPE_VIDEO} <br/>
	 *
	 * @param type
	 * @return
	 */
	private static String getPrivateFilePath(int type, String userId) {
		String fileDir = null;
		String fileSuffix = null;
		switch (type) {
		case TYPE_ADUIO:
			fileDir = AppPaths.getInstance().mExternalFilesRootDir + File.separator + userId + File.separator + Environment.DIRECTORY_MUSIC;
			fileSuffix = ".mp3";
			break;
		case TYPE_VIDEO:
			fileDir = AppPaths.getInstance().mExternalFilesRootDir + File.separator + userId + File.separator + Environment.DIRECTORY_MOVIES;
			fileSuffix = ".mp4";
			break;
		}
		if (fileDir == null) {
			return null;
		}
		File file = new File(fileDir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return null;
			}
		}
		return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
	}

	public static String getRandomImageFilePath() {
		return getPublicFilePath(TYPE_IMAGE);
	}

//	public static String getRandomAudioFilePath() {
//		User user = MyApplication.getInstance().mLoginUser;
//		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
//			return getPrivateFilePath(TYPE_ADUIO, user.getUserId());
//		} else {
//			return getPublicFilePath(TYPE_ADUIO);
//		}
//	}

//	public static String getRandomAudioAmrFilePath() {
//		User user = MyApplication.getInstance().mLoginUser;
//		String filePath = null;
//		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
//			filePath = getPrivateFilePath(TYPE_ADUIO, user.getUserId());
//		} else {
//			filePath = getPublicFilePath(TYPE_ADUIO);
//		}
//		if (!TextUtils.isEmpty(filePath)) {
//			return filePath.replace(".mp3", ".amr");
//		} else {
//			return null;
//		}
//	}

//	public static String getRandomVideoFilePath() {
//		User user = MyApplication.getInstance().mLoginUser;
//		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
//			return getPrivateFilePath(TYPE_VIDEO, user.getUserId());
//		} else {
//			return getPublicFilePath(TYPE_VIDEO);
//		}
//	}



	/**
	 * 在Android 编程中经常会用到uri转化为文件路径
	 * 下面是4.4后通过Uri获取路径以及文件名一种方法
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	/**
	 * 根据图片名称把bitmap保存到本地
	 *
	 * @param bm
	 * @param savePath
	 * @param picName
	 * @throws IOException
	 */
	public static void saveBitmapToPath(Bitmap bm, String savePath, String picName) throws IOException {
		Log.e("", "保存图片");
		if (bm == null){
			return;
		}

		File file = new File(savePath);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (!file.exists() || !file.isDirectory()) {
				file.mkdir();
			}
			String suffixStr = null;
			if (picName.length() >= 4 && picName.lastIndexOf('.') != -1) {
				suffixStr = picName.substring(picName.lastIndexOf('.'));
				if (!".jpg".equals(suffixStr))
					suffixStr = null;
			}

			File f = new File(savePath, picName + (suffixStr == null ? ".jpg" : ""));
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////

	public static void createFileDir(String fileDir) {
		File fd = new File(fileDir);
		if (!fd.exists()) {
			fd.mkdirs();
		}
	}

	/**
	 * 
	 * @param fullName
	 */
	public static void delFile(String fullName) {
		File file = new File(fullName);
		if (file.exists()) {
			if (file.isFile()) {
				try {
					file.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 /sdcard/data/
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			System.out.println(path + tempList[i]);
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]); // 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]); // 再删除空文件夹
			}
		}
	}

	/**
	 * 删除文件夹
	 * 
	 *            String 文件夹路径及名称 如/sdcard/data/
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}

	/*
	* 输入流转字符串
	*
	* */
	public static String inputStream2String(InputStream is)  throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i=-1;
		while((i = is.read() ) != -1){
			baos.write(i);
		}
		return baos.toString();
	}


}
