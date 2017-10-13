package com.oklib.view.stv;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.oklib.R;

/**
 * 创建时间：2017/7/11
 * 编写者：黄伟才
 * 功能描述：
 */

public class OpportunityDemoAdjuster extends SuperTextView.Adjuster {

    private float density;
    private Paint paint;


    public OpportunityDemoAdjuster() {
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }



    @Override
    protected void adjust(SuperTextView v, Canvas canvas) {
        int width = v.getWidth();
        int height = v.getHeight();
        if (density == 0) {
            density = v.getResources().getDisplayMetrics().density;
        }
        paint.setColor(v.getResources().getColor(R.color.oklib_frame_black));
        canvas.drawCircle(width / 2, height / 2, 30 * density, paint);
    }
}
