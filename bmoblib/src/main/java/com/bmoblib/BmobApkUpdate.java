package com.bmoblib;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * 时间：2017/8/30
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：apk更新帮助类
 */

public class BmobApkUpdate {

    private static UpdateResponse ur;

    public static void initAppVersion() {
        //初始化建表操作
        BmobUpdateAgent.initAppVersion();

        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                // TODO Auto-generated method stub
                if (updateStatus == UpdateStatus.Yes) {
                    ur = updateInfo;
                } else if (updateStatus == UpdateStatus.IGNORED) {//新增忽略版本更新
                    Toast.makeText(BmoBHelp.appContext, "该版本已经被忽略更新", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void update(Context context) {
        // 自动更新方法通常可以放在应用的启动页
        BmobUpdateAgent.update(context);
    }

    public static void forceUpdate(Context context) {
        // 手动检查更新
        BmobUpdateAgent.forceUpdate(context);
    }

    public static void silentUpdate(Context context) {
        // 静默下载文件后提示更新
        BmobUpdateAgent.silentUpdate(context);
    }

    public static void deleteApk(Context context) {
        // 删除下载的apk文件
        if (ur != null) {
            File file = new File(Environment
                    .getExternalStorageDirectory(), ur.path_md5 + ".apk");
            if (file != null) {
                if (file.delete()) {
                    Toast.makeText(context, "删除完成",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "删除失败",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "删除完成", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

}
