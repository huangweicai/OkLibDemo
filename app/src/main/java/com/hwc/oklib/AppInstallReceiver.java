package com.hwc.oklib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * 时间：2017/11/29
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：监听apk安装替换卸载广播
 * 注意：自身项目的安装这里是无法监控的、卸载、替换成功可以监听到
 */

public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {//安装成功
            String packageName = intent.getData().getSchemeSpecificPart();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {//卸载成功
            String packageName = intent.getData().getSchemeSpecificPart();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {//替换成功
            String packageName = intent.getData().getSchemeSpecificPart();
        }

    }
}
