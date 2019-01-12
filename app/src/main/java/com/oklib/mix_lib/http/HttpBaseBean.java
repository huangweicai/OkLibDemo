package com.oklib.mix_lib.http;

/**
 * 时间：2017/10/26
 * 作者：蓝天
 * 描述：Http相应数据处理基类
 */

public class HttpBaseBean {

    /**
     * ret : 0
     * enMsg : null
     * zhMsg : null
     * detailMsg : null
     * result : {"token":"45dfa31b-61af-43dc-996b-95bbf1e44331","refreshToken":"8e86f0bcf4adf99d2a3ac6fe1600e354","uid":"e3725c7fbd2d4ef484fb4a50fe3b077b"}
     */

    private int ret;
    private String enMsg;
    private String zhMsg;
    private String detailMsg;
    private Object result;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public String getZhMsg() {
        return zhMsg;
    }

    public void setZhMsg(String zhMsg) {
        this.zhMsg = zhMsg;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
