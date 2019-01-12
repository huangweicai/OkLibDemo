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
import com.oklib.view.chart.SportBarChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2017/8/16
 * 作者：蓝天
 * 描述：运动图表·当日
 */

public class SportBarChartFragment extends Fragment {
    private SportBarChartView sportChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sport_chart, null, false);
        sportChart = (SportBarChartView) view.findViewById(R.id.sportChart);
        return view;
    }

    public void showChart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sportChart
                        .setPillarLightColor(0x90ffffff)
                        .setPillarColor(0xffffffff)
                        .setPillarWidth(16f)//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(false)
                        .setType(BaseChartView.DAY)
                        .setYMaxValue(240)
                        .setUnitValueText("")
                        .setChartColor(0xffffffff)
                        .refreshData(getDayData());
            }
        }, 800);
    }

    private List<BaseChartView.DataBean> getDayData() {
        //日数据源，累加，最大值是下一次的最小值
        List<BaseChartView.DataBean> datas1 = new ArrayList<>();
        datas1.add(new BaseChartView.DataBean("2017-08-04 00:17:00", 40f, 20f, true));
        datas1.add(new BaseChartView.DataBean("2017-08-04 06:17:00", 52f, 40f, true));
        datas1.add(new BaseChartView.DataBean("2017-08-04 12:17:00", 76f, 52f, true));
        datas1.add(new BaseChartView.DataBean("2017-08-04 18:17:00", 98f, 76f, true));
        datas1.add(new BaseChartView.DataBean("2017-08-04 24:17:00", 112f, 98f, false));
        return datas1;
    }

}
