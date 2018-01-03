package com.hwc.oklib.login;

import android.content.Context;
import android.content.Intent;

import com.bmoblib.bean.UserBean;
import com.hwc.oklib.util.FastJsonUtil;
import com.hwc.oklib.util.SPUtils;


/**
 * 时间：2017/10/26
 * 作者：黄伟才
 * 描述：用户管理类
 */

public class UserManager {
    public static final String USER_KEY = "user_Key";
    public static final String PHONE = "phone";
    public static final String PWS = "pws";
    private static UserBean mUserBean = new UserBean();


    /**
     * 清空缓存状态
     * @param context
     */
    public static void clearCacheState(Context context) {
        SPUtils.put(context, USER_KEY, "");
        //SPUtils.put(context, PHONE, "");
        SPUtils.put(context, PWS, "");
//        SPUtils.put(context, "addressState", false);//切换账号可再次弹起地址窗口
        cacheUserBean(context, new UserBean());//更新本地缓存bean，空
    }

    /**
     * 退出登录
     *
     * @param context
     */
    public static void logout(final Context context) {
        //清空缓存状态
        clearCacheState(context);
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);


//        HttpParams params = new HttpParams();
//        HttpUtil.getInstance().postRequest(UrlConfig.SIGN_OUT, params, new HttpUtil.OnHttpListener() {
//            @Override
//            public void onBefore() {
//            }
//
//            @Override
//            public void onSuccess(Object result) {
//                Intent intent = new Intent(context, SignInActivity.class);
//                context.startActivity(intent);
////                ((BaseAppActivity) context).finish();//退出登录，首页finish
//            }
//
//            @Override
//            public void onError(int code, Exception e) {
//                ToastUtil.show(StateCode.getCodeMsg(code));
//            }
//        });
    }

    /**
     * 获取用户对象
     *
     * @return
     */
    public static UserBean getUserBean() {
        return mUserBean;
    }

    /**
     * 启动app启动获取，或者异常问题再次从缓存获取
     *
     * @param context
     */
    public static void updateUserBeanFromCache(Context context) {
        try {
            mUserBean = FastJsonUtil.json2Bean((String) SPUtils.get(context, USER_KEY, ""), UserBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == mUserBean) {
            mUserBean = new UserBean();
        }
    }

    /**
     * 登录或者注册成功后更新
     *
     * @param context
     * @param userBean
     */
    public static void cacheUserBean(Context context, UserBean userBean) {
        //缓存bean
        SPUtils.put(context, USER_KEY, FastJsonUtil.bean2Json(userBean));
        mUserBean = userBean;
    }

    public static void cachePhone(Context context, String phone) {
        SPUtils.put(context, PHONE, phone);
    }

    public static String getCachePhone(Context context) {
        return (String) SPUtils.get(context, PHONE, "");
    }

    public static void cachePws(Context context, String pws) {
        SPUtils.put(context, PWS, pws);
    }

    public static String getCachePws(Context context) {
        return (String) SPUtils.get(context, PWS, "");
    }

}
