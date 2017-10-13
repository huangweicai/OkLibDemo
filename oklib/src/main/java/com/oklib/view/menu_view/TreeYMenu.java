package com.oklib.view.menu_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import com.oklib.view.menu_view.PositionBuilders.MenuPositionBuilder;
import com.oklib.view.menu_view.PositionBuilders.OptionPositionBuilder;
import com.oklib.view.menu_view.PositionBuilders.PositionBuilder;


/**
 * OptionButton分布成一个分叉树的布局，Option最大数量为9个
 */

public class TreeYMenu extends YMenu {
    //9个Option位置的x、y乘积因子
    private static final float[] xTimes = {-1,1,0,-2,-2,2,2,-1,1};
    private static final float[] yTimes = {-1,-1,-2,0,-2,0,-2,-3,-3};

    public TreeYMenu(Context context) {
        super(context);
    }

    public TreeYMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TreeYMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置MenuButton的位置，这是设置在屏幕中下方
    @Override
    public void setMenuPosition(View menuButton) {
        new MenuPositionBuilder(menuButton)
                //设置宽高
                .setWidthAndHeight(getYMenuButtonWidth(), getYMenuButtonHeight())
                //设置参考方向
                .setMarginOrientation(PositionBuilder.MARGIN_RIGHT,PositionBuilder.MARGIN_BOTTOM)
                //设置是否在XY方向处于中心
                .setIsXYCenter(true,false)
                //设置XY方向距离
                .setXYMargin(getYMenuToParentXMargin(),getYMenuToParentYMargin())
                .finish();

//        LayoutParams layoutParams = new LayoutParams(getYMenuButtonWidth(), getYMenuButtonHeight());
//        layoutParams.bottomMargin = getYMenuToParentYMargin();
//        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
//        layoutParams.addRule(CENTER_HORIZONTAL);
//        menuButton.setLayoutParams(layoutParams);
    }

    //设置OptionButton的位置，这里是把9个Option设置为树状布局
    @Override
    public void setOptionPosition(OptionButton optionButton, View menuButton, int index) {
        if (index > 8){
            try {
                throw new Exception("TreeYMenuView的OptionPosition最大数量为9，超过将会发生错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int centerX = menuButton.getLeft() + menuButton.getWidth()/2;
        int centerY = menuButton.getTop() + menuButton.getHeight()/2;
        int halfOptionWidth = getYOptionButtonWidth()/2;
        int halfOptionHeight = getYOptionButtonHeight()/2;

        //利用乘积因子来决定不同位置
        float x = xTimes[index];
        float y = yTimes[index];

        new OptionPositionBuilder(optionButton,menuButton)
                //设置是否以MenuButton为参考
                .isAlignMenuButton(false,false)
                //设置宽高
                .setWidthAndHeight(getYOptionButtonWidth(), getYOptionButtonHeight())
                //设置XY方向的参考，如果设置了MARGIN_LEFT和MARGIN_TOP，那么XMargin和YMargin就是与参照物左边界和上边界的距离
                .setMarginOrientation(PositionBuilder.MARGIN_LEFT,PositionBuilder.MARGIN_TOP)
                //设置XY距离
                .setXYMargin(
                        (int)(centerX + x * getYOptionXMargin() - halfOptionWidth)
                        ,(int)(centerY + y * getYOptionYMargin() - halfOptionHeight)
                )
                .finish();

//        LayoutParams layoutParams = new LayoutParams(getYOptionButtonWidth(), getYOptionButtonHeight());
//        layoutParams.addRule(ALIGN_LEFT);
//        layoutParams.addRule(ALIGN_TOP);
//        layoutParams.leftMargin = (int)(centerX + x * getYOptionXMargin() - halfOptionWidth);
//        layoutParams.topMargin = (int)(centerY + y * getYOptionYMargin() - halfOptionHeight);
//        optionButton.setLayoutParams(layoutParams);
    }

    //设置OptionButton的显示动画，这里是为前三个先从MenuButton冒出，后面的分别从这三个冒出
    @Override
    public Animation createOptionShowAnimation(OptionButton optionButton, int index) {
        float fromX,fromY;
        AnimationSet animationSet = new AnimationSet(true);
        if (index < 3){
            fromX = getYMenuButton().getX() - optionButton.getX();
            fromY = getYMenuButton().getY() - optionButton.getY();
        }else {
            int oldIndex = (index - 3) / 2;
            fromX = getOptionButtonList().get(oldIndex).getX() - optionButton.getX();
            fromY = getOptionButtonList().get(oldIndex).getY() - optionButton.getY();
            //设置冒出动画延时
            animationSet.setStartOffset(getOptionSD_AnimationDuration());
        }

        TranslateAnimation translateAnimation= new TranslateAnimation(
                fromX
                ,0
                ,fromY
                ,0);
        translateAnimation.setDuration(getOptionSD_AnimationDuration());

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(getOptionSD_AnimationDuration());

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setInterpolator(new LinearInterpolator());
        return animationSet;
    }

    //设置OptionButton的消失动画，这里设置的是直接从当前位置移动到MenuButton位置消失
    @Override
    public Animation createOptionDisappearAnimation(OptionButton optionButton, int index) {

        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation= new TranslateAnimation(
                0
                ,getYMenuButton().getX() - optionButton.getX()
                ,0
                ,getYMenuButton().getY() - optionButton.getY()
        );
        translateAnimation.setDuration(getOptionSD_AnimationDuration());
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(getOptionSD_AnimationDuration());

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        //设置动画延时
        animationSet.setStartOffset(60*(getOptionPositionCount() - index));
        return animationSet;
    }
}
