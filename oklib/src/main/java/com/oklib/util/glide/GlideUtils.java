//package com.oklib.util.glide;
//
//import android.content.Context;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//
//import jp.wasabeef.glide.transformations.BlurTransformation;
////import com.oklib.util.glide.blur.BlurTransformation;
//
///**
// * 创建时间：2016/7/27
// * 编写者：黄伟才
// * 功能描述：Glide使用工具类
// */
//public class GlideUtils {
//
//    /**
//     * 加载原图，不做处理
//     * @param context
//     * @param iv
//     * @param url
//     * @param placeholder
//     */
//    public void loadImage(Context context, ImageView iv, String url, int placeholder) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(url)
//                .placeholder(placeholder)
//                .crossFade()
//                .into(iv);
//    }
//
//    public void loadImage(Context context, ImageView iv, String url) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(url)
//                .crossFade()
//                .into(iv);
//    }
//
//    /**
//     * 加载圆形图片
//     *
//     * @param context     上下文
//     * @param iv          加载控件
//     * @param url         图片url
//     * @param placeholder 占位图
//     */
//    public void loadCircleImage(Context context, ImageView iv, String url, int placeholder) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(url)
//                .placeholder(placeholder)
//                .crossFade()
//                .transform(new GlideCircleTransform(context))
//                .into(iv);
//    }
//
//    public void loadCircleImage(Context context, ImageView iv, String url) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(url)
//                .crossFade()
//                .transform(new GlideCircleTransform(context))
//                .into(iv);
//    }
//
//
//    public void loadCircleImage(Context context, ImageView iv, int resId, int placeholder) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(resId)
//                .placeholder(placeholder)
//                .crossFade()
//                .transform(new GlideCircleTransform(context))
//                .into(iv);
//    }
//
//    public void loadCircleImage(Context context, ImageView iv, int resId) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(resId)
//                .crossFade()
//                .transform(new GlideCircleTransform(context))
//                .into(iv);
//    }
//
//    public void loadRoundImage(Context context, ImageView iv, int resId, int placeholder) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(resId)
//                .placeholder(placeholder)
//                .crossFade()
//                .transform(new GlideRoundTransform(context))
//                .into(iv);
//    }
//
//    public void loadRoundImage(Context context, ImageView iv, int resId) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(resId)
//                .crossFade()
//                .transform(new GlideRoundTransform(context))
//                .into(iv);
//    }
//
//
//    /**
//     * 加载圆角矩形图片
//     *
//     * @param context     上下文
//     * @param iv          加载控件
//     * @param url         图片url
//     * @param placeholder 占位图
//     */
//    public void loadRoundImage(Context context, ImageView iv, String url, int placeholder) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(url)
//                .placeholder(placeholder)
//                .crossFade()
//                .transform(new GlideRoundTransform(context))
//                .into(iv);
//    }
//
//    public void loadRoundImage(Context context, ImageView iv, String url) {
//        Glide
//                .with(context.getApplicationContext())
//                .load(url)
//                .crossFade()
//                .transform(new GlideRoundTransform(context))
//                .into(iv);
//    }
//
//    public void loadBlurImage(Context context, ImageView iv, String url) {
//        Glide.with(context)
//                .load(url)
////                .placeholder(R.drawable.loading)
////                .error(R.drawable.failed)
//                .crossFade(1000)
//                .bitmapTransform(new BlurTransformation(context,23,4))//“23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
//                .into(iv);
//    }
//
////            Glide.with(this).load("https://ws1.sinaimg.cn/large/006tNbRwly1ffs6l68mx3j30ge0gen08.jpg")
////                .bitmapTransform(new BlurTransformation(this, 30), new CropCircleTransformation(this))
////            .into((ImageView) findViewById(R.id.img1));
////
////        Glide.with(this).load("https://ws1.sinaimg.cn/large/006tNbRwly1ffs6l68mx3j30ge0gen08.jpg")
////                .bitmapTransform(new CropCircleTransformation(this))
////            .into((ImageView) findViewById(R.id.img2));
////
////        Glide.with(this).load("https://ws1.sinaimg.cn/large/006tNbRwly1ffs6l68mx3j30ge0gen08.jpg")
////                .bitmapTransform(new CropSquareTransformation(this))
////            .into((ImageView) findViewById(R.id.img3));
////
////        Glide.with(this).load("https://ws1.sinaimg.cn/large/006tNbRwly1ffs6l68mx3j30ge0gen08.jpg")
////                .bitmapTransform(new ColorFilterTransformation(this, 0x80dfdfdf))
////            .into((ImageView) findViewById(R.id.img4));
//
//
//
//    //销毁activity时暂停glide请求
//    public void pauseRequests(Context context) {
//        Glide.with(context).pauseRequests();
//    }
//
//    private static GlideUtils glideTools;
//
//
//    private GlideUtils() {
//    }
//
//    private static class SingletonFactory {
//        private static GlideUtils instance = new GlideUtils();
//    }
//
//    static GlideUtils getInstance() {
//        return SingletonFactory.instance;
//    }
//
//    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
//    public Object readResolve() {
//        return getInstance();
//    }
//
//}
