package com.oklib.view_lib.ratingbar;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;

import java.util.ArrayList;
import java.util.List;


 /**
   * 时间：2018/1/3
   * 作者：蓝天
   * 描述：ratingbar使用
   */
public class RatingBarEntryActivity extends BaseAppActivity {

     @Override
     protected int initLayoutId() {
         return R.layout.activity_ratingbar_entry;
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
