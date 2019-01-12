//package com.oklib.glideview.progress;
//
//import android.content.Context;
//
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.AppGlideModule;
//import com.bumptech.glide.okhttp3.OkHttpUrlLoader;
//
//import java.io.InputStream;
//
///**
//  * 时间：2017/8/2
//  * 作者：蓝天
//  * 描述：
//  */
//@GlideModule
//public class ProgressAppGlideModule extends AppGlideModule {
//
//   @Override
//   public void registerComponents(Context context, Registry registry) {
//       registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
//   }
//}