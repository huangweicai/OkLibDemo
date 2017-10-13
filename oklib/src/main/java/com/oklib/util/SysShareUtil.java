package com.oklib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017/5/17
 * 编写者：黄伟才
 * 功能描述：系统分享工具类
 */

public class SysShareUtil {

    private final String SHARE_PANEL_TITLE = "分享到";//分享面板标题

    //分享pdf文件
    public void sharePdf(Context context, String filePath) {
        Uri fileUri = Uri.fromFile(new File(filePath));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("application/pdf");
        context.startActivity(Intent.createChooser(shareIntent, SHARE_PANEL_TITLE));
    }

    //分享文字
    public void shareText(Context context, String shareText) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, SHARE_PANEL_TITLE));
    }

    //分享单张图片
    public void shareSingleImage(Context context, String imagePath) {
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, SHARE_PANEL_TITLE));
    }

    //分享多张图片
    public void shareMultipleImage(Context context, List<String> imagePaths) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (int i = 0; i < imagePaths.size(); i++) {
            String imagePath = imagePaths.get(i);
            Uri imageUri = Uri.fromFile(new File(imagePath));
            uriList.add(imageUri);
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, SHARE_PANEL_TITLE));
    }

    /**
     * 分享图文
     *
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public void shareImageText(Context context, String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain");
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }


    private static SysShareUtil instance;

    private SysShareUtil() {
    }

    //唯一实例入口
    public static SysShareUtil getInstance() {
        if (null == instance) {
            synchronized (SysShareUtil.class) {
                if (null == instance) {
                    instance = new SysShareUtil();
                }
            }
        }
        return instance;
    }

}
