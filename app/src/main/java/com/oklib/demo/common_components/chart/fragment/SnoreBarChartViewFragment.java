package com.oklib.demo.common_components.chart.fragment;


import android.os.Handler;
import android.view.View;

import com.oklib.base.BaseFragment;
import com.oklib.demo.R;
import com.oklib.demo.view.SnoreBarChartView;
import com.oklib.util.DensityUtils;
import com.oklib.view.chart.BaseChartView;

import java.util.ArrayList;
import java.util.List;

import static com.oklib.widget.dialog.StyledDialog.context;

/**
 * 时间：2017/9/27
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：止鼾枕图表
 */

public class SnoreBarChartViewFragment extends BaseFragment {
    private SnoreBarChartView barChartViewDay;
    private SnoreBarChartView barChartViewWeek;
    private SnoreBarChartView barChartViewMonth;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_snorebar_layout;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView(View view) {
        barChartViewDay = (SnoreBarChartView) view.findViewById(R.id.barChartViewDay);
        barChartViewWeek = (SnoreBarChartView) view.findViewById(R.id.barChartViewWeek);
        barChartViewMonth = (SnoreBarChartView) view.findViewById(R.id.barChartViewMonth);
    }

    @Override
    protected void initNet() {

    }

    public void showChart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                barChartViewDay.setChartViewWidth(DensityUtils.dp2px(context, 600));
                barChartViewDay.getStackBarChartView()
                        .setHighPillarColor(new int[]{0xFFB2DDF2, 0x90B2DDF2})//高处低压
                        .setCenterPillarColor(new int[]{0xFF4DB1E2, 0x904DB1E2})
                        .setLowPillarColor(new int[]{0xFF007CB7, 0x90007CB7})//低处高压
                        .setDatumLineColor(0x90222222)//虚线颜色
                        .setDottedLine(true)//是否显示虚线
                        .setPillarGap(DensityUtils.dp2px(context, 3))//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(true)
                        .setType(BaseChartView.DAY)
                        .setYMaxValue(150)
                        .refreshData(getDayData());


                barChartViewWeek.setChartViewWidth(DensityUtils.dp2px(context, 320));
                barChartViewWeek.getStackBarChartView()
                        .setHighPillarColor(new int[]{0xFFB2DDF2, 0x90B2DDF2})//高处低压
                        .setCenterPillarColor(new int[]{0xFF4DB1E2, 0x904DB1E2})
                        .setLowPillarColor(new int[]{0xFF007CB7, 0x90007CB7})//低处高压
                        .setDatumLineColor(0x90222222)//虚线颜色
                        .setDottedLine(true)//是否显示虚线
                        .setPillarWidth(DensityUtils.dp2px(context, 25))
                        .setPillarGap(DensityUtils.dp2px(context, 3))//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(true)
                        .setType(BaseChartView.WEEK)
                        .setYMaxValue(150)
                        .refreshData(getWeekData());

                barChartViewMonth.setChartViewWidth(DensityUtils.dp2px(context, 320));
                barChartViewMonth.setyMaxValue(700);
                barChartViewMonth.getStackBarChartView()
                        .setHighPillarColor(new int[]{0xFFB2DDF2, 0x90B2DDF2})//高处低压
                        .setCenterPillarColor(new int[]{0xFF4DB1E2, 0x904DB1E2})
                        .setLowPillarColor(new int[]{0xFF007CB7, 0x90007CB7})//低处高压
                        .setDatumLineColor(0x90222222)//虚线颜色
                        .setDottedLine(true)//是否显示虚线
                        .setPillarGap(DensityUtils.dp2px(context, 3))//以上子类内容，必须写在下面方法上面
                        .isShowDatumLine_X(false)
                        .isShowDatumLine_Y(true)
                        .setType(BaseChartView.MONTH)
                        .setYMaxValue(700)
                        .refreshData(getMonthData());
            }
        }, 800);
    }

    private List<BaseChartView.DataBean> getDayData() {
        //日数据源
        List<BaseChartView.DataBean> datas1 = new ArrayList<>();
        //注意，25日，26日不影响图表显示，根据“时”按照顺序处理
        datas1.add(new BaseChartView.DataBean("2017-09-25 12:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 13:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 14:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 15:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 16:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 17:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 18:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 19:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 20:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 21:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 22:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 23:17:00", 50, 50+38, 50+38+20, false));
        datas1.add(new BaseChartView.DataBean("2017-09-25 24:17:00", 50, 50+38, 50+38+20, false));

        datas1.add(new BaseChartView.DataBean("2017-09-26 01:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 02:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 03:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 04:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 05:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 06:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 07:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 08:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 09:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 10:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 11:17:00", 50, 50+38, 50+38+20, true));
        datas1.add(new BaseChartView.DataBean("2017-09-26 12:17:00", 50, 50+38, 50+38+20, true));
        return datas1;
    }

    private List<BaseChartView.DataBean> getWeekData() {
        //周数据源
        List<BaseChartView.DataBean> datas2 = new ArrayList<>();
        datas2.add(new BaseChartView.DataBean("2017-09-25 16:17:00", 40f, 40f+30f, 40f+30f+20f, true));
        datas2.add(new BaseChartView.DataBean("2017-09-26 16:17:00", 50, 50+38, 50+38+20, true));
        datas2.add(new BaseChartView.DataBean("2017-09-27 16:17:00", 50, 50+38, 50+38+20, true));
        datas2.add(new BaseChartView.DataBean("2017-09-28 16:17:00", 50, 50+38, 50+38+20, true));
        datas2.add(new BaseChartView.DataBean("2017-09-29 16:17:00", 62f, 62f+38f, 62f+38f+20, true));
        datas2.add(new BaseChartView.DataBean("2017-09-30 16:17:00", 62f, 62f+38f, 62f+38f+20, true));
        datas2.add(new BaseChartView.DataBean("2017-10-01 16:17:00", 62f, 62f+38f, 62f+38f+20, true));
        return datas2;
    }

    private List<BaseChartView.DataBean> getMonthData() {
        //月数据源
        List<BaseChartView.DataBean> datas3 = new ArrayList<>();
        datas3.add(new BaseChartView.DataBean("2017-09-01 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-02 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-03 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-04 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-05 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-06 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-07 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-08 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-09 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-10 16:17:00", 10, 10+20, 10+20+30, true));

        datas3.add(new BaseChartView.DataBean("2017-09-11 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-12 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-13 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-14 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-15 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-16 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-17 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-18 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-19 16:17:00", 10, 10+20, 10+20+30, true));
        datas3.add(new BaseChartView.DataBean("2017-09-20 16:17:00", 10, 10+20, 10+20+30, true));

        datas3.add(new BaseChartView.DataBean("2017-09-21 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-22 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-23 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-24 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-25 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-26 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-27 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-28 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-29 16:17:00", 10, 10+20, 10+20+30,true));
        datas3.add(new BaseChartView.DataBean("2017-09-30 16:17:00", 10, 10+20, 10+20+30,true));
        return datas3;
    }
}
