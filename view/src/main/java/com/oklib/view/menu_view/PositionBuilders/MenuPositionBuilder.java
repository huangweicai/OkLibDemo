package com.oklib.view.menu_view.PositionBuilders;

import android.view.View;
import android.widget.RelativeLayout;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.ALIGN_PARENT_TOP;
import static android.widget.RelativeLayout.CENTER_HORIZONTAL;
import static android.widget.RelativeLayout.CENTER_VERTICAL;

/**
 * MenuButton位置信息的Builder类，
 * 具体功能就是运用建造者模式，传入一个OptionButton对象，按需求配置出一个LayoutParams来缺点OptionButton的位置
 */

public class MenuPositionBuilder extends PositionBuilder {
    private View mMenuButton;
    private int mWidth = 0, mHeight = 0;
    private int mXMargin = 0, mYMargin = 0;
    private @MarginOrientationX int mMarginOrientationX = MARGIN_LEFT;
    private @MarginOrientationY int mMarginOrientationY = MARGIN_TOP;
    private boolean mIsXCenter = false, mIsYCenter = false;

    public MenuPositionBuilder(View menuButton) {
        mMenuButton = menuButton;
    }

    @Override
    public MenuPositionBuilder setWidthAndHeight(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    @Override
    public MenuPositionBuilder setXYMargin(int XMargin, int YMargin) {
        mXMargin = XMargin;
        mYMargin = YMargin;
        return this;
    }

    @Override
    public MenuPositionBuilder setMarginOrientation(@MarginOrientationX int marginOrientationX, @MarginOrientationY int marginOrientationY) {
        mMarginOrientationX = marginOrientationX;
        mMarginOrientationY = marginOrientationY;
        return this;
    }

    //设置是否在XY方向处于中心，这个优先于setXYMargin()方法和setMarginOrientation()方法
    public MenuPositionBuilder setIsXYCenter(boolean isXCenter, boolean isYCenter) {
        mIsXCenter = isXCenter;
        mIsYCenter = isYCenter;
        return this;
    }

    //进行最后的配置操作，就是把前面配置的属性加入到LayoutParams里面，然后绑定MenuButton。
    @Override
    public void finish() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);

        if (mIsXCenter){
            //设置为中心的优先
            layoutParams.addRule(CENTER_HORIZONTAL);
        }else {
            if (mMarginOrientationX == MARGIN_LEFT) {
                layoutParams.leftMargin = mXMargin;
                layoutParams.addRule(ALIGN_PARENT_LEFT);
            } else {
                layoutParams.rightMargin = mXMargin;
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
            }
        }

        if (mIsYCenter){
            layoutParams.addRule(CENTER_VERTICAL);
        }else {
            if (mMarginOrientationY == MARGIN_TOP) {
                layoutParams.topMargin = mYMargin;
                layoutParams.addRule(ALIGN_PARENT_TOP);

            } else {
                layoutParams.bottomMargin = mYMargin;
                layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            }
        }

        mMenuButton.setLayoutParams(layoutParams);
    }
}
