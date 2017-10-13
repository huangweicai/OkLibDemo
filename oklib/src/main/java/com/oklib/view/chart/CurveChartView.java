package com.oklib.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
  * 时间：2017/8/15
  * 作者：黄伟才
  * 简书：http://www.jianshu.com/p/87e7392a16ff
  * github：https://github.com/huangweicai/OkLibDemo
  * 描述：曲线图
  */
public class CurveChartView extends BaseChartView {
   public CurveChartView(Context context) {
       super(context);
   }

   public CurveChartView(Context context, @Nullable AttributeSet attrs) {
       super(context, attrs);
   }

   public CurveChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
       super(context, attrs, defStyleAttr);
   }

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   public CurveChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
       super(context, attrs, defStyleAttr, defStyleRes);
   }

   protected List<DataPoint> maxPoints = new ArrayList<>();//绘制坐标点
   protected List<DataPoint> avgPoints = new ArrayList<>();//绘制坐标点
   protected List<DataPoint> minPoints = new ArrayList<>();//绘制坐标点

   @Override
   protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       if (!isCanvas) {
           return;
       }
       drawData(canvas);
   }

   private int mMaxLineColor = yellowColor;//最高线颜色
   private int mMaxTextColor = blackColor;//最高y轴刻度字体颜色
   private int mAvgLineColor = pinkColor;//平均线颜色
   private int mAvgTextColor = blackColor;//平均y轴刻度字体颜色
   private int mMinLineColor = blueColor;//最低线颜色
   private int mMinTextColor = blackColor;//最低y轴刻度字体颜色
   private int mLineWidth = lineWidth;//绘线宽度

   public CurveChartView seMaxLineColor(int mMaxLineColor) {
       this.mMaxLineColor = mMaxLineColor;
       return this;
   }

   public CurveChartView setMaxTextColor(int mMaxTextColor) {
       this.mMaxTextColor = mMaxTextColor;
       return this;
   }

   public CurveChartView setAvgLineColor(int mAvgLineColor) {
       this.mAvgLineColor = mAvgLineColor;
       return this;
   }

   public CurveChartView setAvgTextColor(int mAvgTextColor) {
       this.mAvgTextColor = mAvgTextColor;
       return this;
   }

   public CurveChartView setMinLineColor(int mMinLineColor) {
       this.mMinLineColor = mMinLineColor;
       return this;
   }

   public CurveChartView setMinTextColor(int mMinTextColor) {
       this.mMinTextColor = mMinTextColor;
       return this;
   }

   public CurveChartView setLineWidht(int lineWidth) {
       this.mLineWidth = lineWidth;
       return this;
   }

   /**
    * 刷新数据
    */
   public void refreshData(List<DataBean> datas) {
       this.maxPoints.clear();
       this.maxMidPoints.clear();
       this.maxMidMidPoints.clear();

       this.avgPoints.clear();
       this.avgMidPoints.clear();
       this.avgMidMidPoints.clear();

       this.minPoints.clear();
       this.minMidPoints.clear();
       this.minMidMidPoints.clear();

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
           loadYearCo();
       }
       //初始化贝塞尔曲线的控制点
       //最高
       initMaxMidPoints(this.maxPoints);
       initMaxMidMidPoints(this.maxMidPoints);
       initMaxControlPoints(this.maxPoints, this.maxMidPoints, this.maxMidMidPoints);
       //平均
       initAvgMidPoints(this.avgPoints);
       initAvgMidMidPoints(this.avgMidPoints);
       initAvgControlPoints(this.avgPoints, this.avgMidPoints, this.avgMidMidPoints);
       //最低
       initMinMidPoints(this.minPoints);
       initMinMidMidPoints(this.minMidPoints);
       initMinControlPoints(this.minPoints, this.minMidPoints, this.minMidMidPoints);

       isCanvas = true;
       invalidate();
   }

   /**
    * 作者：黄伟才
    * 描述：绘制刻度文本，数据轨迹
    */
   private void drawData(Canvas canvas) {
       if (TextUtils.equals(type, DAY)) {
           canvas.drawText("时间", xScale[0].x, xScale[0].y + textHeight, blackPaint);
       }

       //绘制x轴刻度文本，x轴头尾多一个刻度位置
       for (int i = 0; i < xScaleCount - 1; i++) {//8
           if (i < xScaleCount - 1 - 1)
               canvas.drawText(x_scale_text[i], x_scale_text[i].length() > 2 ? xScale[i + 1].x - blackPaint.measureText(x_scale_text[i]) : xScale[i + 1].x - blackPaint.measureText(x_scale_text[i]) / 2, xScale[i].y + textHeight, blackPaint);

           if (isShowDatumLine_X) {
               canvas.drawLine(xScale[i + 1].x, 0, xScale[i + 1].x, height - gapSize, blackPaint);
           }
       }
       //绘制y轴刻度文本，y轴顶部多一个刻度位置
       for (int i = 0; i < yScaleCount; i++) {//3
           if (i != 0) {
               if (i == 1) {
                   blackPaint.setColor(mMaxTextColor);
                   canvas.drawText("最高", yBottomPoint.x - getStringWidth(blackPaint, "最高") / 2, yScale[1].y / 2 + getStringHeight(blackPaint) / 4, blackPaint);
               } else if (i == 2) {
                   blackPaint.setColor(mAvgTextColor);
                   canvas.drawText("平均", yBottomPoint.x - getStringWidth(blackPaint, "最高") / 2, yScale[3].y / 2 + getStringHeight(blackPaint) / 4, blackPaint);
               } else if (i == 3) {
                   blackPaint.setColor(mMinTextColor);
                   canvas.drawText("最低", yBottomPoint.x - getStringWidth(blackPaint, "最高") / 2, yScale[3].y - yScale[1].y / 2 + getStringHeight(blackPaint) / 4, blackPaint);
               }
           }

           if (isShowDatumLine_Y) {
               canvas.drawLine(xScale[1].x, yScale[i].y, width - gapSize, yScale[i].y, blackPaint);
           }
       }

       //绘制数据
       if (maxPoints.size() == 1) {
           //一个数据绘点
           DataPoint maxBean = maxPoints.get(0);
           linePaint.setStyle(Paint.Style.FILL);
           linePaint.setColor(mMaxLineColor);
           canvas.drawCircle(maxBean.x, maxBean.max_y, 4, linePaint);

           DataPoint avgBean = maxPoints.get(0);
           linePaint.setStyle(Paint.Style.FILL);
           linePaint.setColor(mMaxLineColor);
           canvas.drawCircle(avgBean.x, avgBean.avg_y, 4, linePaint);

           DataPoint minBean = maxPoints.get(0);
           linePaint.setStyle(Paint.Style.FILL);
           linePaint.setColor(mMaxLineColor);
           canvas.drawCircle(minBean.x, minBean.min_y, 4, linePaint);

       } else if (maxPoints.size() == 2) {
           //两个数据绘线
           DataPoint maxBean0 = maxPoints.get(0);
           DataPoint maxBean1 = maxPoints.get(1);
           linePaint.setColor(mMaxLineColor);
           linePaint.setStyle(Paint.Style.STROKE);// 空心（保持线条）
           linePath.moveTo(maxBean0.x, maxBean0.max_y);// 起点
           linePath.quadTo((maxBean1.x + maxBean0.x) / 2, (maxBean0.max_y + maxBean1.max_y) / 2,// 控制点
                   maxBean1.x, maxBean1.max_y);
           canvas.drawPath(linePath, linePaint);

           DataPoint avgBean0 = maxPoints.get(0);
           DataPoint avgBean1 = maxPoints.get(1);
           linePaint.setColor(mAvgLineColor);
           linePaint.setStyle(Paint.Style.STROKE);// 空心（保持线条）
           linePath.moveTo(avgBean0.x, avgBean0.max_y);// 起点
           linePath.quadTo((avgBean1.x + avgBean0.x) / 2, (avgBean0.max_y + avgBean1.max_y) / 2,// 控制点
                   avgBean1.x, avgBean1.max_y);
           canvas.drawPath(linePath, linePaint);

           DataPoint minBean0 = maxPoints.get(0);
           DataPoint minBean1 = maxPoints.get(1);
           linePaint.setColor(mMinLineColor);
           linePaint.setStyle(Paint.Style.STROKE);// 空心（保持线条）
           linePath.moveTo(minBean0.x, minBean0.max_y);// 起点
           linePath.quadTo((minBean1.x + minBean0.x) / 2, (minBean0.max_y + minBean1.max_y) / 2,// 控制点
                   minBean1.x, minBean1.max_y);
           canvas.drawPath(linePath, linePaint);
       } else {
           linePaint.setStrokeWidth(mLineWidth);
           linePaint.setStyle(Paint.Style.STROKE);// 空心（保持线条）
           // 最高曲线
           linePath.reset();
           linePaint.setColor(mMaxLineColor);
           drawMaxBezier(canvas);
           // 平均曲线
           linePath.reset();
           linePaint.setColor(mAvgLineColor);
           drawAvgBezier(canvas);
           // 最低曲线
           linePath.reset();
           linePaint.setColor(mMinLineColor);
           drawMinBezier(canvas);
       }

   }

   /**
    * 作者：黄伟才
    * 描述：装载x、y刻度坐标
    */
   private void loadDayCo() {
       xScaleCount = 7;//x日刻度数
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

       x_scale_text[0] = "0";
       x_scale_text[1] = "6";
       x_scale_text[2] = "12";
       x_scale_text[3] = "18";
       x_scale_text[4] = "24";
       float x_data = (width - gapSize * 2 - x_text * 2) / 24f;
       //装载刻度显示的数据
       for (int i = 0; i < 25; i++) {
           for (int j = 0; j < datas.size(); j++) {
               DataBean bean = datas.get(j);
               //2017-08-04 09:17:00
               String date = bean.date.substring(11, 13);//截取“时”  09
               if (Integer.valueOf(date) == i) {
                   //存在刻度点
                   float max_x = x_data * i + xScale[1].x;
                   float max_y = (yMaxValue - datas.get(j).maxValue) * (height - gapSize) / yMaxValue;
                   maxPoints.add(new DataPoint(max_x, max_y));

                   float avg_x = x_data * i + xScale[1].x;
                   float avg_y = (yMaxValue - datas.get(j).avgValue) * (height - gapSize) / yMaxValue;
                   avgPoints.add(new DataPoint(avg_x, avg_y));

                   float min_x = x_data * i + xScale[1].x;
                   float min_y = (yMaxValue - datas.get(j).minValue) * (height - gapSize) / yMaxValue;
                   minPoints.add(new DataPoint(min_x, min_y));
               }
           }
       }

   }

   private void loadWeekCo() {
       xScaleCount = 9;//x周刻度数
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

       float x_data = (width - gapSize * 2 - x_text * 2) / 6f;
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
           Log.d("TAG", "weedDate:" + weedDate);
           for (int j = 0; j < datas.size(); j++) {
               DataBean bean = datas.get(j);
               //2017-08-04 09:17:00
               String date = bean.date.substring(0, 10);//截取“年月日”  04
               if (TextUtils.equals(date, weedDate)) {
                   //存在刻度点
                   float max_x = x_data * i + xScale[1].x;
                   float max_y = (yMaxValue - datas.get(j).maxValue) * (height - gapSize) / yMaxValue;
                   maxPoints.add(new DataPoint(max_x, max_y));

                   float avg_x = x_data * i + xScale[1].x;
                   float avg_y = (yMaxValue - datas.get(j).avgValue) * (height - gapSize) / yMaxValue;
                   avgPoints.add(new DataPoint(avg_x, avg_y));

                   float min_x = x_data * i + xScale[1].x;
                   float min_y = (yMaxValue - datas.get(j).minValue) * (height - gapSize) / yMaxValue;
                   minPoints.add(new DataPoint(min_x, min_y));
               }
           }
       }
   }

   /**
    * 作者：黄伟才
    * 描述：遗留问题，数据与基准线不对齐
    */
   private void loadMonthCo() {
       xScaleCount = 9;//x月刻度数
       yScaleCount = 4;//y月刻度数

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

       int monthDayCount = ChartDateUtil.getCurrentMonthDay();//获取当月的天数
       x_scale_text[0] = ChartDateUtil.getFirstDayOfMonth("M/d");//获取本月第一天
       x_scale_text[1] = "5";
       x_scale_text[2] = "10";
       x_scale_text[3] = "15";
       x_scale_text[4] = "20";
       x_scale_text[5] = "25";
       x_scale_text[6] = "30";
       float x_data = (width - gapSize * 2 - x_text * 2) / 30f;
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
                   //存在刻度点
                   float x;
                   if (i == 1) {
                       x = xScale[1].x;
                   } else if (i == 2) {
                       x = x_text * 1 / 4 + xScale[1].x;
                   } else if (i == 3) {
                       x = x_text * 2 / 4 + xScale[1].x;
                   } else if (i == 4) {
                       x = x_text * 3 / 4 + xScale[1].x;
                   } else if (i == 5) {
                       x = x_text + xScale[1].x;
                   } else {
                       x = x_data * i + xScale[1].x;
                   }

                   float max_y = (yMaxValue - datas.get(j).maxValue) * (height - gapSize) / yMaxValue;
                   maxPoints.add(new DataPoint(x, max_y));

                   float avg_y = (yMaxValue - datas.get(j).avgValue) * (height - gapSize) / yMaxValue;
                   avgPoints.add(new DataPoint(x, avg_y));

                   float min_y = (yMaxValue - datas.get(j).minValue) * (height - gapSize) / yMaxValue;
                   minPoints.add(new DataPoint(x, min_y));
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
           float max_y = (yMaxValue - datas.get(j).maxValue) * (height - gapSize) / yMaxValue;
           maxPoints.add(new DataPoint(x, max_y));

           float avg_y = (yMaxValue - datas.get(j).avgValue) * (height - gapSize) / yMaxValue;
           avgPoints.add(new DataPoint(x, avg_y));

           float min_y = (yMaxValue - datas.get(j).minValue) * (height - gapSize) / yMaxValue;
           minPoints.add(new DataPoint(x, min_y));
       }
   }


   //*****************绘制贝塞尔曲线相关=================
   //中点集合
   private List<DataPoint> maxMidPoints = new ArrayList<>();
   //中点的中点集合
   private List<DataPoint> maxMidMidPoints = new ArrayList<>();
   //移动后的点集合(控制点)
   private List<DataPoint> maxControlPoints = new ArrayList<>();

   //中点集合
   private List<DataPoint> avgMidPoints = new ArrayList<>();
   //中点的中点集合
   private List<DataPoint> avgMidMidPoints = new ArrayList<>();
   //移动后的点集合(控制点)
   private List<DataPoint> avgControlPoints = new ArrayList<>();

   //中点集合
   private List<DataPoint> minMidPoints = new ArrayList<>();
   //中点的中点集合
   private List<DataPoint> minMidMidPoints = new ArrayList<>();
   //移动后的点集合(控制点)
   private List<DataPoint> minControlPoints = new ArrayList<>();


   /**
    * 初始化中点集合
    */
   private void initMaxMidPoints(List<DataPoint> points) {
       maxMidPoints.clear();
       for (int i = 0; i < points.size(); i++) {
           DataPoint midPoint = null;

           if (i == points.size() - 1) {
               return;
           } else {
               midPoint = new DataPoint((points.get(i).x + points.get(i + 1).x) / 2, (points.get(i).y + points.get(i + 1).y) / 2);
           }
           maxMidPoints.add(midPoint);
       }
   }

   /**
    * 初始化中点的中点集合
    */
   private void initMaxMidMidPoints(List<DataPoint> midPoints) {
       maxMidMidPoints.clear();
       for (int i = 0; i < midPoints.size(); i++) {
           DataPoint midMidPoint = null;
           if (i == midPoints.size() - 1) {
               return;
           } else {
               midMidPoint = new DataPoint((midPoints.get(i).x + midPoints.get(i + 1).x) / 2, (midPoints.get(i).y + midPoints.get(i + 1).y) / 2);
           }
           maxMidMidPoints.add(midMidPoint);
       }
   }

   /**
    * 初始化控制点集合
    */
   private void initMaxControlPoints(List<DataPoint> points, List<DataPoint> midPoints, List<DataPoint> midMidPoints) {
       maxControlPoints.clear();
       for (int i = 0; i < points.size(); i++) {
           if (i == 0 || i == points.size() - 1) {
               continue;
           } else {
               DataPoint before = new DataPoint();
               DataPoint after = new DataPoint();
               before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
               before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
               after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
               after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
               maxControlPoints.add(before);
               maxControlPoints.add(after);
           }
       }
   }

   /**
    * 画贝塞尔曲线
    */
   private void drawMaxBezier(Canvas canvas) {
       for (int i = 0; i < maxPoints.size(); i++) {
           if (i == 0) {// 第一条为二阶贝塞尔
               linePath.moveTo(maxPoints.get(i).x, maxPoints.get(i).y);// 起点
               linePath.quadTo(maxControlPoints.get(i).x, maxControlPoints.get(i).y,// 控制点
                       maxPoints.get(i + 1).x, maxPoints.get(i + 1).y);
           } else if (i < maxPoints.size() - 2) {// 三阶贝塞尔
               linePath.cubicTo(maxControlPoints.get(2 * i - 1).x, maxControlPoints.get(2 * i - 1).y,// 控制点
                       maxControlPoints.get(2 * i).x, maxControlPoints.get(2 * i).y,// 控制点
                       maxPoints.get(i + 1).x, maxPoints.get(i + 1).y);// 终点
           } else if (i == maxPoints.size() - 2) {// 最后一条为二阶贝塞尔
               linePath.moveTo(maxPoints.get(i).x, maxPoints.get(i).y);// 起点
               linePath.quadTo(maxControlPoints.get(maxControlPoints.size() - 1).x, maxControlPoints.get(maxControlPoints.size() - 1).y,
                       maxPoints.get(i + 1).x, maxPoints.get(i + 1).y);// 终点
           }
       }
       canvas.drawPath(linePath, linePaint);
   }

   //-----------------------------------

   /**
    * 初始化中点集合
    */
   private void initAvgMidPoints(List<DataPoint> points) {
       avgMidPoints.clear();
       for (int i = 0; i < points.size(); i++) {
           DataPoint midPoint = null;

           if (i == points.size() - 1) {
               return;
           } else {
               midPoint = new DataPoint((points.get(i).x + points.get(i + 1).x) / 2, (points.get(i).y + points.get(i + 1).y) / 2);
           }
           avgMidPoints.add(midPoint);
       }
   }

   /**
    * 初始化中点的中点集合
    */
   private void initAvgMidMidPoints(List<DataPoint> midPoints) {
       avgMidMidPoints.clear();
       for (int i = 0; i < midPoints.size(); i++) {
           DataPoint midMidPoint = null;
           if (i == midPoints.size() - 1) {
               return;
           } else {
               midMidPoint = new DataPoint((midPoints.get(i).x + midPoints.get(i + 1).x) / 2, (midPoints.get(i).y + midPoints.get(i + 1).y) / 2);
           }
           avgMidMidPoints.add(midMidPoint);
       }
   }

   /**
    * 初始化控制点集合
    */
   private void initAvgControlPoints(List<DataPoint> points, List<DataPoint> midPoints, List<DataPoint> midMidPoints) {
       avgControlPoints.clear();
       for (int i = 0; i < points.size(); i++) {
           if (i == 0 || i == points.size() - 1) {
               continue;
           } else {
               DataPoint before = new DataPoint();
               DataPoint after = new DataPoint();
               before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
               before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
               after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
               after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
               avgControlPoints.add(before);
               avgControlPoints.add(after);
           }
       }
   }

   private void drawAvgBezier(Canvas canvas) {
       for (int i = 0; i < avgPoints.size(); i++) {
           if (i == 0) {// 第一条为二阶贝塞尔
               linePath.moveTo(avgPoints.get(i).x, avgPoints.get(i).y);// 起点
               linePath.quadTo(avgControlPoints.get(i).x, avgControlPoints.get(i).y,// 控制点
                       avgPoints.get(i + 1).x, avgPoints.get(i + 1).y);
           } else if (i < avgPoints.size() - 2) {// 三阶贝塞尔
               linePath.cubicTo(avgControlPoints.get(2 * i - 1).x, avgControlPoints.get(2 * i - 1).y,// 控制点
                       avgControlPoints.get(2 * i).x, avgControlPoints.get(2 * i).y,// 控制点
                       avgPoints.get(i + 1).x, avgPoints.get(i + 1).y);// 终点
           } else if (i == avgPoints.size() - 2) {// 最后一条为二阶贝塞尔
               linePath.moveTo(avgPoints.get(i).x, avgPoints.get(i).y);// 起点
               linePath.quadTo(avgControlPoints.get(avgControlPoints.size() - 1).x, avgControlPoints.get(avgControlPoints.size() - 1).y,
                       avgPoints.get(i + 1).x, avgPoints.get(i + 1).y);// 终点
           }
       }
       canvas.drawPath(linePath, linePaint);
   }

   //---------------------------------------

   /**
    * 初始化中点集合
    */
   private void initMinMidPoints(List<DataPoint> points) {
       minMidPoints.clear();
       for (int i = 0; i < points.size(); i++) {
           DataPoint midPoint = null;

           if (i == points.size() - 1) {
               return;
           } else {
               midPoint = new DataPoint((points.get(i).x + points.get(i + 1).x) / 2, (points.get(i).y + points.get(i + 1).y) / 2);
           }
           minMidPoints.add(midPoint);
       }
   }

   /**
    * 初始化中点的中点集合
    */
   private void initMinMidMidPoints(List<DataPoint> midPoints) {
       minMidMidPoints.clear();
       for (int i = 0; i < midPoints.size(); i++) {
           DataPoint midMidPoint = null;
           if (i == midPoints.size() - 1) {
               return;
           } else {
               midMidPoint = new DataPoint((midPoints.get(i).x + midPoints.get(i + 1).x) / 2, (midPoints.get(i).y + midPoints.get(i + 1).y) / 2);
           }
           minMidMidPoints.add(midMidPoint);
       }
   }

   /**
    * 初始化控制点集合
    */
   private void initMinControlPoints(List<DataPoint> points, List<DataPoint> midPoints, List<DataPoint> midMidPoints) {
       minControlPoints.clear();
       for (int i = 0; i < points.size(); i++) {
           if (i == 0 || i == points.size() - 1) {
               continue;
           } else {
               DataPoint before = new DataPoint();
               DataPoint after = new DataPoint();
               before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
               before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
               after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
               after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
               minControlPoints.add(before);
               minControlPoints.add(after);
           }
       }
   }

   private void drawMinBezier(Canvas canvas) {
       for (int i = 0; i < minPoints.size(); i++) {
           if (i == 0) {// 第一条为二阶贝塞尔
               linePath.moveTo(minPoints.get(i).x, minPoints.get(i).y);// 起点
               linePath.quadTo(minControlPoints.get(i).x, minControlPoints.get(i).y,// 控制点
                       minPoints.get(i + 1).x, minPoints.get(i + 1).y);
           } else if (i < minPoints.size() - 2) {// 三阶贝塞尔
               linePath.cubicTo(minControlPoints.get(2 * i - 1).x, minControlPoints.get(2 * i - 1).y,// 控制点
                       minControlPoints.get(2 * i).x, minControlPoints.get(2 * i).y,// 控制点
                       minPoints.get(i + 1).x, minPoints.get(i + 1).y);// 终点
           } else if (i == minPoints.size() - 2) {// 最后一条为二阶贝塞尔
               linePath.moveTo(minPoints.get(i).x, minPoints.get(i).y);// 起点
               linePath.quadTo(minControlPoints.get(minControlPoints.size() - 1).x, minControlPoints.get(minControlPoints.size() - 1).y,
                       minPoints.get(i + 1).x, minPoints.get(i + 1).y);// 终点
           }
       }
       canvas.drawPath(linePath, linePaint);
   }
}
