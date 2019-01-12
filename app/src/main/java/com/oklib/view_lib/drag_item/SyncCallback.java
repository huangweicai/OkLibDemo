package com.oklib.view_lib.drag_item;

/**
 * @author Storm
 */
public abstract class SyncCallback<T> {

    public abstract void onSuccess(T data);

    public void onFailure(Exception e) {
    }
}
