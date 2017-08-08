package com.oklib.demo;

import android.view.View;
import android.view.ViewGroup;

import com.oklib.demo.base.BaseAppActivity;
import com.oklib.view.CommonToolBar;
import com.oklib.view.ProgressWebView;

/**
 * 时间：2017/8/7
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：进度WebView使用演示
 */

public class WebViewActivity extends BaseAppActivity {
    private ProgressWebView wv_webview;
    private String url;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initVariable() {
        url = getIntent().getStringExtra(Common.URL);
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
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color);
    }

    @Override
    protected void initView() {
        wv_webview = findView(R.id.wv_webview);
        wv_webview.loadUrl(url);
    }

    @Override
    protected void initNet() {

    }

    //销毁处需要调用
    @Override
    protected void onDestroy() {
        if (wv_webview != null) {
            wv_webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wv_webview.clearHistory();

            ((ViewGroup) wv_webview.getParent()).removeView(wv_webview);
            wv_webview.destroy();
            wv_webview = null;
        }
        super.onDestroy();
    }
}
