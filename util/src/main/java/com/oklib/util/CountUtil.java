package com.oklib.util;

import com.oklib.util.toast.ToastUtil;

/**
 * 时间：2017/11/21
 * 作者：蓝天
 * 描述：次数工具类
 */

public class CountUtil {

    private static long preKeyBackTime = 0L;
    private static int count = 0;

    /**
     * 次数监听，成功次数回调
     *
     * @param maxCount        最大成功次数
     * @param onCountListener 监听
     * @param hintText        必须三个，提示吐司
     */
    public static void setOnCountListener(int maxCount, OnCountListener onCountListener, String... hintText) {

        if (null == hintText || hintText.length < 3) {
            ToastUtil.show("提示吐司必须提供三个元素值");
            return;
        }

        if (System.currentTimeMillis() - preKeyBackTime < 2000L) {
            count++;
            preKeyBackTime = System.currentTimeMillis();
            if (count == maxCount - 2) {
                ToastUtil.show(hintText[0]);
            } else if (count == maxCount - 1) {
                ToastUtil.show(hintText[1]);
            } else if (count >= maxCount) {
                count = 0;
                preKeyBackTime = 0L;
                ToastUtil.show(hintText[2]);

                if (null != onCountListener) {
                    onCountListener.successCount();
                }
            }
        } else {
            count = 0;
            count++;
            preKeyBackTime = System.currentTimeMillis();
        }
    }

    public interface OnCountListener {
        void successCount();
    }

}
