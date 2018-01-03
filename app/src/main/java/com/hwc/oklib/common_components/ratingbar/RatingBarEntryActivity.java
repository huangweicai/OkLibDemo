package com.hwc.oklib.common_components.ratingbar;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * Created by willy on 2017/10/13.
 */

/**
  * 时间：2018/1/3
  * 作者：黄伟才
  * 描述：ratingbar使用
  */
public class RatingBarEntryActivity extends BaseAppActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_ratingbar_entry;
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
                        mBeans.add(new FunctionDetailBean("activity_avloading_indicator_view.xml", BASE_RES +"/layout/activity_avloading_indicator_view.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Animation Demo"));
        tabLayout.addTab(tabLayout.newTab().setText("RecyclerView Demo"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter {

       private List<Fragment> fragments;

       private SamplePagerAdapter(FragmentManager fm) {
           super(fm);
           fragments = new ArrayList<>();
           fragments.add(new DemoFragment());
           fragments.add(new ListFragment());
       }

       @Override
       public int getCount() {
           return fragments.size();
       }

       @Override
       public Fragment getItem(int position) {
           return fragments.get(position);
       }
   }
}
