package com.hwc.oklib.widget;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwc.oklib.R;
import com.hwc.oklib.util.MD5Util;
import com.hwc.oklib.view.EditTextWithDelete;
import com.hwc.oklib.window.base.BaseDialogFragment;


/**
 * 时间：2017/8/27
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：后台界面密码窗口
 */

public class BackgroudDialog extends BaseDialogFragment {
    @Override
    public float initDimValue() {
        return 0;
    }

    @Override
    public void initOnResume() {
        setWHSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean isCancel() {
        return false;
    }

    @Override
    public int gravity() {
        return 0;
    }

    @Override
    public int style() {
        return 0;
    }

    @Override
    public int initContentView() {
        return R.layout.dialog_backgroud;
    }

    @Override
    protected void argumentsDate() {

    }

    @Override
    public void initActionBarView(View view) {

    }

    private EditTextWithDelete et_input;
    private TextView tv_confirm;

    @Override
    public void initView(View view) {
        et_input = findView(view, R.id.et_input);
        tv_confirm = findView(view, R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onPassWordConfirmListener) {
                    onPassWordConfirmListener.pwConfirm(dialog, MD5Util.md5Code(et_input.getText().toString()));
                }
            }
        });
    }

    public BackgroudDialog setOnDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
        this.onDialogDismissListener = onDialogDismissListener;
        return this;
    }
    public BackgroudDialog setOnPassWordConfirmListener(OnPassWordConfirmListener onPassWordConfirmListener) {
        this.onPassWordConfirmListener = onPassWordConfirmListener;
        return this;
    }
    private OnDialogDismissListener onDialogDismissListener;
    public interface OnDialogDismissListener{
        void dismiss();
    }
    private OnPassWordConfirmListener onPassWordConfirmListener;
    public interface OnPassWordConfirmListener{
        void pwConfirm(BackgroudDialog dialog, String inputText);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != onDialogDismissListener) {
            onDialogDismissListener.dismiss();
        }
    }

    @Override
    public void initData(View view) {

    }

    @Override
    protected void initNet() {

    }

    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private static BackgroudDialog dialog;

    @SuppressLint("ValidFragment")
    private BackgroudDialog() {
    }

    /**
     * 显示dialog
     */
    public static BackgroudDialog create(FragmentManager _fm) {
        fm = _fm;
        dialog = new BackgroudDialog();
        return dialog;
    }

    public void show() {
        ft = fm.beginTransaction();
        dialog.show(ft, "");
    }
}
