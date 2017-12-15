package com.hwc.oklib.common_components.chart.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwc.oklib.R;
import com.hwc.oklib.view.chart.BaseChartView;
import com.hwc.oklib.view.chart.StackBarChartView;

import java.util.ArrayList;
import java.util.List;


/**
 * 时间：2017/8/16
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：堆叠柱状图
 */

public class StackBarChartFragment extends Fragment {
    private StackBarChartView chartView1;
    private StackBarChartView chartView2;
    private StackBarChartView chartView3;
    private StackBarChartView chartView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stack_bar_chart, null, false);
        chartView1 = (StackBarChartView) view.findViewById(R.id.weightChart1);
        chartView2 = (StackBarChartView) view.findViewById(R.id.weightChart2);
        chartView3 = (StackBarChartView) view.findViewById(R.id.weightChart3);
        chartView4 = (StackBarChartView) view.findViewById(R.id.weightChart4);
        return view;
    }

    public void showChart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chartView1
                        .setHighPillarColor(new int[]{0xffff0000, 0x90ff0000})
                        .setCenterPillarColor(new int[]{0xff00ff00, 0x9000ff00})
                        .setLowPillarColor(new int[]{0xff0000ff, 0x900000ff})
                        .setDottedLine(false)
                        .setyTextUnit("min")
                        .setPillarWidth(20)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.DAY)
                        .setYMaxValue(250)
                        .setUnitValueText("bpm")
                        .refreshData(getDayData());
                chartView2
                        .setHighPillarColor(new int[]{0xffff0000, 0x90ff0000})
                        .setCenterPillarColor(new int[]{0xff00ff00, 0x9000ff00})
                        .setLowPillarColor(new int[]{0xff0000ff, 0x900000ff})
                        .setPillarWidth(20)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.WEEK)
                        .setYMaxValue(250)
                        .setUnitValueText("bpm")
                        .refreshData(getWeekData());
                chartView3
                        .setHighPillarColor(new int[]{0xffff0000, 0x90ff0000})
                        .setCenterPillarColor(new int[]{0xff00ff00, 0x9000ff00})
                        .setLowPillarColor(new int[]{0xff0000ff, 0x900000ff})
                        .setPillarWidth(20)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.MONTH)
                        .setYMaxValue(250)
                        .setUnitValueText("bpm")
                        .refreshData(getMonthData());
                chartView4
                        .setHighPillarColor(new int[]{0xffff0000, 0x90ff0000})
                        .setCenterPillarColor(new int[]{0xff00ff00, 0x9000ff00})
                        .setLowPillarColor(new int[]{0xff0000ff, 0x900000ff})
                        .setPillarWidth(20)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.YEAR)
                        .setYMaxValue(250)
                        .setUnitValueText("bpm")
                        .refreshData(getYearData());
            }
        }, 800);
    }

    private List<BaseChartView.DataBean> getDayData() {
        //日数据源
        List<BaseChartView.DataBean> datas1 = new ArrayList<>();
        datas1.add(new BaseChartView.DataBean("2017-09-11 00:17:00", 40f, 40f+30f, 40f+30f+20f, true));
        datas1.add(new BaseChartView.DataBean("2017-09-12 06:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-13 12:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-14 18:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-15 24:17:00", 92f, 92f+78f, 92f+78f+50f, true));
        return datas1;
    }

    private List<BaseChartView.DataBean> getWeekData() {
        //周数据源
        List<BaseChartView.DataBean> datas2 = new ArrayList<>();
        datas2.add(new BaseChartView.DataBean("2017-09-11 16:17:00", 40f, 40f+30f, 40f+30f+20f, true));
        datas2.add(new BaseChartView.DataBean("2017-09-12 16:17:00", 50, 50+38, 50+38+20, true));
        datas2.add(new BaseChartView.DataBean("2017-09-13 16:17:00", 50, 50+38, 50+38+20, true));
        datas2.add(new BaseChartView.DataBean("2017-09-14 16:17:00", 50, 50+38, 50+38+20, true));
        datas2.add(new BaseChartView.DataBean("2017-09-15 16:17:00", 92f, 92f+78f, 92f+78f+50f, true));
        datas2.add(new BaseChartView.DataBean("2017-09-16 16:17:00", 72f, 72f+68f, 72f+68f+50f, true));
        return datas2;
    }

    private List<BaseChartView.DataBean> getMonthData() {
        //月数据源
        List<BaseChartView.DataBean> datas3 = new ArrayList<>();
        datas3.add(new BaseChartView.DataBean("2017-09-01 16:17:00", 40f, 40f+30f, 40f+30f+20f, true));
        datas3.add(new BaseChartView.DataBean("2017-09-05 16:17:00",  50, 50+38, 50+38+20, true));
        datas3.add(new BaseChartView.DataBean("2017-09-10 16:17:00",  50, 50+38, 50+38+20, true));
        datas3.add(new BaseChartView.DataBean("2017-09-15 16:17:00",  50, 50+38, 50+38+20, true));
        datas3.add(new BaseChartView.DataBean("2017-09-20 16:17:00", 92f, 92f+78f, 92f+78f+50f, true));
        datas3.add(new BaseChartView.DataBean("2017-09-25 16:17:00", 72f, 72f+68f, 72f+68f+50f, true));
        datas3.add(new BaseChartView.DataBean("2017-09-30 16:17:00", 72f, 72f+68f, 72f+68f+50f, true));
        return datas3;
    }

    private List<BaseChartView.DataBean> getYearData() {
        //年数据源
        List<BaseChartView.DataBean> datas4 = new ArrayList<>();
        datas4.add(new BaseChartView.DataBean("2017-01-01 16:17:00", 40f, 40f+30f, 40f+30f+20f, true));
        datas4.add(new BaseChartView.DataBean("2017-02-03 16:17:00", 40f, 40f+30f, 40f+30f+20f, true));
        datas4.add(new BaseChartView.DataBean("2017-03-03 16:17:00", 40f, 40f+30f, 40f+30f+20f, true));
        datas4.add(new BaseChartView.DataBean("2017-06-06 16:17:00", 50, 50+38, 50+38+20, true));
        datas4.add(new BaseChartView.DataBean("2017-09-09 16:17:00", 72f, 72f+68f, 72f+68f+50f, true));
        datas4.add(new BaseChartView.DataBean("2017-12-12 16:17:00", 72f, 72f+68f, 72f+68f+50f, true));
        return datas4;
    }
}
