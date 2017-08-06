package com.oklib.demo;

import android.app.Application;

import com.oklib.OkLib;

/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/oklib
 * 描述：
 */

public class AppOkLib extends Application {
    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        OkLib.init(this);
    }
}
