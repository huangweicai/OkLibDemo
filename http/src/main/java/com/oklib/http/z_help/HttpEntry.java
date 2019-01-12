package com.oklib.http.z_help;

import android.app.Application;

import com.oklib.http.OklibHttpUtil;

/**
 * 时间：2017/11/9
 * 作者：蓝天
 * 描述：
 */

public class HttpEntry {

    public static void init(Application _application) {
        OklibHttpUtil.getInstance().init(_application);
        //日志框架打印
//        Logger.addLogAdapter(new AndroidLogAdapter());
    }

}
