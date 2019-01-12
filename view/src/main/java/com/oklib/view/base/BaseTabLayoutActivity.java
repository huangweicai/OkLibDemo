package com.oklib.view.base;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.oklib.view.CommonToolBar;
import com.oklib.view.R;

/**
 * 时间：2017/11/21
 * 作者：蓝天
 * 描述：TabLayout模板
 */

public abstract class BaseTabLayoutActivity extends BaseCommonUseActivity {

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected void afterLayout() {

    }

    @Override
    protected int initLayoutId() {
        return R.layout.oklib_activity_tablayout;
    }

    @Override
    protected void initVariable() {

    }

    protected CommonToolBar tb_toolbar;
    @Override
    protected void initTitle() {
        tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.oklib_btn_back)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(centerTitle(), 17, R.color.oklib_white);
    }

    @Override
    protected void initView() {
        init();
        tabLayout();
    }

    @Override
    protected void initNet() {
        requestNet();
    }

    protected TabLayout tabLayoutCenter;
    protected ViewPager vp_container;
    private void tabLayout() {
        tabLayoutCenter = findView(R.id.tabLayoutCenter);
        vp_container = findView(R.id.vp_container);
        tabLayoutCenter.setupWithViewPager(vp_container);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabFragments()[position];
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles()[position];
            }

            @Override
            public int getCount() {
                return tabTitles().length;
            }
        });
    }

    protected abstract void init();
    protected abstract void requestNet();
    protected abstract String centerTitle();
    protected abstract String[] tabTitles();
    protected abstract Fragment[] tabFragments();
}
