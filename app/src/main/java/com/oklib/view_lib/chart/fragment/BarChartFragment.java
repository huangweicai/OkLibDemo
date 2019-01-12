package com.oklib.view_lib.chart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oklib.R;
import com.oklib.view.chart.BarChartView;
import com.oklib.view.chart.BaseChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2017/8/16
 * 作者：蓝天
 * 描述：柱状图
 */

public class BarChartFragment extends Fragment {
    private BarChartView chartView1;
    private BarChartView chartView2;
    private BarChartView chartView3;
    private BarChartView chartView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barchart, null, false);
        chartView1 = (BarChartView) view.findViewById(R.id.weightChart1);
        chartView2 = (BarChartView) view.findViewById(R.id.weightChart2);
        chartView3 = (BarChartView) view.findViewById(R.id.weightChart3);
        chartView4 = (BarChartView) view.findViewById(R.id.weightChart4);
        return view;
    }

    public void showChart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chartView1
                        .setPillarWidth(30f)
                        .setPillarColor(0x90ff0000)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.DAY)
                        .setYMaxValue(30)
                        .setUnitValueText("Km")
                        .refreshData(getDayData());
                chartView2
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.WEEK)
                        .setYMaxValue(30)
                        .setUnitValueText("Km")
                        .refreshData(getWeekData());
                chartView3
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.MONTH)
                        .setYMaxValue(30)
                        .setUnitValueText("Km")
                        .refreshData(getMonthData());
                chartView4
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.YEAR)
                        .setYMaxValue(30)
                        .setUnitValueText("Km")
                        .refreshData(getYearData());
            }
        }, 800);
    }

    private List<BaseChartView.DataBean> getDayData() {
        //日数据源
        List<BaseChartView.DataBean> datas1 = new ArrayList<>();
        datas1.add(new BaseChartView.DataBean("2017-08-04 00:17:00", 5f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 06:17:00", 12f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 12:17:00", 6f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 18:17:00", 18f));
        datas1.add(new BaseChartView.DataBean("2017-08-04 24:17:00", 12f));
        return datas1;
    }

    private List<BaseChartView.DataBean> getWeekData() {
        //周数据源
        List<BaseChartView.DataBean> datas2 = new ArrayList<>();
        datas2.add(new BaseChartView.DataBean("2017-08-21 16:17:00", 6f));
        datas2.add(new BaseChartView.DataBean("2017-08-22 16:17:00", 16f));
        datas2.add(new BaseChartView.DataBean("2017-08-23 16:17:00", 3f));
        datas2.add(new BaseChartView.DataBean("2017-08-24 16:17:00", 18f));
        datas2.add(new BaseChartView.DataBean("2017-08-25 16:17:00", 12f));
        datas2.add(new BaseChartView.DataBean("2017-08-26 16:17:00", 22f));
        return datas2;
    }

    private List<BaseChartView.DataBean> getMonthData() {
        //月数据源
        List<BaseChartView.DataBean> datas3 = new ArrayList<>();
        datas3.add(new BaseChartView.DataBean("2017-08-01 16:17:00", 6f));
        datas3.add(new BaseChartView.DataBean("2017-08-05 16:17:00", 16f));
        datas3.add(new BaseChartView.DataBean("2017-08-10 16:17:00", 8f));
        datas3.add(new BaseChartView.DataBean("2017-08-15 16:17:00", 2f));
        datas3.add(new BaseChartView.DataBean("2017-08-20 16:17:00", 12f));
        datas3.add(new BaseChartView.DataBean("2017-08-25 16:17:00", 22f));
        datas3.add(new BaseChartView.DataBean("2017-08-30 16:17:00", 13f));
        return datas3;
    }

    private List<BaseChartView.DataBean> getYearData() {
        //年数据源
        List<BaseChartView.DataBean> datas4 = new ArrayList<>();
        datas4.add(new BaseChartView.DataBean("2017-01-01 16:17:00", 6f));
        datas4.add(new BaseChartView.DataBean("2017-02-03 16:17:00", 16f));
        datas4.add(new BaseChartView.DataBean("2017-03-03 16:17:00", 8f));
        datas4.add(new BaseChartView.DataBean("2017-06-06 16:17:00", 2f));
        datas4.add(new BaseChartView.DataBean("2017-09-09 16:17:00", 12f));
        datas4.add(new BaseChartView.DataBean("2017-12-12 16:17:00", 22f));
        return datas4;
    }
}
