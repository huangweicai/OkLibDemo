package com.oklib.view.menu_view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yanzhikai
 * Description: 这个是菜单弹出的选项按钮
 */

public class OptionButton extends android.support.v7.widget.AppCompatImageView {
    private Animation showAnimation,disappearAnimation;
    public static final int FROM_BUTTON_LEFT = 0 , FROM_BUTTON_TOP = 1,FROM_RIGHT = 2,FROM_BOTTOM = 3;
    private @SD_Animation int mSD_Animation = FROM_BUTTON_LEFT;
    private int mDuration = 600;
    private int mIndex;
    private OptionPrepareListener mOptionPrepareListener;


    @IntDef({FROM_BUTTON_LEFT, FROM_BUTTON_TOP,FROM_RIGHT,FROM_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface SD_Animation {}

    public OptionButton(Context context, int index) {
        super(context);
        mIndex = index;
        init();
    }

    public OptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OptionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setClickable(true);

        //在获取到宽高参数之后再进行初始化
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                if (getX() != 0 && getY() != 0 && getWidth() != 0 && getHeight() != 0) {
                    if (mOptionPrepareListener != null){
                        mOptionPrepareListener.onOptionPrepare(OptionButton.this,mIndex);
                    }
//                    setShowAndDisappear();

                    //在这里才设置Gone很重要，让View可以一开始就触发onGlobalLayout()进行初始化
//                    setVisibility(GONE);
                    //设置完后立刻注销，不然会不断回调，浪费很多资源
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
    }

    /*
     * 此方法需要在View初始化之前使用
     */
    public void setSD_Animation(@SD_Animation int sd_animation){
        mSD_Animation = sd_animation;
    }

    /*
     * 此方法需要在View初始化之前使用
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    private void setShowAndDisappear() {
//        setShowAnimation(mDuration);
//        setDisappearAnimation(mDuration);
        //在这里才设置Gone很重要，让View可以一开始就触发onGlobalLayout()进行初始化
        setVisibility(GONE);
    }

    public void setShowAnimation(Animation showAnimation) {
        this.showAnimation = showAnimation;
    }

    public void setDisappearAnimation(Animation disappearAnimation) {
        this.disappearAnimation = disappearAnimation;
        disappearAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }




    public void setOptionPrepareListener(OptionPrepareListener optionPrepareListener) {
        this.mOptionPrepareListener = optionPrepareListener;
    }

    public int getmSD_Animation() {
        return mSD_Animation;
    }

    public Animation getShowAnimation() {
        return showAnimation;
    }

    public Animation getDisappearAnimation() {
        return disappearAnimation;
    }

    public void onShow() {
        setVisibility(VISIBLE);
        if (showAnimation != null) {
            startAnimation(showAnimation);
        }
    }

    public void onClose() {
        if (disappearAnimation != null) {
            startAnimation(disappearAnimation);
        }
    }

    public void onDisappear(){
        setVisibility(GONE);
    }

    public interface OptionPrepareListener{
        void onOptionPrepare(OptionButton optionButton, int index);
    }
}
