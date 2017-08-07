package com.oklib.demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.oklib.demo.base.BaseAppActivity;
import com.oklib.view.CommonToolBar;


/**
   * 时间：2017/8/1
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：入口类，功能列表展示
   */
public class MainActivity extends BaseAppActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CommonToolBar toolbar;
    private TabLayout toolbar_tl_tab;
    private ViewPager vp_container;
    private String[] titles = {"集成框架", "常用组件", "常用工具", "窗口相关"};

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        setStateBarStyle();
    }

    @Override
    protected void initTitle() {
        toolbar = (CommonToolBar) findViewById(R.id.toolbar);
        //toolbar与菜单栏不联系，不会作用到
        //setSupportActionBar(toolbar);
        toolbar.setCenterTitle("OkLib库演示", 17, R.color.app_white_color);
        toolbar.setImmerseState(this);
        toolbar.setRightTitle("支持详细", 14, R.color.app_white_color).setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SupportingDetailsActivity.class);
                intent.putExtra(Common.TITLE, "支持详细");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    private void setStateBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//状态栏字体深白色
//                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//状态栏字体深灰色（6.0以上属性）（注意：1.以最后一个设置为准 2.特定的颜色值才有效果）
            decorView.setSystemUiVisibility(option);
            //最好设置，不设置默认状态栏背景颜色是灰色
            getWindow().setStatusBarColor(0x00ffffff);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.exceptional_support) {
            Intent intent = new Intent(this, SupportExceptionalActivity.class);
            startActivity(intent);
        } else if (id == R.id.feedback_to_improve) {
            Toast.makeText(this, "功能开放中···", Toast.LENGTH_LONG).show();
        } else if (id == R.id.check_updates) {
            Toast.makeText(this, "功能开放中···", Toast.LENGTH_LONG).show();
        } else if (id == R.id.about_author) {
            Toast.makeText(this, "功能开放中···", Toast.LENGTH_LONG).show();
        } else if (id == R.id.jianshu) {//简书
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Common.TITLE, "进度WebView使用演示");
                    intent.putExtra(Common.URL, "http://www.jianshu.com/p/87e7392a16ff");
                    startActivity(intent);
                }
            }, 200);

        } else if (id == R.id.github) {//GitHub
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Common.TITLE, "进度WebView使用演示");
                    intent.putExtra(Common.URL, "https://github.com/huangweicai/OkLibDemo");
                    startActivity(intent);
                }
            }, 200);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
