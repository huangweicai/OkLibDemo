package com.oklib.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.oklib.base.BaseActivity;
import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.demo.widget.CenterWinListDialog;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.oklib.demo.Common.BASE_JAVA;

/**
 * 时间：2017/8/2
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：app相关基类，与具体app业务相关
 */

public abstract class BaseAppActivity extends BaseActivity {
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


    protected boolean ifShowExit = false;
    private long preKeyBackTime = 0L;

    @Override
    public void onBackPressed() {
        if (this.ifShowExit) {
            if (System.currentTimeMillis() - this.preKeyBackTime < 2000L) {
                finish();
                System.exit(0);
            } else {
                this.preKeyBackTime = System.currentTimeMillis();
                ToastUtil.show("再按一次,将退出应用");
            }
        } else {
            finish();
        }
    }
}
