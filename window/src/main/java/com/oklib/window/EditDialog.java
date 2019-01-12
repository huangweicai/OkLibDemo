package com.oklib.window;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oklib.window.base.BaseDialogFragment;

import static java.lang.Thread.sleep;


/**
 * 创建时间：2017/2/6
 * 编写者：蓝天
 * 功能描述：编辑窗口
 */

public class EditDialog extends BaseDialogFragment implements View.OnClickListener {
    private TextView tv_title;
    private TextView tv_explain;
    private EditText et_content;
    private TextView tv_limit;
    private Button btn_cancel;
    private Button btn_confirm;

    @Override
    public float initDimValue() {
        return 0.5f;
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
        return R.layout.oklib_dialog_eidt;
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
        tv_explain = findView(view, R.id.tv_explain);
        et_content = findView(view, R.id.et_content);
        tv_limit = findView(view, R.id.tv_limit);

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
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
     * 设置说明
     */
    public void setExplain(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == tv_explain) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(text)) {
                            tv_explain.setText("");
                        } else {
                            tv_explain.setText(text);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 设置限制
     */
    public void setLimit(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == tv_limit) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(text)) {
                            tv_limit.setText("");
                        } else {
                            tv_limit.setText(text);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 设置内容
     */
    public void setContent(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == et_content) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(text)) {
                            et_content.setText("");
                        } else {
                            et_content.setText(text);
                            et_content.setSelection(text.length());
                        }
                    }
                });
            }
        }).start();
    }


    private static FragmentTransaction ft;
    private static EditDialog dialog;

    @SuppressLint("ValidFragment")
    private EditDialog() {
    }

    /**
     * 显示dialog
     */
    public static EditDialog create(FragmentManager fm) {
        dialog = new EditDialog();
        ft = fm.beginTransaction();
        return dialog;
    }

    public void show() {
        dialog.show(ft, "");
    }

    /**
     * 获取内容
     *
     * @return
     */
    public String getContent() {
        return et_content.getText().toString();
    }

    /**
     * 获取说明
     *
     * @return
     */
    public String getExplain() {
        return tv_explain.getText().toString();
    }

    /**
     * 获取限制
     *
     * @return
     */
    public String getLimit() {
        return tv_limit.getText().toString();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (v == btn_confirm) {
            if (null != confirmListener) {
                confirmListener.confirm(v);
            }
        }
    }
}
