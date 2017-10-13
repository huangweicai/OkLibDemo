package com.oklib.widget;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseDialogFragment;


/**
 * 创建时间：2017/2/6
 * 编写者：黄伟才
 * 功能描述：通用确认dialog
 */

public class ConfirmDialog extends BaseDialogFragment implements View.OnClickListener {
    private TextView tv_title;
    private TextView tv_content;
    private Button btn_cancel;
    private Button btn_confirm;

    @Override
    public float initDimValue() {
        return 0;
    }

    @Override
    public void initOnResume() {
        setWHSize(getScreenWidth(getContext()) * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean isCancel() {
        return true;
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
        return R.layout.oklib_dialog_confirm;
    }

    @Override
    protected void argumentsDate() {

    }

    @Override
    public void initActionBarView(View view) {

    }

    @Override
    public void initView(View view) {
        tv_title = findView(view, R.id.tv_title);
        tv_content = findView(view, R.id.tv_content);
        btn_cancel = findView(view, R.id.btn_cancel);
        btn_confirm = findView(view, R.id.btn_confirm);

        btn_cancel.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void initData(View view) {

    }

    @Override
    protected void initNet() {

    }


    /**
     * 设置标题
     */
    public void setTitle(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == tv_title) {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(text)) {
                            tv_title.setText("");
                        } else {
                            tv_title.setText(text);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 设置内容
     * 包含/这边转成换行符
     */
    public void setContent(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == tv_content) {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(text)) {
                            tv_content.setText("");
                        } else {
                            String mText = text;
                            if (text.contains("/")) {
                                mText = text.replace("/", "\n");
                            }
                            tv_content.setText(mText);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 设置右按钮·确定
     */
    public void setConfirmText(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == btn_confirm) {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(text)) {
                            btn_confirm.setText("");
                        } else {
                            btn_confirm.setText(text);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 设置左按钮·取消
     */
    public void setCancelText(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == btn_cancel) {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(text)) {
                            btn_cancel.setText("");
                        } else {
                            btn_cancel.setText(text);
                        }
                    }
                });
            }
        }).start();
    }


    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private static ConfirmDialog dialog;
    @SuppressLint("ValidFragment")
    private ConfirmDialog() {}

    /**
     * 显示dialog
     */
    public static ConfirmDialog create(FragmentManager _fm) {
        fm = _fm;
        dialog = new ConfirmDialog();
        return dialog;
    }

    public void show() {
        ft = fm.beginTransaction();
        dialog.show(ft, "");
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (v == btn_confirm) {
            if (null != confirmListener) {
                confirmListener.confirm(v);
            }
        }
        if (v == btn_cancel) {
            if (null != onCancelListener) {
                onCancelListener.onCance(v);
            }
        }
    }

    public interface OnCancelListener {
        void onCance(View v);
    }

    public OnCancelListener onCancelListener;

    public void setOnCancelListener(OnCancelListener _onCancelListener) {
        onCancelListener = _onCancelListener;
    }
}
