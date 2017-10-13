package com.bmoblib.bean;

/**
 * 时间：2017/9/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：公告通知类
 */

public class Notice {

    private String noticeText;
    private boolean isShowNotice;
    private String url;

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public boolean isShowNotice() {
        return isShowNotice;
    }

    public void setShowNotice(boolean showNotice) {
        isShowNotice = showNotice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
