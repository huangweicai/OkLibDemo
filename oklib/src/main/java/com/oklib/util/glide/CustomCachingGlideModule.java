//package com.oklib.util.glide;
//
//import android.content.Context;
//import android.os.Environment;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
//import com.bumptech.glide.module.GlideModule;
//
//import java.io.File;
//
///**
// * 创建时间：2016/8/30
// * 编写者：黄伟才
// * 功能描述：延迟配置Glide，设置Glide缓存目录
// */
//public class CustomCachingGlideModule implements GlideModule {
//
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        //设置缓存大小
//        int cacheSize100MegaBytes = 524288000;//500MB
//
//        //缓存路径
//        //注意：这里暂时同图片选择存放在同一目录
//        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String mPicturesDir = file.getAbsolutePath();
//        //设置路径方法
//        builder.setDiskCache(
//                new DiskLruCacheFactory(mPicturesDir, cacheSize100MegaBytes)
//        );
//
//
//        //设置缓存路径的子文件夹
//        // In case you want to specify a cache sub folder (i.e. "glidecache"):
//        //builder.setDiskCache(
//        //    new DiskLruCacheFactory(downloadDirectoryPath, "glidecache", cacheSize100MegaBytes )
//        //);
//
//        //-----------其他设置-----------
//        //缓存在应用的私有目录
//        //builder.setDiskCache(
//        //        new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)
//        //);
//
//        //缓存在应用的公有目录
//        //builder.setDiskCache(
//        //new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide) {
//        // nothing to do here
//    }
//}
