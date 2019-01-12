package com.oklib.mix_lib.http;

/**
 * 创建时间：2017/6/30
 * 编写者：蓝天
 * 功能描述：接口地址
 */

public class UrlConfig {
    //公用端口地址
    private static final String BASE_DOMAIN_PORT = "https://xxx";//域名
    private static final String BASE_IP_PORT = "http://0.0.0.0";//测试地址

    private static String port;
    public static boolean isDomain = true;//是否是域名地址

    static {
        if (isDomain) {
            port = BASE_DOMAIN_PORT;
        } else {
            port = BASE_IP_PORT;
        }
    }

    //判断用户是否存在
    public static final String USER_IS_EXIST = port + "/user/exists";

}
