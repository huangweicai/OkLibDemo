package com.oklib.view.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 创建时间：2017/3/11
 * 编写者：蓝天
 * 功能描述：Activity基类，该类只做业务无关的抽象，骨架类
 */

public abstract class BaseAbstractActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeLayout();
        setContentView(initLayoutId());
        afterLayout();
        initVariable();
        initTitle();
        initView();
        initNet();
    }

    /**
     * layout之前执行
     *
     * @return
     */
    protected abstract void beforeLayout();

    /**
     * 设置layout资源
     *
     * @return
     */
    protected abstract int initLayoutId();

    /**
     * layout之后执行
     *
     * @return
     */
    protected abstract void afterLayout();

    /**
     * 初始化变量 ，通常intent传值处理
     *
     * @return
     */
    protected abstract void initVariable();

    /**
     * 初始化标题
     *
     * @return
     */
    protected abstract void initTitle();

    /**
     * 初始化视图控件
     *
     * @return
     */
    protected abstract void initView();

    /**
     * 初始化网络操作
     *
     * @return
     */
    protected abstract void initNet();


    /**
     * 通过Id得到view的实例
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T> T findView(int viewId) {
        return (T) findViewById(viewId);
    }

}
