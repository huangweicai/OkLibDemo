package com.hwc.oklib.window_related;

import android.view.View;
import android.widget.TextView;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.widget.FoodSearchDialog;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2017/11/16
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：标志tag搜索窗口
 */

public class SearchDialogActivity extends BaseAppActivity {
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
                        mBeans.add(new FunctionDetailBean("activity_confirm_dialog.xml", BASE_RES +"/layout/activity_confirm_dialog.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        ((TextView) findView(R.id.tv_showDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodSearchDialog.create(getSupportFragmentManager()).show();
            }
        });
    }

    @Override
    protected void initNet() {

    }
}
