package com.oklib.win_lib;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.window.LoadingDialog;


/**
 * 时间：2017/8/17
 * 作者：蓝天
 * 描述：加载等待窗口
 */

public class LoadingDialogActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_loading_dialog;
    }

    private LoadingDialog loadingDialog;
    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(context);

        //显示窗口
        ((TextView)findView(R.id.tv_showDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();

                //关闭窗口
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoadingDialog();
                    }
                }, 2000);
            }
        });

        //显示窗口自定义提示文本
        ((TextView)findView(R.id.tv_showDialogCustomHint)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("加载中，请稍后···");

                //关闭窗口
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoadingDialog();
                    }
                }, 2000);
            }
        });

    }

     /**
       * 描述：自定义加载提示
       */
    protected void showLoadingDialog(String msg) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            if (msg != null) {
                loadingDialog.setTip(msg);
                loadingDialog.onContentChanged();
            }
            loadingDialog.show();
        }
    }

     /**
       * 描述：默认加载提示
       */
    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

     /**
       * 描述：关闭加载等待窗口
       */
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
