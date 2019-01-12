package com.oklib.view.progress;

import android.content.res.Resources;

/**
 * 时间：2017/8/28
 * 作者：蓝天
 * 描述：
 */

public class Utils {
    private Utils() {
    }

    public static float dp2px(Resources resources, float dp) {
        float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5F;
    }

    public static float sp2px(Resources resources, float sp) {
        float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
