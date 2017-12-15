package com.hwc.oklib.integration_framework;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.camera.MultiImageSelectorActivity;
import com.hwc.oklib.camera.help.CameraManager;
import com.hwc.oklib.camera.help.CameraUtil;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.util.FileUtil;
import com.hwc.oklib.util.active_permission.PermissionFail;
import com.hwc.oklib.util.active_permission.PermissionGen;
import com.hwc.oklib.util.active_permission.PermissionSuccess;

import java.io.File;
import java.util.List;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/2
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：拍照、相册使用演示
 */

public class CameraActivity extends BaseAppActivity {
    private Button btn_take_photo;
    private Button btn_photo_album;
    private ImageView iv_image;
    private int type = 1;//1拍照 2相册

    @Override
    protected int initLayoutId() {
        return R.layout.activity_camera;
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
                        mBeans.add(new FunctionDetailBean("activity_camera.xml", BASE_RES +"/layout/activity_camera.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        btn_take_photo = findView(R.id.btn_take_photo);
        btn_photo_album = findView(R.id.btn_photo_album);
        iv_image = findView(R.id.iv_image);

        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                type = 1;
                PermissionGen.with(CameraActivity.this)
                        .addRequestCode(100)
                        .permissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        .request();
            }
        });
        btn_photo_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                type = 2;
                PermissionGen.with(CameraActivity.this)
                        .addRequestCode(100)
                        .permissions(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        .request();
            }
        });
    }

    @Override
    protected void initNet() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void openCamera() {
        //请求码100，请求成功
        switch (type) {
            case 1://拍照
                CameraManager.getInstance().showCameraAction(CameraActivity.this);
                break;
            case 2://相册
                CameraManager.getInstance().showAlbumAction(CameraActivity.this, true, 1, CameraManager.MODE_SINGLE);
                break;
        }
    }

    @PermissionFail(requestCode = 100)
    public void failOpenCamera(boolean isCompletelyFail) {
        //请求码100，请求失败
        Toast.makeText(context, "权限请求失败", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CameraManager.REQUEST_CAMERA) {
            ToastUtil.show("请求拍照");
            //请求拍照
            if (resultCode == RESULT_OK) {
                if (CameraManager.mTmpFile != null) {
                    Uri o = CameraManager.getInstance().getUriForFile(context, CameraManager.mTmpFile);
                    CameraManager.mNewPhotoUri = CameraUtil.getOutputMediaFileUri(context, CameraUtil.MEDIA_TYPE_IMAGE);
                    CameraManager.getInstance().cropImage(this, o, CameraManager.mNewPhotoUri, CameraManager.REQUEST_CODE_CROP_PHOTO);
                }
            } else {
                while (CameraManager.mTmpFile != null && CameraManager.mTmpFile.exists()) {
                    boolean success = CameraManager.mTmpFile.delete();
                    if (success) {
                        CameraManager.mTmpFile = null;
                    }
                }
            }
        } else if (requestCode == CameraManager.REQUEST_IMAGE) {
            ToastUtil.show("请求图片");
            //请求图片
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Uri o = CameraManager.getInstance().getUriForFile(context, new File(path.get(0)));
                CameraManager.mNewPhotoUri = CameraUtil.getOutputMediaFileUri(context, CameraUtil.MEDIA_TYPE_IMAGE);
                CameraManager.getInstance().cropImage(this, o, CameraManager.mNewPhotoUri, CameraManager.REQUEST_CODE_CROP_PHOTO);
            }

        } else if (requestCode == CameraManager.REQUEST_CODE_CROP_PHOTO) {
            //裁剪
            if (resultCode == RESULT_OK) {
                if (CameraManager.mNewPhotoUri != null) {
                    CameraManager.mCurrentFile = new File(CameraManager.mNewPhotoUri.getPath());
                    CameraManager.mlocitionFileUrl = FileUtil.getRealFilePath(context, CameraManager.mNewPhotoUri);

                    ToastUtil.show("裁剪成功："+CameraManager.mlocitionFileUrl);
                    Log.d("TAG", "CameraManager.mlocitionFileUrl："+CameraManager.mlocitionFileUrl);
                    iv_image.setImageBitmap(BitmapFactory.decodeFile(CameraManager.mlocitionFileUrl));
                }
            }
        }

    }
}
