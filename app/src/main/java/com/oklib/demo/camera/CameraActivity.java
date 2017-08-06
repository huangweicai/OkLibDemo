package com.oklib.demo.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.util.FileUtil;
import com.oklib.util.permission.PermissionFail;
import com.oklib.util.permission.PermissionGen;
import com.oklib.util.permission.PermissionSuccess;

import java.io.File;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.help.CameraManager;
import me.nereo.multi_image_selector.help.CameraUtil;


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
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
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
                CameraManager.getInstance().showAlbumAction(CameraActivity.this, false, 1, CameraManager.MODE_SINGLE);
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

                    Log.d("TAG", "CameraManager.mlocitionFileUrl："+CameraManager.mlocitionFileUrl);
                    iv_image.setImageBitmap(BitmapFactory.decodeFile(CameraManager.mlocitionFileUrl));
                }
            }
        }

    }
}
