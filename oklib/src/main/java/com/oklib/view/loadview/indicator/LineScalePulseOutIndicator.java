package com.oklib.view.loadview.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack on 2015/10/19.
 */
public class LineScalePulseOutIndicator extends LineScaleIndicator {
    Random r = new Random();

    @Override
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<Animator>();
        for (int i = 0; i < 60; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);;
            scaleAnim.setDuration(500);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(r.nextInt(500) + 100L);
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
