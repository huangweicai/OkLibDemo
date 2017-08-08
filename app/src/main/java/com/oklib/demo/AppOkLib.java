package com.oklib.demo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bmoblib.BmoBHelp;
import com.oklib.OkLib;

/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：程序入口，做一些初始化的工作
 */

public class AppOkLib extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //使用OkLib库必须先调用初始化方法
        new OkLib.Builder()
                .setApplication(this)
                .setPackageName(BuildConfig.APPLICATION_ID)
                .isDebug(true)
                .isShowToast(true)
                .build();

        //初始化比目
        BmoBHelp.initBmob(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
