package com.oklib.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * 时间：2017/8/7
 * 作者：蓝天
 * 描述：显示进度的WebView
 */
//@Override
//protected void onDestroy() {
//        if (wv_webview != null) {
//        wv_webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//        wv_webview.clearHistory();
//
//        ((ViewGroup) wv_webview.getParent()).removeView(wv_webview);
//        wv_webview.destroy();
//        wv_webview = null;
//        }
//        super.onDestroy();
//        }

public class ProgressWebView extends WebView {
    private ProgressBar progressbar;

    public ProgressWebView(Context context) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5, 0, 0));
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
//        设置不用系统浏览器打开,直接显示在当前Webview
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //设置 缓存模式 
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        removeJavascriptInterface("searchBoxJavaBridge_");
        WebSettings settings = getSettings();
        settings.setBuiltInZoomControls(true); // 设置显示缩放能力
        settings.setDisplayZoomControls(false);//不显示webview缩放按钮
        settings.setSupportZoom(true); // 支持缩放
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);//缩小到屏幕显示
        settings.setDatabaseEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true); //设置可以访问文件 
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (isNetworkAvailable(this.getContext())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        settings.setAppCacheEnabled(true);//开启 Application Caches 功能
        String cacheDirPath = this.getContext().getFilesDir().getAbsolutePath() + "COCOWebView";
        settings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //滚动时影响进度条位置
//        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
//        lp.x = l;
//        lp.y = t;
//        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 描述：判断网络是否有效.
     *
     * @param context the context
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
