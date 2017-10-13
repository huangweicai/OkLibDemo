package com.oklib.view.letters_nav.utils;


import com.oklib.view.letters_nav.Letter;
import com.oklib.view.letters_nav.bean.SortModel;

import java.lang.reflect.Field;


/**
 * Created by zz on 2016/8/16.
 */
public class LetterUtil {

    public static String getSortValue(SortModel sortModel) {
        Class<? extends SortModel> clazz = sortModel.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Letter letter = field.getAnnotation(Letter.class);
            if (letter != null) {
                boolean value = letter.isSortField();
                if (value) {
                    try {
                        field.setAccessible(true);
                        Object fieldValue = field.get(sortModel);
                        return fieldValue.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
