package com.oklib.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;


import java.util.List;


/**
 * 时间：2017/9/13
 * 作者：黄伟才
 * 描述：堆叠柱状图
 */

public class StackBarChartView extends BaseChartView {
    public StackBarChartView(Context context) {
        super(context);
        init();
    }

    public StackBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StackBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StackBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPillarWidth = dip2px(getContext(), 7);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = super.width;
        this.height = super.height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isCanvas) {
            return;
        }
        drawData(canvas);
    }

    private float mPillarWidth;//柱子宽度
    private boolean isDottedLine;//是否是虚线
    protected int datumLineColor = 0xFFE5E5E5;//基准线颜色
    private String yTextUnit = "";//y刻度文本单位
    private int[] mHighPillarColor = {yellowColor, yellowColor};//高强度柱子颜色
    private int[] mCenterPillarColor = {greenColor, greenColor};//中强度柱子颜色
    private int[] mLowPillarColor = {blueColor, blueColor};//低强度柱子颜色
    private int pillarGap = 5;//柱子间隙
    public int width;//680
    public int height;//400
    public String monthText;//月

    public StackBarChartView setMonthText(String month) {
        this.monthText = month;
        return this;
    }
    public StackBarChartView setPillarGap(int pillarGap) {
        this.pillarGap = pillarGap;
        return this;
    }

    public StackBarChartView setDatumLineColor(int datumLineColor) {
        this.datumLineColor = datumLineColor;
        return this;
    }

    public StackBarChartView setDottedLine(boolean dottedLine) {
        isDottedLine = dottedLine;
        return this;
    }

    public StackBarChartView setyTextUnit(String yTextUnit) {
        this.yTextUnit = yTextUnit;
        return this;
    }

    public StackBarChartView setPillarWidth(float mPillarWidth) {
        this.mPillarWidth = mPillarWidth;
        return this;
    }

    public StackBarChartView setHighPillarColor(int[] mHighPillarColor) {
        this.mHighPillarColor = mHighPillarColor;
        return this;
    }

    public StackBarChartView setCenterPillarColor(int[] mCenterPillarColor) {
        this.mCenterPillarColor = mCenterPillarColor;
        return this;
    }

    public StackBarChartView setLowPillarColor(int[] mLowPillarColor) {
        this.mLowPillarColor = mLowPillarColor;
        return this;
    }

    /**
     * 刷新数据
     */
    public void refreshData(List<DataBean> datas) {
        this.pointDatas.clear();

        this.datas.clear();
        this.datas.addAll(datas);

        y_scale_text[0] = "" + yMaxValue;
        y_scale_text[1] = "" + yMaxValue * 2 / 3;
        y_scale_text[2] = "" + yMaxValue * 1 / 3;
        y_scale_text[3] = "0";
        if (type.equals(DAY)) {
            loadDayCo();
        } else if (type.equals(WEEK)) {
            loadWeekCo();
        } else if (type.equals(MONTH)) {
            loadMonthCo();
        } else if (type.equals(YEAR)) {
            //loadYearCo();
        }
        isCanvas = true;
        invalidate();
    }

    /**
     * 作者：黄伟才
     * 描述：绘制刻度文本，数据轨迹
     */
    private void drawData(Canvas canvas) {
        linePaint.setColor(blackColor);
        linePaint.setStyle(Paint.Style.STROKE);

        //绘制x轴刻度文本，x轴头尾多一个刻度位置
        for (int i = 0; i < xScaleCount; i++) {
            if (TextUtils.equals(type, WEEK)) {
                canvas.drawText(x_scale_text[i], xScale[i].x, xScale[i].y + textHeight, blackPaint);
            } else {
                canvas.drawText(x_scale_text[i], xScale[i].x - blackPaint.measureText(x_scale_text[i]) / 2, xScale[i].y + textHeight, blackPaint);
            }

            if (isShowDatumLine_X) {
                Paint linePaint = new Paint();
                linePaint.setAntiAlias(true);
                linePaint.setStrokeWidth(2);
                if (isDottedLine) {
                    linePaint.setStyle(Paint.Style.STROKE);
                    linePaint.setColor(datumLineColor);
                    DashPathEffect effects = new DashPathEffect(new float[]{6, 8, 8, 8}, 0);
                    linePaint.setPathEffect(effects);
                    canvas.drawRect(xScale[i].x, 0, xScale[i].x, height - gapSize, linePaint);
                } else {
                    canvas.drawLine(xScale[i].x, 0, xScale[i].x, height - gapSize, linePaint);
                }
            }
        }
        //绘制y轴刻度文本，y轴顶部多一个刻度位置
        for (int i = 0; i < yScaleCount; i++) {//3
//            if (i != 0) {
//                String yText = y_scale_text[i] + yTextUnit;
//                int offsetX = getStringWidth(blackPaint, yText) - gapSize;
//                if (offsetX > 0) {
//                    canvas.drawText(yText, yBottomPoint.x - offsetX, yScale[i].y, blackPaint);
//                } else {
//                    canvas.drawText(yText, yBottomPoint.x, yScale[i].y, blackPaint);
//                }
//            }

            int mLineWidth;
            if (TextUtils.equals(type, WEEK) || TextUtils.equals(type, MONTH)) {
                mLineWidth = width;
            } else {
                mLineWidth = width - gapSize;
            }

            if (isShowDatumLine_Y) {
                Paint linePaint = new Paint();
                linePaint.setAntiAlias(true);
                linePaint.setStrokeWidth(2);
                linePaint.setColor(datumLineColor);
                if (isDottedLine) {
                    linePaint.setStyle(Paint.Style.STROKE);
                    DashPathEffect effects = new DashPathEffect(new float[]{6, 6, 6, 6}, 0);
                    linePaint.setPathEffect(effects);
                    if (i < yScaleCount - 1) {
                        canvas.drawRect(xScale[0].x, yScale[i].y+1, mLineWidth, yScale[i].y+1, linePaint);
                    }
                } else {
                    canvas.drawLine(xScale[0].x, yScale[i].y+1, mLineWidth, yScale[i].y+1, linePaint);
                }
            }

        }

        if (TextUtils.equals(type, DAY)) {
            processDayData(canvas);
        } else if (TextUtils.equals(type, WEEK)) {
            processWeekData(canvas);
            blackPaint.setColor(0xff000000);
            canvas.drawLine(xLeftPoint.x, xLeftPoint.y, width, xRightPoint.y, blackPaint);
        } else if (TextUtils.equals(type, MONTH)) {
            processMonthData(canvas);
            blackPaint.setColor(0xff000000);
            canvas.drawLine(xLeftPoint.x, xLeftPoint.y, width, xRightPoint.y, blackPaint);
        }

    }

    private void processDayData(Canvas canvas) {
        for (int i = 0; i < pointDatas.size(); i++) {
            DataPoint bean = pointDatas.get(i);
            linePaint.setStyle(Paint.Style.FILL);
            Shader mLowShader = new LinearGradient(bean.x, bean.lowValue_y, bean.x, height - gapSize,
                    mLowPillarColor, null, Shader.TileMode.REPEAT);
            Shader mCenterShader = new LinearGradient(bean.x, bean.centerValue_y, bean.x, bean.lowValue_y,
                    mCenterPillarColor, null, Shader.TileMode.REPEAT);
            Shader mHighShader = new LinearGradient(bean.x, bean.highValue_y, bean.x, bean.centerValue_y,
                    mHighPillarColor, null, Shader.TileMode.REPEAT);
            //绘制数据
            if (i < pointDatas.size() - 1) {
                //低柱子
                linePaint.setShader(mLowShader);
                RectF oval1 = new RectF(bean.x + pillarGap, bean.lowValue_y, pointDatas.get(i + 1).x - pillarGap, height - gapSize);
                canvas.drawRect(oval1, linePaint);
                //中柱子
                linePaint.setShader(mCenterShader);
                RectF oval2 = new RectF(bean.x + pillarGap, bean.centerValue_y, pointDatas.get(i + 1).x - pillarGap, bean.lowValue_y);
                canvas.drawRect(oval2, linePaint);
                //高柱子
                linePaint.setShader(mHighShader);
                RectF oval3 = new RectF(bean.x + pillarGap, bean.highValue_y, pointDatas.get(i + 1).x - pillarGap, bean.centerValue_y);
                canvas.drawRect(oval3, linePaint);

                if (bean.isShowText) {
                    String text = ChartDateUtil.formatVirtualValue("" + datas.get(i).highValue);
                    canvas.drawText(text, bean.x - blackPaint.measureText(text) / 2 + (pointDatas.get(i + 1).x - bean.x) / 2, bean.highValue_y - dip2px(getContext(), 3), blackPaint);
                }
            }

        }
    }

    private void processWeekData(Canvas canvas) {
        for (int i = 0; i < pointDatas.size(); i++) {
            DataPoint bean = pointDatas.get(i);
            linePaint.setStyle(Paint.Style.FILL);
            Shader mLowShader = new LinearGradient(bean.x, bean.lowValue_y, bean.x, height - gapSize,
                    mLowPillarColor, null, Shader.TileMode.REPEAT);
            Shader mCenterShader = new LinearGradient(bean.x, bean.centerValue_y, bean.x, bean.lowValue_y,
                    mCenterPillarColor, null, Shader.TileMode.REPEAT);
            Shader mHighShader = new LinearGradient(bean.x, bean.highValue_y, bean.x, bean.centerValue_y,
                    mHighPillarColor, null, Shader.TileMode.REPEAT);
            //绘制数据
            //低柱子
            linePaint.setShader(mLowShader);
            RectF oval1 = new RectF(bean.x, bean.lowValue_y, bean.x + mPillarWidth, height - gapSize);
            canvas.drawRect(oval1, linePaint);
            //中柱子
            linePaint.setShader(mCenterShader);
            RectF oval2 = new RectF(bean.x, bean.centerValue_y, bean.x + mPillarWidth, bean.lowValue_y);
            canvas.drawRect(oval2, linePaint);
            //高柱子
            linePaint.setShader(mHighShader);
            RectF oval3 = new RectF(bean.x, bean.highValue_y, bean.x + mPillarWidth, bean.centerValue_y);
            canvas.drawRect(oval3, linePaint);

            if (bean.isShowText) {
                String text = ChartDateUtil.formatVirtualValue("" + datas.get(i).highValue);
                canvas.drawText(text, bean.x - blackPaint.measureText(text) / 2 + mPillarWidth / 2, bean.highValue_y - dip2px(getContext(), 3), blackPaint);
            }
        }
    }

    private void processMonthData(Canvas canvas) {
        for (int i = 0; i < pointDatas.size(); i++) {//3
            DataPoint bean = pointDatas.get(i);
            linePaint.setStyle(Paint.Style.FILL);
            Shader mLowShader = new LinearGradient(bean.x, bean.lowValue_y, bean.x, height - gapSize,
                    mLowPillarColor, null, Shader.TileMode.REPEAT);
            Shader mCenterShader = new LinearGradient(bean.x, bean.centerValue_y, bean.x, bean.lowValue_y,
                    mCenterPillarColor, null, Shader.TileMode.REPEAT);
            Shader mHighShader = new LinearGradient(bean.x, bean.highValue_y, bean.x, bean.centerValue_y,
                    mHighPillarColor, null, Shader.TileMode.REPEAT);
            //绘制数据
            if (i < pointDatas.size() - 1) {
                //低柱子
                linePaint.setShader(mLowShader);
                RectF oval1 = new RectF(bean.x + pillarGap, bean.lowValue_y, pointDatas.get(i + 1).x - pillarGap, height - gapSize);
                canvas.drawRect(oval1, linePaint);
                //中柱子
                linePaint.setShader(mCenterShader);
                RectF oval2 = new RectF(bean.x + pillarGap, bean.centerValue_y, pointDatas.get(i + 1).x - pillarGap, bean.lowValue_y);
                canvas.drawRect(oval2, linePaint);
                //高柱子
                linePaint.setShader(mHighShader);
                RectF oval3 = new RectF(bean.x + pillarGap, bean.highValue_y, pointDatas.get(i + 1).x - pillarGap, bean.centerValue_y);
                canvas.drawRect(oval3, linePaint);

                if (bean.isShowText) {
                    //float highValue_y = (yMaxValue - highValueTotal) * (height - gapSize) / yMaxValue;
                    float totalValue = yMaxValue - (pointDatas.get(i).highValue_y * yMaxValue) / (height - gapSize);//总值
                    String text = ChartDateUtil.formatVirtualValue("" + totalValue);
                    canvas.drawText(text, bean.x - blackPaint.measureText(text) / 2 + (pointDatas.get(i + 1).x - bean.x) / 2, bean.highValue_y - dip2px(getContext(), 3), blackPaint);
                }
            }

        }
    }


    protected String[] x_scale_text = new String[25];

    /**
     * 作者：黄伟才
     * 描述：装载x、y刻度坐标
     */
    private void loadDayCo() {
        xScaleCount = 13;//x日刻度数
        yScaleCount = 4;//y日刻度数

        //装载x刻度文本坐标
        int x_text = (width - gapSize * 2) / (xScaleCount - 1);
        for (int i = 0; i < xScaleCount; i++) {
            Point point = new Point();
            point.x = gapSize + x_text * i;
            point.y = height - gapSize;
            xScale[i] = point;
        }
        //装载y刻度文本坐标
        int y_text = (height - gapSize) / (yScaleCount - 1);
        for (int i = 0; i < yScaleCount; i++) {
            Point point = new Point();
            point.x = width - gapSize;
            point.y = y_text * i;
            yScale[i] = point;
        }
        x_scale_text[0] = "12:00";
        x_scale_text[1] = "14:00";
        x_scale_text[2] = "16:00";
        x_scale_text[3] = "18:00";
        x_scale_text[4] = "20:00";
        x_scale_text[5] = "22:00";
        x_scale_text[6] = "24:00";
        x_scale_text[7] = "02:00";
        x_scale_text[8] = "04:00";
        x_scale_text[9] = "06:00";
        x_scale_text[10] = "08:00";
        x_scale_text[11] = "10:00";
        x_scale_text[12] = "12:00";
        float x_data = (width - gapSize * 2) / 24f;
        //装载刻度显示的数据

        //昨天数据，昨天的24点就是今天的0点
        for (int i = 12; i < 25; i++) {
            for (int j = 0; j < datas.size()/2; j++) {
                DataBean bean = datas.get(j);
                //2017-08-04 09:17:00
                String hour = bean.date.substring(11, 13);//截取“时”  09
                if (Integer.valueOf(hour) == i) {
                    //存在刻度点
                    float x = x_data * (i - 12) + xScale[0].x;
                    float highValue_y = (yMaxValue - datas.get(j).highValue) * (height - gapSize) / yMaxValue;
                    float centerValue_y = (yMaxValue - datas.get(j).centerValue) * (height - gapSize) / yMaxValue;
                    float lowValue_y = (yMaxValue - datas.get(j).lowValue) * (height - gapSize) / yMaxValue;
                    pointDatas.add(new DataPoint(x, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                    if (i == 24) {
                        pointDatas.add(new DataPoint(x_data * (25 - 12) + xScale[0].x, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                    }
                }
            }
        }
        //今天数据
        for (int i = 0; i < 13; i++) {
            for (int j = 12; j < datas.size(); j++) {
                DataBean bean = datas.get(j);
                //2017-08-04 09:17:00
                String hour = bean.date.substring(11, 13);//截取“时”  09
                if (Integer.valueOf(hour) == i) {
                    //存在刻度点
                    float x = x_data * (i + 11) + xScale[0].x;
                    float highValue_y = (yMaxValue - datas.get(j).highValue) * (height - gapSize) / yMaxValue;
                    float centerValue_y = (yMaxValue - datas.get(j).centerValue) * (height - gapSize) / yMaxValue;
                    float lowValue_y = (yMaxValue - datas.get(j).lowValue) * (height - gapSize) / yMaxValue;
                    pointDatas.add(new DataPoint(x, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                    if (i == 12) {
                        pointDatas.add(new DataPoint(x_data * (13 + 11) + xScale[0].x, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                    }
                }
            }
        }


//        for (int i = 0; i < 25; i++) {
//            for (int j = 0; j < datas.size(); j++) {
//                DataBean bean = datas.get(j);
//                //2017-08-04 09:17:00
//                String date = bean.date.substring(8, 10);//截取“日”  04
//                String hour = bean.date.substring(11, 13);//截取“时”  09
//                if (Integer.valueOf(hour) == i) {
//                    //存在刻度点
//                    float x = x_data * i + xScale[0].x;
//                    float highValue_y = (yMaxValue - datas.get(j).highValue) * (height - gapSize) / yMaxValue;
//                    float centerValue_y = (yMaxValue - datas.get(j).centerValue) * (height - gapSize) / yMaxValue;
//                    float lowValue_y = (yMaxValue - datas.get(j).lowValue) * (height - gapSize) / yMaxValue;
////                    String currentDate = DateUtil.getCurrentDay(DateUtil.dateFormatYMDHMS);
////                    if (TextUtils.equals(date, currentDate.substring(8, 10))) {
////                        //是当天日期
////
////                    }else{
////                        //昨天日期
////
////                    }
//                    pointDatas.add(new DataPoint(x, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
//                }
//            }
//        }

    }


    private void loadWeekCo() {
        xScaleCount = 7;//x周刻度数
        yScaleCount = 4;//y周刻度数

        //装载x刻度文本坐标
        int x_text = (width - gapSize * 2) / (xScaleCount - 1);
        for (int i = 0; i < xScaleCount; i++) {
            Point point = new Point();
            point.x = gapSize + x_text * i;
            point.y = height - gapSize;
            xScale[i] = point;
        }
        //装载y刻度文本坐标
        int y_text = (height - gapSize) / (yScaleCount - 1);
        for (int i = 0; i < yScaleCount; i++) {
            Point point = new Point();
            point.x = width - gapSize;
            point.y = y_text * i;
            yScale[i] = point;
        }

        float x_data = (width - gapSize * 2) / 6f;
        //装载刻度显示的数据
        for (int i = 0; i < 7; i++) {
            //周一 周二 周三 周四 周五 周六 周日
            String weedDate = ChartDateUtil.getDayOfWeek("yyyy-MM-dd", i + 2);//i+2从周一开始  i+1从周日开始
            //x_scale_text[i] = ChartDateUtil.getDayOfWeekByDate(weedDate);//友好显示，周一到周日
            String mWeedDate = "周一";
            if (i == 0) {
                mWeedDate = "周一";
            } else if (i == 1) {
                mWeedDate = "周二";
            } else if (i == 2) {
                mWeedDate = "周三";
            } else if (i == 3) {
                mWeedDate = "周四";
            } else if (i == 4) {
                mWeedDate = "周五";
            } else if (i == 5) {
                mWeedDate = "周六";
            } else if (i == 6) {
                mWeedDate = "周日";
            }
            x_scale_text[i] = mWeedDate;//友好显示，周一到周日
//            if (i == 0) {
//                x_scale_text[i] = getStringByFormat(weedDate, "yyyy-MM-dd", "M月d日");//08月1日-->8月1日
//            } else {
//                x_scale_text[i] = getStringByFormat(weedDate, "yyyy-MM-dd", "d");//01-->1  11-->11
//            }
            for (int j = 0; j < datas.size(); j++) {
                DataBean bean = datas.get(j);
                //2017-08-04 09:17:00
                String date = bean.date.substring(0, 10);//截取“年月日”  04
                if (TextUtils.equals(date, weedDate)) {
                    //存在刻度点
                    float x = x_data * i + xScale[0].x;// - mPillarWidth / 2
                    float highValue_y = (yMaxValue - datas.get(j).highValue) * (height - gapSize) / yMaxValue;
                    float centerValue_y = (yMaxValue - datas.get(j).centerValue) * (height - gapSize) / yMaxValue;
                    float lowValue_y = (yMaxValue - datas.get(j).lowValue) * (height - gapSize) / yMaxValue;
                    pointDatas.add(new DataPoint(x, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                }
            }
        }
    }

    /**
     * 作者：黄伟才
     */
    private void loadMonthCo() {
        xScaleCount = 4;//x月刻度数
        yScaleCount = 4;//y月刻度数

        //装载x刻度文本坐标
        int x_text = (width - gapSize) / (xScaleCount - 1);
        for (int i = 0; i < xScaleCount; i++) {
            Point point = new Point();
            point.x = gapSize + x_text * i;
            point.y = height - gapSize;
            xScale[i] = point;
        }
        //装载y刻度文本坐标
        int y_text = (height - gapSize) / (yScaleCount - 1);
        for (int i = 0; i < yScaleCount; i++) {
            Point point = new Point();
            point.x = width - gapSize;
            point.y = y_text * i;
            yScale[i] = point;
        }

        int monthDayCount = ChartDateUtil.getCurrentMonthDay();//获取当月的天数
        String mMonthText;
        if (TextUtils.isEmpty(monthText)) {
            mMonthText = ChartDateUtil.getFirstDayOfMonth("MM");
        }else{
            mMonthText = this.monthText;
        }
        x_scale_text[0] = mMonthText + ".01";//获取本月第一天
        x_scale_text[1] = mMonthText + ".10";
        x_scale_text[2] = mMonthText + ".20";
        x_scale_text[3] = "";
        //float x_data = (width - gapSize * 2) / 30f;

        float highValueTotal = 0;
        float centerValueTotal = 0;
        float lowValueTotal = 0;
        //装载刻度显示的数据
        for (int i = 1; i <= 31; i++) {
            String monthDate = ChartDateUtil.getDayOfMonth("yyyy-MM-dd", i);//获取本月某一天日期，从1开始，本月第几天
            Log.d("TAG", "monthDate:" + monthDate);
            for (int j = 0; j < datas.size(); j++) {
                DataBean bean = datas.get(j);
                //2017-08-04 09:17:00
                String date = bean.date.substring(0, 10);//截取“年月日”  04

                if (TextUtils.equals(date, monthDate)) {
                    //Log.d("TAG", "date:"+date);
                    DataBean dataBean = datas.get(j);
                    //存在刻度点
                    float x;
                    if (i < 11) {//1-10
                        if (i == 1) {
                            highValueTotal = 0;
                            centerValueTotal = 0;
                            lowValueTotal = 0;
                        }
                        //累加总和
                        highValueTotal += dataBean.highValue;
                        centerValueTotal += dataBean.centerValue;
                        lowValueTotal += dataBean.lowValue;

                        if (i == 10) {
                            x = xScale[0].x;
                            float highValue_y = (yMaxValue - highValueTotal) * (height - gapSize) / yMaxValue;
                            float centerValue_y = (yMaxValue - centerValueTotal) * (height - gapSize) / yMaxValue;
                            float lowValue_y = (yMaxValue - lowValueTotal) * (height - gapSize) / yMaxValue;
                            pointDatas.add(new DataPoint(x - mPillarWidth / 2, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                        }
                    } else if (i < 21) {//11-20
                        if (i == 11) {
                            highValueTotal = 0;
                            centerValueTotal = 0;
                            lowValueTotal = 0;
                        }
                        //累加总和
                        highValueTotal += dataBean.highValue;
                        centerValueTotal += dataBean.centerValue;
                        lowValueTotal += dataBean.lowValue;

                        if (i == 20) {
                            x = xScale[1].x;
                            float highValue_y = (yMaxValue - highValueTotal) * (height - gapSize) / yMaxValue;
                            float centerValue_y = (yMaxValue - centerValueTotal) * (height - gapSize) / yMaxValue;
                            float lowValue_y = (yMaxValue - lowValueTotal) * (height - gapSize) / yMaxValue;
                            pointDatas.add(new DataPoint(x - mPillarWidth / 2, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                        }
                    } else if (i < 32) {//21-31
                        if (i == 21) {
                            highValueTotal = 0;
                            centerValueTotal = 0;
                            lowValueTotal = 0;
                        }
                        //累加总和
                        highValueTotal += dataBean.highValue;
                        centerValueTotal += dataBean.centerValue;
                        lowValueTotal += dataBean.lowValue;

                        int currentMonthDay = getCurrentMonthDay();//当月天数
                        if (i == currentMonthDay) {
                            x = xScale[2].x;
                            float highValue_y = (yMaxValue - highValueTotal) * (height - gapSize) / yMaxValue;
                            float centerValue_y = (yMaxValue - centerValueTotal) * (height - gapSize) / yMaxValue;
                            float lowValue_y = (yMaxValue - lowValueTotal) * (height - gapSize) / yMaxValue;
                            pointDatas.add(new DataPoint(x - mPillarWidth / 2, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
                            pointDatas.add(new DataPoint(xScale[3].x - mPillarWidth / 2, highValue_y, centerValue_y, lowValue_y, bean.isShowText));//预留多一个用于显示
                        }
                    }
                }
            }
        }

    }

    private void loadYearCo() {
        xScaleCount = 7;//x年刻度数
        yScaleCount = 4;//y年刻度数

        //装载x刻度文本坐标
        int x_text = (width - gapSize * 2) / (xScaleCount - 1);
        for (int i = 0; i < xScaleCount; i++) {
            Point point = new Point();
            point.x = gapSize + x_text * i;
            point.y = height - gapSize;
            xScale[i] = point;
        }
        //装载y刻度文本坐标
        int y_text = (height - gapSize) / (yScaleCount - 1);
        for (int i = 0; i < yScaleCount; i++) {
            Point point = new Point();
            point.x = width - gapSize;
            point.y = y_text * i;
            yScale[i] = point;
        }


        x_scale_text[0] = ChartDateUtil.getFirstDayOfMonth("yyyy/1");//获取本月第一天
        x_scale_text[1] = "3";
        x_scale_text[2] = "6";
        x_scale_text[3] = "9";
        x_scale_text[4] = "12";
        float x_data = (width - gapSize * 2 - x_text * 2) / 12f;
        Log.d("TAG", "x_data:" + x_data);
        //装载刻度显示的数据
        for (int j = 0; j < datas.size(); j++) {
            DataBean bean = datas.get(j);
            //2017-08-04 09:17:00
            String date = bean.date.substring(5, 7);//截取“年月日”  08
            float x = 0;
            float y = 0;
            if (TextUtils.equals(date, "01")) {
                //存在刻度点
                x = x_data * 0 + xScale[1].x;
            } else if (TextUtils.equals(date, "02")) {
                //存在刻度点
                x = x_data * 3 / 2 + xScale[1].x;
            } else if (TextUtils.equals(date, "03")) {
                //存在刻度点
                x = x_data * 3 + xScale[1].x;
            } else if (TextUtils.equals(date, "04")) {
                //存在刻度点
                x = x_data * 4 + xScale[1].x;
            } else if (TextUtils.equals(date, "05")) {
                //存在刻度点
                x = x_data * 5 + xScale[1].x;
            } else if (TextUtils.equals(date, "06")) {
                //存在刻度点
                x = x_data * 6 + xScale[1].x;
            } else if (TextUtils.equals(date, "07")) {
                //存在刻度点
                x = x_data * 7 + xScale[1].x;
            } else if (TextUtils.equals(date, "08")) {
                //存在刻度点
                x = x_data * 8 + xScale[1].x;
            } else if (TextUtils.equals(date, "09")) {
                //存在刻度点
                x = x_data * 9 + xScale[1].x;
            } else if (TextUtils.equals(date, "10")) {
                //存在刻度点
                x = x_data * 10 + xScale[1].x;
            } else if (TextUtils.equals(date, "11")) {
                //存在刻度点
                x = x_data * 11 + xScale[1].x;
            } else if (TextUtils.equals(date, "12")) {
                //存在刻度点
                x = x_data * 12 + xScale[1].x;
            }
            float highValue_y = (yMaxValue - datas.get(j).highValue) * (height - gapSize) / yMaxValue;
            float centerValue_y = (yMaxValue - datas.get(j).centerValue) * (height - gapSize) / yMaxValue;
            float lowValue_y = (yMaxValue - datas.get(j).lowValue) * (height - gapSize) / yMaxValue;
            pointDatas.add(new DataPoint(x - mPillarWidth / 2, highValue_y, centerValue_y, lowValue_y, bean.isShowText));
        }
    }


}
