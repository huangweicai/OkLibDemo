package com.oklib.util;

import android.content.Context;

/**
 * 时间：2017/9/14
 * 作者：蓝天
 * 描述：错误信息反馈工具类
 */

public class ErrorMessageUtil {
    public static String printErrorMessage(Context context, String methodName, String errorMessage) {
        return "\n############################errorMessage start ##############################\n"
                + MobileUtil.printMobileInfo(context) + MobileUtil.printSystemInfo() + "\n错误信息：" + errorMessage + "\n方法名：" + methodName + "\n当前app版本号：" + VersionUtil.getVersion(context)
                + "\n############################errorMessage end##############################";
    }
}
