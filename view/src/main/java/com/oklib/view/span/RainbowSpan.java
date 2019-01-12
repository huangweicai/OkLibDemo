package com.oklib.view.span;

import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * 时间：2018/2/10
 * 作者：蓝天
 * 描述：彩虹样的Span，其实实现起来也是很简单的，主要是用到了Paint的Shader技术
 */

public class RainbowSpan extends CharacterStyle implements UpdateAppearance {
    private final int[] colors;

    public RainbowSpan(int[] colors) {
        this.colors = colors;
    }

    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(0, 0, 0, paint.getTextSize() * colors.length, colors, null,
                Shader.TileMode.MIRROR);
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }
}
