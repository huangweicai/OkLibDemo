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
 * 描述：字体多色渐变动画效果
 * 其实结合上面的RainbowSpan和AnimateForegroundColorSpan的例子便可以实现AnimatedRainbowSpan。
 * 实现思路：通过ObjectAnimator动画调整RainbowSpan中矩阵的平移，从而实现动画彩虹的效果。
 */

public class AnimatedColorSpan extends CharacterStyle implements UpdateAppearance {
    private final int[] colors;
    private Shader shader = null;
    private Matrix matrix = new Matrix();
    private float translateXPercentage = 0;

    public AnimatedColorSpan(int[] colors) {
        this.colors = colors;
    }

    public void setTranslateXPercentage(float percentage) {
        translateXPercentage = percentage;
    }

    public float getTranslateXPercentage() {
        return translateXPercentage;
    }

    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL);
        float width = paint.getTextSize() * colors.length;
        if (shader == null) {
            shader = new LinearGradient(0, 0, 0, width, colors, null,
                    Shader.TileMode.MIRROR);
        }
        matrix.reset();
        matrix.setRotate(90);
        matrix.postTranslate(width * translateXPercentage, 0);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }
}
