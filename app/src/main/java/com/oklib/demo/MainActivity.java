package com.oklib.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.oklib.demo.base.BaseAppActivity;

/**
 * 时间：2017/8/5
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/oklib
 * 描述：首页功能展示入口
 */

public class MainActivity extends BaseAppActivity {
    private TabLayout toolbar_tl_tab;
    private ViewPager vp_container;
    private String[] titles = {"集成框架", "常用组件", "常用工具", "窗口相关"};

    @Override
    protected int initLayoutId() {
        return R.layout.activiyt_mian;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        toolbar_tl_tab = findView(R.id.toolbar_tl_tab);
        vp_container = findView(R.id.vp_container);
        toolbar_tl_tab.setupWithViewPager(vp_container);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", position);
                return MainFragment.getInstance(bundle);
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
