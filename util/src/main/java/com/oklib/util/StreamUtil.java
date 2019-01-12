package com.oklib.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 创建时间：2017/4/27
 * 编写者：蓝天
 * 功能描述：流工具类
 */

public class StreamUtil {


    /**
     * 一个获取InputStream中字符串内容的方法
     * @param inputStream
     * @return
     * 默认utf-8编码
     */
    public String getString(InputStream inputStream) {
        return getString(inputStream, "utf-8");
    }

    /**
     * inputStream
     * inputStreamReader
     * BufferedReader
     * @param inputStream
     * @param format
     * @return
     */
    public String getString(InputStream inputStream, String format) {
        InputStreamReader inputStreamReader = null;
        try {
            //不同的文本文件可能编码不同，如果出现乱码，可能需要调整编码
            inputStreamReader = new InputStreamReader(inputStream, format);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    private static StreamUtil instance;

    private StreamUtil() {
    }

    //唯一实例入口
    public static StreamUtil getInstance() {
        if (null == instance) {
            synchronized (StreamUtil.class) {
                if (null == instance) {
                    instance = new StreamUtil();
                }
            }
        }
        return instance;
    }
}
