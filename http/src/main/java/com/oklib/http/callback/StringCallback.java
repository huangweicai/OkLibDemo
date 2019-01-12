package com.oklib.http.callback;

import com.oklib.http.convert.StringConvert;

import okhttp3.Response;


/**
 * 返回字符串类型的数据
 */
public abstract class StringCallback extends AbsCallback<String> {

    @Override
    public String convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        try {
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}