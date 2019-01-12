package com.oklib.view.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/**
 * 时间：2018/2/10
 * 作者：蓝天
 * 描述：加边框效果
 * FrameSpan实现给相应的字符序列添加边框的效果，整体思路其实比较简单。
 * 计算字符序列的宽度；
 * 根据计算的宽度、上下坐标、起始坐标绘制矩形；
 * 绘制文字
 */

public class FrameSpan extends ReplacementSpan {
    private final Paint mPaint;
    private int mWidth;

    public FrameSpan() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //return text with relative to the Paint
        mWidth = (int) paint.measureText(text, start, end);
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        //draw the frame with custom Paint
        canvas.drawRect(x, top, x + mWidth, bottom, mPaint);
        canvas.drawText(text, start, end, x, y, paint);
    }
}
