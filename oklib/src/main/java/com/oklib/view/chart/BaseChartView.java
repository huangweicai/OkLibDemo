package com.oklib.view.chart;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 时间：2017/8/4
 * 作者：黄伟才
 * 描述：开测吧·自定义图表基类
 * 定义图表架子及绘制的共同部分
 */

public abstract class BaseChartView extends View {
    public static final String DAY = "day";
    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    //辅助属性
    protected int chartFinishTime = 800;//图表完成时间
    protected int yMaxValue = 150;//y最大值，用于计算比例
    protected boolean isShowDatumLine_X = false;//是否显示X轴基准线
    protected boolean isShowDatumLine_Y = false;//是否显示Y轴基准线
    protected String type = DAY;
    protected String unitText = "Kg";

    //装载数据相关
    protected Point[] xScale = new Point[32];
    protected Point[] yScale = new Point[32];
    protected List<DataBean> datas = new ArrayList<>();//装载真实数据源
    protected List<DataPoint> pointDatas = new ArrayList<>();//装载坐标数据点
    protected Point yTopPoint;
    protected Point yBottomPoint;
    protected Point xLeftPoint;
    protected Point xRightPoint;
    protected String[] x_scale_text = new String[10];//这里预留10个空间
    protected String[] y_scale_text = new String[4];//第一位是最大值，刻度不显示

    //色值相关
    protected int blackColor = 0xff303130;//灰黑色
    protected int greenColor = 0xff5EBD87;//绿色，体重
    protected int yellowColor = 0xffFA8E3F;//黄色，运动
    protected int pinkColor = 0xffFD7CA7;//粉红，血压
    protected int pinkLightColor = 0xffFFBFD4;//浅色粉红，血压
    protected int blueColor = 0xff54A6F0;//蓝色，心电
    protected int chartColor = blackColor;//灰黑色

    //绘制对象相关
    protected Paint blackPaint;
    protected Paint linePaint;
    protected Path linePath;
    protected PathMeasure mPathMeasure;//追踪Path的坐标
    protected Path mPathCircleDst;
    protected float animValue;//轨迹进度值
    protected ValueAnimator lineAnim;

    //尺寸大小相关
    protected int textSize = 14;//绘制文本大小
    protected int lineWidth = 2;//绘线宽度
    protected int xScaleCount = 7;//x刻度数
    protected int yScaleCount = 4;//y刻度数

    protected int radiusSize = 4;//最后小圆半径
    protected int textHeight;//文本高度，模拟

    protected int width;//680
    protected int height;//400
    protected int gapSize;//图表四周间隙大小

    protected float cacheX;//缓存最后第二位x坐标
    protected float cacheY;//缓存最后第二位y坐标
    protected boolean isCanvas = false;//是否可以绘制


    public BaseChartView(Context context) {
        super(context);
        init();
    }

    public BaseChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        textSize = dip2px(getContext(), 12);
        gapSize = dip2px(getContext(), 25);
        lineWidth = dip2px(getContext(), 2);
        textHeight = dip2px(getContext(), 12);
        radiusSize = dip2px(getContext(), 4);

        blackPaint = new Paint();
        blackPaint.setColor(blackColor);
        blackPaint.setDither(true);
        blackPaint.setAntiAlias(true);
        blackPaint.setTextSize(textSize);
        linePaint = new Paint();
        linePaint.setColor(greenColor);
        linePaint.setDither(true);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        linePath = new Path();
        mPathMeasure = new PathMeasure();
        mPathCircleDst = new Path();

        lineAnim = ValueAnimator.ofFloat(0, 1);
        lineAnim.setDuration(chartFinishTime);
        lineAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        yTopPoint = new Point(width - gapSize, 0);
        yBottomPoint = new Point(width - gapSize, height - gapSize);
        xLeftPoint = new Point(gapSize, height - gapSize);
        xRightPoint = new Point(width - gapSize, height - gapSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        blackPaint.setColor(chartColor);
        //绘制x轴
        canvas.drawLine(xLeftPoint.x, xLeftPoint.y, xRightPoint.x, xRightPoint.y, blackPaint);
    }

    /**
     * 作者：黄伟才
     * 描述：是否显示X轴基准线
     */
    public BaseChartView isShowDatumLine_X(boolean isShow) {
        this.isShowDatumLine_X = isShow;
        return this;
    }

    /**
     * 作者：黄伟才
     * 描述：是否显示Y轴基准线
     */
    public BaseChartView isShowDatumLine_Y(boolean isShow) {
        this.isShowDatumLine_Y = isShow;
        return this;
    }

    /**
     * 设置图表x轴、y轴及相关文本颜色
     * @param chartColor
     * @return
     */
    public BaseChartView setChartColor(int chartColor) {
        this.chartColor = chartColor;
        return this;
    }

    /**
     * 设置值所属单位
     *
     * @param unitText
     * @return
     */
    public BaseChartView setUnitValueText(String unitText) {
        this.unitText = unitText;
        return this;
    }

    /**
     * y最大值
     *
     * @param yMaxValue
     * @return
     */
    public BaseChartView setYMaxValue(int yMaxValue) {
        this.yMaxValue = yMaxValue;
        return this;
    }

     /**
       * 作者：黄伟才
       * 描述：动画完成时间，一般用于轨迹追踪时间
       */
    public BaseChartView setChartFinishTime(int time) {
        this.chartFinishTime = time;
        return this;
    }


    /**
     * 设置类型，用于区分日、周、月、年
     *
     * @param type
     * @return
     */
    public BaseChartView setType(String type) {
        this.type = type;
        return this;
    }

     /**
       * 作者：黄伟才
       * 描述：刷新数据
       */
    public abstract void refreshData(List<DataBean> datas);


    protected int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //格式化如：100.0--->100
    //格式化如：100.2--->100.2
    protected String fotmatStr(String str) {
        if (str.contains(".0")) {
            return str.replace(".0", "");
        } else {
            return str;
        }
    }

    /**
     * 获取当月的天数
     */
    protected int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    protected int getStringWidth(Paint paint, String str) {
        return (int) paint.measureText(str);
    }

    //除4才是文本高度半？
    protected int getStringHeight(Paint paint) {
        Paint.FontMetrics fr = paint.getFontMetrics();
        return (int) Math.ceil(fr.descent - fr.top) + 2;  //ceil() 函数向上舍入为最接近的整数。
    }

    //装载真实数据源
    public static class DataBean {
        public String date;
        public float value;
        public float minValue;//最小值
        public boolean isLight;//是否浅色

        public float maxValue;//最大值
        public float avgValue;//平均值

        public float[] y_hp_value;//高压范围
        public float[] y_lp_value;//低压范围

        public float highValue;
        public float centerValue;
        public float lowValue;
        public boolean isShowText;//是否每个柱子都显示文本

        public DataBean() {
        }
        public DataBean(String date, float value) {
            this.date = date;
            this.value = value;
        }
        public DataBean(String date, float[] y_hp_value, float[] y_lp_value, boolean isLight) {
            //血压 新
            this.date = date;
            this.y_hp_value = y_hp_value;
            this.y_lp_value = y_lp_value;
            this.isLight = isLight;
        }
        public DataBean(String date, float value, float minValue, boolean isLight) {
            //血压
            this.date = date;
            this.value = value;
            this.minValue = minValue;
            this.isLight = isLight;
        }
        public DataBean(String date, float maxValue, float avgValue, float minValue) {
            //心率
            this.date = date;
            this.maxValue = maxValue;
            this.avgValue = avgValue;
            this.minValue = minValue;
        }
        public DataBean(String date, float lowValue, float centerValue, float highValue, boolean isShowText) {
            //心率
            this.date = date;
            this.lowValue = lowValue;
            this.centerValue = centerValue;
            this.highValue = highValue;
            this.isShowText = isShowText;
        }
    }

    //装载坐标数据点
    protected static class DataPoint {
        public float x;
        public float y;
        public float min_y;//最小值
        public boolean isLight;//是否浅色

        public float max_y;//最大值
        public float avg_y;//平均值

        public float[] y_hp;//高压范围
        public float[] y_lp;//低压范围

        public float highValue_y;
        public float centerValue_y;
        public float lowValue_y;
        public boolean isShowText;//是否每个柱子都显示文本

        public DataPoint() {
        }
        public DataPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
        public DataPoint(float x, float[] y_hp, float[] y_lp, boolean isLight) {
            //血压 新
            this.x = x;
            this.y_hp = y_hp;
            this.y_lp = y_lp;
            this.isLight = isLight;
        }
        public DataPoint(float x, float y, float min_y, boolean isLight) {
            this.x = x;
            this.y = y;
            this.min_y = min_y;
            this.isLight = isLight;
        }
        public DataPoint(float x, float y, float max_y, float avg_y, float min_y) {
            this.x = x;
            this.y = y;
            this.max_y = max_y;
            this.avg_y = avg_y;
            this.min_y = min_y;
        }
        public DataPoint(float x, float highValue_y, float centerValue_y, float lowValue_y, boolean isShowText) {
            this.x = x;
            this.highValue_y = highValue_y;
            this.centerValue_y = centerValue_y;
            this.lowValue_y = lowValue_y;
            this.isShowText = isShowText;
        }

    }
}
