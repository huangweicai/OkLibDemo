package com.hwc.oklib.common_components;

import android.content.Intent;
import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;

import hwc.loadsir.target.AnimateActivity;
import hwc.loadsir.target.ConvertorActivity;
import hwc.loadsir.target.FragmentSingleActivity;
import hwc.loadsir.target.KeepTitleActivity;
import hwc.loadsir.target.MultiFragmentActivity;
import hwc.loadsir.target.MultiFragmentWithViewPagerActivity;
import hwc.loadsir.target.NormalActivity;
import hwc.loadsir.target.PlaceholderActivity;
import hwc.loadsir.target.ViewTargetActivity;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/9/30
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：LoadSir优雅处理空数据界面
 * 注意：需要在Application入口初始化
 */
public class LoadSirEntryActivity extends BaseAppActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_loadsir_layout;
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
                        mBeans.add(new FunctionDetailBean("activity_loadsir_layout.xml", BASE_RES + "/layout/activity_loadsir_layout.xml"));

                        String baseJavaUrl = "https://github.com/huangweicai/OkLibDemo/tree/master/loadsir/src/main/java/hwc/loadsir/target/";
                        String baseResUrl = "https://github.com/huangweicai/OkLibDemo/tree/master/loadsir/src/main/res/layout/";
                        mBeans.add(new FunctionDetailBean("NormalActivity.xml", baseJavaUrl + "NormalActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_content.xml", baseResUrl + "activity_content.xml"));

                        mBeans.add(new FunctionDetailBean("PlaceholderActivity.xml", baseJavaUrl + "PlaceholderActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_placeholder.xml", baseResUrl + "activity_placeholder.xml"));

                        mBeans.add(new FunctionDetailBean("ConvertorActivity.xml", baseJavaUrl + "ConvertorActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_activity_convertor.xml", baseResUrl + "activity_activity_convertor.xml"));

                        mBeans.add(new FunctionDetailBean("FragmentSingleActivity.xml", baseJavaUrl + "FragmentSingleActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_fragment.xml", baseResUrl + "activity_fragment.xml"));

                        mBeans.add(new FunctionDetailBean("MultiFragmentActivity.xml", baseJavaUrl + "MultiFragmentActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_fragment_mutil.xml", baseResUrl + "activity_fragment_mutil.xml"));

                        mBeans.add(new FunctionDetailBean("MultiFragmentWithViewPagerActivity.xml", baseJavaUrl + "MultiFragmentWithViewPagerActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_fragment_viewpager.xml", baseResUrl + "activity_fragment_viewpager.xml"));

                        mBeans.add(new FunctionDetailBean("ViewTargetActivity.xml", baseJavaUrl + "ViewTargetActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_view.xml", baseResUrl + "activity_view.xml"));

                        mBeans.add(new FunctionDetailBean("AnimateActivity.xml", baseJavaUrl + "AnimateActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_content.xml", baseResUrl + "activity_content.xml"));

                        mBeans.add(new FunctionDetailBean("KeepTitleActivity.xml", baseJavaUrl + "KeepTitleActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_fragment.xml", baseResUrl + "activity_fragment.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }

    public void inActivity(View view) {
        startActivity(new Intent(this, NormalActivity.class));
    }

    public void showPlaceholder(View view) {
        startActivity(new Intent(this, PlaceholderActivity.class));
    }


    public void inActivityWithConvertor(View view) {
        startActivity(new Intent(this, ConvertorActivity.class));
    }

    public void inFragment(View view) {
        startActivity(new Intent(this, FragmentSingleActivity.class));
    }

    public void inFragmentMutil(View view) {
        startActivity(new Intent(this, MultiFragmentActivity.class));
    }

    public void inFragmentViewSirPager(View view) {
        startActivity(new Intent(this, MultiFragmentWithViewPagerActivity.class));
    }

    public void inView(View view) {
        startActivity(new Intent(this, ViewTargetActivity.class));
    }

    public void animatCallback(View view) {
        startActivity(new Intent(this, AnimateActivity.class));
    }

    public void titleBarActivity(View view) {
        startActivity(new Intent(this, KeepTitleActivity.class));
    }
}
