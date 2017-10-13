package com.oklib.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 创建时间：2016/3/30
 * 编写者：黄伟才
 * 功能描述：DialogFragment对话框基类
 */
public abstract class BaseDialogFragment extends DialogFragment {
    @Override
    public void onStart() {
        super.onStart();
        setWindowDim(initDimValue());
    }

    @Override
    public void onResume() {
        super.onResume();
        initOnResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //窗体关闭方式
        initDialogAttr(isCancel(), style());
        //初始化布局
        View view = inflater.inflate(initContentView(), null);
        //处理arguments数据
        argumentsDate();
        //设置actionBarView
        initActionBarView(view);
        //初始化视图
        initView(view);
        //初始化视图数据
        initData(view);
        //请求网络
        initNet();
        return view;
    }

    /**
     * 设置背景昏暗程度
     */
    public abstract float initDimValue();

    /**
     * 预处理，设置大小，位置
     */
    public abstract void initOnResume();

    /**
     * 触碰没有窗体位置是否关闭dialog
     */
    public abstract boolean isCancel();

    /**
     * 样式dialog
     */
    public abstract int gravity();

    /**
     * 样式dialog
     */
    public abstract int style();

    /**
     * 初始化布局
     */
    public abstract int initContentView();

    /**
     * 处理arguments数据
     */
    protected abstract void argumentsDate();

    /**
     * 设置actionBarView
     */
    public abstract void initActionBarView(View view);

    /**
     * 初始化控件
     */
    public abstract void initView(View view);

    /**
     * 初始化数据
     */
    public abstract void initData(View view);

    /**
     * 请求网络
     */
    protected abstract void initNet();

    /**
     * 常用设置属性
     *
     * @param cancel
     */
    private void initDialogAttr(boolean cancel, int style) {
        //没有标题，透明背景
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //取消空白处触碰销毁对话框（如下两种方法都可行），true为可销毁，false为不可销毁
        getDialog().setCanceledOnTouchOutside(cancel);
        //设置窗体显示样式
        getDialog().getWindow().getAttributes().windowAnimations = style;//R.style.fmDialogAnim
        //设置位置
        setGravity(gravity());
    }

    /**
     * 设置窗体大小
     *
     * @param width
     * @param height
     */
    protected void setWHSize(int width, int height) {
        //高度计算从屏幕左上角开始，填充碎片从界面左上角
        getDialog().getWindow().setLayout(width, height);
    }

    /**
     * 设置窗体位置
     *
     * @param gravity 传0默认居中
     */
    protected void setGravity(int gravity) {
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.gravity = gravity;//Gravity.BOTTOM
        getDialog().getWindow().setAttributes(layoutParams);
    }

    /**
     * 设置背景昏暗程度
     */
    protected void setWindowDim(float dimValue) {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = dimValue;
        window.setAttributes(windowParams);
    }

    protected <T> T findView(View view, int viewId) {
        return (T) view.findViewById(viewId);
    }
    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    protected int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    protected int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    protected int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @return
     */
    protected int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 多个回调这里追加
     */
    public interface OnConfirmListener {
        void confirm(View v);
    }

    public OnConfirmListener confirmListener;

    public void setOnConfirmListener(OnConfirmListener _confirmListener) {
        confirmListener = _confirmListener;
    }
}