package com.oklib.mix_lib.qrcode;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.oklib.CommonManager;
import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.util.active_permission.PermissionFail;
import com.oklib.util.active_permission.PermissionGen;
import com.oklib.util.active_permission.PermissionSuccess;



/**
 * 时间：2017/9/12
 * 作者：蓝天
 * 描述：二维码入口
 */

public class QRCodeEntryActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_qrcode_entry;
    }

    public void qrCode(View view) {
        PermissionGen.with(QRCodeEntryActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
        //Toast.makeText(context, "权限请求成功", Toast.LENGTH_LONG).show();

        //二维码使用相关
        //注意：最好先请求权限，再跳到二维码扫描界面，避免第一次摄像头使用权限问题
        Intent intent = new Intent(this, QRCodeActivity.class);
        intent.putExtra(CommonManager.TITLE, "二维码使用");
        startActivity(intent);
    }

    @PermissionFail(requestCode = 100)
    public void failOpenCamera(boolean isCompletelyFail) {
        //请求码100，请求失败
        Toast.makeText(context, "扫描二维码需要打开相机和散光灯的权限", Toast.LENGTH_LONG).show();
    }

    public void generateQRCode(View view) {
        //二维码生成相关
        Intent intent = new Intent(this, GenerateQRCodeActivity.class);
        intent.putExtra(CommonManager.TITLE, "二维码生成");
        startActivity(intent);
    }

    @Override
    protected void initView() {

    }

}
