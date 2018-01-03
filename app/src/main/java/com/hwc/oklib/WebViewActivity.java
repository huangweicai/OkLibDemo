package com.hwc.oklib;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.util.SysShareUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.ProgressWebView;


/**
 * 时间：2017/8/7
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：进度WebView使用演示
 */

public class WebViewActivity extends BaseAppActivity {
    public static final String IS_SHOW_WEB_URL = "isShowWebUrl";

    private ProgressWebView wv_webview;
    private String url;
    private boolean isShowWebUrl = false;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initVariable() {
        url = getIntent().getStringExtra(Common.URL);
        isShowWebUrl = getIntent().getBooleanExtra(IS_SHOW_WEB_URL, false);
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

        if (isShowWebUrl) {
            tb_toolbar.setRightTitle("发送", 14, R.color.app_white_color)//右标题
                    .setRightTitleListener(new View.OnClickListener() {//有标题监听
                        @Override
                        public void onClick(View v) {
                            SysShareUtil.getInstance().shareText(context, url);
                        }
                    });
        }
    }

    @Override
    protected void initView() {
        wv_webview = findView(R.id.wv_webview);
        wv_webview.loadUrl(url);
    }

    @Override
    protected void initNet() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_webview.canGoBack()) {
            wv_webview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
