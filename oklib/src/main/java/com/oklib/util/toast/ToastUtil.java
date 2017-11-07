package com.oklib.util.toast;

import android.widget.Toast;
import com.oklib.OkLib;

import static com.oklib.AppConfig.isShowToast;

/**
 * 时间：2017/8/3
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：Toast工具类
 */
public class ToastUtil {

    public static void success(CharSequence text) {
        if(isShowToast)
        Toasty.success(OkLib.appContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void error(CharSequence text) {
        if(isShowToast)
        Toasty.error(OkLib.appContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(CharSequence text) {
        if(isShowToast)
        Toasty.info(OkLib.appContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void warn(CharSequence text) {
        if(isShowToast)
        Toasty.warning(OkLib.appContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void show(CharSequence text) {
        if(isShowToast)
        Toasty.normal(OkLib.appContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(CharSequence text, int resId) {
        if(isShowToast)
        Toasty.normal(OkLib.appContext, text, OkLib.appContext.getResources().getDrawable(resId)).show();
    }

}
