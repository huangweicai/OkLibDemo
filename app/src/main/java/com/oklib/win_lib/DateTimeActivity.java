package com.oklib.win_lib;

import android.view.View;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.util.toast.ToastUtil;
import com.oklib.window.DateTimeDialog;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间：2017/8/20
 * 作者：蓝天
 * 描述：日期选择器窗口
 */

public class DateTimeActivity extends BaseAppActivity implements DateTimeDialog.MyOnDateSetListener {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_multi_select_listpop;
    }

    @Override
    protected void initView() {

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
