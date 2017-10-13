package com.oklib.util.image_;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.oklib.R;


/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：
 */

public class LoaderOptions {
    protected int placeholderResId;
    protected int errorResId;
    protected boolean isCenterCrop = true;
    protected boolean isCenterInside;
    protected Bitmap.Config config;
    protected int targetWidth;
    protected int targetHeight;
    protected float bitmapAngle;
    private Builder builder;

    private LoaderOptions(){

    }

    private LoaderOptions(Builder builder) {
        this.builder = builder;
        this.placeholderResId = builder.placeholderResId;
        this.errorResId = builder.errorResId;
        this.isCenterCrop = builder.isCenterCrop;
        this.isCenterInside = builder.isCenterInside;
        this.config = builder.config;
        this.targetWidth = builder.targetWidth;
        this.targetHeight = builder.targetHeight;
        this.bitmapAngle = builder.bitmapAngle;
    }

    public static final class Builder {
        private int placeholderResId = R.mipmap.oklib_maotouying_icon;
        private int errorResId = R.mipmap.oklib_maotouying_icon;
        private boolean isCenterCrop;
        private boolean isCenterInside;
        private Bitmap.Config config = Bitmap.Config.RGB_565;
        private int targetWidth;
        private int targetHeight;
        private float bitmapAngle;

        public Builder(){
        }

        public Builder placeholder(@DrawableRes int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        public Builder error(@DrawableRes int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder centerCrop() {
            isCenterCrop = true;
            return this;
        }

        public Builder centerInside() {
            isCenterInside = true;
            return this;
        }

        public Builder config(Bitmap.Config config) {
            this.config = config;
            return this;
        }

        public Builder resize(int targetWidth, int targetHeight) {
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
            return this;
        }

        public Builder angle(float bitmapAngle) {
            this.bitmapAngle = bitmapAngle;
            return this;
        }

        public LoaderOptions build() {
            return new LoaderOptions(this);
        }
    }

}
