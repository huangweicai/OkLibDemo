package com.hwc.oklib.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 时间：2017/8/27
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：
 */

public class MD5Util {

    /**
     * @param psd MD5要加密的对象
     * @return MD5加密后市返回一个32位小写的字符串，返回“”，代表加密异常（32位 小写）
     *
     */
    public static String md5Code(String psd) {
        try {
            // 加盐
            psd = psd + "";//这里不加
            // 1，获取加密算法对象，单利设计模式
            MessageDigest instance = MessageDigest.getInstance("MD5");
            // 2，通过加密算法操作，对psd进行哈希加密操作
            byte[] digest = instance.digest(psd.getBytes());
            StringBuffer sb = new StringBuffer();
            // 循环16次
            for (byte b : digest) {
                // 获取b的后8位
                int i = b & 0xff;
                // 将10进制数，转化为16进制
                String hexString = Integer.toHexString(i);
                // 容错处理，长度小于2的，自动补0
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                // 把生成的32位字符串添加到stringBuffer中
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
