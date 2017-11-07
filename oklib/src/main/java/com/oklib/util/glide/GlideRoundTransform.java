//package com.oklib.util.glide;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.RectF;
//
//import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
//import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
//
///**
// * 创建时间：2016/7/20
// * 编写者：黄伟才
// * 功能描述：矩形圆角图片
// * 注意：宽高会等比拉伸
// */
//public class GlideRoundTransform extends BitmapTransformation {
//
//    private static float radius = 0f;
//
//    public GlideRoundTransform(Context context) {
//        this(context, 4);
//    }
//
//    //该构造方法可以动态改变角度大小 dp
//    public GlideRoundTransform(Context context, int dp) {
//        super(context);
//        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
//    }
//
//    @Override
//    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//        return roundCrop(pool, toTransform);
//    }
//
//    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
//        if (source == null) return null;
//
//        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);
//        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        return result;
//    }
//
//    @Override
//    public String getId() {
//        return getClass().getName() + Math.round(radius);
//    }
//}
//
//
