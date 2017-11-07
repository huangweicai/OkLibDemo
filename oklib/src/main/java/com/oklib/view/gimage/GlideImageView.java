package com.oklib.view.gimage;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.bumptech.glide.request.RequestOptions;
import com.oklib.view.gimage.progress.OnGlideImageViewListener;
import com.oklib.view.gimage.progress.OnProgressListener;

 /**
   * 时间：2017/8/2
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：
   */
public class GlideImageView extends ShapeImageView {

    private GlideImageLoader mImageLoader;

    public GlideImageView(Context context) {
        this(context, null);
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mImageLoader = GlideImageLoader.create(this);
    }

    public GlideImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = GlideImageLoader.create(this);
        }
        return mImageLoader;
    }

    public String getImageUrl() {
        return getImageLoader().getImageUrl();
    }

    public RequestOptions requestOptions(int placeholderResId) {
        return getImageLoader().requestOptions(placeholderResId);
    }

    public RequestOptions circleRequestOptions(int placeholderResId) {
        return getImageLoader().circleRequestOptions(placeholderResId);
    }

    public GlideImageView load(int resId, RequestOptions options) {
        getImageLoader().load(resId, options);
        return this;
    }

    public GlideImageView load(Uri uri, RequestOptions options) {
        getImageLoader().load(uri, options);
        return this;
    }

    public GlideImageView load(String url, RequestOptions options) {
        getImageLoader().load(url, options);
        return this;
    }

    public GlideImageView loadImage(String url, int placeholderResId) {
        getImageLoader().loadImage(url, placeholderResId);
        return this;
    }

    public GlideImageView loadLocalImage(@DrawableRes int resId, int placeholderResId) {
        getImageLoader().loadLocalImage(resId, placeholderResId);
        return this;
    }

    public GlideImageView loadLocalImage(String localPath, int placeholderResId) {
        getImageLoader().loadLocalImage(localPath, placeholderResId);
        return this;
    }

    public GlideImageView loadCircleImage(String url, int placeholderResId) {
        setShapeType(ShapeType.CIRCLE);
        getImageLoader().loadCircleImage(url, placeholderResId);
        return this;
    }

    public GlideImageView loadLocalCircleImage(int resId, int placeholderResId) {
        setShapeType(ShapeType.CIRCLE);
        getImageLoader().loadLocalCircleImage(resId, placeholderResId);
        return this;
    }

    public GlideImageView loadLocalCircleImage(String localPath, int placeholderResId) {
        setShapeType(ShapeType.CIRCLE);
        getImageLoader().loadLocalCircleImage(localPath, placeholderResId);
        return this;
    }

    //--------------start---------------------
    //矩形高斯模糊图（原图）
    public GlideImageView loadBlurImage(String url, int placeholderResId) {
        getImageLoader().loadBlurImage(url, placeholderResId);
        return this;
    }
    //设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”
    public GlideImageView loadBlurImage(String url, int placeholderResId, int radius, int sampling) {
        getImageLoader().loadBlurImage(url, placeholderResId, radius, sampling);
        return this;
    }

    //圆形高斯模糊图（经过圆形处理，暂时支持url形式，本地资源直接使用load方法自定义options即可）
    public GlideImageView loadBlurCircleImage(String url, int placeholderResId) {
        setShapeType(ShapeType.CIRCLE);
        getImageLoader().loadBlurImage(url, placeholderResId);
        return this;
    }
    //设置模糊度,默认”25"   图片缩放比例,默认“1”
    public GlideImageView loadBlurCircleImage(String url, int placeholderResId, int radius, int sampling) {
        setShapeType(ShapeType.CIRCLE);
        getImageLoader().loadBlurImage(url, placeholderResId, radius, sampling);
        return this;
    }
    //--------------end---------------------

    public GlideImageView listener(OnGlideImageViewListener listener) {
        getImageLoader().setOnGlideImageViewListener(getImageUrl(), listener);
        return this;
    }

    public GlideImageView listener(OnProgressListener listener) {
        getImageLoader().setOnProgressListener(getImageUrl(), listener);
        return this;
    }

}
