package com.hwc.oklib.util;

/**
 * 时间：2017/11/20
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：英文标示转中文
 */

public class CarTypeToCN {

    public static String getCarTypeCN(String carType) {
        switch (carType) {
            case "America":
                return "美国";
            case "England":
                return "英国";
            case "Italy":
                return "意大利";
            case "France":
                return "法国";
            case "Germany":
                return "德国";
            case "China":
                return "中国";
            case "Japan":
                return "日本";
        }
        return "";
    }


}
