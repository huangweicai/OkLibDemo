package com.oklib.view.loadview.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack on 2015/10/19.
 */
public class LineScaleIndicator extends BaseIndicatorController {
    public static final float SCALE = 1.0f;

    float[] scaleYFloats = new float[60];

    {
        for (int i = 0; i < scaleYFloats.length; i++) {
            scaleYFloats[i] = SCALE;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float translateX = getWidth() / 120;
        float translateY = getHeight() / 2;
        for (int i = 0; i < 60; i++) {
            canvas.save();
            canvas.translate( i * 2 * translateX, translateY);
            canvas.scale(SCALE, scaleYFloats[i]);
            RectF rectF = new RectF(-translateX / 2, getTop(i), translateX / 2, getBottom(i));
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.restore();
        }
    }

    private int getBottom(int i) {
        return i < 30 ? i * getHeight() / 60 :  getHeight() - i * getHeight() / 60;
    }

    private int getTop(int i) {
        return i < 30 ? -i * getHeight() / 60 : i * getHeight() / 60 - getHeight();
    }

    Random r = new Random();

    @Override
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<Animator>();
        for (int i = 0; i < 60; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);
            scaleAnim.setDuration(500);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(r.nextInt(500) + 100);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleYFloats[index] = (Float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }

}
