package com.oklib.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 创建时间：2017/3/1
 * 编写者：蓝天
 * 功能描述：滚动控制及监听（最左边、最右边触发加载更多）
 */
public class ScrollMonitorViewPager extends ViewPager {
    private boolean isScroll = true;

    public ScrollMonitorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ScrollMonitorViewPager(Context context) {
        super(context);
    }

    /**
     * 设置vp是否可以滑动
     *
     * @param isScroll
     */
    public void isScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /* return false;//super.onTouchEvent(arg0); */
        if (isScroll)
            return super.onTouchEvent(ev);
        else
            return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    private float x1, x2;
    private float y1, y2;
    private final int MAX_VALUE = 50;

    //先onTouchEvent执行
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //可以区分向左、向右
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();

            if (x1 - x2 > MAX_VALUE) {
                if (Math.abs(y2 - y1) < ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    if (listener != null) {
                        if (this.getCurrentItem() == this.getAdapter().getCount() - 1) {
                            listener.onRight();//左--->右
                        }
                    }
                }
            } else if (x2 - x1 > MAX_VALUE) {
                if (Math.abs(y2 - y1) < ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    if (listener != null) {
                        if (this.getCurrentItem() == 0) {
                            listener.onLeft();//右--->左
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    private OnViewPagerListener listener;

    public void setOnViewPagerListener(OnViewPagerListener _listener) {
        listener = _listener;
    }

    public interface OnViewPagerListener {
        void onLeft();

        void onRight();
    }

}