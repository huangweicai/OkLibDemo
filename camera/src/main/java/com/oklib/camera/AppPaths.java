package com.oklib.camera;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 * 创建时间：2017/7/10
 * 编写者：蓝天
 * 功能描述：初始化项目目录（相册需要）
 */

public class AppPaths {
    //手机外部存储 files 卸载app时，会自动删除文件
    public String mExternalFilesRootDir;//根目录
    public String mExternalFilesPicturesDir;//图片
    public String mExternalFilesVoicesDir;//音频
    public String mExternalFilesVideosDir;//视频
    public String mExternalFilesDownloadDir;//app下载相关数据
    //手机外部存储 caches
    public String mExternalCacheDir;//外部临时缓存文件

    //手机内部存储
    public String mInternalFilesDir;//内部缓存文件目录
    public String mInternalCacheDir;//内部临时缓存文件

    /**
     * 初始化项目目录
     * @param application
     */
    public void initAppDir(Application application) {
        try {
            File file = application.getExternalFilesDir(null);
            if (!file.exists()) {
                file.mkdirs();
            }
            // /storage/emulated/0/Android/data/packageName/files
            AppPaths.getInstance().mExternalFilesRootDir = file.getAbsolutePath();//file目录下，下面在文件目录下还建子目录

            // /storage/emulated/0/Android/data/packageName/files/Pictures
            file = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                file.mkdirs();
            }
            AppPaths.getInstance().mExternalFilesPicturesDir = file.getAbsolutePath();

            // /storage/emulated/0/Android/data/packageName/files/Music
            file = application.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            if (!file.exists()) {
                file.mkdirs();
            }
            AppPaths.getInstance().mExternalFilesVoicesDir = file.getAbsolutePath();

            // /storage/emulated/0/Android/data/packageName/files/Movies
            file = application.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            if (!file.exists()) {
                file.mkdirs();
            }
            AppPaths.getInstance().mExternalFilesVideosDir = file.getAbsolutePath();

            // /storage/emulated/0/Android/data/packageName/files/Download
            file = application.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            if (!file.exists()) {
                file.mkdirs();
            }
            AppPaths.getInstance().mExternalFilesDownloadDir = file.getAbsolutePath();


            // /storage/emulated/0/Android/data/packageName/cache
            file = application.getExternalCacheDir();
            if (!file.exists()) {
                file.mkdirs();
            }
            AppPaths.getInstance().mExternalCacheDir = file.getAbsolutePath();

            // /data/data/packageName/cache
            file = application.getCacheDir();
            if (!file.exists()) {
                file.mkdirs();
            }
            AppPaths.getInstance().mInternalCacheDir = file.getAbsolutePath();

            // /data/data/packageName/files
            file = application.getFilesDir();
            if (!file.exists()) {
                file.mkdirs();
            }
            AppPaths.getInstance().mInternalFilesDir = file.getAbsolutePath();

        }catch (Exception e){
        }

    }

    private AppPaths() {
    }

    private static class SingletonFactory {
        private static AppPaths instance = new AppPaths();
    }

    public static AppPaths getInstance() {
        return SingletonFactory.instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }
}
