package com.oklib.mix_lib.http;

import android.view.View;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.http.OklibHttp;
import com.oklib.http.cache.CacheMode;
import com.oklib.http.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 时间：2017/9/3
 * 作者：蓝天
 * 描述：okhttp封装网络请求使用样例
 */

public class HttpActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {
        OklibHttp.init(getApplication());
    }

    public void get(View view) {
        String url = "http://lf.snssdk.com/neihan/service/tabs/?essence=1&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120";
        OklibHttp.get(url)                           // 请求方式和请求url
                .tag(this)                      // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")           // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)   // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void post(View view) {

    }
}
