package com.oklib.mix_lib;

import android.Manifest;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.util.active_permission.PermissionFail;
import com.oklib.util.active_permission.PermissionGen;
import com.oklib.util.active_permission.PermissionSuccess;
import com.oklib.view.ProgressWebView;


/**
 * 时间：2017/8/2
 * 作者：蓝天
 * 描述：6.0动态权限统一封装框架使用演示
 */

public class PermissionActivity extends BaseAppActivity {
    private ProgressWebView wv_webview;
    private String url = "http://www.jianshu.com/p/7236bb0d91ea";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initView() {
        wv_webview = findView(R.id.wv_webview);
        wv_webview.loadUrl(url);
    }

    /**
     * 作者：黄伟才
     * 描述：动态权限使用
     */
    @Override
    protected void initNet() {
        //具体参考：http://www.jianshu.com/p/7236bb0d91ea
        PermissionGen.with(PermissionActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS
                )
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void openCamera() {
        //请求码100，请求成功
        Toast.makeText(context, "权限请求成功", Toast.LENGTH_LONG).show();
    }

    @PermissionFail(requestCode = 100)
    public void failOpenCamera() {
        //请求码100，请求失败
        Toast.makeText(context, "权限请求失败", Toast.LENGTH_LONG).show();
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
