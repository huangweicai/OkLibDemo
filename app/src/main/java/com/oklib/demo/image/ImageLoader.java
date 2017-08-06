package com.oklib.demo.image;

import android.view.View;

import com.squareup.picasso.Callback;

import java.io.File;

/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/oklib
 * 描述：图片管理类，提供对外接口。
 * 静态代理模式，开发者只需要关心ImageLoader + LoaderOptions
 */

public class ImageLoader implements ILoaderProxy {
    private static ILoaderProxy sLoader;
    private static volatile ImageLoader sInstance;

    private ImageLoader() {
        sLoader = new PicassoLoader();
    }

    //单例模式
    public static ImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    //若切换其它默认图片加载框架，可以实现一键替换
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }

    //可以随时替换图片加载框架
    public void setImageLoader(ILoaderProxy loader) {
        if (loader != null) {
            sLoader = loader;
        }
    }

    @Override
    public void loadImage(View view, String path, LoaderOptions options) {
        sLoader.loadImage(view, path, options);
    }

    @Override
    public void loadImage(View view, int drawable, LoaderOptions options) {
        sLoader.loadImage(view, drawable, options);
    }

    @Override
    public void loadImage(View view, File file, LoaderOptions options) {
        sLoader.loadImage(view, file, options);
    }

    @Override
    public void saveImage(String url, File destFile, Callback callback) {
        sLoader.saveImage(url, destFile, callback);
    }

    @Override
    public void clearMemoryCache() {
        sLoader.clearMemoryCache();
    }

    @Override
    public void clearDiskCache() {
        sLoader.clearDiskCache();
    }

}
