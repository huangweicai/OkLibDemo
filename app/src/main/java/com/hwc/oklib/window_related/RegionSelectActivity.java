package com.hwc.oklib.window_related;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.window.RegionSelectionDialog;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/20
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：地区选择窗口
 */

public class RegionSelectActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_multi_select_listpop;
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
                        mBeans.add(new FunctionDetailBean("activity_multi_select_listpop.xml", BASE_RES +"/layout/activity_multi_select_listpop.xml"));
                        showDetail();
                    }
                });
    }

    private TextView tv_text;
    @Override
    protected void initView() {
        tv_text = findView(R.id.tv_text);
    }

    @Override
    protected void initNet() {

    }

    public void doClick(View view) {
        RegionSelectionDialog dialog = RegionSelectionDialog.create(getSupportFragmentManager());
        dialog.show();
        dialog.setOnSelectListener(new RegionSelectionDialog.OnSelectListener() {
            @Override
            public void onSelect(String selectRegion) {
                //选中回调
                Toast.makeText(context, selectRegion, Toast.LENGTH_LONG).show();
            }
        });
    }
}
