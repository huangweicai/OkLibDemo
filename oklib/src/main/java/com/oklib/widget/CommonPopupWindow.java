package com.oklib.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 创建时间：2017/7/21
 * 编写者：黄伟才
 * 功能描述：通用pop
 *
 *
 //初始化PopupWindow
 private void initPop() {
 customPopupWindow = new CustomPopupWindow.Builder(this)
 .setContentView(R.layout.pop_layout)
 .setwidth(LinearLayout.LayoutParams.MATCH_PARENT)
 .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
 .setAnimationStyle(R.style.anim_popUpWindow_bottom)
 .build();

 customPopupWindow.getItemView(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Toast.makeText(MainActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
customPopupWindow.dismiss();
}
});
 }
 */

public class CommonPopupWindow {
    private PopupWindow mPopupWindow;
    private View contentView;
    private static Context mContext;

    public CommonPopupWindow(Builder builder) {
        contentView = LayoutInflater.from(mContext).inflate(builder.contentViewId, null);
        mPopupWindow = new PopupWindow(contentView, builder.width, builder.height);
        //设置点击外部可以取消，必须和下面这个方法配合才有效
        mPopupWindow.setOutsideTouchable(true);
        //设置一个空背景,设置了这个背景之后，设置点击外部取消才有效
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Popupwindow可以点击,PopupWindow弹出后，所有的触屏和物理按键都有PopupWindows处理。
        // 其他任何事件的响应都必须发生在PopupWindow消失之后， （home  等系统层面的事件除外）。
        // 比如这样一个PopupWindow出现的时候，按back键首先是让PopupWindow消失，
        // 第二次按才是退出activity，
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(builder.animStyle); //设置pop显示的动画效果
    }

    /**
     * popup 消失
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
    

    /**
     * 相对于窗体的显示位置
     *
     * @param view 可以为Activity中的任意一个View（最终的效果一样），
     *                   会通过这个View找到其父Window，也就是Activity的Window。
     * @param gravity 在窗体中的位置，默认为Gravity.NO_GRAVITY
     * @param x 表示距离Window边缘的距离，方向由Gravity决定。
     *          例如：设置了Gravity.TOP，则y表示与Window上边缘的距离；
     *          而如果设置了Gravity.BOTTOM，则y表示与下边缘的距离。
     * @param y
     * @return
     */
    public CommonPopupWindow showAtLocation(View view, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(view, gravity, x, y);
        }
        return this;
    }
    

    /**
     * 显示在anchor控件的正下方，或者相对这个控件的位置
     *
     * @param anchor
     * @param xoff
     * @param yoff
     * @param gravity
     * @return
     */
    public CommonPopupWindow showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (mPopupWindow != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mPopupWindow.showAsDropDown(anchor, xoff, yoff,gravity);
            }
        }
        return this;
    }

    /**
     * 根据id获取view
     *
     * @param viewId
     * @return
     */
    public View getItemView(int viewId) {
        if (mPopupWindow != null) {
            return contentView.findViewById(viewId);
        }
        return null;
    }

    /**
     * 根据id设置pop内部的控件的点击事件的监听
     *
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getItemView(viewId);
        view.setOnClickListener(listener);
    }

    /**
     * builder 类
     */
    public static class Builder {
        private int contentViewId;
        private int width;
        private int height;
        private int animStyle; //动画效果

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setContentView(int contentViewId) {
            this.contentViewId = contentViewId;
            return this;
        }

        public Builder setwidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setheight(int height) {
            this.height = height;
            return this;
        }
        

        public Builder setAnimationStyle(int animStyle) {
            this.animStyle = animStyle;
            return this;
        }

        public CommonPopupWindow build() {
            return new CommonPopupWindow(this);
        }
    }
}
