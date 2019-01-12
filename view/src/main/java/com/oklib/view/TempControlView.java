package com.oklib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 温度控制view
 */
public class TempControlView extends View {

    // 控件宽
    private int width;
    // 控件高
    private int height;
    // 刻度盘半径
    private int dialRadius;
    // 圆弧半径
    private int arcRadius;
    // 刻度高
    private int scaleHeight = dp2px(10);
    // 刻度盘画笔
    private Paint dialPaint;
    // 圆弧画笔
    private Paint arcPaint;
    // 标题画笔
    private Paint titlePaint;
    // 温度标识画笔
    private Paint tempFlagPaint;
    // 旋转按钮画笔
    private Paint buttonPaint;
    // 温度显示画笔
    private Paint tempPaint;
    // 文本提示
    private String title = "最高温度设置";
    // 温度
    private int temperature = 15;
    // 最低温度
    private int minTemp = 15;
    // 最高温度
    private int maxTemp = 30;
    // 四格代表温度1度
    private int angleRate = 4;
    // 每格的角度
    private float angleOne = (float) 270 / (maxTemp - minTemp) / angleRate;
    // 按钮图片
    private Bitmap buttonImage = BitmapFactory.decodeResource(getResources(),
            R.drawable.btn_rotate);
    // 按钮图片阴影
    private Bitmap buttonImageShadow = BitmapFactory.decodeResource(getResources(),
            R.drawable.btn_rotate_shadow);
    // 抗锯齿
    private PaintFlagsDrawFilter paintFlagsDrawFilter;
    // 温度改变监听
    private OnTempChangeListener onTempChangeListener;
    // 控件点击监听
    private OnClickListener onClickListener;

    // 以下为旋转按钮相关

    // 当前按钮旋转的角度
    private float rotateAngle;
    // 当前的角度
    private float currentAngle;

    public TempControlView(Context context) {
        this(context, null);
    }

    public TempControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TempControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dialPaint = new Paint();
        dialPaint.setAntiAlias(true);
        dialPaint.setStrokeWidth(dp2px(2));
        dialPaint.setStyle(Paint.Style.STROKE);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(Color.parseColor("#3CB7EA"));
        arcPaint.setStrokeWidth(dp2px(2));
        arcPaint.setStyle(Paint.Style.STROKE);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(sp2px(15));
        titlePaint.setColor(Color.parseColor("#3B434E"));
        titlePaint.setStyle(Paint.Style.STROKE);

        tempFlagPaint = new Paint();
        tempFlagPaint.setAntiAlias(true);
        tempFlagPaint.setTextSize(sp2px(25));
        tempFlagPaint.setColor(Color.parseColor("#E4A07E"));
        tempFlagPaint.setStyle(Paint.Style.STROKE);

        buttonPaint = new Paint();
        tempFlagPaint.setAntiAlias(true);
        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        tempPaint = new Paint();
        tempPaint.setAntiAlias(true);
        tempPaint.setTextSize(sp2px(60));
        tempPaint.setColor(Color.parseColor("#E27A3F"));
        tempPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 控件宽、高
        width = height = Math.min(h, w);
        // 刻度盘半径
        dialRadius = width / 2 - dp2px(20);
        // 圆弧半径
        arcRadius = dialRadius - dp2px(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        drawArc(canvas);
        drawText(canvas);
        drawButton(canvas);
        drawTemp(canvas);
    }

    /**
     * 绘制刻度盘
     *
     * @param canvas 画布
     */
    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        // 逆时针旋转135-2度
        canvas.rotate(-133);
        dialPaint.setColor(Color.parseColor("#3CB7EA"));
        for (int i = 0; i < angleRate * (maxTemp - minTemp); i++) {
            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
            canvas.rotate(angleOne);
        }

        canvas.rotate(90);
        dialPaint.setColor(Color.parseColor("#E37364"));
        for (int i = 0; i < (temperature - minTemp) * angleRate; i++) {
            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
            canvas.rotate(angleOne);
        }
        canvas.restore();
    }

    /**
     * 绘制刻度盘下的圆弧
     *
     * @param canvas 画布
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 0, 265, false, arcPaint);
        canvas.restore();
    }

    /**
     * 绘制标题与温度标识
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        canvas.save();

        // 绘制标题
        float titleWidth = titlePaint.measureText(title);
        canvas.drawText(title, (width - titleWidth) / 2, dialRadius * 2 + dp2px(15), titlePaint);

        // 绘制最小温度标识
        // 最小温度如果小于10，显示为0x
        String minTempFlag = minTemp < 10 ? "0" + minTemp : minTemp + "";
        float tempFlagWidth = titlePaint.measureText(maxTemp + "");
        canvas.rotate(55, width / 2, height / 2);
        canvas.drawText(minTempFlag, (width - tempFlagWidth) / 2, height + dp2px(5), tempFlagPaint);

        // 绘制最大温度标识
        canvas.rotate(-105, width / 2, height / 2);
        canvas.drawText(maxTemp + "", (width - tempFlagWidth) / 2, height + dp2px(5), tempFlagPaint);
        canvas.restore();
    }

    /**
     * 绘制旋转按钮
     *
     * @param canvas 画布
     */
    private void drawButton(Canvas canvas) {
        // 按钮宽高
        int buttonWidth = buttonImage.getWidth();
        int buttonHeight = buttonImage.getHeight();
        // 按钮阴影宽高
        int buttonShadowWidth = buttonImageShadow.getWidth();
        int buttonShadowHeight = buttonImageShadow.getHeight();

        // 绘制按钮阴影
        canvas.drawBitmap(buttonImageShadow, (width - buttonShadowWidth) / 2,
                (height - buttonShadowHeight) / 2, buttonPaint);

        Matrix matrix = new Matrix();
        // 设置按钮位置
        matrix.setTranslate(buttonWidth / 2, buttonHeight / 2);
        // 设置旋转角度
        matrix.preRotate(45 + rotateAngle);
        // 按钮位置还原，此时按钮位置在左上角
        matrix.preTranslate(-buttonWidth / 2, -buttonHeight / 2);
        // 将按钮移到中心位置
        matrix.postTranslate((width - buttonWidth) / 2, (height - buttonHeight) / 2);

        //设置抗锯齿
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawBitmap(buttonImage, matrix, buttonPaint);
    }

    /**
     * 绘制温度
     *
     * @param canvas 画布
     */
    private void drawTemp(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);

        float tempWidth = tempPaint.measureText(temperature + "");
        float tempHeight = (tempPaint.ascent() + tempPaint.descent()) / 2;
        canvas.drawText(temperature + "°", -tempWidth / 2 - dp2px(5), -tempHeight, tempPaint);
        canvas.restore();
    }

    private boolean isDown;
    private boolean isMove;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                float downX = event.getX();
                float downY = event.getY();
                currentAngle = calcAngle(downX, downY);
                break;

            case MotionEvent.ACTION_MOVE:
                isMove = true;
                float targetX;
                float targetY;
                downX = targetX = event.getX();
                downY = targetY = event.getY();
                float angle = calcAngle(targetX, targetY);

                // 滑过的角度增量
                float angleIncreased = angle - currentAngle;

                // 防止越界
                if (angleIncreased < -270) {
                    angleIncreased = angleIncreased + 360;
                } else if (angleIncreased > 270) {
                    angleIncreased = angleIncreased - 360;
                }

                IncreaseAngle(angleIncreased);
                currentAngle = angle;
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (isDown) {
                    if (isMove) {
                        // 纠正指针位置
                        rotateAngle = (float) ((temperature - minTemp) * angleRate * angleOne);
                        invalidate();
                        // 回调温度改变监听
                        if (onTempChangeListener != null) {
                            onTempChangeListener.change(temperature);
                        }
                        isMove = false;
                    } else {
                        // 点击事件
                        if (onClickListener != null) {
                            onClickListener.onClick(temperature);
                        }
                    }
                    isDown = false;
                }
                break;
            }
        }
        return true;
    }

    /**
     * 以按钮圆心为坐标圆点，建立坐标系，求出(targetX, targetY)坐标与x轴的夹角
     *
     * @param targetX x坐标
     * @param targetY y坐标
     * @return (targetX, targetY)坐标与x轴的夹角
     */
    private float calcAngle(float targetX, float targetY) {
        float x = targetX - width / 2;
        float y = targetY - height / 2;
        double radian;

        if (x != 0) {
            float tan = Math.abs(y / x);
            if (x > 0) {
                if (y >= 0) {
                    radian = Math.atan(tan);
                } else {
                    radian = 2 * Math.PI - Math.atan(tan);
                }
            } else {
                if (y >= 0) {
                    radian = Math.PI - Math.atan(tan);
                } else {
                    radian = Math.PI + Math.atan(tan);
                }
            }
        } else {
            if (y > 0) {
                radian = Math.PI / 2;
            } else {
                radian = -Math.PI / 2;
            }
        }
        return (float) ((radian * 180) / Math.PI);
    }

    /**
     * 增加旋转角度
     *
     * @param angle 增加的角度
     */
    private void IncreaseAngle(float angle) {
        rotateAngle += angle;
        if (rotateAngle < 0) {
            rotateAngle = 0;
        } else if (rotateAngle > 270) {
            rotateAngle = 270;
        }
        // 加上0.5是为了取整时四舍五入
        temperature = (int) ((rotateAngle / angleOne) / angleRate + 0.5) + minTemp;
    }

    /**
     * 设置几格代表1度，默认4格
     *
     * @param angleRate 几格代表1度
     */
    public void setAngleRate(int angleRate) {
        this.angleRate = angleRate;
    }

    /**
     * 设置温度
     *
     * @param temp 设置的温度
     */
    public void setTemp(int temp) {
        setTemp(minTemp, maxTemp, temp);
    }

    /**
     * 设置温度
     *
     * @param minTemp 最小温度
     * @param maxTemp 最大温度
     * @param temp    设置的温度
     */
    public void setTemp(int minTemp, int maxTemp, int temp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        if (temp < minTemp) {
            this.temperature = minTemp;
        } else {
            this.temperature = temp;
        }

        // 计算旋转角度
        rotateAngle = (float) ((temp - minTemp) * angleRate * angleOne);
        // 计算每格的角度
        angleOne = (float) 270 / (maxTemp - minTemp) / angleRate;
        invalidate();
    }

    /**
     * 设置温度改变监听
     *
     * @param onTempChangeListener 监听接口
     */
    public void setOnTempChangeListener(OnTempChangeListener onTempChangeListener) {
        this.onTempChangeListener = onTempChangeListener;
    }

    /**
     * 设置点击监听
     *
     * @param onClickListener 点击回调接口
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 温度改变监听接口
     */
    public interface OnTempChangeListener {
        /**
         * 回调方法
         *
         * @param temp 温度
         */
        void change(int temp);
    }

    /**
     * 点击回调接口
     */
    public interface OnClickListener {
        /**
         * 点击回调方法
         *
         * @param temp 温度
         */
        void onClick(int temp);
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}
