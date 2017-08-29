package com.oklib.demo.common_components.vp_hss;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.view.CommonToolBar;
import com.oklib.view.ZoomOutPageTransformer;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/8/28
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：VP水平滑动缩放图片使用演示
 */

public class VPHorizontalSlidingScaleActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_vp_horizontal_sliding_scale;
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
                        mBeans.add(new FunctionDetailBean("activity_vp_horizontal_sliding_scale.xml", BASE_RES +"/layout/activity_vp_horizontal_sliding_scale.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        mViewPager.setOffscreenPageLimit(4-1);//缓存，根据需求自定义
        mViewPager.setPageMargin(-200);//处理两边碎片显示
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                return new PageFragment();
            }
        });
    }

    @Override
    protected void initNet() {

    }
}
