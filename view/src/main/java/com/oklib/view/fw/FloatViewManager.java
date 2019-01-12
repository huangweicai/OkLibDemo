package com.oklib.view.fw;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ScrollView;

/**
 * 时间：2017/10/24
 * 作者：蓝天
 * 描述：悬浮窗口管理类
 */

public class FloatViewManager {

    //上下文
    private Context mContext;

    //窗口管理类
    private WindowManager mWindowManager;

    //窗口参数对象
    private WindowManager.LayoutParams wmParams;

    //本类实例
    private static FloatViewManager instance;

    //自定义的FloatView
    private ScrollView mFloatView;


    private FloatViewManager() {

    }

    /**
     * 单例
     *
     * @return
     */
    public static FloatViewManager getInstance() {
        if (null == instance) {
            synchronized (FloatViewManager.class) {
                if (null == instance) {
                    instance = new FloatViewManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化窗口
     */
    public void create(Context mContext, View mFloatView) {
        this.mContext = mContext;
        this.mFloatView = (ScrollView) mFloatView;

        //悬浮参数初始化
        initFloatView();

        //悬浮视图触碰监听
        setOnTouchListener();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 在手机屏幕上显示自定义的FloatView
     */
    private void initFloatView() {
        //初始化窗口管理对象
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
//        wmParams.width = getWidgetWidth(mFloatView);
//        wmParams.height = getWidgetHeight(mFloatView);
        wmParams.width = dip2px(mContext, 150);
        wmParams.height = dip2px(mContext, 250);

        //窗口图案放置位置
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;//Gravity位置影响滑动效果
        // 如果忽略gravity属性，那么它表示窗口的绝对X位置。
        wmParams.x = 0;
        //如果忽略gravity属性，那么它表示窗口的绝对Y位置。
        wmParams.y = 0;
        //电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下。
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        //FLAG_NOT_FOCUSABLE让window不能获得焦点，这样用户快就不能向该window发送按键事件及按钮事件
        //FLAG_NOT_TOUCH_MODAL即使在该window在可获得焦点情况下，仍然把该window之外的任何event发送到该window之后的其他window.
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 期望的位图格式。默认为不透明。参考android.graphics.PixelFormat。
        wmParams.format = PixelFormat.RGBA_8888;
    }

    public boolean isCanTouch = true;
    private void setOnTouchListener() {
//        //设置监听浮动窗口的触摸移动
//        mFloatView.getChildAt(0).setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                isCanTouch = true;
//                return false;
//            }
//        });

        mFloatView.setOnTouchListener(new View.OnTouchListener() {

            int startX = 0;
            int startY = 0;
            int minDistance = ViewConfiguration.get(mContext).getScaledTouchSlop();

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (!isCanTouch) {
                            return false;
                        }

                        //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                        wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth() / 2;

                        //40为状态栏的高度
                        wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight() / 2 - 40;

                        //刷新
                        mWindowManager.updateViewLayout(mFloatView, wmParams);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        if (!isCanTouch) {
                            return false;
                        }

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP:
                        isCanTouch = false;
                        break;
                }

                return false;
            }
        });
    }


    /**
     * 显示悬浮窗口
     */
    public void showFloatView() {
        if (mWindowManager == null && mFloatView == null) {
            return;
        }
        mWindowManager.addView(mFloatView, wmParams);
    }

    /**
     * 在不使用悬浮窗口时，切记要移除
     * （通常在onDestroy执行）
     */
    public void removeFloatView() {
        if (mWindowManager != null && mFloatView != null) {
            mWindowManager.removeView(mFloatView);
        }
    }


    private int getWidgetWidth(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredWidth();
    }

    private int getWidgetHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        //view.getMeasuredWidth(); // 获取宽度
        //view.getMeasuredHeight(); // 获取高度
        return view.getMeasuredHeight();
    }

}
