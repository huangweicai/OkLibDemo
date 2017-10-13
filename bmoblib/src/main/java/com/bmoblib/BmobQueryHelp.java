package com.bmoblib;

import com.bmoblib.bean.CommoTools;
import com.bmoblib.bean.CommonComponents;
import com.bmoblib.bean.IntegrationFramework;
import com.bmoblib.bean.Notice;
import com.bmoblib.bean.Sponsor;
import com.bmoblib.bean.Supporter;
import com.bmoblib.bean.TechnicalData;
import com.bmoblib.bean.Version;
import com.bmoblib.bean.WindowRelated;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 时间：2017/8/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：数据查询帮助类
 */

public class BmobQueryHelp {

    /**
     * 通知
     */
    public static void queryNotice(final OnNoticeListener onNoticeListener) {
        BmobQuery<Notice> query = new BmobQuery<>();
        //执行查询方法
        query.findObjects(new FindListener<Notice>() {
            @Override
            public void done(List<Notice> notices, BmobException e) {
                if (null != onNoticeListener) {
                    onNoticeListener.result(notices.get(0), e);
                }
            }
        });
    }
    /**
     * 集成框架
     */
    public static void queryIntegrationFramework(int page, final OnIntegrationFrameworkListener onIntegrationFrameworkListener) {
        BmobQuery<IntegrationFramework> query = new BmobQuery<>();
        query.setLimit(15);//每页15条记录，默认10条
        query.setSkip(15 * page);
        //执行查询方法
        query.findObjects(new FindListener<IntegrationFramework>() {
            @Override
            public void done(List<IntegrationFramework> object, BmobException e) {
                if (null != onIntegrationFrameworkListener) {
                    onIntegrationFrameworkListener.result(object, e);
                }
            }
        });
    }
    /**
     * 常用组件
     */
    public static void queryCommonComponents(int page, final OnCommonComponentsListener onCommonComponentsListener) {
        BmobQuery<CommonComponents> query = new BmobQuery<>();
        query.setLimit(15);//每页15条记录，默认10条
        query.setSkip(15 * page);
        //执行查询方法
        query.findObjects(new FindListener<CommonComponents>() {
            @Override
            public void done(List<CommonComponents> object, BmobException e) {
                if (null != onCommonComponentsListener) {
                    onCommonComponentsListener.result(object, e);
                }
            }
        });
    }
    /**
     * 常用工具
     */
    public static void queryCommoTools(int page, final OnCommoToolsListener onCommoToolsListener) {
        BmobQuery<CommoTools> query = new BmobQuery<>();
        query.setLimit(15);//每页15条记录，默认10条
        query.setSkip(15 * page);
        //执行查询方法
        query.findObjects(new FindListener<CommoTools>() {
            @Override
            public void done(List<CommoTools> object, BmobException e) {
                if (null != onCommoToolsListener) {
                    onCommoToolsListener.result(object, e);
                }
            }
        });
    }
    /**
     * 窗口相关
     */
    public static void queryWindowRelated(int page, final OnWindowRelatedListener onWindowRelatedListener) {
        BmobQuery<WindowRelated> query = new BmobQuery<>();
        query.setLimit(15);//每页15条记录，默认10条
        query.setSkip(15 * page);
        //执行查询方法
        query.findObjects(new FindListener<WindowRelated>() {
            @Override
            public void done(List<WindowRelated> object, BmobException e) {
                if (null != onWindowRelatedListener) {
                    onWindowRelatedListener.result(object, e);
                }
            }
        });
    }

    /**
     * 查询更新表
     */
    public static void queryUpdate(final OnUpdateQueryListener onUpdateQueryListener) {
        BmobQuery<Version> query = new BmobQuery<>();
        //执行查询方法
        query.findObjects(new FindListener<Version>() {
            @Override
            public void done(List<Version> object, BmobException e) {
                if (null != onUpdateQueryListener) {
                    onUpdateQueryListener.result(object.get(0), e);
                }
            }
        });
    }

    /**
     * 查询赞助商表
     * 排序为金额高的在前面，最少200上榜
     */
    public static void querySponsor(int page, final OnSponsorQueryListener onSponsorQueryListener) {
        BmobQuery<Sponsor> query = new BmobQuery<>();
        // 根据sum字段升序显示数据，只影响升序或降序，对我想要的金额从大到小排列不符合
        //query.order("sum");
        query.setLimit(10);//每页10条记录，默认10条
        query.setSkip(10 * page);
        //执行查询方法
        query.findObjects(new FindListener<Sponsor>() {
            @Override
            public void done(List<Sponsor> object, BmobException e) {
                if (null != onSponsorQueryListener) {
                    onSponsorQueryListener.result(object, e);
                }
            }
        });
    }

    /**
     * 查询支持者表
     * 排序按照时间顺序，最近时间在前面，最少5元上榜
     * （下面不需要进行本地排序处理，表中排序过了）
     * 支持分页，每页20条
     */
    public static void querySupporter(int page, final OnSupporterQueryListener onSupporterQueryListener) {
        BmobQuery<Supporter> query = new BmobQuery<>();
        query.setLimit(10);//每页10条记录，默认10条
        query.setSkip(10 * page);
        //执行查询方法
        query.findObjects(new FindListener<Supporter>() {
            @Override
            public void done(List<Supporter> object, BmobException e) {
                if (null != onSupporterQueryListener) {
                    onSupporterQueryListener.result(object, e);
                }
//                if (e == null) {
//                    Toast.makeText(BmoBHelp.appContext, "查询成功：共" + object.size() + "条数据。", Toast.LENGTH_LONG).show();
//                    Log.d("TAG", "查询成功：共" + object.size() + "条数据。");
//                    for (Supporter bean : object) {
//                        Log.d("TAG", "getObjectId:" + bean.getObjectId()+"---getHeadPortrait:" + bean.getHeadPortrait()+"---getName:" + bean.getName()+"---getIntroduce:" + bean.getIntroduce()+"---getSum:" + bean.getSum()+"---getReferralLinks:" + bean.getReferralLinks());
//                    }
//                } else {
//                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                }
            }
        });
    }

    /**
     * 查询技术资料表
     * 按照插入记录排序，降序，最新的记录在最顶
     */
    public static void queryTechnicalData(int page, final OnTechnicalDataQueryListener onTechnicalDataQueryListener) {
        BmobQuery<TechnicalData> query = new BmobQuery<>();
        //query.order("sum");
        query.setLimit(10);//每页10条记录，默认10条
        query.setSkip(10 * page);
        //执行查询方法
        query.findObjects(new FindListener<TechnicalData>() {
            @Override
            public void done(List<TechnicalData> object, BmobException e) {
                if (null != onTechnicalDataQueryListener) {
                    onTechnicalDataQueryListener.result(object, e);
                }
            }
        });
    }

    public interface OnNoticeListener {
        void result(Notice notice, BmobException e);
    }
    public interface OnIntegrationFrameworkListener {
        void result(List<IntegrationFramework> object, BmobException e);
    }
    public interface OnCommonComponentsListener {
        void result(List<CommonComponents> object, BmobException e);
    }
    public interface OnCommoToolsListener {
        void result(List<CommoTools> object, BmobException e);
    }
    public interface OnWindowRelatedListener {
        void result(List<WindowRelated> object, BmobException e);
    }

    public interface OnUpdateQueryListener {
        void result(Version versionBean, BmobException e);
    }

    public interface OnSponsorQueryListener {
        void result(List<Sponsor> object, BmobException e);
    }

    public interface OnSupporterQueryListener {
        void result(List<Supporter> object, BmobException e);
    }

    public interface OnTechnicalDataQueryListener {
        void result(List<TechnicalData> object, BmobException e);
    }

}
