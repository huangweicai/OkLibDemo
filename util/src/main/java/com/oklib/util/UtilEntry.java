package com.oklib.util;

import android.app.Application;


 /**
 * 时间：2017/11/9
 * 作者：蓝天
 * 描述：工具类入口
 */

public class UtilEntry {

    public static Application application;
    public static boolean isDebug = true;

    public static void init(Application _application, boolean _isDebug) {
        application = _application;
        isDebug = _isDebug;
    }
}
