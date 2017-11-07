package com.bmoblib;

import com.bmoblib.bean.FeedBack;
import com.bmoblib.bean.Sponsor;
import com.bmoblib.bean.Supporter;
import com.bmoblib.bean.TechnicalData;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 时间：2017/8/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：数据插入帮助类
 */

public class BmobInsertHelp {

    /**
     * 插入反馈内容
     */
    public static void insertFeedBack(String content, String contactWay, String contactType, final OnInsertSuccessListener onInsertSuccessListener) {
        final FeedBack feedBack = new FeedBack();
        feedBack.setContent(content);
        feedBack.setContactWay(contactWay);
        feedBack.setContactType(contactType);
        feedBack.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.success(objectId);
                    }
                }else{
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.fail(e);
                    }
                }
            }
        });
    }

    /**
     * 支付成功，插入购买推广位信息
     * 插入支持者表
     */
    public static void insertSupporter(Supporter supporter, final OnInsertSuccessListener onInsertSuccessListener) {
        supporter.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.success(objectId);
                    }
                }else{
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.fail(e);
                    }
                }
            }
        });
    }

    /**
     * 支付成功，插入购买推广位信息
     * 插入赞助商表
     */
    public static void insertSponsor(Sponsor sponsor, final OnInsertSuccessListener onInsertSuccessListener) {
        sponsor.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.success(objectId);
                    }
                }else{
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.fail(e);
                    }
                }
            }
        });
    }
    /**
     * 插入技术资料
     */
    public static void insertTechnicalData(TechnicalData technicalData, final OnInsertSuccessListener onInsertSuccessListener) {
        technicalData.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.success(objectId);
                    }
                }else{
                    if (null != onInsertSuccessListener) {
                        onInsertSuccessListener.fail(e);
                    }
                }
            }
        });
    }

    public interface OnInsertSuccessListener{
        void success(String objectId);
        void fail(BmobException e);
    }

}
