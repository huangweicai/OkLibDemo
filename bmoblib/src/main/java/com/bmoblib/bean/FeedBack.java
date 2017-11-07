package com.bmoblib.bean;

import cn.bmob.v3.BmobObject;

/**
 * 时间：2017/8/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：反馈表
 */

public class FeedBack extends BmobObject {

    private String content;//反馈内容
    private String contactWay;//联系方式
    private String contactType;//联系类型

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
}
