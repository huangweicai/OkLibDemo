package com.hwc.oklib.base;

import android.content.Context;
import android.os.Bundle;

import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.base.BaseTabLayoutActivity;
import com.hwc.oklib.widget.CenterWinListDialog;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.hwc.oklib.Common.BASE_JAVA;

/**
 * 时间：2017/11/19
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：刷新Activity
 */

public abstract class BaseAppTabLayoutActivity extends BaseTabLayoutActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
    }

    protected List<FunctionDetailBean> mBeans = new ArrayList<>();
    protected void showDetail() {
        mBeans.add(0,new FunctionDetailBean(context.getClass().getSimpleName()+".java", BASE_JAVA + context.getClass().getName().replace(".", "/")+".java"));
        Logger.d("url："+mBeans.get(0).getUrl());
        final CenterWinListDialog centerWinListDialog = CenterWinListDialog.create(getSupportFragmentManager());
        centerWinListDialog.show();
        centerWinListDialog.addDataList(mBeans);
    }

}
