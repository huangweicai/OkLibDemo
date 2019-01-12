package com.oklib.view_lib.tablayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.tabsegment.TabSegment;


/**
 * 时间：2017/9/11
 * 作者：蓝天
 * 描述：TabLayout组件样例
 */

public class TabLayoutActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected void initView() {
        tabLayoutTop();
        tabLayoutCenter();
        customTab();
    }

    private void customTab() {
        TabSegment tabSegment = (TabSegment) findViewById(R.id.tabSegment);
        tabSegment.setText("Tab1", "Tab2", "Tab3", "Tab4");
        tabSegment.setSelectedIndex(0);
        tabSegment.setOnSegmentControlClickListener(new TabSegment.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                Toast.makeText(context, index + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Toolbar不通过ViewPager生成tab，自定义tab生成
    private TabLayout tabLayoutTop;
    private void tabLayoutTop() {
        tabLayoutTop = findView(R.id.tabLayoutTop);
        tabLayoutTop.setTabMode(TabLayout.MODE_SCROLLABLE);//TabLayout.MODE_FIXED 屏幕等分宽度模式，默认模式

        String[] titles = {"标题1", "标题2"};
        for (int i = 0; i < titles.length; i++) {
            tabLayoutTop.addTab(tabLayoutTop.newTab().setText(titles[i]), i);
            //设置tab选中及默认状态

        }
        tabLayoutTop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //选中是回调
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(TabLayoutActivity.this, "标题1", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(TabLayoutActivity.this, "标题2", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }

            }

            //从选中到不再选中时回调
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("TAG", "onTabUnselected 0");
                        break;
                    case 1:
                        Log.d("TAG", "onTabUnselected 1");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("TAG", "onTabReselected");
            }
        });
    }


    private TabLayout tabLayoutCenter;
    private ViewPager vp_container;
    private String[] title = {"标题1", "标题2", "标题3", "标题4"};
    private void tabLayoutCenter() {
        tabLayoutCenter = findView(R.id.tabLayoutCenter);
        vp_container = findView(R.id.vp_container);
        tabLayoutCenter.setupWithViewPager(vp_container);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new TabLayoutFragment();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }

            @Override
            public int getCount() {
                return title.length;
            }
        });
    }

}
