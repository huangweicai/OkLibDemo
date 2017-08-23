package com.oklib.demo.common_components.chart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oklib.demo.R;
import com.oklib.view.chart.BaseChartView;
import com.oklib.view.chart.HistogramBarChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2017/8/16
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：镂空柱状图
 */

public class HistogramBarChartFragment extends Fragment {
    private HistogramBarChartView chartView1;
    private HistogramBarChartView chartView2;
    private HistogramBarChartView chartView3;
    private HistogramBarChartView chartView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histogram_bar_chart, null, false);
        chartView1 = (HistogramBarChartView) view.findViewById(R.id.weightChart1);
        chartView2 = (HistogramBarChartView) view.findViewById(R.id.weightChart2);
        chartView3 = (HistogramBarChartView) view.findViewById(R.id.weightChart3);
        chartView4 = (HistogramBarChartView) view.findViewById(R.id.weightChart4);
        return view;
    }

    public void showChart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chartView1
                        .setNoMedicineColor(0xffff0000)
                        .setMedicineColor(0xff00ff00)
                        .setNoMedicineText("未打针")
                        .setMedicineText("打针")
                        .setBloodText("高针114-128/低针68-78")
                        .setPillarWidth(26f)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.DAY)
                        .setYMaxValue(240)
                        .setUnitValueText("mmHg")
                        .refreshData(getDayData());

                chartView2
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.WEEK)
                        .setYMaxValue(240)
                        .setUnitValueText("mmHg")
                        .refreshData(getWeekData());
                chartView3
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.MONTH)
                        .setYMaxValue(240)
                        .setUnitValueText("mmHg")
                        .refreshData(getMonthData());
                chartView4
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.YEAR)
                        .setYMaxValue(240)
                        .setUnitValueText("mmHg")
                        .refreshData(getYearData());
            }
        }, 800);
    }

    private List<BaseChartView.DataBean> getDayData() {
        //日数据源
        List<BaseChartView.DataBean> datas1 = new ArrayList<>();
        datas1.add(new BaseChartView.DataBean("2017-08-04 00:17:00", 60f, 30f, false));
        datas1.add(new BaseChartView.DataBean("2017-08-04 06:17:00", 122f, 60f, false));
        datas1.add(new BaseChartView.DataBean("2017-08-04 12:17:00", 86f, 40f, true));
        datas1.add(new BaseChartView.DataBean("2017-08-04 18:17:00", 108f, 80f, false));
        datas1.add(new BaseChartView.DataBean("2017-08-04 24:17:00", 92f, 10f, true));
        return datas1;
    }

    private List<BaseChartView.DataBean> getWeekData() {
        //周数据源
        List<BaseChartView.DataBean> datas2 = new ArrayList<>();
        datas2.add(new BaseChartView.DataBean("2017-08-21 16:17:00", 60f, 40f, true));
        datas2.add(new BaseChartView.DataBean("2017-08-22 16:17:00", 86f, 70f, false));
        datas2.add(new BaseChartView.DataBean("2017-08-23 16:17:00", 86f, 20f, true));
        datas2.add(new BaseChartView.DataBean("2017-08-24 16:17:00", 68f, 30f, false));
        datas2.add(new BaseChartView.DataBean("2017-08-25 16:17:00", 72f, 33f, true));
        datas2.add(new BaseChartView.DataBean("2017-08-26 16:17:00", 112f, 60f, true));
        datas2.add(new BaseChartView.DataBean("2017-08-27 16:17:00", 122f, 80f, true));
        return datas2;
    }

    private List<BaseChartView.DataBean> getMonthData() {
        //月数据源
        List<BaseChartView.DataBean> datas3 = new ArrayList<>();
        datas3.add(new BaseChartView.DataBean("2017-08-01 16:17:00", 60f, 40f, false));
        datas3.add(new BaseChartView.DataBean("2017-08-05 16:17:00", 116f, 92f, true));
        datas3.add(new BaseChartView.DataBean("2017-08-10 16:17:00", 98f, 70f, true));
        datas3.add(new BaseChartView.DataBean("2017-08-15 16:17:00", 52f, 30f, false));
        datas3.add(new BaseChartView.DataBean("2017-08-20 16:17:00", 102f, 20f, true));
        datas3.add(new BaseChartView.DataBean("2017-08-25 16:17:00", 82f, 60f, true));
        datas3.add(new BaseChartView.DataBean("2017-08-30 16:17:00", 132f, 110f, true));
        return datas3;
    }

    private List<BaseChartView.DataBean> getYearData() {
        //年数据源
        List<BaseChartView.DataBean> datas4 = new ArrayList<>();
        datas4.add(new BaseChartView.DataBean("2017-01-01 16:17:00", 60f, 30f, true));
        datas4.add(new BaseChartView.DataBean("2017-02-03 16:17:00", 116f, 100f, false));
        datas4.add(new BaseChartView.DataBean("2017-03-03 16:17:00", 86f, 33f, false));
        datas4.add(new BaseChartView.DataBean("2017-06-06 16:17:00", 72f, 20f, true));
        datas4.add(new BaseChartView.DataBean("2017-09-09 16:17:00", 122f, 85f, false));
        datas4.add(new BaseChartView.DataBean("2017-12-12 16:17:00", 82f, 60f, true));
        return datas4;
    }
}
