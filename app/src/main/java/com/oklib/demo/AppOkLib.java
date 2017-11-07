package com.oklib.demo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.bmoblib.BmoBHelp;
import com.oklib.OkLib;
import com.oklib.OkLibApp;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
//import com.tencent.bugly.crashreport.CrashReport;

import hwc.loadsir.LoadSirApp;


/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：程序入口，做一些初始化的工作
 */

public class AppOkLib extends OkLibApp {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        initSopfix();
        application = this;
        //使用OkLib库必须先调用初始化方法
        new OkLib.Builder()
                .setApplication(this)
                .setPackageName(BuildConfig.APPLICATION_ID)
                .isDebug(true)
                .isShowToast(true)
                .build();

        //初始化比目
        BmoBHelp.initBmob(this);
        //bugly
//        CrashReport.initCrashReport(getApplicationContext(), "36087ab55d", false);
        //LoadSir初始化
        LoadSirApp.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initSopfix() {
        String versionName = "1.1.0";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // initialize最好放在attachBaseContext最前面
        SophixManager.getInstance().setContext(this)
                .setAppVersion(versionName)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
//                        String msg = new StringBuilder("").append(" Mode:").append(mode).append(" Code:").append(code).append
//                                (" Info:").append(info).append(" HandlePatchVersion:").append(handlePatchVersion).toString();
//                        Toast.makeText(BtApplication.this, ""+msg, Toast.LENGTH_LONG).show();
//                        if (null != onPatchListener) {
//                            onPatchListener.onPatch(msg);
//                        }

                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            //Toast.makeText(BtApplication.this, "补丁加载成功，请重新启动应用", Toast.LENGTH_LONG).show();
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            //Toast.makeText(BtApplication.this, "新补丁生效需要重启", Toast.LENGTH_LONG).show();
                            //System.exit(0);
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            SophixManager.getInstance().cleanPatches();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
