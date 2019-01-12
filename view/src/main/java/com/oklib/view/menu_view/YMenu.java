package com.oklib.view.menu_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.oklib.view.R;

import java.util.ArrayList;

/**
 * 一个可以弹出收回菜单栏的自定义View，带有动画效果
 * @author Yanzhikai
 */

public abstract class YMenu extends RelativeLayout implements OptionButton.OptionPrepareListener {
    public final static String TAG = "ymenuview";

    private Context mContext;
    private Button mYMenuButton;

    private int drawableIds[] = {R.drawable.oklib_zero, R.drawable.oklib_one, R.drawable.oklib_two, R.drawable.oklib_three,
            R.drawable.oklib_four, R.drawable.oklib_five, R.drawable.oklib_six, R.drawable.oklib_seven};
    private ArrayList<OptionButton> optionButtonList;
    //“选项”占格个数
    private int optionPositionCount = 8;
    //“选项”占格列数
    private int optionColumns = 1;
    //OptionButton实际数量
    private int optionButtonCount = 0;
    private int[] banArray = {};
    private ArrayList<Boolean> banList;
    //MenuButton宽高
    private int mYMenuButtonWidth = 80, mYMenuButtonHeight = 80;
    //OptionButton宽高
    private int mYOptionButtonWidth = 80, mYOptionButtonHeight = 80;
    //MenuButton的X方向边距和Y方向边距（距离父ViewGroup边界）
    private int mYMenuToParentXMargin = 50, mYMenuToParentYMargin = 50;
    //第一个OptionButton的X方向间隔和Y方向间隔
    private int mYOptionYMargin = 15, mYOptionXMargin = 15;
    //第一个OptionButton的X方向边距和Y方向边距（距离父ViewGroup边界）
    private int mYOptionToParentYMargin = 160, mYOptionToParentXMargin = 50;

    private
    @DrawableRes
    int mMenuButtonBackGroundId = R.drawable.oklib_setting;
    private
    @DrawableRes
    int mOptionsBackGroundId = R.drawable.oklib_null_drawable;

    //菜单是否正在打开
    private boolean isShowMenu = false;
    //菜单打开关闭动画
    private Animation menuOpenAnimation, menuCloseAnimation;
    private Animation.AnimationListener animationListener;

    private int mOptionSD_AnimationMode = OptionButton.FROM_BUTTON_TOP;
    private int mOptionSD_AnimationDuration = 600;
    private int mMenuAnimationDuration = 600;
    private OnOptionsClickListener mOnOptionsClickListener;


    public YMenu(Context context) {
        super(context);
        init(context);
    }

    public YMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init(context);
    }

    public YMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init(context);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.oklib_YMenuView, 0, 0);
        mYMenuButtonWidth = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_menuButtonWidth, mYMenuButtonWidth);
        mYMenuButtonHeight = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_menuButtonHeight, mYMenuButtonHeight);
        mYOptionButtonWidth = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_optionButtonWidth, mYOptionButtonWidth);
        mYOptionButtonHeight = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_optionButtonHeight, mYOptionButtonHeight);
        mYMenuToParentXMargin = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_menuToParentXMargin, mYMenuToParentXMargin);
        mYMenuToParentYMargin = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_menuToParentYMargin, mYMenuToParentYMargin);
        optionPositionCount = typedArray.getInteger(R.styleable.oklib_YMenuView_oklib_optionPositionCounts, optionPositionCount);
        optionColumns = typedArray.getInteger(R.styleable.oklib_YMenuView_oklib_optionColumns, optionColumns);
        mYOptionToParentYMargin = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_optionToParentYMargin, mYOptionToParentYMargin);
        mYOptionToParentXMargin = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_optionToParentXMargin, mYOptionToParentXMargin);
        mYOptionYMargin = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_optionYMargin, mYOptionYMargin);
        mYOptionXMargin = typedArray.getDimensionPixelSize(R.styleable.oklib_YMenuView_oklib_optionXMargin, mYOptionXMargin);
        mMenuButtonBackGroundId = typedArray.getResourceId(R.styleable.oklib_YMenuView_oklib_menuButtonBackGround, mMenuButtonBackGroundId);
        mOptionsBackGroundId = typedArray.getResourceId(R.styleable.oklib_YMenuView_oklib_optionsBackGround, R.drawable.oklib_null_drawable);
        mOptionSD_AnimationMode = typedArray.getInt(R.styleable.oklib_YMenuView_oklib_sd_animMode, mOptionSD_AnimationMode);
        mOptionSD_AnimationDuration = typedArray.getInt(R.styleable.oklib_YMenuView_oklib_sd_duration, mOptionSD_AnimationDuration);
        isShowMenu = typedArray.getBoolean(R.styleable.oklib_YMenuView_oklib_isShowMenu, isShowMenu);

        typedArray.recycle();
    }

    private void init(Context context) {
        mContext = context;
        initMenuAnim();
        setMenuButton();

        //在获取到宽高参数之后再进行初始化
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getWidth() != 0 && getHeight() != 0) {
                    try {
//                        Log.d(TAG, "onGlobalLayout: ");
                        setOptionButtons();
                        setOptionBackGrounds(mOptionsBackGroundId);
                        setOptionsImages(drawableIds);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //设置完后立刻注销，不然会不断回调，浪费很多资源
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }


    //初始化MenuButton的点击动画
    private void initMenuAnim() {
        menuOpenAnimation = AnimationUtils.loadAnimation(mContext, R.anim.oklib_rotate_open);
        menuCloseAnimation = AnimationUtils.loadAnimation(mContext, R.anim.oklib_rotate_close);
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mYMenuButton.setClickable(false);
//                Log.d(TAG, "onAnimationStart: ");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mYMenuButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        menuOpenAnimation.setDuration(mMenuAnimationDuration);
        menuCloseAnimation.setDuration(mMenuAnimationDuration);
        menuOpenAnimation.setAnimationListener(animationListener);
        menuCloseAnimation.setAnimationListener(animationListener);
    }


    //初始化BanList
    private void initBan() throws Exception {
        banList = new ArrayList<>(optionPositionCount);
        optionButtonCount = optionPositionCount;
        for (int i = 0; i < optionPositionCount; i++) {
            banList.add(false);
        }
        for (Integer i : banArray) {
            if (i >= 0 && i < optionPositionCount) {
                banList.set(i, true);
                optionButtonCount--;
            } else {
                throw new Exception("Ban数组设置不合理，含有负数或者超出范围");
            }
        }
    }


    private void setMenuButton() {
        mYMenuButton = new Button(mContext);
        //生成ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mYMenuButton.setId(generateViewId());
        }
        //调用抽象方法来确定MenuButton的位置
        setMenuPosition(mYMenuButton);
//        mSetting.setMenuPosition(mYMenuButton);

        //设置打开关闭事件
        mYMenuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowMenu) {
                    showMenu();
                } else {
                    closeMenu();
                }
            }
        });
        mYMenuButton.setBackgroundResource(mMenuButtonBackGroundId);

        addView(mYMenuButton);
    }


    //设置选项按钮
    private void setOptionButtons() throws Exception {
        optionButtonList = new ArrayList<>(optionPositionCount);
        initBan();
        for (int i = 0; i < optionPositionCount; i++) {
//            if (isBan && banArray.length > 0) {
//                //Ban判断
//                if (i > banArray[n] || banArray[n] > optionPositionCount - 1) {
//                    throw new Exception("Ban数组设置不合理，含有负数、重复数字或者超出范围");
//                } else if (i == banArray[n]) {
//                    if (n < banArray.length - 1) {
//                        n++;
//                    }else {
//                        isBan = false;
//                    }
//                    continue;
//                }
//            }
            if (!banList.get(i)) {
                OptionButton optionButton = new OptionButton(mContext, i);
                setOptionPosition(optionButton, mYMenuButton, i);
                optionButton.setOptionPrepareListener(this);
//                mSetting.setOptionPosition(optionButton, mYMenuButton, i);
                addView(optionButton);
                optionButtonList.add(optionButton);
            }
        }
    }


    @Override
    public void onOptionPrepare(OptionButton optionButton, int index) {
        optionButton.setShowAnimation(createOptionShowAnimation(optionButton, index));
        optionButton.setDisappearAnimation(createOptionDisappearAnimation(optionButton, index));
        //在这里才设置Gone很重要，让View可以一开始就触发onGlobalLayout()进行初始化
        if (isShowMenu){
            optionButton.setVisibility(VISIBLE);
        }else {
            optionButton.setVisibility(GONE);
        }
    }

    /**
     * 设置MenuButton的位置,重写该方法进行自定义设置
     *
     * @param menuButton 传入传入MenuButton，此时它的宽高位置属性还未设置，需要在此方法设置。
     */
    public abstract void setMenuPosition(View menuButton);

    /**
     * 设置OptionButton的位置,重写该方法进行自定义设置
     *
     * @param optionButton 传入OptionButton，此时它的宽高位置属性还未设置，需要在此方法设置。
     * @param menuButton   传入MenuButton，此时它已经初始化完毕，可以利用。
     * @param index        传入的是该OptionButton的索引，用于区分不同OptionButton。
     */
    public abstract void setOptionPosition(OptionButton optionButton, View menuButton, int index);

    /**
     * 设置OptionButton的显示动画,重写该方法进行自定义设置
     *
     * @param optionButton 传入了该动画所属的OptionButton，此时它的宽高位置属性已初始化完毕，可以利用。
     * @param index        传入的是该OptionButton的索引，用于区分不同OptionButton。
     * @return             返回的是创建好的动画
     */
    public abstract Animation createOptionShowAnimation(OptionButton optionButton, int index);


    /**
     * 设置OptionButton的消失动画,重写该方法进行自定义设置
     *
     * @param optionButton 传入了该动画所属的OptionButton，此时它的宽高位置属性已初始化完毕，可以利用。
     * @param index        传入的是该OptionButton的索引，用于区分不同OptionButton。
     * @return             返回的是创建好的动画
     */
    public abstract Animation createOptionDisappearAnimation(OptionButton optionButton, int index);

    //设置选项按钮的background
    public void setOptionBackGrounds(@DrawableRes Integer drawableId) {
        for (int i = 0; i < optionButtonList.size(); i++) {
            if (drawableId == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    optionButtonList.get(i).setBackground(null);
                }
            } else {
                optionButtonList.get(i).setBackgroundResource(drawableId);
            }

        }

    }

    //设置选项按钮的图片资源，顺便设置点击事件
    private void setOptionsImages(int... drawableIds) throws Exception {
        this.drawableIds = drawableIds;
        if (optionPositionCount > drawableIds.length + banArray.length) {
            throw new Exception("Drawable资源数量不足");
        }

        for (int i = 0; i < optionButtonList.size(); i++) {
            optionButtonList.get(i).setOnClickListener(new MyOnClickListener(i));
            if (drawableIds == null) {
                optionButtonList.get(i).setImageDrawable(null);
            } else {
                optionButtonList.get(i).setImageResource(drawableIds[i]);
            }

        }
    }

    //设置MenuButton弹出菜单选项时候MenuButton自身的动画，默认为顺时针旋转180度，为空则是关闭动画效果
    public void setMenuOpenAnimation(Animation menuOpenAnimation) {
        if (menuOpenAnimation == null) {
            menuOpenAnimation = AnimationUtils.loadAnimation(mContext, R.anim.oklib_anim_null);
        }
        if (menuOpenAnimation.getDuration() == 0) {
            menuOpenAnimation.setDuration(mMenuAnimationDuration);
        }
        menuOpenAnimation.setAnimationListener(animationListener);
        this.menuOpenAnimation = menuOpenAnimation;


    }

    //设置MenuButton收回菜单选项时候MenuButton自身的动画，默认为逆时针旋转180度，为空则是关闭动画动画效果
    public void setMenuCloseAnimation(Animation menuCloseAnimation) {
        if (menuCloseAnimation == null) {
            menuCloseAnimation = AnimationUtils.loadAnimation(mContext, R.anim.oklib_anim_null);
        }
        if (menuCloseAnimation.getDuration() == 0) {
            menuCloseAnimation.setDuration(mOptionSD_AnimationDuration);
        }
        menuCloseAnimation.setAnimationListener(animationListener);
        this.menuCloseAnimation = menuCloseAnimation;
    }

    //弹出菜单
    public void showMenu() {
        if (!isShowMenu) {
            for (OptionButton button : optionButtonList) {
                button.onShow();
            }
            if (menuOpenAnimation != null) {
                mYMenuButton.startAnimation(menuOpenAnimation);
            }
            isShowMenu = true;
        }
    }

    //关闭菜单
    public void closeMenu() {
        if (isShowMenu) {
            for (OptionButton button : optionButtonList) {
                button.onClose();
            }
            if (menuCloseAnimation != null) {
                mYMenuButton.startAnimation(menuCloseAnimation);
            }
            isShowMenu = false;
        }
    }

    //让OptionButton直接消失，不执行关闭动画
    public void disappearMenu() {
        if (isShowMenu) {
            for (OptionButton button : optionButtonList) {
                button.onDisappear();
            }
            isShowMenu = false;
        }
    }


    //清除所有View，用于之后刷新
    private void cleanMenu() {
        removeAllViews();
        if (optionButtonList != null) {
            optionButtonList.clear();
        }
        isShowMenu = false;
    }

    /*
     * 对整个YMenuView进行重新初始化，用于在做完一些设定之后刷新
     */
    public void refresh() {
        cleanMenu();
        init(mContext);
    }

    public Button getYMenuButton() {
        return mYMenuButton;
    }

    public int getOptionPositionCount() {
        return optionPositionCount;
    }

    public int getOptionColumns() {
        return optionColumns;
    }

    //获取实际的OptionButton数量
    public int getOptionButtonCount() {
        return optionButtonCount;
    }

    public int[] getDrawableIds() {
        return drawableIds;
    }

    public int getMenuButtonBackGroundId() {
        return mMenuButtonBackGroundId;
    }

    public int getOptionsBackGroundId() {
        return mOptionsBackGroundId;
    }

    public int getOptionSD_AnimationDuration() {
        return mOptionSD_AnimationDuration;
    }

    public
    @OptionButton.SD_Animation
    int getOptionSD_AnimationMode() {
        return mOptionSD_AnimationMode;
    }

    public int getYMenuToParentYMargin() {
        return mYMenuToParentYMargin;
    }

    public int getYMenuToParentXMargin() {
        return mYMenuToParentXMargin;
    }

    public int getYMenuButtonWidth() {
        return mYMenuButtonWidth;
    }

    public int getYMenuButtonHeight() {
        return mYMenuButtonHeight;
    }

    public int getYOptionButtonWidth() {
        return mYOptionButtonWidth;
    }

    public int getYOptionButtonHeight() {
        return mYOptionButtonHeight;
    }

    public int getYOptionXMargin() {
        return mYOptionXMargin;
    }

    public int getYOptionYMargin() {
        return mYOptionYMargin;
    }

    public int getYOptionToParentYMargin() {
        return mYOptionToParentYMargin;
    }

    public int getYOptionToParentXMargin() {
        return mYOptionToParentXMargin;
    }

    public ArrayList<OptionButton> getOptionButtonList() {
        return optionButtonList;
    }


    public void setOnOptionsClickListener(OnOptionsClickListener onOptionsClickListener) {
        this.mOnOptionsClickListener = onOptionsClickListener;
    }


    private class MyOnClickListener implements OnClickListener {
        private int index;

        public MyOnClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            if (mOnOptionsClickListener != null) {
                mOnOptionsClickListener.onOptionsClick(index);
            }
        }
    }


    /*
     * 下面的set方法需要在View还没有初始化的时候调用，例如Activity的onCreate方法里
     * 如果不在View还没初始化的时候调用，请使用完set这些方法之后调用refresh()方法刷新
     */

    //设置OptionButton的Drawable资源
    public void setOptionDrawableIds(int... drawableIds) {
        this.drawableIds = drawableIds;
    }


    //设置禁止放置选项的位置序号，注意不能输入负数、重复数字或者大于等于optionPositionCounts的数，会报错。
    public void setBanArray(int... banArray) {
        this.banArray = banArray;

    }

    //设置“选项”占格个数
    public void setOptionPositionCount(int optionPositionCount) {
        this.optionPositionCount = optionPositionCount;
    }

    //设置一开始的时候是否展开菜单
    public void setIsShowMenu(boolean isShowMenu) {
        this.isShowMenu = isShowMenu;
    }

    public void setOptionColumns(int optionColumns) {
        this.optionColumns = optionColumns;
    }


    public void setYMenuButtonWidth(int mYMenuButtonWidth) {
        this.mYMenuButtonWidth = mYMenuButtonWidth;
    }

    public void setYMenuButtonHeight(int mYMenuButtonHeight) {
        this.mYMenuButtonHeight = mYMenuButtonHeight;
    }

    public void setYMenuButtonBottomMargin(int mYMenuButtonBottomMargin) {
        this.mYMenuToParentYMargin = mYMenuButtonBottomMargin;
    }

    public void setYMenuButtonRightMargin(int mYMenuButtonRightMargin) {
        this.mYMenuToParentXMargin = mYMenuButtonRightMargin;
    }

    public void setYOptionButtonWidth(int mYOptionButtonWidth) {
        this.mYOptionButtonWidth = mYOptionButtonWidth;
    }

    public void setYOptionButtonHeight(int mYOptionButtonHeight) {
        this.mYOptionButtonHeight = mYOptionButtonHeight;
    }

    public void setYOptionToParentYMargin(int mYOptionToParentYMargin) {
        this.mYOptionToParentYMargin = mYOptionToParentYMargin;
    }

    public void setYOptionToParentXMargin(int mYOptionToParentXMargin) {
        this.mYOptionToParentXMargin = mYOptionToParentXMargin;
    }

    public void setYOptionXMargin(int mYOptionXMargin) {
        this.mYOptionXMargin = mYOptionXMargin;
    }

    public void setmYOptionYMargin(int mYOptionYMargin) {
        this.mYOptionYMargin = mYOptionYMargin;
    }

    //使用OptionButton里面的静态变量，如OptionButton.FROM_BUTTON_LEFT
    public void setOptionSD_AnimationMode(int optionSD_AnimationMode) {
        this.mOptionSD_AnimationMode = optionSD_AnimationMode;
    }

    public void setOptionSD_AnimationDuration(int optionSD_AnimationDuration) {
        this.mOptionSD_AnimationDuration = optionSD_AnimationDuration;
    }

    public void setMenuButtonBackGroundId(int menuButtonBackGroundId) {
        this.mMenuButtonBackGroundId = menuButtonBackGroundId;
    }

    public void setOptionsBackGroundId(int optionsBackGroundId) {
        this.mOptionsBackGroundId = optionsBackGroundId;
    }


    //用于让用户在外部实现点击事件的接口，index可以区分OptionButton
    public interface OnOptionsClickListener {
        public void onOptionsClick(int index);
    }

}
