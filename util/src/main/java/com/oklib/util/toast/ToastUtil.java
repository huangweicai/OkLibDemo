package com.oklib.util.toast;

import android.widget.Toast;

import com.oklib.util.UtilEntry;

/**
 * 时间：2017/8/3
 * 作者：蓝天
 * 描述：Toast工具类
 */
public class ToastUtil {

    public static void success(CharSequence text) {
        if (UtilEntry.isDebug)
            if (null != UtilEntry.application)
                Toasty.success(UtilEntry.application, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void error(CharSequence text) {
        if (UtilEntry.isDebug)
            if (null != UtilEntry.application)
                Toasty.error(UtilEntry.application, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(CharSequence text) {
        if (UtilEntry.isDebug)
            if (null != UtilEntry.application)
                Toasty.info(UtilEntry.application, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void warn(CharSequence text) {
        if (UtilEntry.isDebug)
            if (null != UtilEntry.application)
                Toasty.warning(UtilEntry.application, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void show(CharSequence text) {
        if (UtilEntry.isDebug)
            if (null != UtilEntry.application)
                Toasty.normal(UtilEntry.application, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(CharSequence text, int resId) {
        if (UtilEntry.isDebug)
            if (null != UtilEntry.application)
                Toasty.normal(UtilEntry.application, text, UtilEntry.application.getResources().getDrawable(resId)).show();
    }

}
