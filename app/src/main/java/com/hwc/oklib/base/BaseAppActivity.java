package com.hwc.oklib.base;

import android.content.Context;
import android.os.Bundle;

import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.base.BaseCommonUseActivity;
import com.hwc.oklib.widget.CenterWinListDialog;
import com.hwc.oklib.window.LoadingDialog;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.hwc.oklib.Common.BASE_JAVA;

/**
 * 时间：2017/8/2
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：app相关基类，与具体app业务相关
 */

public abstract class BaseAppActivity extends BaseCommonUseActivity {
    public Context context;
    public LoadingDialog mWaitDialog;

    @Override
    protected void beforeLayout() {
    }

    @Override
    protected void afterLayout() {
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        mWaitDialog = new LoadingDialog(this);
        super.onCreate(savedInstanceState);
    }

    protected List<FunctionDetailBean> mBeans = new ArrayList<>();
    protected void showDetail() {
        mBeans.add(0,new FunctionDetailBean(context.getClass().getSimpleName()+".java", BASE_JAVA + File.separator +context.getClass().getName().replace(".", "/")+".java"));
        Logger.d("url："+mBeans.get(0).getUrl());
        final CenterWinListDialog centerWinListDialog = CenterWinListDialog.create(getSupportFragmentManager());
        centerWinListDialog.show();
        centerWinListDialog.addDataList(mBeans);
    }


    /**
     * 弹出对话框
     *
     * @param msg
     */
    protected void showWaitDialog(String msg) {
        if (!isFinishing())
            if (mWaitDialog != null && !mWaitDialog.isShowing()) {
                if (msg != null) {
                    mWaitDialog.setTip(msg);
                    mWaitDialog.onContentChanged();
                }
                mWaitDialog.show();
            }
    }

    /**
     * 弹出对话框
     */
    public void showWaitDialog() {
        if (!isFinishing())
            if (mWaitDialog != null && !mWaitDialog.isShowing()) {
                mWaitDialog.show();
            }
    }

    /**
     * 关闭对话框
     */
    public void dismissWaitDialog() {
        if (!isFinishing())
            if (mWaitDialog != null && mWaitDialog.isShowing()) {
                mWaitDialog.dismiss();
            }
    }
}
