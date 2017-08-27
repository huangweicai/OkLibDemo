package com.oklib.demo.common_components;

import android.view.View;

import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/8/3
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：CommonToolbar使用演示
 */

public class ToolbarActivity extends BaseAppActivity {
    private CommonToolBar tb_toolbar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_toolbar;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        //注意：高度一定要用wrap_content属性
        tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle("中间标题", 16, R.color.oklib_white)//中间标题
                .setCenterDraw(R.mipmap.oklib_maotouying_icon, CommonToolBar.LEFT, 10)//中间标题内设置图标
                .setCenterTitleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.success("中间标题");
                    }
                })
                .setRightTitle("确定", 14, R.color.oklib_red)//右标题
                .setRightImage(R.color.oklib_blue, true)//有标题背景图或背景色 R.mipmap.oklib_maotouying_icon
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_toolbar.xml", BASE_RES +"/layout/activity_toolbar.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initNet() {

    }
}
