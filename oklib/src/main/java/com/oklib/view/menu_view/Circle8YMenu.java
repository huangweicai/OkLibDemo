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


/**
 * OptionButton围绕着MenuButton的布局，Option最大数量为8个
 */

public class Circle8YMenu extends YMenu{
    //8个Option位置的x、y乘积因子
    private float[] xyTimes = {0,0.707f,1,0.707f,0,-0.707f,-1,-0.707f};

    public Circle8YMenu(Context context) {
        super(context);
    }

    public Circle8YMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Circle8YMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置MenuButton的位置，这里设置成位于ViewGroup中心
    @Override
    public void setMenuPosition(View menuButton) {
        new MenuPositionBuilder(menuButton)
                .setWidthAndHeight(getYMenuButtonWidth(),getYMenuButtonHeight())
                .setMarginOrientation(PositionBuilder.MARGIN_RIGHT,PositionBuilder.MARGIN_BOTTOM)
                .setIsXYCenter(true,true)
                .setXYMargin(getYMenuToParentXMargin(),getYMenuToParentYMargin())
                .finish();
    }

    //设置OptionButton的位置，这里是设置成圆形围绕着MenuButton
    @Override
    public void setOptionPosition(OptionButton optionButton, View menuButton, int index) {
        if (index >= 8){
            try {
                throw new Exception("Circle8YMenuView的OptionPosition最大数量为8，超过将会发生错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        int centerX = menuButton.getLeft() + menuButton.getWidth()/2;
        int centerY = menuButton.getTop() + menuButton.getHeight()/2;
        int halfOptionWidth = getYOptionButtonWidth()/2;
        int halfOptionHeight = getYOptionButtonHeight()/2;
        //利用乘积因子来决定不同位置
        float x = xyTimes[index % 8];
        float y = xyTimes[(index + 6) % 8];

        OptionPositionBuilder OptionPositionBuilder = new OptionPositionBuilder(optionButton,menuButton);
        OptionPositionBuilder
                .isAlignMenuButton(false,false)
                .setWidthAndHeight(getYOptionButtonWidth(), getYOptionButtonHeight())
                .setMarginOrientation(PositionBuilder.MARGIN_LEFT,PositionBuilder.MARGIN_TOP)
                //计算OptionButton的位置
                .setXYMargin(
                        (int)(centerX + x * getYOptionXMargin() - halfOptionWidth)
                        ,(int)(centerY + y * getYOptionXMargin() - halfOptionHeight)
                )
                .finish();
    }

    //设置OptionButton的显示动画
    @Override
    public Animation createOptionShowAnimation(OptionButton optionButton, int index) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation= new TranslateAnimation(
                getYMenuButton().getX() - optionButton.getX()
                ,0
                ,getYMenuButton().getY() - optionButton.getY()
                ,0);
        translateAnimation.setDuration(getOptionSD_AnimationDuration());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(getOptionSD_AnimationDuration());
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        //为不同的Option设置延时
        if (index % 2 == 1) {
            animationSet.setStartOffset(getOptionSD_AnimationDuration()/2);
        }
        return animationSet;
    }

    //设置OptionButton的消失动画
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
        //为不同的Option设置延时
        if (index % 2 == 0) {
            animationSet.setStartOffset(getOptionSD_AnimationDuration()/2);
        }
        return animationSet;
    }
}
