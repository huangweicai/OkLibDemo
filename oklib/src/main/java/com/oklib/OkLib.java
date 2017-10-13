package com.oklib;

import android.app.Application;

import com.oklib.util.http.OkGo;
import com.oklib.widget.dialog.StyledDialog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * 时间：2017/7/31
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：库初始化相关工作
 */

public final class OkLib {
    private OkLib() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private OkLib(Builder builder) {
        appContext = builder.appContext;
        packageName = builder.packageName;
        isDebug = builder.isDebug;
        isShowToast = builder.isShowToast;
        init();
    }

    public static Application appContext;
    public static boolean isDebug = true;
    public static boolean isShowToast = true;
    public static String packageName = "";

    private void init() {
        //相册初始化目录
        //AppDir.getInstance().initAppDir(appContext);
        AppPaths.getInstance().initAppDir(appContext);
        //日志框架打印
        Logger.addLogAdapter(new AndroidLogAdapter());
        //dialog大全
        StyledDialog.init(appContext);
        //http
        OkGo.getInstance().init(appContext);
    }

    public static final class Builder {
        private String packageName;
        private Application appContext;
        private boolean isDebug;
        private boolean isShowToast;

        public Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder setApplication(Application appContext) {
            this.appContext = appContext;
            return this;
        }

        public Builder isDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder isShowToast(boolean isShowToast) {
            this.isShowToast = isShowToast;
            return this;
        }

        public OkLib build() {
            return new OkLib(this);
        }
    }

}
