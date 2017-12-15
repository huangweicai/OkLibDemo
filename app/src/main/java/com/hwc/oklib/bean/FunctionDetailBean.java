package com.hwc.oklib.bean;

/**
 * 时间：2017/8/7
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：功能演示，更多信息数据载体
 */

public class FunctionDetailBean {
    private String name;
    private String url;

    public FunctionDetailBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
