package com.oklib.view.stv;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * 创建时间：2017/7/11
 * 编写者：蓝天
 * 功能描述：
 */

public class RippleAdjuster extends SuperTextView.Adjuster {
    private static final float DEFAULT_RADIUS = 50;

    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private int rippleColor = Color.parseColor("#ce938d");
    private float x = -1;
    private float y = -1;
    private Paint paint;
    private float density;
    private float radius = DEFAULT_RADIUS;
    private RectF rectF = new RectF();
    private float velocity = 2f;



    public RippleAdjuster(int rippleColor) {
        this.rippleColor = rippleColor;
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(rippleColor);
    }


    @Override
    protected void adjust(SuperTextView v, Canvas canvas) {
        int width = v.getWidth();
        int height = v.getHeight();

        if (density == 0) {
            density = v.getResources().getDisplayMetrics().density;
        }
        if (x == -1) {
            x = width / 2;
        }
        if (y == -1) {
            x = height / 2;

        }
        if (radius < ((float) width) * 1.1) {
            radius = (radius + velocity);
        }
        rectF.setEmpty();
        rectF.set(0, 0, width, height);
        // 创建一个图层，在图层上演示图形混合后的效果
        int sc = canvas.saveLayer(0, 0, width, height, null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        paint.setColor(v.getSolid());
        canvas.drawRoundRect(rectF, height / 2, height / 2, paint);
        paint.setXfermode(xfermode);
        paint.setColor(rippleColor);
        canvas.drawCircle(x, y, radius * density, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    @Override
    public boolean onTouch(SuperTextView v, MotionEvent event) {
        int action = event.getAction();
        //LogUtils.e("action = " + action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                radius = DEFAULT_RADIUS;
                v.setAutoAdjust(true);
                v.startAnim();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                v.stopAnim();
                v.setAutoAdjust(false);
                //LogUtils.e("stopAnim()");
                break;
        }
        return true;
    }
}
