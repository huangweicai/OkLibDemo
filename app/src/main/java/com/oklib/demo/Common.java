package com.oklib.demo;

/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：常用数据管理
 */

public final class Common {
    //0集成框架 1常用组件 2常用工具 3窗口相关
    public static String[] getDatas(int type) {
        switch (type) {
            case 0:
                return INTEGRATION_FRAMEWORK;
            case 1:
                return COMMON_COMPONENTS;
            case 2:
                return COMMON_TOOLS;
            case 3:
                return WINDOW_RELATED;
        }
        return null;
    }

    //集成框架
    public static final String[] INTEGRATION_FRAMEWORK = {
            "6.0动态权限统一封装框架",
            "拍照选择、相册选择",
    };
    //常用组件
    public static final String[] COMMON_COMPONENTS = {
            "刷新组件",
            "toolbar封装统一菜单栏",
            "GlideImageView，补充高斯模糊、默认无按下效果等",
            "动态shape，select",
            "TextView炫酷效果",
    };
    //常用工具
    public static final String[] COMMON_TOOLS = {
            "日志logger",
            "拓展Toast，Snackbar",
    };
    //窗口相关
    public static final String[] WINDOW_RELATED = {
    };


    public static final String[] MAIN_LIST = {
            "GlideImageView演示",
            "刷新组件",
            "6.0动态权限统一封装框架",
            "拍照选择、相册选择",
            "日志logger",
            "拓展toast，Snackbar",
            "toolbar封装统一菜单栏",
            "动态shape，select",
            "TextView炫酷效果",
            "http，文件上传，下载，通知栏更新",
            "文件相关",
            "字体相关，样式、动画等",
            "本地缓存 Acache、SP",
            "数据库 ORMLite",
            "app系统相关，信息、振动、音效",
            "视频列表及播放方案",
            "状态栏侵入方案",
            "网络状态判断",
            "正则，手机号码、邮箱、身份证、汉字等",
            "时间工具类",
            "Zip压缩",
            "一键换肤",
            "intent各种跳转，打电话，web等",
    };


    //---------------------------

    public static final String TITLE = "title";
    public static final String URL = "url";


    public static final String BASE_JAVA= "https://github.com/huangweicai/OkLibDemo/tree/master/app/src/main/java/";
    public static final String BASE_RES = "https://github.com/huangweicai/OkLibDemo/tree/master/app/src/main/res";
}
