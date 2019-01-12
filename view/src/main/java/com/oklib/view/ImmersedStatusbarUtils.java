package com.oklib.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 创建时间：2017/3/1
 * 编写者：蓝天
 * 功能描述：状态栏侵入式工具类
 */

public class ImmersedStatusbarUtils {

    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity       要实现的沉浸式状态栏的Activity
     * @param titleViewGroup 头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     * @param isImmerse      侵入状态栏是否生效
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void initAfterSetContentView(Activity activity, View titleViewGroup, boolean isImmerse) {
        if (!isImmerse) {
            return;
        }

        if (activity == null)
            return;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = activity.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        
        //5.1特殊处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//状态栏字体深白色
//                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//状态栏字体深灰色（6.0以上属性）（注意：1.以最后一个设置为准 2.特定的颜色值才有效果）
            decorView.setSystemUiVisibility(option);
            //最好设置，不设置默认状态栏背景颜色是灰色
//            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.colorTheme));
            activity.getWindow().setStatusBarColor(0x00ffffff);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        if (titleViewGroup == null)
            return;

        // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
        int statusBarHeight = getStatusBarHeight(activity);
        titleViewGroup.setPadding(0, statusBarHeight, 0, 0);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void initAfterSetContentView(Activity activity, View titleViewGroup) {
        initAfterSetContentView(activity, titleViewGroup, true);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;

    }

}
