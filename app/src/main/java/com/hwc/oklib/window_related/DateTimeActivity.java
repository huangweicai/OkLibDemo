package com.hwc.oklib.window_related;

import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.window.DateTimeDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/20
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：日期选择器窗口
 */

public class DateTimeActivity extends BaseAppActivity implements DateTimeDialog.MyOnDateSetListener {
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

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }

    // 日期 格式化 工具
    private SimpleDateFormat mDateFormatter = new SimpleDateFormat("yyyy-MM-d");
    private DateTimeDialog dateTimeDialog;

    public void doClick(View view) {
        dateTimeDialog = new DateTimeDialog(this, null, this);
        dateTimeDialog.hideOrShow();
    }

    @Override
    public void onDateSet(Date date) {
        //选择时间回调
        ToastUtil.show(mDateFormatter.format(date));
    }
}
