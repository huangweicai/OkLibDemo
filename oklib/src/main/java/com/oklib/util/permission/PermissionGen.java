package com.oklib.util.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import com.oklib.util.permission.internal.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.oklib.util.permission.internal.Utils.getActivity;


/**
 * Created by namee on 2015. 11. 17..
 */
public class PermissionGen {
    private String[] mPermissions;
    private int mRequestCode;
    private Object object;

    //--------------------单例------------------------
    private PermissionGen(Object object) {
        this.object = object;
    }

    private PermissionGen() {
    }

    private static class SingletonFactory {
        private static PermissionGen instance = new PermissionGen();
    }

    public static PermissionGen getInstance() {
        return SingletonFactory.instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }
    //---------------------单例-----------------------

    public static PermissionGen with(Activity activity) {
        return new PermissionGen(activity);
    }

    public static PermissionGen with(Fragment fragment) {
        return new PermissionGen(fragment);
    }

    public PermissionGen permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionGen addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public void request() {
        requestPermissions(object, mRequestCode, mPermissions);
    }

//    public void needPermission(Activity activity, int requestCode, String[] permissions) {
//        requestPermissions(activity, requestCode, permissions);
//    }
//
//    public void needPermission(Fragment fragment, int requestCode, String[] permissions) {
//        requestPermissions(fragment, requestCode, permissions);
//    }

    public static void needPermission(Activity activity, int requestCode, String permission) {
        needPermission(activity, requestCode, new String[]{permission});
    }

    public static void needPermission(Fragment fragment, int requestCode, String permission) {
        needPermission(fragment, requestCode, new String[]{permission});
    }

    public static void needPermission(Activity activity, int requestCode, String... permissions) {
        requestPermissions(activity, requestCode, permissions);
    }

    public static void needPermission(Fragment fragment, int requestCode, String... permissions) {
        requestPermissions(fragment, requestCode, permissions);
    }


    @TargetApi(value = Build.VERSION_CODES.M)//23
    private static void requestPermissions(Object object, int requestCode, String[] permissions) {
        if (!Utils.isOverMarshmallow()) {
            //<23版本
            doExecuteSuccess(object, requestCode);
            return;
        }
        List<String> deniedPermissions = Utils.findDeniedPermissions(getActivity(object), permissions);

        if (deniedPermissions.size() > 0) {
            if (object instanceof Activity) {
                //三个参数
                //ActivityCompat.requestPermissions((Activity) context, deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
                //两个参数
                ((Activity) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (object instanceof Fragment) {
                ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }
        } else {
            doExecuteSuccess(object, requestCode);
        }
    }


    private static void doExecuteSuccess(Object activity, int requestCode) {
        Method executeMethod = Utils.findMethodWithRequestCode(activity.getClass(),
                PermissionSuccess.class, requestCode);

        //根据注解的requestCode去执行对应的方法，唯一标识是requestCode码值
        executeMethod(activity, executeMethod);
    }

    private static void doExecuteFail(Object activity, int requestCode) {
        Method executeMethod = Utils.findMethodWithRequestCode(activity.getClass(),
                PermissionFail.class, requestCode);

        executeMethod(activity, executeMethod);
    }

    private static void executeMethod(Object activity, Method executeMethod) {
        if (executeMethod != null) {
            try {
                if (!executeMethod.isAccessible()) executeMethod.setAccessible(true);
                //executeMethod.invoke(activity, null);//java.lang.reflect.InvocationTargetException
                executeMethod.invoke(activity, new Object[]{});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    private static void requestResult(Object obj, int requestCode, String[] permissions,
                                      int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (deniedPermissions.size() > 0) {
            //同时申请多个权限时，只要有一个失败，这里统一走失败方法
            doExecuteFail(obj, requestCode);
        } else {
            //全部授权
            doExecuteSuccess(obj, requestCode);
        }
    }
}
