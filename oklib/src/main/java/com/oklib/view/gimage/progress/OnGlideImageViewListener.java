package com.oklib.view.gimage.progress;


import com.bumptech.glide.load.engine.GlideException;

 /**
   * 时间：2017/8/2
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：
   */
public interface OnGlideImageViewListener {

    void onProgress(int percent, boolean isDone, GlideException exception);
}
