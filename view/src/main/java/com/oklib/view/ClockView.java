package com.oklib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

/**
 * 时间：2018/2/4
 * 作者：蓝天
 * 描述：时钟view
 */

public class ClockView extends View {
    /**
     * 时钟背景颜色
     */
    @ColorInt
    protected static final int CLOCK_BACKGROUND_COLOR = 0xFFF0F0F0;
    /**
     * 时钟圆环颜色
     */
    @ColorInt
    protected static final int CLOCK_RING_COLOR = 0xFFc9c9c9;
    /**
     * 字体颜色
     */
    @ColorInt
    protected static final int TEXT_COLOR = 0xFF141414;
    /**
     * 时钟和分钟的颜色
     */
    protected static final int HOUR_MINUTE_COLOR = 0xFF5B5B5B;
    /**
     * 秒钟的颜色
     */
    @ColorInt
    private static final int SECOND_COLOR = 0xFFB55050;
    @ColorInt
    private static final int CLOCK_SCALE_COLOR = 0xffc9c9c9;
    /**
     * 时钟最小尺寸
     */
    private static final int CLOCK_MIN_SIZE = 200;
    /**
     * 时钟及分钟的宽度
     */
    private static final int HOUR_MINUTE_WIDTH = 16;
    /**
     * 秒钟的宽度
     */
    private static final int SECOND_WIDTH = 8;
    /**
     * 时钟刻度的宽度
     */
    private static final int SCALE_WIDTH = 4;
    //每秒 秒针移动6°
    private static final int DEGREE = 6;
    /**
     * 时钟文本
     */
    private static final String[] CLOCK_TEXT = {"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    /**
     * 时
     */
    private float hour = 5;
    /**
     * 分
     */
    private float minute = 30;
    /**
     * 秒
     */
    private float second = 5;
    /**
     * 绘制时钟的Paint
     */
    private Paint hourPaint;
    /**
     * 绘制分钟的Paint
     */
    private Paint minutePaint;
    /**
     * 绘制秒钟的Paint
     */
    private Paint secondPaint;
    /**
     * 圆环的宽度
     */
    private int clockRingWidth = 10;
    /**
     * 时钟大小
     */
    private int clockSize;
    /**
     * 绘制时钟的Paint
     */
    private Paint clockPaint;
    /**
     * 绘制时钟圆环的Paint
     */
    private Paint clockRingPaint;
    /**
     * 时钟中心外部圆
     */
    private Paint clockCenterOuterCirclePaint;
    /**
     * 时钟中心内部圆
     */
    private Paint clockCenterInnerCirclePaint;
    /**
     * 绘制时钟刻度的Paint
     */
    private Paint clockScalePaint;
    /**
     * 绘制时钟文本的Paint
     */
    private Paint clockTextPaint;
    /**
     * 获取时间的日历工具
     */
    private Calendar calendar = null;

    public ClockView(Context context) {
        super(context);
        initView();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    protected void initView() {
        clockPaint = new Paint();
        clockPaint.setColor(CLOCK_BACKGROUND_COLOR);
        clockPaint.setAntiAlias(true);
        clockRingPaint = new Paint();
        clockRingPaint.setColor(CLOCK_RING_COLOR);
        clockRingPaint.setStrokeWidth(dp2px(clockRingWidth));
        clockRingPaint.setStyle(Paint.Style.STROKE);
        clockRingPaint.setAntiAlias(true);
        //添加阴影 0x80000000
        clockRingPaint.setShadowLayer(4, 2, 2, 0x80000000);
        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(HOUR_MINUTE_COLOR);
        hourPaint.setStrokeWidth(HOUR_MINUTE_WIDTH);
        //设置为圆角
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        //添加阴影
        hourPaint.setShadowLayer(4, 0, 0, 0x80000000);
        minutePaint = new Paint();
        minutePaint.setAntiAlias(true);
        minutePaint.setColor(HOUR_MINUTE_COLOR);
        minutePaint.setStrokeWidth(HOUR_MINUTE_WIDTH);
        //设置为圆角
        minutePaint.setStrokeCap(Paint.Cap.ROUND);
        //添加阴影
        minutePaint.setShadowLayer(4, 0, 0, 0x80000000);
        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setColor(SECOND_COLOR);
        secondPaint.setStrokeWidth(SECOND_WIDTH);
        //设置为圆角
        secondPaint.setStrokeCap(Paint.Cap.ROUND);
        //添加阴影
        secondPaint.setShadowLayer(4, 3, 0, 0x80000000);
        clockCenterOuterCirclePaint = new Paint();
        clockCenterOuterCirclePaint.setAntiAlias(true);
        clockCenterOuterCirclePaint.setColor(HOUR_MINUTE_COLOR);
        //添加阴影
        clockCenterOuterCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);
        clockCenterInnerCirclePaint = new Paint();
        clockCenterInnerCirclePaint.setAntiAlias(true);
        clockCenterInnerCirclePaint.setColor(SECOND_COLOR);
        //添加阴影
        clockCenterInnerCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);
        clockScalePaint = new Paint();
        clockScalePaint.setAntiAlias(true);
        clockScalePaint.setColor(CLOCK_SCALE_COLOR);
        //设置为圆角
        clockScalePaint.setStrokeCap(Paint.Cap.ROUND);
        clockScalePaint.setStrokeWidth(SCALE_WIDTH);
        clockTextPaint = new Paint();
        clockTextPaint.setAntiAlias(true);
        clockTextPaint.setStrokeWidth(1f);
        clockTextPaint.setColor(TEXT_COLOR);
        clockTextPaint.setTextSize(sp2px(13));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        clockSize = dp2px(CLOCK_MIN_SIZE);
        if (clockSize > width) {
            width = clockSize;
        } else {
            clockSize = width;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            clockSize = w;
        }
        int minSize = dp2px(CLOCK_MIN_SIZE);
        if (clockSize < minSize) {
            clockSize = minSize;
        }
    }

    private void getTime() {
        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        System.out.println(hour + ":" + minute + ":" + second);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getTime();
        canvas.translate(clockSize / 2, clockSize / 2);
        drawClock(canvas);
        drawClockRing(canvas);
        drawClockScale(canvas);
        drawClockScaleText(canvas);
        drawHourPointer(canvas);
        drawMinutePointer(canvas);
        drawCenterOuterCircle(canvas);
        drawSecondPointer(canvas, second * DEGREE);
        drawCenterInnerCircle(canvas);
        postInvalidateDelayed(1000);
    }

    /**
     * 画表盘背景
     *
     * @param canvas 画布
     */
    private void drawClock(Canvas canvas) {
        canvas.drawCircle(0, 0, clockSize / 2 - 4, clockPaint);
        canvas.save();
    }

    /**
     * 画表盘最外层圆环
     *
     * @param canvas 画布
     */
    private void drawClockRing(Canvas canvas) {
        canvas.save();
        float radius = clockSize / 2 - dp2px(clockRingWidth + 6) / 2;
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        clockRingPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, 0, 360, false, clockRingPaint);
        canvas.restore();
    }

    /**
     * 画时针
     *
     * @param canvas 画布
     */
    private void drawHourPointer(Canvas canvas) {
        int length = clockSize / 4;
        canvas.save();
        //这里没有算秒钟对时钟的影响
        float degree = hour * 5 * DEGREE + minute / 2f;
        canvas.rotate(degree, 0, 0);
        canvas.drawLine(0, 0, 0, -length, hourPaint);
        canvas.restore();
    }

    /**
     * 画分针
     *
     * @param canvas 画布
     */
    private void drawMinutePointer(Canvas canvas) {
        int length = clockSize / 3 - dp2px(2);
        canvas.save();
        float degree = minute * DEGREE + second / 10f;
        canvas.rotate(degree, 0, 0);
        canvas.drawLine(0, 0, 0, -length, minutePaint);
        canvas.restore();
    }

    /**
     * 画秒针
     *
     * @param canvas 画布
     */
    private void drawSecondPointer(Canvas canvas, float degrees) {
        int length = clockSize / 2 - dp2px(15);
        canvas.save();
        canvas.rotate(degrees);
        canvas.drawLine(0, length / 5, 0, -length * 4 / 5, secondPaint);
        canvas.restore();
    }

    /**
     * 绘制时钟刻度
     *
     * @param canvas
     */
    private void drawClockScale(Canvas canvas) {
        canvas.save();
        int startY = clockSize / 2 - dp2px(clockRingWidth + 6) / 2 - dp2px(clockRingWidth) / 2;
        int endY = startY - dp2px(5);
        int endY2 = startY - dp2px(10);
        //canvas.rotate(-180);
        for (int i = 0; i <= 360; i += DEGREE) {
            if (i % 5 == 0) {
                canvas.drawLine(0, startY, 0, endY2, clockScalePaint);
            } else {
                canvas.drawLine(0, startY, 0, endY, clockScalePaint);
            }
            canvas.rotate(DEGREE);
        }
        canvas.restore();
    }

    /**
     * 绘制时钟刻度文本
     *
     * @param canvas
     */
    private void drawClockScaleText(Canvas canvas) {
        canvas.save();
        //canvas.rotate(-180f);
        float dis = clockTextPaint.measureText(CLOCK_TEXT[1]) / 2;
        Paint.FontMetrics fontMetrics = clockTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        float radius = clockSize / 2 - dp2px(clockRingWidth + 6) / 2 - dp2px(clockRingWidth) / 2 - dp2px(10) - fontHeight / 2;
        for (int i = 0; i < CLOCK_TEXT.length; i++) {
            float x = (float) (Math.sin(Math.PI - Math.PI / 6 * i) * radius - dis);
            if (i == 0) {
                x -= dis;
            }
            float y = (float) (Math.cos(Math.PI - Math.PI / 6 * i) * radius + dis);
            canvas.drawText(CLOCK_TEXT[i], x, y, clockTextPaint);
        }
        canvas.restore();
    }

    /**
     * 画中心黑圆
     *
     * @param canvas 画布
     */
    private void drawCenterOuterCircle(Canvas canvas) {
        int radius = clockSize / 20;
        canvas.save();
        canvas.drawCircle(0, 0, radius, clockCenterOuterCirclePaint);
        canvas.restore();
    }

    /**
     * 红色中心圆
     *
     * @param canvas 画布
     */
    private void drawCenterInnerCircle(Canvas canvas) {
        int radius = clockSize / 40;
        canvas.save();
        canvas.drawCircle(0, 0, radius, clockCenterInnerCirclePaint);
        canvas.restore();
    }

    /**
     * 将 dp 转换为 px
     *
     * @param dp 需转换数
     * @return 返回转换结果
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
