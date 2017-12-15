package com.hwc.oklib.common_components.chart;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.common_components.chart.fragment.BarChartFragment;
import com.hwc.oklib.common_components.chart.fragment.CurveChartFragment;
import com.hwc.oklib.common_components.chart.fragment.CustomBloodChartFragment;
import com.hwc.oklib.common_components.chart.fragment.HistogramBarChartFragment;
import com.hwc.oklib.common_components.chart.fragment.LineChartFragment;
import com.hwc.oklib.common_components.chart.fragment.SnoreBarChartViewFragment;
import com.hwc.oklib.common_components.chart.fragment.SportBarChartFragment;
import com.hwc.oklib.view.CommonToolBar;

import static com.hwc.oklib.Common.BASE_JAVA;
import static com.hwc.oklib.Common.BASE_RES;


/**
  * 时间：2017/8/23
  * 作者：黄伟才
  * 简书：http://www.jianshu.com/p/87e7392a16ff
  * github：https://github.com/huangweicai/OkLibDemo
  * 描述：图表使用演示
  * 注意：1.每个x刻度对应一个y数值点 2.数据从左到右升序
  */
public class ChartActivity extends BaseAppActivity {
   private TabLayout toolbar_tl_tab;
   private ViewPager vp_container;
   private String[] titles = {"折线图", "曲线图", "柱状图", "镂空柱状图", "定制血压图", "运动柱状图", "堆叠柱状图"};

    @Override
    protected int initLayoutId() {
        return R.layout.activity_chart;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_chart.xml", BASE_RES +"/layout/activity_chart.xml"));

                        String fragmentPackage = "com/oklib/demo/common_components/chart/fragment/";
                        mBeans.add(new FunctionDetailBean("LineChartFragment.java", BASE_JAVA + fragmentPackage+"LineChartFragment.java"));
                        mBeans.add(new FunctionDetailBean("fragment_linechart.xml", BASE_RES +"/layout/fragment_linechart.xml"));

                        mBeans.add(new FunctionDetailBean("CurveChartFragment.java", BASE_JAVA + fragmentPackage+"CurveChartFragment.java"));
                        mBeans.add(new FunctionDetailBean("fragment_rate_chart.xml", BASE_RES +"/layout/fragment_rate_chart.xml"));

                        mBeans.add(new FunctionDetailBean("BarChartFragment.java", BASE_JAVA + fragmentPackage+"BarChartFragment.java"));
                        mBeans.add(new FunctionDetailBean("fragment_barchart.xml", BASE_RES +"/layout/fragment_barchart.xml"));

                        mBeans.add(new FunctionDetailBean("HistogramBarChartFragment.java", BASE_JAVA + fragmentPackage+"HistogramBarChartFragment.java"));
                        mBeans.add(new FunctionDetailBean("fragment_histogram_bar_chart.xml", BASE_RES +"/layout/fragment_histogram_bar_chart.xml"));

                        mBeans.add(new FunctionDetailBean("CustomBloodChartFragment.java", BASE_JAVA + fragmentPackage+"CustomBloodChartFragment.java"));
                        mBeans.add(new FunctionDetailBean("fragment_blood_chart.xml", BASE_RES +"/layout/fragment_blood_chart.xml"));

                        mBeans.add(new FunctionDetailBean("SportBarChartFragment.java", BASE_JAVA + fragmentPackage+"SportBarChartFragment.java"));
                        mBeans.add(new FunctionDetailBean("fragment_sport_chart.xml", BASE_RES + "/layout/fragment_sport_chart.xml"));

                        showDetail();
                    }
                });
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
                } else if (position == 6) {
                    //堆叠柱状图
                    SnoreBarChartViewFragment chartFragment = new SnoreBarChartViewFragment();
                    chartFragment.showChart();
                    return chartFragment;
                }
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
