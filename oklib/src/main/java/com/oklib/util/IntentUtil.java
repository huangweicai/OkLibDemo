package com.oklib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 时间：2017/8/7
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：intent各种跳转，打电话，web等
 */

public class IntentUtil {

    /**
     * 本地手机浏览器打开url
     *
     * @param context
     */
    public static void localWebOpenUrl(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 调用拨号界面，拨打电话
     * @param context
     * @param phoneNum
     */
    public static void callPhoneByView(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳过拨号界面，直接拨打电话
     *
     * @param context
     * @param phoneNum
     * 注意：记得添加权限<uses-permission android:name="android.permission.CALL_PHONE" />
     * 6.0之后还需要动态申请权限
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        context.startActivity(intent);
    }


}
