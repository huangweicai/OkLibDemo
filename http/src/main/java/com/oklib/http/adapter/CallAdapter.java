package com.oklib.http.adapter;


/**
 * 返回值的适配器
 *
 * @param <T>
 */
public interface CallAdapter<T> {

    /**
     * call执行的代理方法
     */
    <R> T adapt(Call<R> call);
}