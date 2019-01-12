package com.oklib.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 时间：2017/12/12
 * 作者：蓝天
 * 描述：通过代码定义shape/selector
 */

public class DrawableUtil {
    /**
     * 定义一个shape资源
     *
     * @param rgb
     * @param corneradius
     * @return
     */
    public static GradientDrawable getDrawable(Context context, int rgb, int corneradius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setColor(rgb);
//        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
//        gradientDrawable.setCornerRadius(corneradius);
//        gradientDrawable.setStroke(DensityUtils.dp2px(context, 1), rgb);
        return gradientDrawable;
    }

    public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        //设置默认状态
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }
}
