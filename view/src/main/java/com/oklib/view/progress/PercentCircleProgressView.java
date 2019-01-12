package com.oklib.view.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.oklib.view.R;

import java.text.DecimalFormat;

/**
  * 时间：2017/8/25
  * 作者：蓝天
  * 描述：百分占比，自定义进度view，带完成时跟随圆
  */
public class PercentCircleProgressView extends View {
   private static final String TAG = "SportCircleView";
   private int finishProgressColor;//完成距离弧的颜色
   private int unfinishProgressColor;//未完成距离弧的颜色
   private int finishProgressLineSize;//完成距离弧线宽度
   private int unfinishProgressLineSize;//未完成距离弧线宽度
   private int progressCircleLineSize;//圆圈圆环弧线宽度
   private int progressCircleRadiusSize;//圆圈圆环弧线半径
   private float goalValue;//目标距离
   private float achieveValue;//完成距离
   private int centerTextSize;//中间数据的字体大小
   private int centerTextColor;//中间数据的字体颜色
   private int centerUnitTextSize;//单位的字体大小
   private int centerUnitTextColor;//单位的字体颜色

   private float differ;
   private int duration;
   private Paint upperPaint;
   private Paint lowerPaint;
   private Paint circlePaint;
   private Paint distancePaint;
   private RectF finishRectF = null;
   private RectF unfinishRectF = null;
   private float currentSweepAngle;            //当前转过的角度值
   private float sweepAngle;                   //最终角度值

   private float originX;                      //原点x坐标
   private float originY;                      //原点y坐标

   public PercentCircleProgressView(Context context) {
       this(context, null);
   }

   public PercentCircleProgressView(Context context, @Nullable AttributeSet attrs) {
       this(context, attrs, 0);
   }

   public PercentCircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
       super(context, attrs, defStyleAttr);
       init(context, attrs, defStyleAttr);
   }

   /**
    * 初始化
    *
    * @param context
    * @param attrs
    * @param defStyleAttr
    */
   private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
       TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.oklib_ProgressCircleView, defStyleAttr, 0);
       finishProgressColor = ta.getColor(R.styleable.oklib_ProgressCircleView_oklib_pcv_finishProgressColor, Color.WHITE);
       unfinishProgressColor = ta.getColor(R.styleable.oklib_ProgressCircleView_oklib_pcv_unfinishProgressColor, 0xFFe06127);
       finishProgressLineSize = ta.getDimensionPixelSize(R.styleable.oklib_ProgressCircleView_oklib_pcv_finishProgressLineSize, 20);
       unfinishProgressLineSize = ta.getDimensionPixelSize(R.styleable.oklib_ProgressCircleView_oklib_pcv_unfinishProgressLineSize, 16);
       progressCircleLineSize = ta.getDimensionPixelSize(R.styleable.oklib_ProgressCircleView_oklib_pcv_progressCircleLineSize, 14);
       progressCircleRadiusSize = ta.getDimensionPixelSize(R.styleable.oklib_ProgressCircleView_oklib_pcv_progressCircleRadiusSize, 14);
       goalValue = ta.getFloat(R.styleable.oklib_ProgressCircleView_oklib_pcv_goalValue, 360);
       achieveValue = ta.getFloat(R.styleable.oklib_ProgressCircleView_oklib_pcv_achieveValue, 0);
       centerTextSize = ta.getDimensionPixelSize(R.styleable.oklib_ProgressCircleView_oklib_pcv_centerTextSize, 100);
       centerTextColor = ta.getColor(R.styleable.oklib_ProgressCircleView_oklib_pcv_centerTextColor, Color.WHITE);
       centerUnitTextSize = ta.getDimensionPixelSize(R.styleable.oklib_ProgressCircleView_oklib_pcv_centerUnitTextSize, 30);
       centerUnitTextColor = ta.getColor(R.styleable.oklib_ProgressCircleView_oklib_pcv_centerUnitTextColor, Color.WHITE);
       duration = ta.getInt(R.styleable.oklib_ProgressCircleView_oklib_pcv_animDuration, 2000);
       ta.recycle();

       upperPaint = new Paint();
       upperPaint.setAntiAlias(true);
       upperPaint.setColor(finishProgressColor);
       upperPaint.setStrokeWidth(finishProgressLineSize);
       upperPaint.setStyle(Paint.Style.STROKE);

       lowerPaint = new Paint();
       lowerPaint.setAntiAlias(true);
       lowerPaint.setColor(unfinishProgressColor);
       lowerPaint.setStrokeWidth(unfinishProgressLineSize);
       lowerPaint.setStyle(Paint.Style.STROKE);

       circlePaint = new Paint();
       circlePaint.setAntiAlias(true);
       circlePaint.setColor(finishProgressColor);

       distancePaint = new Paint();
       distancePaint.setAntiAlias(true);
       distancePaint.setColor(finishProgressColor);
       distancePaint.setTextAlign(Paint.Align.CENTER);
   }

   @Override
   protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       if (finishRectF == null) {
           calculate();
       }
       drawFinish(canvas);
       drawUnfinish(canvas);
       drawCircle(canvas);
       drawDistance(canvas);
   }

   private void calculate() {
       //差值 ＝ 小圆外边环宽度 + 小圆半径
       differ = progressCircleLineSize + finishProgressLineSize / 2.0f;
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
           finishRectF = new RectF(getPaddingStart() + differ, getPaddingTop() + differ,
                   getWidth() - getPaddingEnd() - differ, getHeight() - getPaddingBottom() - differ);
       }

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
           unfinishRectF = new RectF(getPaddingStart() + differ, getPaddingTop() + differ,
                   getWidth() - getPaddingEnd() - differ, getHeight() - getPaddingBottom() - differ);
       }

       originX = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2f + getPaddingLeft();
       originY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2f + getPaddingTop();
   }


   /**
    * 画目标距离部分
    *
    * @param canvas
    */
   private void drawUnfinish(Canvas canvas) {
       canvas.drawArc(unfinishRectF, currentSweepAngle - 90, 360 - currentSweepAngle, false, lowerPaint);
   }

   /**
    * 画小圆圈
    *
    * @param canvas
    */
   private void drawCircle(Canvas canvas) {
       float r = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2.0f - differ;
       float x = (float) (r * Math.cos(Math.toRadians(currentSweepAngle - 90)) + originX);
       float y = (float) (r * Math.sin(Math.toRadians(currentSweepAngle - 90)) + originY);
       circlePaint.setStyle(Paint.Style.FILL);
       circlePaint.setColor(finishProgressColor);
       canvas.drawCircle(x, y, differ, circlePaint);
       circlePaint.setStyle(Paint.Style.FILL);
       circlePaint.setColor(unfinishProgressColor);
       canvas.drawCircle(x, y, progressCircleRadiusSize, circlePaint);
   }

   /**
    * 画完成距离部分
    *
    * @param canvas
    */
   private void drawFinish(Canvas canvas) {
       canvas.drawArc(finishRectF, -90, currentSweepAngle, false, upperPaint);
   }

   /**
    * @param canvas
    */
   private void drawDistance(Canvas canvas) {
       distancePaint.setTextSize(centerTextSize);
       distancePaint.setColor(centerTextColor);
       DecimalFormat format = new DecimalFormat("##0.0");
       String value = sweepAngle == 0 ? "0.0" : format.format(achieveValue * (currentSweepAngle / sweepAngle));
       //Log.d(TAG, "currentSweepAngle:"+currentSweepAngle+"---sweepAngle:"+sweepAngle+"--->"+(currentSweepAngle / sweepAngle)+"--->>"+(achieveValue * (currentSweepAngle / sweepAngle)));
       Paint.FontMetrics valueMetrics = distancePaint.getFontMetrics();
       canvas.drawText(value, getWidth() / 2, getHeight() / 2 + Math.abs(valueMetrics.ascent + valueMetrics.descent) / 2, distancePaint);
       //画单位
       distancePaint.setTextSize(centerUnitTextSize);
       distancePaint.setColor(centerUnitTextColor);
       Paint.FontMetrics unitMetrics = distancePaint.getFontMetrics();
       canvas.drawText("Km", getWidth() / 2, getHeight() / 2 + Math.abs(valueMetrics.ascent - valueMetrics.descent) +
               Math.abs(unitMetrics.ascent + unitMetrics.descent) / 2, distancePaint);
   }

   /**
    * 设置距离并开启动画
    *
    * @param goalValue
    * @param achieveValue
    */
   public PercentCircleProgressView setProgressValue(float goalValue, float achieveValue) {
       this.goalValue = goalValue;
       this.achieveValue = achieveValue;
       return this;
   }

   /**
    * 开始轨迹动画
    */
   public void startAnim() {
       currentSweepAngle = 0;
       sweepAngle = 360f * (achieveValue / goalValue);
       ValueAnimator anim = ValueAnimator.ofFloat(0, sweepAngle);
       anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator animation) {
               currentSweepAngle = (float) animation.getAnimatedValue();
               postInvalidate();
           }
       });
       anim.setDuration(duration);
       anim.start();
   }
}
