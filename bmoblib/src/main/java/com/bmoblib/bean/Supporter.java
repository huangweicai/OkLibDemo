package com.bmoblib.bean;


import cn.bmob.v3.BmobObject;

/**
 * 时间：2017/8/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：支持者表
 */

public class Supporter extends BmobObject {

    private String headPortrait;//头像
    private String name;//名称
    private String introduce;//介绍
    private Double sum;//金额
    private String referralLinks;//推广链接
    private String orderId;//订单id

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getReferralLinks() {
        return referralLinks;
    }

    public void setReferralLinks(String referralLinks) {
        this.referralLinks = referralLinks;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
