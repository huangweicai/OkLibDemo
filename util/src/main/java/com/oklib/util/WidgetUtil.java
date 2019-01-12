package com.oklib.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 时间：2017/11/16
 * 作者：蓝天
 * 描述：控件工具类
 */

public class WidgetUtil {


    /**
     * 改变tv中文本部分样式
     *
     * @param tv              文本控件
     * @param text            文本
     * @param specialTextSign 特殊字符串，该字符串需要特殊处理
     * @param color           颜色
     * @param isFontStyle     是否设置字体样式（否，设置该字符串的背景）
     */
    public static void changeTextStyle(TextView tv, String text, String specialTextSign, int color, boolean isFontStyle) {
        if (TextUtils.isEmpty(text)) return;
        if (TextUtils.isEmpty(specialTextSign)) return;
        SpannableStringBuilder myStyleStr;
        Map<Integer, String> indexMap = new HashMap<>();
        int cacheIndex = 0;//累增index
        while (text.indexOf(specialTextSign, cacheIndex) != -1) {
            //特殊字符存在几次，这里执行几次
            //包含
            int index = text.indexOf(specialTextSign, cacheIndex);
            cacheIndex = index + 1;
            indexMap.put(index, "");
        }
        myStyleStr = new SpannableStringBuilder(text);
        for (int start : indexMap.keySet()) {
            //包头不包尾（所以尾巴位置需要进一位）
            int end = start + specialTextSign.length();
            if (isFontStyle) {
                //设置字体样式
                myStyleStr.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            } else {
                //设置该位置下字符串的背景样式
                myStyleStr.setSpan(new BackgroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        tv.setText(myStyleStr);
    }
}
