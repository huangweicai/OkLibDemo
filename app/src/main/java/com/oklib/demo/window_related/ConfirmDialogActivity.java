package com.oklib.demo.window_related;

import android.view.View;
import android.widget.TextView;

import com.oklib.base.BaseDialogFragment;
import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;
import com.oklib.widget.ConfirmDialog;

/**
 * 时间：2017/8/17
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：居中确定取消窗口演示
 */

public class ConfirmDialogActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_confirm_dialog;
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
                        showDetail(null);
                    }
                });
    }

    @Override
    protected void initView() {
        ((TextView)findView(R.id.tv_showDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfirmDialog dialog = ConfirmDialog.create(getSupportFragmentManager());
                dialog.setTitle("发现新版本");
                dialog.setContent("1.修复了显示界面\n2.增强了交互体验");
                dialog.show();
                dialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                    @Override
                    public void confirm(View v) {
                        ToastUtil.success("确定");
                    }
                });
                dialog.setOnCancelListener(new ConfirmDialog.OnCancelListener() {
                    @Override
                    public void onCance(View v) {
                        ToastUtil.error("取消");
                    }
                });

            }
        });



    }

    @Override
    protected void initNet() {

    }
}
