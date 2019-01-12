package com.oklib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oklib.base.BaseAppActivity;
import com.oklib.bean.MainBean;
import com.oklib.view.CommonToolBar;
import com.oklib.view.ImmersedStatusbarUtils;
import com.tencent.bugly.beta.Beta;

/**
 * 时间：2018/5/21
 * 作者：蓝天
 * 描述：
 */
public class MainActivity extends BaseAppActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private CommonToolBar tb_toolbar;
    private TabLayout toolbar_tl_tab;
    private ViewPager vp_container;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        ifShowExit = true;
        ImmersedStatusbarUtils.initAfterSetContentView(this, null);
        Beta.checkUpgrade(false, false);//点击检查true,自动检查false;显示弹窗true,否则false
    }

    @Override
    protected void initTitle() {
        tb_toolbar = (CommonToolBar) findViewById(R.id.tb_toolbar);
        //toolbar与菜单栏不联系，不会作用到
        //setSupportActionBar(tb_toolbar);
        tb_toolbar.setCenterTitle("OkLib工具库", 18, R.color.app_white_color);
        tb_toolbar.setImmerseState(this);
        tb_toolbar.setRightTitle("公告", 14, R.color.app_white_color).setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noticeUrl = "http://mp.weixin.qq.com/s?__biz=MzIyMDA2Njk3Mg==&mid=100000483&idx=1&sn=f5f7bbc8a0d9cec72bc4fa51182b3909&chksm=17d0e71020a76e069e361775132034d47feba792d65de7e1214dad546486a8c637b892a5d009#rd";
                MainBean mainBean = new MainBean();
                mainBean.setTitle("公告");
                mainBean.setUrl(noticeUrl);
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("mainBean", mainBean);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, tb_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar_tl_tab = (TabLayout) findViewById(R.id.toolbar_tl_tab);
        vp_container = (ViewPager) findViewById(R.id.vp_container);
        toolbar_tl_tab.setupWithViewPager(vp_container);
        toolbar_tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                MainFragment mainFragment = new MainFragment();
                mainFragment.setArguments(bundle);
                return mainFragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return CommonManager.TITLES[position];
            }

            @Override
            public int getCount() {
                return CommonManager.TITLES.length;
            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            mBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();
                if (id == R.id.my_collect) {
                    Intent intent = new Intent(context, CollectActivity.class);
                    startActivity(intent);
                } else if (id == R.id.check_update) {
                    //参数1：isManual 用户手动点击检查，非用户点击操作请传false
                    //参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
                    Beta.checkUpgrade(true, false);//点击检查true,自动检查false;显示弹窗true,否则false
                } else if (id == R.id.get_code) {
                    final View view = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_more, null);
                    final ImageView iv_reward = view.findViewById(R.id.iv_reward);
                    iv_reward.setImageResource(R.drawable.xingqiu_qrcode_icon);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setView(view)
                            .create();
                    dialog.show();
                } else if (id == R.id.reward) {
                    final View view = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_more, null);
                    final ImageView iv_reward = view.findViewById(R.id.iv_reward);
                    iv_reward.setImageResource(R.drawable.lantian_reward_icon);
                    final TextView tv_title = view.findViewById(R.id.tv_title);
                    tv_title.setText("微信扫一扫打赏\n在“技术微讯”点击内推圈菜单栏加入内推圈");
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setView(view)
                            .create();
                    dialog.show();
                }
            }
        }, 200);
        return true;
    }


    //通用
    protected boolean ifShowExit = false;
    private long preKeyBackTime = 0L;
    public void mBackPressed() {
        if (this.ifShowExit) {
            if (System.currentTimeMillis() - this.preKeyBackTime < 2000L) {
                finish();
                System.exit(0);
            } else {
                this.preKeyBackTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次,将退出应用", Toast.LENGTH_LONG).show();
            }
        } else {
            finish();
        }
    }

}
