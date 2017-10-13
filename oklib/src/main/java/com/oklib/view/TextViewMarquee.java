package com.oklib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 时间：2017/9/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：跑马灯view
 */

@SuppressLint("AppCompatCustomView")
public class TextViewMarquee extends TextView {
    public TextViewMarquee(Context context) {
        this(context, (AttributeSet)null);
    }

    public TextViewMarquee(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setSingleLine();
        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.setMarqueeRepeatLimit(-1);
    }

    public TextViewMarquee(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setSingleLine();
        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.setMarqueeRepeatLimit(-1);
    }

    public boolean isFocused() {
        return true;
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    public void onWindowFocusChanged(boolean focused) {
        if(focused) {
            super.onWindowFocusChanged(focused);
        }
    }
}
