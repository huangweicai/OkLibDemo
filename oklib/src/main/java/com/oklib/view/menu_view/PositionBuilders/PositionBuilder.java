package com.oklib.view.menu_view.PositionBuilders;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 用于建立MenuButton和OptionButton位置信息的Builder抽象类
 */

public abstract class PositionBuilder {
    //以某对象的边缘做为参考物
    public static final int MARGIN_LEFT = 0, MARGIN_TOP = 1, MARGIN_RIGHT = 2, MARGIN_BOTTOM = 3;

    @IntDef({MARGIN_LEFT,MARGIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface MarginOrientationX {}

    @IntDef({MARGIN_TOP,MARGIN_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface MarginOrientationY {}

    //设置宽高
    public abstract PositionBuilder setWidthAndHeight(int width,int height);

    //设置XY距离
    public abstract PositionBuilder setXYMargin(int XMargin,int YMargin);

    //设置XY方向的参考，如果设置了MARGIN_LEFT和MARGIN_TOP，那么XMargin和YMargin就是与参照物左边界和上边界的距离
    public abstract PositionBuilder setMarginOrientation(@MarginOrientationX int marginOrientationX,@MarginOrientationY int marginOrientationY);

    //进行最后的配置操作
    public abstract void finish();

}
