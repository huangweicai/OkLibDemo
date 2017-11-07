package com.oklib.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.oklib.R;

 /**
   * 时间：2017/5/12
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：Baidu加载中view
   */
public class BaiduLoadCircleView extends View {
    /**
     * 第一个动画的索引
     */
    private int changeIndex = 0;
    /**
     * 圆的颜色值
     */
    private int[] colors = new int[]{
            getResources().getColor(R.color.oklib_color_red),
            getResources().getColor(R.color.oklib_color_blue),
            getResources().getColor(R.color.oklib_color_black)};
    /**
     * 偏移量
     */
    private Float maxWidth = 50f;
    /**
     * 圆的半径 默认10f
     */
    private Float circleRadius = 10f;

    /**
     * 当前偏移的X坐标
     */
    private Float currentX = 0f;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 属性动画
     */
    private ValueAnimator valueAnimator;
    /**
     * 持续时间
     */
    private int duration = 800;
    public BaiduLoadCircleView(Context context) {
        this(context, null);
    }

    public BaiduLoadCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaiduLoadCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.oklib_LoadCircleView);
            circleRadius = typedArray.getFloat(R.styleable.oklib_LoadCircleView_oklib_circle_radius, circleRadius);
            duration = typedArray.getInt(R.styleable.oklib_LoadCircleView_oklib_duration, duration);
            typedArray.recycle();//记得回收
        }
        startAnimator();
    }
    /**
     * 位移动画
     */
    private void startAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0f, maxWidth, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (Float) animation.getAnimatedValue();
                invalidate();//执行刷新onDraw()
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                changePoint(changeIndex);
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * 先执行动画的目标和中间停止的动画目标交换
     * （颜色切换）
     *
     * @param index 最先执行的动画的索引
     */
    private void changePoint(int index) {
        int temp = colors[2];
        colors[2] = colors[index];
        colors[index] = temp;
        changeIndex = (index == 0) ? 1 : 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        /**左边圆**/
        paint.setColor(colors[0]);
        canvas.drawCircle(centerX - currentX, centerY, circleRadius, paint);
        /**右边圆**/
        paint.setColor(colors[1]);
        canvas.drawCircle(centerX + currentX, centerY, circleRadius, paint);
        /**中间圆**/
        paint.setColor(colors[2]);
        canvas.drawCircle(centerX, centerY, circleRadius, paint);
    }

    //销毁View的时候回调
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valueAnimator.cancel();
    }
}
