package com.bmoblib;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import c.b.BP;
import c.b.PListener;
import c.b.QListener;


/**
 * 时间：2017/8/21
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：支付帮助类
 */
public class BmobPayHelp {

    // 此为测试Appid,请将Appid改成你自己的Bmob AppId
    private static String APPID = "997fdb34dd996a342687a46a4a377fac";

    private Context context;

    // 初始化BmobPay对象,可以在支付时再初始化
    public void initPay(Application _application) {
        BP.init(APPID);
    }

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                context.startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    context.startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(context,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }

    public static final int REQUESTPERMISSION = 101;

    private void installApk(String s) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
        } else {
            installBmobPayPlugin(s);
        }
    }


    /**
     * 作者：黄伟才
     * 描述：支付宝支持
     */
    public void alipay(Context context, String productName, double price, String describe, OnPayListener onPayListener) {
        this.context = context;
        pay(true, productName, price, describe, onPayListener);
    }

    /**
     * 作者：黄伟才
     * 描述：微信支付
     */
    public void wechatPay(Context context, String productName, double price, String describe, OnPayListener onPayListener) {
        this.context = context;
        pay(false, productName, price, describe, onPayListener);
    }

    public interface OnPayListener {
        void success();

        void fail();

        //订单回调先于支付成功方法回调
        void orderId(String orderId);
    }


    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    private void pay(final boolean alipayOrWechatPay, String productName, double price, String describe, final OnPayListener onPayListener) {
        if (alipayOrWechatPay) {
            if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                    "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                Toast.makeText(context, "请安装支付宝客户端", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        } else {
            if (checkPackageInstalled("com.tencent.mm", "http://weixin.qq.com")) {// 需要用微信支付时，要安装微信客户端，然后需要插件
                // 有微信客户端，看看有无微信支付插件，170602更新了插件，这里可检查可不检查
                if (!BP.isAppUpToDate(context, "cn.bmob.knowledge", 8)) {
                    Toast.makeText(
                            context,
                            "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)",
                            Toast.LENGTH_SHORT).show();
                    installApk("bp.db");
                    return;
                }
            } else {// 没有安装微信
                Toast.makeText(context, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        showDialog("正在获取订单...\nSDK版本号:" + BP.getPaySdkVersion());

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.bmob.app.sport",
                    "com.bmob.app.sport.wxapi.BmobActivity");
            intent.setComponent(cn);
            context.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        BP.pay(productName, describe, price, alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                hideDialog();
                if (null != onPayListener) {
                    onPayListener.fail();
                }
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                hideDialog();
                if (null != onPayListener) {
                    onPayListener.success();
                }
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                hideDialog();
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                if (null != onPayListener) {
                    onPayListener.orderId(orderId);
                }
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {
                if (null != onPayListener) {
                    onPayListener.fail();
                }
                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            context,
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_SHORT).show();
                    installApk("bp.db");
                } else {
                    Toast.makeText(context, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    // 执行订单查询
    public void queryOrder(String orderId) {
        BP.query(orderId, new QListener() {

            @Override
            public void succeed(String status) {
                Toast.makeText(context, "查询成功!该订单状态为 : " + status,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail(int code, String reason) {
                Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ProgressDialog dialog;

    private void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(context);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    /**
     * 安装assets里的apk文件
     *
     * @param fileName
     */
    public void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* 私有构造方法，防止被实例化 */
    private BmobPayHelp() {
    }

    /* 此处使用一个内部类来维护单例 */
    private static class SingletonFactory {
        private static BmobPayHelp instance = new BmobPayHelp();
    }

    /* 获取实例 */
    public static BmobPayHelp getInstance() {
        return SingletonFactory.instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }
}
