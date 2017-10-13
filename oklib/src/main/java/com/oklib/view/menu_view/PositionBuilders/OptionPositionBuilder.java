package com.oklib.view.menu_view.PositionBuilders;

import android.view.View;
import android.widget.RelativeLayout;

import com.oklib.view.menu_view.OptionButton;

import static android.widget.RelativeLayout.ABOVE;
import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.ALIGN_PARENT_TOP;
import static android.widget.RelativeLayout.BELOW;
import static android.widget.RelativeLayout.LEFT_OF;
import static android.widget.RelativeLayout.RIGHT_OF;

/**
 * OptionButton位置信息的Builder类，
 * 具体功能就是运用建造者模式，传入一个OptionButton对象，按需求配置出一个LayoutParams来缺点OptionButton的位置
 */

public class OptionPositionBuilder extends PositionBuilder {
    private OptionButton mOptionButton;
    private View mMenuButton;
    private int mWidth = 0, mHeight = 0;
    private int mXMargin = 0, mYMargin = 0;
    private @PositionBuilder.MarginOrientationX int mMarginOrientationX = MARGIN_LEFT;
    private @PositionBuilder.MarginOrientationY int mMarginOrientationY = MARGIN_TOP;
    private boolean mIsAlignMenuButtonX = false,mIsAlignMenuButtonY = false;

    public OptionPositionBuilder(OptionButton optionButton, View menuButton) {
        mOptionButton = optionButton;
        mMenuButton = menuButton;
    }

    @Override
    public OptionPositionBuilder setWidthAndHeight(int width,int height){
        mWidth = width;
        mHeight = height;
        return this;
    }

    @Override
    public OptionPositionBuilder setXYMargin(int XMargin, int YMargin) {
        mXMargin = XMargin;
        mYMargin = YMargin;
        return this;
    }

    @Override
    public OptionPositionBuilder setMarginOrientation(@MarginOrientationX int marginOrientationX, @MarginOrientationY int marginOrientationY) {
        mMarginOrientationX = marginOrientationX;
        mMarginOrientationY = marginOrientationY;
        return this;
    }

    //设置是否以MenuButton为参考
    public OptionPositionBuilder isAlignMenuButton(boolean isAlignMenuButtonX,boolean isAlignMenuButtonY){
        mIsAlignMenuButtonX = isAlignMenuButtonX;
        mIsAlignMenuButtonY = isAlignMenuButtonY;
        return this;
    }

    //进行最后的配置操作，就是把前面配置的属性加入到LayoutParams里面，然后绑定OptionButton。
    @Override
    public void finish(){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth,mHeight);

        if (mMarginOrientationX == MARGIN_LEFT){
            layoutParams.leftMargin = mXMargin;
            if (mIsAlignMenuButtonX){
                layoutParams.addRule(RIGHT_OF,mMenuButton.getId());
            }else {
                layoutParams.addRule(ALIGN_PARENT_LEFT);
            }
        }else {
            layoutParams.rightMargin = mXMargin;
            if (mIsAlignMenuButtonX){
                layoutParams.addRule(LEFT_OF,mMenuButton.getId());
            }else {
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
            }
        }
        if (mMarginOrientationY == MARGIN_TOP){
            layoutParams.topMargin = mYMargin;
            if (mIsAlignMenuButtonY){
                layoutParams.addRule(BELOW,mMenuButton.getId());
            }else {
                layoutParams.addRule(ALIGN_PARENT_TOP);
            }
        }else {
            layoutParams.bottomMargin = mYMargin;
            if (mIsAlignMenuButtonY){
                layoutParams.addRule(ABOVE,mMenuButton.getId());
            }else {
                layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            }
        }
        mOptionButton.setLayoutParams(layoutParams);
    }


}
