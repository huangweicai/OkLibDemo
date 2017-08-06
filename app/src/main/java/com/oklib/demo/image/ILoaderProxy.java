package com.oklib.demo.image;

import android.view.View;

import com.squareup.picasso.Callback;

import java.io.File;

/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：
 */

public interface ILoaderProxy {
    void loadImage(View view, String path, LoaderOptions options);

    void loadImage(View view, int drawable, LoaderOptions options);

    void loadImage(View view, File file, LoaderOptions options);

    /**
     * 保存图片到本地相册
     * @param url
     * @param destFile
     * @param callback
     */
    void saveImage(String url, File destFile, Callback callback);

    /**
     * 清理内存缓存
     */
    void clearMemoryCache();

    /**
     * 清理磁盘缓存
     */
    void clearDiskCache();
}
