package com.oklib.demo.util;

import android.content.Context;
import android.content.pm.PackageManager;


/**
 * 创建时间：2017/7/4
 * 编写者：黄伟才
 * 功能描述：
 */

public class PackageInfoUtil {

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        android.content.pm.PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionCode;
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        android.content.pm.PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);//0代表是获取版本信息
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }
}
