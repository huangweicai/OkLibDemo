package com.oklib.camera;

import android.app.Application;

/**
 * 时间：2017/11/9
 * 作者：蓝天
 * 描述：
 */

public class CameraEntry {

    public static String packageName = "";

    public static void init(Application application, String _packageName) {
        packageName = _packageName;
        AppPaths.getInstance().initAppDir(application);
    }
}
