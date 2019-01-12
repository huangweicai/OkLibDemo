package com.oklib.view_lib.refresh.bean;

/**
 * 创建时间：2017/4/18
 * 编写者：蓝天
 * 功能描述：
 */

public class ViewHolderBean {

    private int stepNum;
    private String time;

    public ViewHolderBean() {
    }
    public ViewHolderBean(int stepNum, String time) {
        this.stepNum = stepNum;
        this.time = time;
    }
    public ViewHolderBean(int stepNum) {
        this.stepNum = stepNum;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
