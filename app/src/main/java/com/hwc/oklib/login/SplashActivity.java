package com.hwc.oklib.login;

import android.content.Intent;
import android.os.Handler;
import android.widget.LinearLayout;

import com.hwc.oklib.MainActivity;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.util.Debug;
import com.hwc.oklib.util.active_permission.PermissionFail;
import com.hwc.oklib.util.active_permission.PermissionSuccess;
import com.hwc.oklib.view.ImmersedStatusbarUtils;

import butterknife.BindView;

/**
 * 创建时间：2017/7/4
 * 编写者：黄伟才
 * 功能描述：闪屏页
 */

public class SplashActivity extends BaseAppActivity {
    @BindView(R.id.ll_wrapper)
    LinearLayout llWrapper;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        ImmersedStatusbarUtils.initAfterSetContentView(this, llWrapper, true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);//跳转无动画
                finish();
//                PermissionGen.needPermission(SplashActivity.this, 200,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }, 2000);
    }

    @Override
    protected void initNet() {

    }

    @PermissionSuccess(requestCode = 200)
    public void openCamera() {
//        Debug.d("SplashActivity openCamera PermissionSuccess");
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        overridePendingTransition(0, 0);//跳转无动画
//        finish();
    }

    @PermissionFail(requestCode = 200)
    public void failOpenCamera() {
        Debug.d("SplashActivity failOpenCamera PermissionFail");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        Debug.d("SplashActivity onRequestPermissionsResult");
//        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

}
