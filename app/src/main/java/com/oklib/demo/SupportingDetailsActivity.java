package com.oklib.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.oklib.demo.base.BaseAppActivity;
import com.oklib.util.SPUtils;
import com.oklib.view.CommonToolBar;

/**
 * 时间：2017/8/7
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：支持详细
 */

public class SupportingDetailsActivity extends BaseAppActivity implements ViewPager.OnPageChangeListener {
    private TabLayout toolbar_tl_tab;
    private ViewPager vp_container;
    private String[] titles = {"赞助商", "支持者", "技术资料"};

    @Override
    protected int initLayoutId() {
        return R.layout.activity_supporting_details;
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
                .setRightTitle("关于", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setCancelable(false);
                        dialog.setMessage(getResources().getString(R.string.supporting_details_text));
                        dialog.setPositiveButton("支付宝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, PayActivity.class);
                                intent.putExtra(Common.TITLE, "支付宝购买");
                                intent.putExtra(PayActivity.PAY_TYPE_KEY, PayActivity.ALIPAY);
                                startActivity(intent);
                            }
                        });
//                        dialog.setNegativeButton("微信", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(context, PayActivity.class);
//                                intent.putExtra(Common.TITLE, "微信购买");
//                                intent.putExtra(PayActivity.PAY_TYPE_KEY, PayActivity.WECHAT_PAY);
//                                startActivity(intent);
//                            }
//                        });
                        dialog.setNeutralButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void initView() {
        toolbar_tl_tab = findView(R.id.toolbar_tl_tab);
        vp_container = findView(R.id.vp_container);
        vp_container.setOffscreenPageLimit(2);
        vp_container.setOnPageChangeListener(this);
        toolbar_tl_tab.setupWithViewPager(vp_container);
        toolbar_tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0 || position == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", position);
                    return SupportingDetailsFragment.getInstance(bundle);
                } else {
                    if (position == 2) {
                        return TechnicalDataFragment.getInstance(null);
                    }
                    return null;
                }
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

        int pagePos = (int) SPUtils.get(context, VP_PAGE, 0);
        vp_container.setCurrentItem(pagePos);
    }

    @Override
    protected void initNet() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private final String VP_PAGE = "vpPage";//页码
    @Override
    public void onPageSelected(int position) {
        SPUtils.put(context, VP_PAGE, position);//缓存页码，下次直接打开
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
