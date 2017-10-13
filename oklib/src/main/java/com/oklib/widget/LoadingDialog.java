package com.oklib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.oklib.R;


/**
 * 时间：2017/8/17
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：加载等待窗口
 */
public class LoadingDialog extends Dialog {

    private static Context mContext;
    private String tip = "请稍候";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oklib_dialog_loading);

        this.setCancelable(true); // 是否可以按“返回键”消失
        this.setCanceledOnTouchOutside(false); // 点击加载框以外的区域

        ((TextView) findViewById(R.id.dialog_loading_tip)).setText(tip);// 设置加载信息
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ((TextView) findViewById(R.id.dialog_loading_tip)).setText(tip);// 设置加载信息
    }

    /**
     * 作者：黄伟才
     * 描述：设置提示文本
     */
    public LoadingDialog setTip(String tip) {
        this.tip = tip;
        return this;
    }

    /**
     * 作者：黄伟才
     * 描述：设置外部点击是否消失
     */
    public LoadingDialog setOnTouchOutsideCanceled(boolean canceledOnTouchOutside) {
        this.setCancelable(canceledOnTouchOutside); // 是否可以按“返回键”消失
        this.setCanceledOnTouchOutside(canceledOnTouchOutside); // 点击加载框以外的区域
        return this;
    }

    public LoadingDialog(Context context) {
        super(context, R.style.oklib_LoadingDialogStyle);
        mContext = context;
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.oklib_PopWindowAnimStyle);
    }

}