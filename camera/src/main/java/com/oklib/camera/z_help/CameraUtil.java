package com.oklib.camera.z_help;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

public final class CameraUtil {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /**
     * Create a file Uri for saving an image or video
     *
     * @param context
     * @param type    the type of the file you want saved {@link #MEDIA_TYPE_IMAGE}
     *                {@link #MEDIA_TYPE_VIDEO}
     * @return return the uri of the file ,if create failed,return null
     */
    public static Uri getOutputMediaFileUri(Context context, int type) {
        File file = getOutputMediaFile(context, type);
        if (file == null) {
            return null;
        }
        return Uri.fromFile(file);//照片 截取输出的outputUri， 只能使用 Uri.fromFile
    }

    /**
     * Create a file for saving an image or video,is default in the
     * ../Pictures/[you app PackageName] directory
     *
     * @param context
     * @param type    the type of the file you want saved {@link #MEDIA_TYPE_IMAGE}
     *                {@link #MEDIA_TYPE_VIDEO}
     * @return return the file you create,if create failed,return null
     */
    private static File getOutputMediaFile(Context context, int type) {
        String filePath = null;
        if (type == MEDIA_TYPE_IMAGE) {
            filePath = FileUtil.getRandomImageFilePath();
        } else if (type == MEDIA_TYPE_VIDEO) {
            //filePath = FileUtil.getRandomVideoFilePath();
        } else {
            return null;
        }
        if (TextUtils.isEmpty(filePath)) {
            return null;
        } else {
            return new File(filePath);
        }
    }

    /**
     * invoke the system Camera app and capture a image。 you can received the
     * capture result in {@link Activity.onActivityResult(int,int,Intent)}。 If
     * successed,you can use the outputUri to get the image
     *
     * @param activity
     * @param outputUri   拍照后图片的存储路径
     * @param requestCode
     */
    public static void captureImage(Activity activity, Uri outputUri, int requestCode) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 调用系统裁减功能，裁减某张指定的图片，并输出到指定的位置
     *
     * @param activity
     * @param originalFileUri 原始图片位置
     * @param outputFileUri   裁减后图片的输出位置，两个地址最好不一样。如果一样的话，有的手机上面无法保存裁减的结果
     * @return
     */
    public static void cropImage(Activity activity, Uri originalFileUri, Uri outputFileUri, int requestCode, int aspectX, int aspectY, int outputX,
                                 int outputY) {
        if (originalFileUri == null) {
            return;
        }
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(originalFileUri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 调用系统图库选择照片 使用 {@link getImagePathFromUri}方法从
     * onActivityResult的data.getData()中解析获得的Uri
     *
     * @param activity
     * @param requestCode
     * @return
     */
    public static void pickImageSimple(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 获得The data stream for the file
     */
    public static String getImagePathFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                return getImagePathFromUriKitkat(context, uri);
            }
        }


        return getImagePathFromUriSimple(context, uri);
    }

    /**
     * 4.4以下
     *
     * @param context
     * @param uri
     */
    private static String getImagePathFromUriSimple(Context context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);

        String path = null;
        try {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            if (cursor.moveToFirst()) {
                path = cursor.getString(column_index);
            }

        } catch (Exception e) {

        }
        if (cursor!=null){
            cursor.close();
        }

        return path;

    }

    /**
     * 4.4以上的Document Uri
     *
     * @param context
     * @param uri
     * @return
     */
    private static String getImagePathFromUriKitkat(Context context, Uri uri) {
        String wholeID = DocumentsContract.getDocumentId(uri);
        if (TextUtils.isEmpty(wholeID) || !wholeID.contains(":")) {
            return null;
        }
        // 获得资源唯一ID
        String id = wholeID.split(":")[1];
        // 定义索引字段
        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);

        String filePath = null;
        if (cursor.moveToFirst()) {
            // DATA字段就是本地资源的全路径
            filePath = cursor.getString(columnIndex);
        }
        // 切记要关闭游标
        cursor.close();
        return filePath;
    }

    /**
     * 调用系统图库选择照片,裁减后返回
     * ,4.4上无法确定用户是否是在图库里选择的照片，所以不使用该方法，使用pickImageSimple，返回后在调用裁减
     *
     * @param activity
     * @param filePath    拍照后图片的存储路径
     * @param requestCode
     * @return
     */
    @Deprecated
    public static void pickImageCrop(Activity activity, Uri outputUri, int requestCode, int aspectX, int aspectY, int outputX, int outputY) {
        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        Intent intent = new Intent();
        // 根据版本号不同使用不同的Action
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }

        intent.setType("image/*");
        intent.putExtra("crop", "true");
        // 裁剪框比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // 图片输出大小
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        activity.startActivityForResult(intent, requestCode);
    }

    public static interface ScannerResult {
        void onResult(boolean success);
    }

    /**
     * 扫描某张指定的图片放入系统媒体库
     */
    public static void scannerImage(Activity activity, final Uri fileUri, final ScannerResult scannerResult) {
        if (fileUri == null) {
            if (scannerResult != null) {
                scannerResult.onResult(false);
            }
            return;
        }
        sMediaScannerConnection = new MediaScannerConnection(activity, new MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                sMediaScannerConnection.scanFile(fileUri.getPath(), "image/*");
            }

            public void onScanCompleted(String path, Uri uri) {
                sMediaScannerConnection.disconnect();
                if (scannerResult != null) {
                    scannerResult.onResult(uri != null);
                }
            }
        });
        sMediaScannerConnection.connect();
    }

    private static MediaScannerConnection sMediaScannerConnection;

    /**
     * 查询某张图片有没有被扫描到媒体库
     *
     * @param activity
     * @param filePath
     * @return 返回这个图片在媒体库的Uri，如果没有扫描到媒体库，则返回null
     */
    public static Uri isImageFileInMedia(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media.DISPLAY_NAME + "='" + file.getName() + "'", null, null);
        Uri uri = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            long id = cursor.getLong(0);
            uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
        }
        return uri;
    }

}
