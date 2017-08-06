package com.oklib.demo.base;

import android.content.Context;
import android.os.Bundle;

import com.oklib.base.BaseActivity;

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
}
