package com.oklib.view_lib.vp_hss;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.ZoomOutPageTransformer;


/**
 * 时间：2017/8/28
 * 作者：蓝天
 * 描述：VP滑动缩放图片样例
 */

public class VPHorizontalSlidingScaleActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_vp_horizontal_sliding_scale;
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

}
