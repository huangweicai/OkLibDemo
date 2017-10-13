package com.oklib.util.camera.help;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.oklib.OkLib;
import com.oklib.R;
import com.oklib.util.camera.MultiImageSelectorActivity;
import com.oklib.util.camera.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 创建时间：2017/2/14
 * 编写者：黄伟才
 * 功能描述：相机、相册管理类
 */

public class CameraManager {

    public static File mTmpFile;
    // 请求加载系统照相机
    public static final int REQUEST_CAMERA = 100;

    /**
     * 系统照相机
     *
     * @param activity
     */
    public void showCameraAction(Activity activity) {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            try {
                mTmpFile = FileUtils.createTmpFile(activity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mTmpFile != null && mTmpFile.exists()) {
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, mTmpFile));
                // 此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１
                //cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                activity.startActivityForResult(cameraIntent, REQUEST_CAMERA);
            } else {
                Toast.makeText(activity, "图片错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, R.string.oklib_msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    public void showCameraAction(Fragment fragment) {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            try {
                mTmpFile = FileUtils.createTmpFile(fragment.getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mTmpFile != null && mTmpFile.exists()) {
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(fragment.getContext(), mTmpFile));
                // 此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１
                //cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                fragment.startActivityForResult(cameraIntent, REQUEST_CAMERA);
            } else {
                Toast.makeText(fragment.getContext(), "图片错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(fragment.getContext(), R.string.oklib_msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 兼容Android N（7.0），文件系统权限的变化问题
     *
     * @param context
     * @param file
     * @return
     */
    public Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), OkLib.packageName + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


    public static Uri mNewPhotoUri;
    //存储本地头像地址
    public static String mlocitionFileUrl;
    public static File mCurrentFile;

    //图库选择回调
    public static final int REQUEST_IMAGE = 11;
    //裁剪图片
    public static final int REQUEST_CODE_CROP_PHOTO = 12;
    //裁剪照片==图片输出大小
    public static final int PHOTO_OUTPUT_X = 600;
    public static final int PHOTO_OUTPUT_Y = 600;

    public static final int MODE_SINGLE = MultiImageSelectorActivity.MODE_SINGLE;//单选
    public static final int MODE_MULTI = MultiImageSelectorActivity.MODE_MULTI;//多选

    /**
     * 三方相册库（封装了系统相册）
     *
     * @param activity
     */
    public void showAlbumAction(Activity activity, boolean isShowCamera, int maxCount, int mode) {
        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, isShowCamera);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxCount);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, mode);
        // 默认选择图片,回填选项(支持String ArrayList)
        //  intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
        activity.startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void showAlbumAction(Fragment fragment, boolean isShowCamera, int maxCount, int mode) {
        Intent intent = new Intent(fragment.getContext(), MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, isShowCamera);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxCount);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, mode);
        // 默认选择图片,回填选项(支持String ArrayList)
        //  intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
        fragment.startActivityForResult(intent, REQUEST_IMAGE);
    }

    private boolean isFreeCut = false;
    public static int aspectX = 1;
    public static int aspectY = 1;
    public static int outputX = 600;
    public static int outputY = 600;

    /**
     * 调用系统裁减功能，裁减某张指定的图片，并输出到指定的位置
     *
     * @param
     * @param originalFileUri 原始图片位置
     * @param outputFileUri   裁减后图片的输出位置，两个地址最好不一样。如果一样的话，有的手机上面无法保存裁减的结果
     * @return
     */
    public void cropImage(Activity activity, Uri originalFileUri, Uri outputFileUri, int requestCode) {
        if (originalFileUri == null) {
            return;
        }
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(originalFileUri, "image/*");
        intent.putExtra("crop", "true");
        if (!isFreeCut) {
            //默认长宽等比裁剪
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
            // 裁剪后输出图片的尺寸大小
            if (outputX != 0) {
                intent.putExtra("outputX", outputX);
                intent.putExtra("outputY", outputY);
            }
        }
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        activity.startActivityForResult(intent, requestCode);
    }

    public void cropImage(Fragment fragment, Uri originalFileUri, Uri outputFileUri, int requestCode) {
        if (originalFileUri == null) {
            return;
        }
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(originalFileUri, "image/*");//这里7.0系统需要FileProvider.getUriForFile处理
        intent.putExtra("crop", "true");
        if (!isFreeCut) {
            //默认长宽等比裁剪
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
            // 裁剪后输出图片的尺寸大小
            if (outputX != 0) {
                intent.putExtra("outputX", outputX);
                intent.putExtra("outputY", outputY);
            }
        }
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);//照片 截取输出的outputUri， 只能使用 Uri.fromFile
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        //7.0临时权限需要，添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 设置是否剪辑和是否自由剪辑
     *
     * @param isFreeCut 是否自由剪辑(不限定宽高)
     */
    public void setIsFreeCut(boolean isFreeCut) {
        this.isFreeCut = isFreeCut;
    }

    /**
     * 获取完整的图片
     */
//    private void getPic() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.putExtra("return-data", true);
//        intent.setType("image/*"); // 说明你想获得图片
//        startActivityForResult(intent, PHONTO);
//    }

    private static CameraManager cameraManager;

    private CameraManager() {
    }

    //唯一实例入口
    public static CameraManager getInstance() {
        if (null == cameraManager) {
            synchronized (CameraManager.class) {
                if (null == cameraManager) {
                    cameraManager = new CameraManager();
                }
            }
        }
        return cameraManager;
    }
}
