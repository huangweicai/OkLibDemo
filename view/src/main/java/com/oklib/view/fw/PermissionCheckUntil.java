package com.oklib.view.fw;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * 时间：2017/10/24
 * 作者：蓝天
 * 描述：权限判断类
 */

public class PermissionCheckUntil {
    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     */
    public static boolean getAppOps(Context context) {

        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {

            try {
                Object object = context.getSystemService("appops");
                if (object == null) {
                    return false;
                }
                Class localClass = object.getClass();

                Class[] arrayOfClass = new Class[3];
                arrayOfClass[0] = Integer.TYPE;
                arrayOfClass[1] = Integer.TYPE;
                arrayOfClass[2] = String.class;
                Method method = localClass.getMethod("checkOp", arrayOfClass);
                if (method == null) {
                    return false;
                }
                Object[] arrayOfObject1 = new Object[3];
                arrayOfObject1[0] = Integer.valueOf(24);
                arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
                arrayOfObject1[2] = context.getPackageName();

                int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
                return m == AppOpsManager.MODE_ALLOWED;
            } catch (Exception ex) {
            }
            return false;

        }else{
            return false;
        }

    }
}
