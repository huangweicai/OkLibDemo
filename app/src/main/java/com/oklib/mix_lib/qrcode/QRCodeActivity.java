package com.oklib.mix_lib.qrcode;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.camera.MultiImageSelectorActivity;
import com.oklib.camera.z_help.CameraManager;
import com.oklib.camera.z_help.CameraUtil;
import com.oklib.camera.z_help.FileUtil;
import com.oklib.util.active_permission.PermissionFail;
import com.oklib.util.active_permission.PermissionGen;
import com.oklib.util.active_permission.PermissionSuccess;
import com.oklib.zxing.code.QRCodeView;
import com.oklib.zxing.zxing.QRCodeDecoder;
import com.oklib.zxing.zxing.ZXingView;

import java.io.File;
import java.util.List;

/**
 * 时间：2017/9/12
 * 作者：蓝天
 * 描述：二维码使用
 */

public class QRCodeActivity extends BaseAppActivity implements QRCodeView.Delegate {
    private static final String TAG = QRCodeActivity.class.getSimpleName();

    private QRCodeView mQRCodeView;

    private boolean isCutPicture = false;//是否截图

    @Override
    protected int initLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView() {
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    //振动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        //vibrate();
        //mQRCodeView.startSpot();

        mQRCodeView.startSpotAndShowRect();//开始识别扫描框，包括二维码、条形码
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_spot:
                mQRCodeView.startSpot();
                break;
            case R.id.stop_spot:
                mQRCodeView.stopSpot();
                break;
            case R.id.start_spot_showrect:
                mQRCodeView.startSpotAndShowRect();
                break;
            case R.id.stop_spot_hiddenrect:
                mQRCodeView.stopSpotAndHiddenRect();
                break;
            case R.id.show_rect:
                mQRCodeView.showScanRect();
                break;
            case R.id.hidden_rect:
                mQRCodeView.hiddenScanRect();
                break;
            case R.id.start_preview:
                mQRCodeView.startCamera();
                break;
            case R.id.stop_preview:
                mQRCodeView.stopCamera();
                break;
            case R.id.open_flashlight:
                mQRCodeView.openFlashlight();
                break;
            case R.id.close_flashlight:
                mQRCodeView.closeFlashlight();
                break;
            case R.id.scan_barcode:
                mQRCodeView.changeToScanBarcodeStyle();
                break;
            case R.id.scan_qrcode:
                mQRCodeView.changeToScanQRCodeStyle();
                break;
            case R.id.choose_qrcde_from_gallery:
                PermissionGen.with(QRCodeActivity.this)
                        .addRequestCode(100)
                        .permissions(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        .request();
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void openCamera() {
        //请求码100，请求成功
        CameraManager.getInstance().showAlbumAction(QRCodeActivity.this, false, 1, CameraManager.MODE_SINGLE);
    }

    @PermissionFail(requestCode = 100)
    public void failOpenCamera(boolean isCompletelyFail) {
        //请求码100，请求失败
        Toast.makeText(context, "权限请求失败，无法查看相册中的二维码", Toast.LENGTH_LONG).show();
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

                if (isCutPicture) {
                    //裁剪
                    CameraManager.getInstance().cropImage(this, o, CameraManager.mNewPhotoUri, CameraManager.REQUEST_CODE_CROP_PHOTO);
                }else{
                    //不裁剪
                    //识别二维码
                    extractQRCode(path.get(0));
                }
            }

        } else if (requestCode == CameraManager.REQUEST_CODE_CROP_PHOTO) {
            //裁剪
            if (resultCode == RESULT_OK) {
                if (CameraManager.mNewPhotoUri != null) {
                    CameraManager.mCurrentFile = new File(CameraManager.mNewPhotoUri.getPath());
                    CameraManager.mlocitionFileUrl = FileUtil.getRealFilePath(context, CameraManager.mNewPhotoUri);

                    Log.d("TAG", "CameraManager.mlocitionFileUrl：" + CameraManager.mlocitionFileUrl);
                    //iv_image.setImageBitmap(BitmapFactory.decodeFile(CameraManager.mlocitionFileUrl));

                    //识别二维码
                    extractQRCode(CameraManager.mlocitionFileUrl);
                }
            }
        }

    }


    //识别二维码
    private void extractQRCode(final String picturePath) {

        //显示扫描框
        mQRCodeView.showScanRect();

        /**
         * 这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
         * 请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return QRCodeDecoder.syncDecodeQRCode(picturePath);
            }

            @Override
            protected void onPostExecute(String result) {
                if (TextUtils.isEmpty(result)) {
                    Toast.makeText(QRCodeActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QRCodeActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

}
