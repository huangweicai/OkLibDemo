package com.oklib.http.callback;

import android.os.Environment;

import com.oklib.http.convert.FileConvert;

import java.io.File;

import okhttp3.Response;


/**
 * 文件的回调下载进度监听
 */
public abstract class FileCallback extends AbsCallback<File> {

    private FileConvert convert;    //文件转换类

    public FileCallback() {
        this(null);
    }

    public FileCallback(String destFileName) {
        this(Environment.getExternalStorageDirectory() + FileConvert.DM_TARGET_FOLDER, destFileName);
    }

    public FileCallback(String destFileDir, String destFileName) {
        convert = new FileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertSuccess(Response response) throws Exception {
        File file = convert.convertSuccess(response);
        response.close();
        return file;
    }
}