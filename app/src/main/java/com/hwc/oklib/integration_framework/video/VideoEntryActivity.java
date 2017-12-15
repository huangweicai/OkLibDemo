package com.hwc.oklib.integration_framework.video;

import android.content.Intent;
import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;

import static com.hwc.oklib.Common.BASE_JAVA;
import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/9/27
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：视频列表入口
 */
public class VideoEntryActivity extends BaseAppActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_video_entry;
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
                        mBeans.add(new FunctionDetailBean("activity_camera.xml", BASE_RES + "/layout/activity_camera.xml"));

                        String packagePath = "com/oklib/demo/integration_framework/video/";
                        mBeans.add(new FunctionDetailBean("TinyWindowPlayActivity.java", BASE_JAVA + packagePath + "TinyWindowPlayActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_tiny_window_play.xml", BASE_RES + "/layout/activity_tiny_window_play.xml"));

                        mBeans.add(new FunctionDetailBean("RecyclerViewActivity.java", BASE_JAVA + packagePath + "RecyclerViewActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_recycler_view.xml", BASE_RES + "/layout/activity_recycler_view.xml"));

                        mBeans.add(new FunctionDetailBean("ChangeClarityActivity.java", BASE_JAVA + packagePath + "ChangeClarityActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_change_clarity.xml", BASE_RES + "/layout/activity_change_clarity.xml"));

                        mBeans.add(new FunctionDetailBean("UseInFragActivity.java", BASE_JAVA + packagePath + "UseInFragActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_use_in_frag.xml", BASE_RES + "/layout/activity_use_in_frag.xml"));

                        mBeans.add(new FunctionDetailBean("ChangeClarityActivity.java", BASE_JAVA + packagePath + "ChangeClarityActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_process_home1.xml", BASE_RES + "/layout/activity_process_home1.xml"));

                        mBeans.add(new FunctionDetailBean("ProcessHome2Activity.java", BASE_JAVA + packagePath + "ProcessHome2Activity.java"));
                        mBeans.add(new FunctionDetailBean("activity_process_home2.xml", BASE_RES + "/layout/activity_process_home2.xml"));

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

    public void tinyWindow(View view) {
        startActivity(new Intent(this, TinyWindowPlayActivity.class));
    }

    public void videoList(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void changeClarity(View view) {
        startActivity(new Intent(this, ChangeClarityActivity.class));
    }

    public void useInFragment(View view) {
        startActivity(new Intent(this, UseInFragActivity.class));
    }

    public void processHomeKeyInActivity(View view) {
        // 在Activity中使用NiceVideoPlayer，如果需要处理播放时按下Home键的逻辑.
        startActivity(new Intent(this, ProcessHome1Activity.class));
    }

    public void processHomeKeyInFragment(View view) {
        // 在Fragment中使用NiceVideoPlayer，如果需要处理播放时按下Home键的逻辑.
        startActivity(new Intent(this, ProcessHome2Activity.class));
    }
}
