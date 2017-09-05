package com.oklib.demo.integration_framework;

import android.view.View;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.util.http.OkGo;
import com.oklib.util.http.cache.CacheMode;
import com.oklib.util.http.callback.StringCallback;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;
import com.orhanobut.logger.Logger;

import okhttp3.Call;
import okhttp3.Response;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/9/3
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：封装网络请求使用演示
 */

public class HttpActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_http;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_http.xml", BASE_RES + "/layout/activity_http.xml"));
                        mBeans.add(new FunctionDetailBean("网络请求参考okgo文档", "http://www.jianshu.com/p/6aa5cb272514"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }


    public void get(View view) {
        String url = "http://lf.snssdk.com/neihan/service/tabs/?essence=1&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120";
        OkGo.get(url)                           // 请求方式和请求url
                .tag(this)                      // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")           // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)   // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        Logger.d(s);
                        ToastUtil.show(s);
                    }
                });
    }

    public void post(View view) {

    }
}
