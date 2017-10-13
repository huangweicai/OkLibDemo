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

import java.util.List;


/**
  * 时间：2017/8/15
  * 作者：黄伟才
  * 简书：http://www.jianshu.com/p/87e7392a16ff
  * github：https://github.com/huangweicai/OkLibDemo
  * 描述：折线图
  */
public class LineChartView extends BaseChartView {
   public LineChartView(Context context) {
       super(context);
       init();
   }

   public LineChartView(Context context, @Nullable AttributeSet attrs) {
       super(context, attrs);
       init();
   }

   public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
       super(context, attrs, defStyleAttr);
       init();
   }

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
       super(context, attrs, defStyleAttr, defStyleRes);
       init();
   }

   private void init() {

   }

   @Override
   protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       if (!isCanvas) {
           return;
       }
       drawData(canvas);
   }

   private int mLineColor = greenColor;//绘线颜色
   private int mTextColor = greenColor;//绘字体颜色
   private int mCircleColor = greenColor;//小圆颜色
   private int mCircleRadiusSize = radiusSize;//小圆半径
   private int mTextSize = textSize;//字体大小
   private int mLineWidth = lineWidth;//绘线宽度

   public LineChartView setLineColor(int mLineColor) {
       this.mLineColor = mLineColor;
       return this;
   }

   public LineChartView setTextColor(int mTextColor) {
       this.mTextColor = mTextColor;
       return this;
   }

   public LineChartView setCircleColor(int mCircleColor) {
       this.mCircleColor = mCircleColor;
       return this;
   }

   public LineChartView setCircleRadiusSize(int mCircleRadiusSize) {
       this.mCircleRadiusSize = mCircleRadiusSize;
       return this;
   }

   public LineChartView setTextSize(int mTextSize) {
       this.mTextSize = mTextSize;
       return this;
   }

   public LineChartView setLineSize(int lineWidht) {
       this.mLineWidth = lineWidht;
       return this;
   }

   /**
    * 作者：黄伟才
    * 描述：刷新数据，该方法放在链式写法最后
    */
   public void refreshData(List<DataBean> datas) {
       this.pointDatas.clear();

       this.datas.clear();
       this.datas.addAll(datas);

       if (lineAnim.isRunning()) {
           lineAnim.cancel();
       }
       animValue = 0;

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
       lineAnim.start();
       isCanvas = true;
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
           if (i != 0)
               canvas.drawText(y_scale_text[i], yBottomPoint.x, yScale[i].y, blackPaint);

           if (isShowDatumLine_Y) {
               canvas.drawLine(xScale[1].x, yScale[i].y, width - gapSize, yScale[i].y, blackPaint);
           }
       }

       //绘制数据
       linePath.reset();
       if (pointDatas.size() == 1) {
           DataPoint bean = pointDatas.get(0);
           linePaint.setColor(mCircleColor);//圆颜色
           linePaint.setStrokeWidth(mLineWidth);
           canvas.drawCircle(bean.x, bean.y - mCircleRadiusSize / 2, mCircleRadiusSize, linePaint);
           linePaint.setColor(mTextColor);//字体颜色
           linePaint.setTextSize(textSize);
           linePaint.setStrokeWidth(lineWidth / 2);
           linePaint.setStyle(Paint.Style.FILL);
           String lastText = fotmatStr(datas.get(0).value + unitText);
           canvas.drawText(lastText, bean.x - blackPaint.measureText(lastText) / 2, bean.y - mCircleRadiusSize * 2, linePaint);
       } else if (pointDatas.size() > 1) {
           linePaint.setColor(mLineColor);
           for (int i = 0; i < pointDatas.size(); i++) {
               DataPoint bean = pointDatas.get(i);

               float x = bean.x;
               float y = bean.y;

               if (i < datas.size() - 2) {
                   cacheX = x;
                   cacheY = y;
               }

               if (i == 0) {
                   linePath.moveTo(x, y);
               } else {
                   if (i == datas.size() - 1) {
                       //Math.sqrt(double n)//平方根
                       //Math.pow(x, y)//x的y次方
                       double powX = Math.pow(x - cacheX, 2);//x距离平方
                       double powY = Math.pow(y - cacheY, 2);//y距离平方
                       double obliqueLineSize = Math.sqrt(powX + powY);//斜线长度
                       double offsetX = mCircleRadiusSize * (x - cacheX) / obliqueLineSize;
                       double offsetY = mCircleRadiusSize * (y - cacheY) / obliqueLineSize;
                       linePath.lineTo((float) (x - offsetX), (float) (y + offsetY));//最后计算偏移

                       if (animValue == 1) {
                           //绘制空心圆
                           linePaint.setColor(mCircleColor);//圆颜色
                           linePaint.setTextSize(mTextSize);
                           linePaint.setStrokeWidth(mLineWidth);
                           canvas.drawCircle(x, y - mCircleRadiusSize / 2, mCircleRadiusSize, linePaint);
                           linePaint.setColor(mTextColor);//字体颜色
                           linePaint.setTextSize(textSize);
                           linePaint.setStrokeWidth(lineWidth / 2);
                           linePaint.setStyle(Paint.Style.FILL);
                           String lastText = fotmatStr(datas.get(i).value + unitText);
                           canvas.drawText(lastText, x - blackPaint.measureText(lastText) / 2, y - mCircleRadiusSize * 2, linePaint);
                       }
                   } else {
                       linePath.lineTo(x, y);
                   }
               }

           }
           linePaint.setColor(mLineColor);
           linePaint.setStrokeWidth(mLineWidth);
           linePaint.setStyle(Paint.Style.STROKE);
           mPathMeasure.setPath(linePath, false);
           mPathMeasure.getSegment(0, animValue * mPathMeasure.getLength(), mPathCircleDst, true);
           canvas.drawPath(mPathCircleDst, linePaint);
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
                   float x = x_data * i + xScale[1].x;
                   float y = (yMaxValue - datas.get(j).value) * (height - gapSize) / yMaxValue;
                   pointDatas.add(new DataPoint(x, y));
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
                   float x = x_data * i + xScale[1].x;
                   float y = (yMaxValue - datas.get(j).value) * (height - gapSize) / yMaxValue;
                   pointDatas.add(new DataPoint(x, y));
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
       float x_data = ((width - gapSize * 2) - x_text * 2) / 30;
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
                   float y = (yMaxValue - datas.get(j).value) * (height - gapSize) / yMaxValue;
                   pointDatas.add(new DataPoint(x, y));
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
           y = (yMaxValue - datas.get(j).value) * (height - gapSize) / yMaxValue;
           pointDatas.add(new DataPoint(x, y));
       }
   }

}
