package com.oklib.view_lib.chart;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view_lib.chart.fragment.BarChartFragment;
import com.oklib.view_lib.chart.fragment.CurveChartFragment;
import com.oklib.view_lib.chart.fragment.CustomBloodChartFragment;
import com.oklib.view_lib.chart.fragment.HistogramBarChartFragment;
import com.oklib.view_lib.chart.fragment.LineChartFragment;
import com.oklib.view_lib.chart.fragment.SportBarChartFragment;


/**
  * 时间：2017/8/23
  * 作者：蓝天
  * 描述：图表使用演示
  * 注意：1.每个x刻度对应一个y数值点 2.数据从左到右升序
  */
public class ChartActivity extends BaseAppActivity {
   private TabLayout toolbar_tl_tab;
   private ViewPager vp_container;
   private String[] titles = {"折线图", "曲线图", "柱状图", "镂空柱状图", "定制血压图", "运动柱状图"};//, "堆叠柱状图"

    @Override
    protected int initLayoutId() {
        return R.layout.activity_chart;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {
        toolbar_tl_tab = (TabLayout) findViewById(R.id.toolbar_tl_tab);
        vp_container = (ViewPager) findViewById(R.id.vp_container);
        toolbar_tl_tab.setupWithViewPager(vp_container);
        toolbar_tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    //折线图
                    LineChartFragment chartFragment = new LineChartFragment();
                    chartFragment.showChart();
                    return chartFragment;
                } else if (position == 1) {
                    //曲线图
                    CurveChartFragment chartFragment = new CurveChartFragment();
                    chartFragment.showChart();
                    return chartFragment;
                } else if (position == 2) {
                    //柱状图
                    BarChartFragment chartFragment = new BarChartFragment();
                    chartFragment.showChart();
                    return chartFragment;
                } else if (position == 3) {
                    //镂空柱状图
                    HistogramBarChartFragment chartFragment = new HistogramBarChartFragment();
                    chartFragment.showChart();
                    return chartFragment;
                } else if (position == 4) {
                    //定制血压图
                    CustomBloodChartFragment chartFragment = new CustomBloodChartFragment();
                    chartFragment.showChart();
                    return chartFragment;
                } else if (position == 5) {
                    //运动柱状图
                    SportBarChartFragment chartFragment = new SportBarChartFragment();
                    chartFragment.showChart();
                    return chartFragment;
                }
//                else if (position == 6) {
//                    //堆叠柱状图
//                    SnoreBarChartViewFragment chartFragment = new SnoreBarChartViewFragment();
//                    chartFragment.showChart();
//                    return chartFragment;
//                }
                return null;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });
    }

    @Override
    protected void initNet() {

    }

}
