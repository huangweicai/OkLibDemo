package com.oklib.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 碎片基类
 * @author 蓝天
 * @created 2017/2/6
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayoutId(), null);
        initVariable();
        initView(view);
        initNet();
        return view;
    }

    /**
     * 设置layout资源
     * @return
     */
    protected abstract int initLayoutId();

    /**
     * 初始化变量 ，通常intent传值处理
     * @return
     */
    protected abstract void initVariable();

    /**
     * 初始化视图控件
     * @return
     */
    protected abstract void initView(View view);

    /**
     * 初始化网络操作
     * @return
     */
    protected abstract void initNet();

    protected <T> T findView(@Nullable View view, @Nullable int viewId) {
        return (T) view.findViewById(viewId);
    }

}
