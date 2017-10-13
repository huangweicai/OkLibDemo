package com.bmoblib;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;


/**
 * 时间：2017/8/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：初始化比目
 */

public class BmoBHelp {

    public static Application appContext;

    public static void initBmob(Context context) {
        appContext = (Application) context;
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(context, "997fdb34dd996a342687a46a4a377fac");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(context, "997fdb34dd996a342687a46a4a377fac", "bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
//        BmobConfig config =new BmobConfig.Builder(context)
//        //设置appkey
//        .setApplicationId("997fdb34dd996a342687a46a4a377fac")
//        //请求超时时间（单位为秒）：默认15s
//        .setConnectTimeout(30)
//        //文件分片上传时每片的大小（单位字节），默认512*1024
//        .setUploadBlockSize(1024*1024)
//        //文件的过期时间(单位为秒)：默认1800s
//        .setFileExpiration(2500)
//        .build();
//        Bmob.initialize(config);

        //比目支付
        BmobPayHelp.getInstance().initPay(appContext);

        //apk更新初始化（新版本的更新有问题，等迭代）
        //BmobApkUpdate.initAppVersion();
    }

}
