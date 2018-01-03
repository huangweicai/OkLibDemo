package com.hwc.oklib.integration_framework.qrcode;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.util.active_permission.PermissionFail;
import com.hwc.oklib.util.active_permission.PermissionGen;
import com.hwc.oklib.util.active_permission.PermissionSuccess;
import com.hwc.oklib.view.CommonToolBar;

import static com.hwc.oklib.Common.BASE_JAVA;
import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/9/12
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
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
        intent.putExtra(Common.TITLE, "二维码使用");
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
        intent.putExtra(Common.TITLE, "二维码生成");
        startActivity(intent);
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
                        mBeans.add(new FunctionDetailBean("activity_qrcode_entry.xml", BASE_RES + "/layout/activity_qrcode_entry.xml"));

                        String packagePath = "com/oklib/demo/integration_framework/qrcode/";
                        mBeans.add(new FunctionDetailBean("QRCodeActivity.java", BASE_JAVA + packagePath+"QRCodeActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_qrcode.xml", BASE_RES +"/layout/activity_qrcode.xml"));

                        mBeans.add(new FunctionDetailBean("GenerateQRCodeActivity.java", BASE_JAVA + packagePath+"GenerateQRCodeActivity.java"));
                        mBeans.add(new FunctionDetailBean("activity_generate_qrcode.xml", BASE_RES +"/layout/activity_generate_qrcode.xml"));

                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }
}
