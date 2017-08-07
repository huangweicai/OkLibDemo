package com.oklib.demo;

import android.app.Application;

import com.oklib.OkLib;

/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：
 */

public class AppOkLib extends Application {
    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //使用OkLib库必须先调用初始化方法
        OkLib.init(this);
    }
}
