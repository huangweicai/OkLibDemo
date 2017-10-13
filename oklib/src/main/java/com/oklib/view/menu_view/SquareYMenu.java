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
 * OptionButton和MenuButton组成一个正方形的布局，Option最大数量为8个
 */

public class SquareYMenu extends YMenu {
    //8个Option位置的x、y乘积因子
    private static final int[] xTimes = {-1,-1,0,-2,-2,-1,-2,0};
    private static final int[] yTimes = {-1,0,-1,-2,-1,-2,0,-2};

    public SquareYMenu(Context context) {
        super(context);
    }

    public SquareYMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareYMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置MenuButton的位置，这里是和默认一样放在右下
    @Override
    public void setMenuPosition(View menuButton) {
        new MenuPositionBuilder(menuButton)
                .setWidthAndHeight(getYMenuButtonWidth(), getYMenuButtonHeight())
                .setMarginOrientation(PositionBuilder.MARGIN_RIGHT,PositionBuilder.MARGIN_BOTTOM)
                .setIsXYCenter(false,false)
                .setXYMargin(getYMenuToParentXMargin(),getYMenuToParentYMargin())
                .finish();
    }

    //设置OptionButton的位置，这里是8个Option和MenuButton一起组成9宫格布局
    @Override
    public void setOptionPosition(OptionButton optionButton, View menuButton, int index) {
        if (index > 7){
            try {
                throw new Exception("SquareYMenuView的OptionPosition最大数量为8，超过将会发生错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int centerX = menuButton.getLeft() + menuButton.getWidth()/2;
        int centerY = menuButton.getTop() + menuButton.getHeight()/2;
        int halfOptionWidth = getYOptionButtonWidth()/2;
        int halfOptionHeight = getYOptionButtonHeight()/2;

        int x = xTimes[index];
        int y = yTimes[index];

        OptionPositionBuilder OptionPositionBuilder = new OptionPositionBuilder(optionButton,menuButton);
        OptionPositionBuilder
                .isAlignMenuButton(false,false)//是否以MenuButton为参考
                .setWidthAndHeight(getYOptionButtonWidth(), getYOptionButtonHeight())
                .setMarginOrientation(PositionBuilder.MARGIN_LEFT,PositionBuilder.MARGIN_TOP)
                .setXYMargin(
                        centerX + x * getYOptionXMargin() - halfOptionWidth
                        ,centerY + y * getYOptionYMargin() - halfOptionHeight
                )
                .finish();
    }

    //设置OptionButton的显示动画，先出现三个，然后后面的在这三个位置上出现
    @Override
    public Animation createOptionShowAnimation(OptionButton optionButton, int index) {
        float fromX,fromY;
        AnimationSet animationSet = new AnimationSet(true);
        if (index < 3){
            fromX = getYMenuButton().getX() - optionButton.getX();
            fromY = getYMenuButton().getY() - optionButton.getY();
        }else if (index < 6){
            fromX = getOptionButtonList().get(0).getX() - optionButton.getX();
            fromY = getOptionButtonList().get(0).getY() - optionButton.getY();
            animationSet.setStartOffset(getOptionSD_AnimationDuration() );
        }else {
            int oldIndex = index % 5;
            fromX = getOptionButtonList().get(oldIndex).getX() - optionButton.getX();
            fromY = getOptionButtonList().get(oldIndex).getY() - optionButton.getY();
            animationSet.setStartOffset(getOptionSD_AnimationDuration() );
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

    //设置OptionButton的消失动画
    @Override
    public Animation createOptionDisappearAnimation(OptionButton optionButton, int index) {
        float toX,toY;
        AnimationSet animationSet = new AnimationSet(true);
        if (index < 3){
            toX = getYMenuButton().getX() - optionButton.getX();
            toY = getYMenuButton().getY() - optionButton.getY();
            if (getOptionButtonCount() > 3) {
                animationSet.setStartOffset(getOptionSD_AnimationDuration());
            }
        }else if (index < 6){
            toX = getOptionButtonList().get(0).getX() - optionButton.getX();
            toY = getOptionButtonList().get(0).getY() - optionButton.getY();
        }else {
            int oldIndex = index % 5;
            toX = getOptionButtonList().get(oldIndex).getX() - optionButton.getX();
            toY = getOptionButtonList().get(oldIndex).getY() - optionButton.getY();
        }

        TranslateAnimation translateAnimation= new TranslateAnimation(
                0
                ,toX
                ,0
                ,toY);
        translateAnimation.setDuration(getOptionSD_AnimationDuration());
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(getOptionSD_AnimationDuration());
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        return animationSet;
    }
}
