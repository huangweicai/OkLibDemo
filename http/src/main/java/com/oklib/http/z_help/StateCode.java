package com.oklib.http.z_help;

/**
 * 时间：2017/11/9
 * 作者：蓝天
 * 描述：状态码处理
 */

public class StateCode {

    public static final int NO = -200;//无匹配码值
    public static final int NO_NET = -201;//请检查网络

    public static final int OK = 0;//业务成功
    public static final int ERROR = -1;//未知错误
    public static final int PARAM_ERROR = 10007;//参数错误

    public static final int PARAM_MISSING = 10013;//参数缺失
    public static final int SIGNATURE_ERROR = 10016;//签名错误
    public static final int PASSWORD_NOT_QUALIFIED = 10020;//密码不符合规范

    public static final int PASSWORD_ERROR = 10022;//用户密码错误
    public static final int SMS_CODE_INCORRECT = 10027;//短信验证码校验不正确

    public static final int DEBUG_MODE_FORBIDDEN = 10400;//debug模式被禁止
    public static final int NOT_EXISTS = 10404;//记录不存在
    public static final int FILE_TOO_LARGE = 10405;//上传图片大小不能超过1M

    public static final int TOKEN_EXPIRED = 20002;//Token无效
    public static final int MOBILE_ALREADY_EXISTS = 20010;//此手机号已是美的用户,可直接登录

    public static final int AI_RESPONSE_ERROR = 30001;//AI返回结果格式不正确
    public static final int IMAGE_UPLOAD_FAILED = 30002;//图片上传失败
    public static final int IMAGE_RECOGNIZE_ERROR = 3003;//图片识别失败
    public static final int IMAGE_STATIC_FAILED = 30004;//图片识别上报失败
    public static final int NOT_FIND_RECIPE = 30005;//菜谱不存在
    public static final int IMAGE_TOO_LARGE = 30006;//图片太大
    public static final int FILE_TYPE_ERROR = 30007;//只能识别图片

    public static final int FILE_MUST_IMAGE = 30008;//只能上传图片

    public static final int INVENTORY_SHORTAGE = 30010;//库存不足
    public static final int INSUFFICIENT_INTEGRATION = 30011;//积分不够
    public static final int AWARD_TIME_ERROR = 30012;//奖品兑奖时间已结束
    public static final int EXCHANGE_LIMIT = 30013;//您已经兑换过这个奖品了
    public static final int EXCHANGE_NO_PRIZE = 30014;//没有奖品了

    //根据状态码匹配文本
    private static String getCodeMsg(int code, String defaultStr) {
        switch (code) {
            case NO_NET:
                return "请检查网络";

            case OK:
                return "业务成功";
            case ERROR:
                return "未知错误";
            case PARAM_ERROR:
                return "参数错误";
            case PARAM_MISSING:
                return "参数缺失";
            case SIGNATURE_ERROR:
                return "签名错误";
            case PASSWORD_NOT_QUALIFIED:
                return "密码不符合规范";
            case PASSWORD_ERROR:
                return "用户密码错误";
            case SMS_CODE_INCORRECT:
                return "短信验证码校验不正确";
            case DEBUG_MODE_FORBIDDEN:
                return "debug模式被禁止";
            case NOT_EXISTS:
                return "记录不存在";
            case FILE_TOO_LARGE:
                return "上传图片大小不能超过1M";
            case TOKEN_EXPIRED:
                return "Token无效";
            case MOBILE_ALREADY_EXISTS:
                return "此手机号已是美的用户,可直接登录";
            case AI_RESPONSE_ERROR:
                return "AI返回结果格式不正确";
            case IMAGE_UPLOAD_FAILED:
                return "图片上传失败";
            case IMAGE_RECOGNIZE_ERROR:
                return "图片识别失败";
            case IMAGE_STATIC_FAILED:
                return "图片识别上报失败";
            case NOT_FIND_RECIPE:
                return "菜谱不存在";
            case IMAGE_TOO_LARGE:
                return "图片太大";
            case FILE_TYPE_ERROR:
                return "只能识别图片";
            case FILE_MUST_IMAGE:
                return "只能上传图片";
            case INVENTORY_SHORTAGE:
                return "库存不足";
            case INSUFFICIENT_INTEGRATION:
                return "积分不够";
            case AWARD_TIME_ERROR:
                return "奖品兑奖时间已结束";
            case EXCHANGE_LIMIT:
                return "您已经兑换过这个奖品了";
            case EXCHANGE_NO_PRIZE:
                return "没有奖品了";
        }
        return defaultStr;
    }

    public static String getCodeMsg(int code) {
        return getCodeMsg(code, "暂无匹配的码值");
    }

}
