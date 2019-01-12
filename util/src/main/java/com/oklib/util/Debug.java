package com.oklib.util;

import android.util.Log;


/**
 * 创建时间：2017/6/8
 * 编写者：蓝天
 * 功能描述：
 * 1、支持自定义打印标签
 * 2、支持打印基础类型、对象类型
 */
public class Debug {

    private static final String TAG = "Debug";//默认打印tag
    private static final String EMPTY_TAG = "打印标签不可为null";

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(Object tag, Object msg) {
        if (UtilEntry.isDebug) {
            if (null != tag) {
                if (tag instanceof String) {
                    Log.d((String) tag, "" + msg);
                } else {
                    //打印类名
                    Log.d(tag.getClass().getSimpleName(), "" + msg);
                }
            } else {
                Log.d(TAG, EMPTY_TAG);
            }
        }
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (UtilEntry.isDebug) {
            if (null != tag) {
                if (tag instanceof String) {
                    Log.v(tag, "" + msg);
                } else {
                    //打印类名
                    Log.v(tag.getClass().getSimpleName(), "" + msg);
                }
            } else {
                Log.v(TAG, EMPTY_TAG);
            }
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (UtilEntry.isDebug) {
            if (null != tag) {
                if (tag instanceof String) {
                    Log.i(tag, "" + msg);
                } else {
                    //打印类名
                    Log.i(tag.getClass().getSimpleName(), "" + msg);
                }
            } else {
                Log.i(TAG, EMPTY_TAG);
            }
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (UtilEntry.isDebug) {
            if (null != tag) {
                if (tag instanceof String) {
                    Log.e((String) tag, "" + msg);
                } else {
                    //打印类名
                    Log.e(tag.getClass().getSimpleName(), "" + msg);
                }
            } else {
                Log.e(TAG, EMPTY_TAG);
            }
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (UtilEntry.isDebug) {
            if (null != tag) {
                if (tag instanceof String) {
                    Log.w(tag, "" + msg);
                } else {
                    //打印类名
                    Log.w(tag.getClass().getSimpleName(), "" + msg);
                }
            } else {
                Log.d(TAG, EMPTY_TAG);
            }
        }
    }

}
