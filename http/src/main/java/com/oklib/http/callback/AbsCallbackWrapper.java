package com.oklib.http.callback;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 所有回调的包装类,空实现
 *
 * @param <T>
 */
public class AbsCallbackWrapper<T> extends AbsCallback<T> {
    @Override
    public T convertSuccess(Response value) throws Exception {
        value.close();
        return (T) value;
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {
    }
}