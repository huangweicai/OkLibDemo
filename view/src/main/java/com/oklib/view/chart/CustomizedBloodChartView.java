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
  * 作者：蓝天
  * 描述：定制血压图
  */
public class CustomizedBloodChartView extends BaseChartView {
   public CustomizedBloodChartView(Context context) {
       super(context);
       init();
   }

   public CustomizedBloodChartView(Context context, @Nullable AttributeSet attrs) {
       super(context, attrs);
       init();
   }

   public CustomizedBloodChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
       super(context, attrs, defStyleAttr);
       init();
   }

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   public CustomizedBloodChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
       super(context, attrs, defStyleAttr, defStyleRes);
       init();
   }

   private float notMedicineDistance;//未服药距离
   private float medicineDistance;//服药距离
   private float medicineRadiusSize;//服药圆大小
   private float textHeght;//预估字体高度
   private float textGapSize;//圆与字体间隙大小
   private float sharpSpoutHeight;//尖嘴高度

   private void init() {
       notMedicineDistance = dip2px(getContext(), 65);
       medicineDistance = dip2px(getContext(), 20);
       medicineRadiusSize = dip2px(getContext(), 3);
       textHeght = dip2px(getContext(), 10);
       mPillarWidth = dip2px(getContext(), 7);
       textGapSize = dip2px(getContext(), 7);
       sharpSpoutHeight = dip2px(getContext(), 3);
   }

   @Override
   protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       if (!isCanvas) {
           return;
       }
       drawData(canvas);
   }

   private int mNoMedicineColor = pinkLightColor;//未服药颜色
   private int mMedicineColor = pinkColor;//服药颜色
   private int mMedicineScaleColor = 0x50FD7CA7;//服药颜色，封闭图形背景色
   private int mNoMedicineScaleColor = 0x90FFBFD4;//未服药颜色，封闭图形背景色
   private String mNoMedicineText = "未服药";
   private String mMedicineText = "服药";
   private String mBloodText = "高压114-128/低压68-78";
   private float mPillarWidth;//柱子宽度

   public CustomizedBloodChartView setMedicineScaleColor(int mMedicineScaleColor) {
       this.mMedicineScaleColor = mMedicineScaleColor;
       return this;
   }

   public CustomizedBloodChartView setNoMedicineScaleColor(int mNoMedicineScaleColor) {
       this.mNoMedicineScaleColor = mNoMedicineScaleColor;
       return this;
   }

   public CustomizedBloodChartView setNoMedicineColor(int mNoMedicineColor) {
       this.mNoMedicineColor = mNoMedicineColor;
       return this;
   }

   public CustomizedBloodChartView setMedicineColor(int mMedicineColor) {
       this.mMedicineColor = mMedicineColor;
       return this;
   }

   public CustomizedBloodChartView setNoMedicineText(String mNoMedicineText) {
       this.mNoMedicineText = mNoMedicineText;
       return this;
   }

   public CustomizedBloodChartView setMedicineText(String mMedicineText) {
       this.mMedicineText = mMedicineText;
       return this;
   }

   public CustomizedBloodChartView setBloodText(String mBloodText) {
       this.mBloodText = mBloodText;
       return this;
   }

   public CustomizedBloodChartView setPillarWidth(float pillarWidth) {
       this.mPillarWidth = pillarWidth;
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
           loadYearCo();
       }
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
               if (i != 2) {
                   //血压这里，最后第二位不显示
                   canvas.drawText(y_scale_text[i], yBottomPoint.x, yScale[i].y, blackPaint);
               }
           }

           if (isShowDatumLine_Y) {
               canvas.drawLine(xScale[1].x, yScale[i].y, width - gapSize, yScale[i].y, blackPaint);
           }
       }

       //绘制数据
       for (int i = 0; i < pointDatas.size(); i++) {
           DataPoint bean = pointDatas.get(i);
           //绘制数据
           if (bean.y_hp[1] > 0) {
               //封闭图形
               linePath.reset();
               linePath.moveTo(bean.x-lineWidth/2, bean.y_hp[0]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_hp[0]+sharpSpoutHeight);
               linePath.lineTo(bean.x+mPillarWidth+lineWidth/2, bean.y_hp[0]);

               linePath.lineTo(bean.x+mPillarWidth+lineWidth/2, bean.y_hp[1]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_hp[1]+sharpSpoutHeight);
               linePath.lineTo(bean.x-lineWidth/2, bean.y_hp[1]);
               linePath.close();
               linePaint.setStyle(Paint.Style.FILL);
               linePaint.setColor(bean.isLight?mNoMedicineScaleColor:mMedicineScaleColor);
               linePaint.setStrokeWidth(0);
               linePaint.setStyle(Paint.Style.FILL);
               canvas.drawPath(linePath, linePaint);

               //高压低值
               linePath.reset();
               linePath.moveTo(bean.x, bean.y_hp[1]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_hp[1]+sharpSpoutHeight);
               linePath.lineTo(bean.x+mPillarWidth, bean.y_hp[1]);
               linePaint.setStrokeWidth(lineWidth);
               linePaint.setStyle(Paint.Style.STROKE);
               linePaint.setColor(bean.isLight?mNoMedicineColor:mMedicineColor);
               canvas.drawPath(linePath, linePaint);
           }
           //高压高值
           if (bean.y_hp[0] > 0) {
               linePath.reset();
               linePath.moveTo(bean.x, bean.y_hp[0]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_hp[0]+sharpSpoutHeight);
               linePath.lineTo(bean.x+mPillarWidth, bean.y_hp[0]);
               linePaint.setStrokeWidth(lineWidth);
               linePaint.setStyle(Paint.Style.STROKE);
               linePaint.setColor(bean.isLight?mNoMedicineColor:mMedicineColor);
               canvas.drawPath(linePath, linePaint);
           }

           if (bean.y_lp[1] > 0) {
               //封闭图形
               linePath.reset();
               linePath.moveTo(bean.x-lineWidth/2, bean.y_lp[0]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_lp[0]-sharpSpoutHeight);
               linePath.lineTo(bean.x+mPillarWidth+lineWidth/2, bean.y_lp[0]);

               linePath.lineTo(bean.x+mPillarWidth+lineWidth/2, bean.y_lp[1]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_lp[1]-sharpSpoutHeight);
               linePath.lineTo(bean.x-lineWidth/2, bean.y_lp[1]);
               linePath.close();
               linePaint.setStyle(Paint.Style.FILL);
               linePaint.setColor(bean.isLight?mNoMedicineScaleColor:mMedicineScaleColor);
               linePaint.setStrokeWidth(0);
               linePaint.setStyle(Paint.Style.FILL);
               canvas.drawPath(linePath, linePaint);

               //低压低值
               linePath.reset();
               linePath.moveTo(bean.x, bean.y_lp[1]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_lp[1]-sharpSpoutHeight);
               linePath.lineTo(bean.x+mPillarWidth, bean.y_lp[1]);
               linePaint.setStrokeWidth(lineWidth);
               linePaint.setStyle(Paint.Style.STROKE);
               linePaint.setColor(bean.isLight?mNoMedicineColor:mMedicineColor);
               canvas.drawPath(linePath, linePaint);
           }
           //低压高值
           if (bean.y_lp[0] > 0) {
               linePath.reset();
               linePath.moveTo(bean.x, bean.y_lp[0]);
               linePath.lineTo(bean.x+mPillarWidth/2, bean.y_lp[0]-sharpSpoutHeight);
               linePath.lineTo(bean.x+mPillarWidth, bean.y_lp[0]);
               linePaint.setStrokeWidth(lineWidth);
               linePaint.setStyle(Paint.Style.STROKE);
               linePaint.setColor(bean.isLight?mNoMedicineColor:mMedicineColor);
               canvas.drawPath(linePath, linePaint);
           }

           if (i == pointDatas.size() - 1) {
               //最后一个显示文本
               blackPaint.setColor(bean.isLight?mNoMedicineColor:mMedicineColor);
               //高压文本
               String fotmatStr = fotmatStr(datas.get(i).y_hp_value[0] + "");
               canvas.drawText(fotmatStr, bean.x+mPillarWidth/2-getStringWidth(linePaint, fotmatStr), bean.y_hp[0]-textHeght/2, blackPaint);
               //低压文本
               fotmatStr = fotmatStr(datas.get(i).y_lp_value[1] + "");
               canvas.drawText(fotmatStr(datas.get(i).y_lp_value[1]+""), bean.x+mPillarWidth/2-getStringWidth(linePaint, fotmatStr), bean.y_lp[1]+textHeght*3/2, blackPaint);
           }
       }

       //绘制顶部信息
       linePaint.setStyle(Paint.Style.FILL);
       blackPaint.setColor(blackColor);
       canvas.drawText(mMedicineText, medicineDistance, yScale[1].y / 3, blackPaint);
       linePaint.setColor(mMedicineColor);
       canvas.drawCircle(medicineDistance - textGapSize, yScale[1].y / 3 - textHeght / 2, medicineRadiusSize, linePaint);
       canvas.drawText(mNoMedicineText, notMedicineDistance, yScale[1].y / 3, blackPaint);
       linePaint.setColor(mNoMedicineColor);
       canvas.drawCircle(notMedicineDistance - textGapSize, yScale[1].y / 3 - textHeght / 2, medicineRadiusSize, linePaint);
       canvas.drawText(mBloodText, width - getStringWidth(blackPaint, mBloodText) - gapSize, yScale[1].y / 3, blackPaint);
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
                   float x = x_data * i + xScale[1].x - mPillarWidth / 2;
                   //高压范围
                   float y_hp_high = (yMaxValue - bean.y_hp_value[0]) * (height - gapSize) / yMaxValue;//高压高值
                   float y_hp_low = (yMaxValue - bean.y_hp_value[1]) * (height - gapSize) / yMaxValue;//高压低值
                   //低压范围
                   float y_lp_high = (yMaxValue - bean.y_lp_value[0]) * (height - gapSize) / yMaxValue;//低压高值
                   float y_lp_low = (yMaxValue - bean.y_lp_value[1]) * (height - gapSize) / yMaxValue;//低压低值
                   pointDatas.add(new DataPoint(x, new float[]{y_hp_high, y_hp_low}, new float[]{y_lp_high, y_lp_low}, bean.isLight));
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
                   float x = x_data * i + xScale[1].x - mPillarWidth / 2;
                   //高压范围
                   float y_hp_high = (yMaxValue - bean.y_hp_value[0]) * (height - gapSize) / yMaxValue;//高压高值
                   float y_hp_low = (yMaxValue - bean.y_hp_value[1]) * (height - gapSize) / yMaxValue;//高压低值
                   //低压范围
                   float y_lp_high = (yMaxValue - bean.y_lp_value[0]) * (height - gapSize) / yMaxValue;//低压高值
                   float y_lp_low = (yMaxValue - bean.y_lp_value[1]) * (height - gapSize) / yMaxValue;//低压低值
                   pointDatas.add(new DataPoint(x, new float[]{y_hp_high, y_hp_low}, new float[]{y_lp_high, y_lp_low}, bean.isLight));
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
                   //高压范围
                   float y_hp_high = (yMaxValue - bean.y_hp_value[0]) * (height - gapSize) / yMaxValue;//高压高值
                   float y_hp_low = (yMaxValue - bean.y_hp_value[1]) * (height - gapSize) / yMaxValue;//高压低值
                   //低压范围
                   float y_lp_high = (yMaxValue - bean.y_lp_value[0]) * (height - gapSize) / yMaxValue;//低压高值
                   float y_lp_low = (yMaxValue - bean.y_lp_value[1]) * (height - gapSize) / yMaxValue;//低压低值
                   pointDatas.add(new DataPoint(x - mPillarWidth / 2, new float[]{y_hp_high, y_hp_low}, new float[]{y_lp_high, y_lp_low}, bean.isLight));
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
           //高压范围
           float y_hp_high = (yMaxValue - bean.y_hp_value[0]) * (height - gapSize) / yMaxValue;//高压高值
           float y_hp_low = (yMaxValue - bean.y_hp_value[1]) * (height - gapSize) / yMaxValue;//高压低值
           //低压范围
           float y_lp_high = (yMaxValue - bean.y_lp_value[0]) * (height - gapSize) / yMaxValue;//低压高值
           float y_lp_low = (yMaxValue - bean.y_lp_value[1]) * (height - gapSize) / yMaxValue;//低压低值
           pointDatas.add(new DataPoint(x - mPillarWidth / 2, new float[]{y_hp_high, y_hp_low}, new float[]{y_lp_high, y_lp_low}, bean.isLight));
       }
   }

}
