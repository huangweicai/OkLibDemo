package com.oklib.view.gimage.progress;

import android.content.Context;

import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

 /**
   * 时间：2017/8/2
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：
   */
@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }
}