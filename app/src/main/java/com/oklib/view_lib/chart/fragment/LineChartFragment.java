package com.oklib.view_lib.chart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oklib.R;
import com.oklib.view.chart.BaseChartView;
import com.oklib.view.chart.LineChartView;

import java.util.ArrayList;
import java.util.List;


/**
 * 时间：2017/8/16
 * 作者：蓝天
 * 描述：线形图
 */

public class LineChartFragment extends Fragment {
    private LineChartView chartView1;
    private LineChartView chartView2;
    private LineChartView chartView3;
    private LineChartView chartView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linechart, null, false);
        chartView1 = (LineChartView) view.findViewById(R.id.weightChart1);
        chartView2 = (LineChartView) view.findViewById(R.id.weightChart2);
        chartView3 = (LineChartView) view.findViewById(R.id.weightChart3);
        chartView4 = (LineChartView) view.findViewById(R.id.weightChart4);
        return view;
    }

    public void showChart() {
        //注意：图表使用一定要延迟使用
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chartView1
                        .setLineColor(0xffff0000)
                        .setTextColor(0xff0000ff)
                        .setCircleColor(0xff00ff00)
                        .setTextSize(24)
                        .setCircleRadiusSize(14)
                        .setLineSize(10)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.DAY)
                        .setYMaxValue(150)
                        .setUnitValueText("Kg")
                        .refreshData(getDayData());
                chartView2
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.WEEK)
                        .setYMaxValue(150)
                        .setUnitValueText("Kg")
                        .refreshData(getWeekData());
                chartView3
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.MONTH)
                        .setYMaxValue(150)
                        .setUnitValueText("Kg")
                        .refreshData(getMonthData());
                chartView4
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.YEAR)
                        .setYMaxValue(150)
                        .setUnitValueText("Kg")
                        .refreshData(getYearData());
            }
        }, 800);
    }

    private List<BaseChartView.DataBean> getDayData() {
        //日数据源
        List<BaseChartView.DataBean> datas1 = new ArrayList<>();
        datas1.add(new BaseChartView.DataBean("2017-08-04 00:17:00", 60f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 06:17:00", 122f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 12:17:00", 86f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 18:17:00", 108f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 24:17:00", 92f));
        datas1.add(new BaseChartView.DataBean("20170804 241700", 92f));
        return datas1;
    }

    private List<BaseChartView.DataBean> getWeekData() {
        //周数据源
        List<BaseChartView.DataBean> datas2 = new ArrayList<>();
        datas2.add(new BaseChartView.DataBean("2017-08-21 16:17:00", 60f));
        datas2.add(new BaseChartView.DataBean("2017-08-22 16:17:00", 86f));
        datas2.add(new BaseChartView.DataBean("2017-08-23 16:17:00", 86f));
        datas2.add(new BaseChartView.DataBean("2017-08-24 16:17:00", 68f));
        datas2.add(new BaseChartView.DataBean("2017-08-25 16:17:00", 72f));
        datas2.add(new BaseChartView.DataBean("2017-08-26 16:17:00", 112f));
        return datas2;
    }

    private List<BaseChartView.DataBean> getMonthData() {
        //月数据源
        List<BaseChartView.DataBean> datas3 = new ArrayList<>();
        datas3.add(new BaseChartView.DataBean("2017-08-01 16:17:00", 60f));
        datas3.add(new BaseChartView.DataBean("2017-08-05 16:17:00", 116f));
        datas3.add(new BaseChartView.DataBean("2017-08-10 16:17:00", 98f));
        datas3.add(new BaseChartView.DataBean("2017-08-15 16:17:00", 52f));
        datas3.add(new BaseChartView.DataBean("2017-08-20 16:17:00", 102f));
        datas3.add(new BaseChartView.DataBean("2017-08-25 16:17:00", 82f));
        datas3.add(new BaseChartView.DataBean("2017-08-30 16:17:00", 132f));
        return datas3;
    }

    private List<BaseChartView.DataBean> getYearData() {
        //年数据源
        List<BaseChartView.DataBean> datas4 = new ArrayList<>();
        datas4.add(new BaseChartView.DataBean("2017-01-01 16:17:00", 60f));
        datas4.add(new BaseChartView.DataBean("2017-02-03 16:17:00", 116f));
        datas4.add(new BaseChartView.DataBean("2017-03-03 16:17:00", 86f));
        datas4.add(new BaseChartView.DataBean("2017-06-06 16:17:00", 72f));
        datas4.add(new BaseChartView.DataBean("2017-09-09 16:17:00", 122f));
        datas4.add(new BaseChartView.DataBean("2017-12-12 16:17:00", 82f));
        return datas4;
    }
}
