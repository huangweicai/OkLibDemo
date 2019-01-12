package com.oklib.view.menu_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.oklib.view.menu_view.PositionBuilders.MenuPositionBuilder;
import com.oklib.view.menu_view.PositionBuilders.OptionPositionBuilder;
import com.oklib.view.menu_view.PositionBuilders.PositionBuilder;

import static com.oklib.view.menu_view.OptionButton.FROM_BUTTON_LEFT;
import static com.oklib.view.menu_view.OptionButton.FROM_RIGHT;
import static com.oklib.view.menu_view.OptionButton.FROM_BUTTON_TOP;
import static com.oklib.view.menu_view.OptionButton.FROM_BOTTOM;


/**
 * @author Yanzhikai
 * Description: 一个可以弹出收回菜单栏的自定义View，带有动画效果
 */

public class YMenuView extends YMenu implements OptionButton.OptionPrepareListener{
    public final static String TAG = "ymenuview";

    public YMenuView(Context context) {
        super(context);
    }

    public YMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setMenuPosition(View menuButton){
        new MenuPositionBuilder(menuButton)
                //设置宽高
                .setWidthAndHeight(getYMenuButtonWidth(), getYMenuButtonHeight())
                //设置参考方向
                .setMarginOrientation(PositionBuilder.MARGIN_RIGHT,PositionBuilder.MARGIN_BOTTOM)
                //设置是否在XY方向处于中心
                .setIsXYCenter(false,false)
                //设置XY方向的距离，如果设置了MARGIN_LEFT和MARGIN_TOP，那么XMargin和YMargin就是与参照物左边界和上边界的距离
                .setXYMargin(getYMenuToParentXMargin(),getYMenuToParentYMargin())
                //最后确认时候调用
                .finish();


//        LayoutParams layoutParams = new LayoutParams(getYMenuButtonWidth(), getYMenuButtonHeight());
//        layoutParams.setMarginEnd(getYMenuToParentXMargin());
//        layoutParams.bottomMargin = getYMenuToParentYMargin();
//        layoutParams.addRule(ALIGN_PARENT_RIGHT);
//        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
//        menuButton.setLayoutParams(layoutParams);
    }


    @Override
    public void setOptionPosition(OptionButton optionButton, View menuButton, int index){
//        Log.d(TAG, "setOptionPosition: " + menuButton.getX());
        //设置动画模式和时长
        optionButton.setSD_Animation(getOptionSD_AnimationMode());
        optionButton.setDuration(getOptionSD_AnimationDuration());

        //计算OptionButton的位置
        int position = index % getOptionColumns();

        new OptionPositionBuilder(optionButton,menuButton)
                //设置宽高
                .setWidthAndHeight(getYOptionButtonWidth(), getYOptionButtonHeight())
                //设置在XY方向是否以MenuButton作为参照物
                .isAlignMenuButton(false,false)
                //设置参考方向
                .setMarginOrientation(PositionBuilder.MARGIN_RIGHT,PositionBuilder.MARGIN_BOTTOM)
                //设置XY方向的距离，如果设置了MARGIN_LEFT和MARGIN_TOP，那么XMargin和YMargin就是与参照物左边界和上边界的距离
                .setXYMargin(
                        getYOptionToParentXMargin()
                                + getYOptionXMargin() * position
                                + getYOptionButtonWidth() * position,
                        getYOptionToParentYMargin()
                                + (getYOptionButtonHeight() + getYOptionYMargin())
                                * (index / getOptionColumns()))
                //最后确认时候调用
                .finish();

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                getYOptionButtonWidth()
//                , getYOptionButtonHeight());
//
//        layoutParams.rightMargin = getYOptionToParentXMargin()
//                + getYOptionXMargin() * position
//                + getYOptionButtonWidth() * position;
//
//        layoutParams.bottomMargin = getYOptionToParentYMargin()
//                + (getYOptionButtonHeight() + getYOptionYMargin())
//                * (index / getOptionColumns());
//        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
//        layoutParams.addRule(ALIGN_PARENT_RIGHT);
//
//        optionButton.setLayoutParams(layoutParams);
    }

    @Override
    public Animation createOptionShowAnimation(OptionButton optionButton, int index){
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(getOptionSD_AnimationDuration());
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,0);
        switch (optionButton.getmSD_Animation()){
            //从MenuButton的左边移入
            case FROM_BUTTON_LEFT:
                translateAnimation= new TranslateAnimation(getYMenuButton().getX() - optionButton.getRight(),0
                        ,0,0);
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
                break;
            case FROM_RIGHT:
                //从右边缘移入
                translateAnimation= new TranslateAnimation((getWidth() - optionButton.getX()),0,0,0);
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
//                showAnimation.setInterpolator(new OvershootInterpolator(1.3f));
                break;
            case FROM_BUTTON_TOP:
                //从MenuButton的上边缘移入
                translateAnimation= new TranslateAnimation(0,0,
                        getYMenuButton().getY() - optionButton.getBottom(),0);
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
                break;
            case FROM_BOTTOM:
                //从下边缘移入
                translateAnimation = new TranslateAnimation(0,0,getHeight() - optionButton.getY(),0);
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
        }

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    @Override
    public Animation createOptionDisappearAnimation(OptionButton optionButton, int index){
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(getOptionSD_AnimationDuration());
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,0);
        switch (optionButton.getmSD_Animation()) {
            case FROM_BUTTON_LEFT:
                //从MenuButton的左边移入
                translateAnimation= new TranslateAnimation(0,getYMenuButton().getX() - optionButton.getRight()
                        ,0,0);
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
                break;
            case FROM_RIGHT:
                //从右边缘移出
                translateAnimation = new TranslateAnimation(0, (getWidth()- optionButton.getX()),
                        0, 0);
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
                break;
            case FROM_BUTTON_TOP:
                //从MenuButton的上边移入
                translateAnimation = new TranslateAnimation(0, 0,
                        0, getYMenuButton().getY() - optionButton.getBottom());
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
                break;
            case FROM_BOTTOM:
                //从下边缘移出
                translateAnimation = new TranslateAnimation(0,0,0,getHeight() - optionButton.getY());
                translateAnimation.setDuration(getOptionSD_AnimationDuration());
        }

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }







}
