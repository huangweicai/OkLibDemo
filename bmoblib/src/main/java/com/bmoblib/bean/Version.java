package com.bmoblib.bean;

import cn.bmob.v3.BmobObject;

/**
 * 时间：2017/9/3
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：更新表
 */

public class Version extends BmobObject {
    private int versionCode;//2
    private String versionName;//1.1.0
    private boolean isforce;//是否强制更新
    private String apkUrl;//apk地址
    private String platform;//平台
    private long targetSize;//apk大小
    private String updateLog;//更新log

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isforce() {
        return isforce;
    }

    public void setIsforce(boolean isforce) {
        this.isforce = isforce;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(long targetSize) {
        this.targetSize = targetSize;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    @Override
    public String toString() {
        return "Version{" +
                "versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", isforce=" + isforce +
                ", apkUrl='" + apkUrl + '\'' +
                ", platform='" + platform + '\'' +
                ", targetSize='" + targetSize + '\'' +
                ", updateLog='" + updateLog + '\'' +
                '}';
    }
}
