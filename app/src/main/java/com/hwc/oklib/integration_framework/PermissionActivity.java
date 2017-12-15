package com.hwc.oklib.integration_framework;

import android.Manifest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.ProgressWebView;
import com.hwc.oklib.util.active_permission.PermissionFail;
import com.hwc.oklib.util.active_permission.PermissionGen;
import com.hwc.oklib.util.active_permission.PermissionSuccess;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/2
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
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
                        mBeans.add(new FunctionDetailBean("activity_permission.xml", BASE_RES + "/layout/activity_permission.xml"));
                        mBeans.add(new FunctionDetailBean("Android6.0动态权限处理方案", url));
                        showDetail();
                    }
                });
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
