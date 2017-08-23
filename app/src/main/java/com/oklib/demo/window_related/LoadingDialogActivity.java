package com.oklib.demo.window_related;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.view.CommonToolBar;
import com.oklib.widget.LoadingDialog;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/8/17
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：加载等待窗口演示
 */

public class LoadingDialogActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_loading_dialog;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_loading_dialog.xml", BASE_RES +"/layout/activity_loading_dialog.xml"));
                        showDetail();
                    }
                });
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

    @Override
    protected void initNet() {

    }


     /**
       * 作者：黄伟才
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
       * 作者：黄伟才
       * 描述：默认加载提示
       */
    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

     /**
       * 作者：黄伟才
       * 描述：关闭加载等待窗口
       */
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
