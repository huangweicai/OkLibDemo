package com.oklib.demo.common_use;

import android.widget.TextView;

import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间：2017/8/3
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/oklib
 * 描述：logger演示使用
 */

public class LoggerActivity extends BaseAppActivity {
    private TextView tv_logger;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_logger;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        tv_logger = findView(R.id.tv_logger);
    }

    private String jsonStr = "{\"status\":\"3\",\"message\":\"\",\"errCode\":\"0\",\"data\":[{\"time\":\"2014-12-12 20:37\",\"context\":\"到达：湖南湘潭公司 已收件\"},{\"time\":\"2014-12-12 21:31\",\"context\":\"到达：湖南湘潭公司 发往：福建厦门分拨中心\"},{\"time\":\"2014-12-13 02:24\",\"context\":\"到达：湖南长沙分拨中心\"},{\"time\":\"2014-12-17 20:02\",\"context\":\"到达：福建厦门公司国贸分部 发往：福建厦门公司国贸分部\"},{\"time\":\"2014-12-17 20:33\",\"context\":\"到达：福建厦门公司国贸分部 由 图片 签收\"}],\"html\":\"\",\"mailNo\":\"1201519497579\",\"expTextName\":\"韵达快递\",\"expSpellName\":\"yunda\",\"update\":\"1420006818\",\"cache\":\"0\",\"ord\":\"ASC\",\"tel\":\"021-39207888\"}";

    @Override
    protected void initNet() {
        Logger.addLogAdapter(new AndroidLogAdapter());

        Logger.d("debug");
        Logger.e("error");
        Logger.w("warning");
        Logger.v("verbose");
        Logger.i("information");
        Logger.wtf("wtf!!!!");

        Logger.d("hello %s", "world");

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        List<String> list = new ArrayList<>();
        list.add("日志");
        list.add("开源");
        String[] array = {"数组1", "数组2", "数组3"};

        Logger.d(map);
        //Logger.d(SET);
        Logger.d(list);
        Logger.d(array);

        Logger.json(jsonStr);
        //Logger.xml(XML_CONTENT);
    }

    //打印结果
    private String printStr = "08-03 10:45:39.211 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:51)\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ debug\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo D/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo E/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo E/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo E/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo E/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo E/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:52)\n" +
            "08-03 10:45:39.212 15479-15479/com.oklib.demo E/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo E/PRETTY_LOGGER: │ error\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo E/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:53)\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: │ warning\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo W/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:54)\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: │ verbose\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo V/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo I/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo I/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo I/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo I/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.213 15479-15479/com.oklib.demo I/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:55)\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo I/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo I/PRETTY_LOGGER: │ information\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo I/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo A/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo A/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo A/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo A/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo A/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:56)\n" +
            "08-03 10:45:39.214 15479-15479/com.oklib.demo A/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo A/PRETTY_LOGGER: │ wtf!!!!\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo A/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:58)\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ hello world\n" +
            "08-03 10:45:39.215 15479-15479/com.oklib.demo D/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:68)\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ {key2=value2, key1=value1}\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:70)\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ [日志, 开源]\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.216 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:71)\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ [数组1, 数组2, 数组3]\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ Thread: main\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ BaseActivity.onCreate  (BaseActivity.java:21)\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │    LoggerActivity.initNet  (LoggerActivity.java:73)\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄\n" +
            "08-03 10:45:39.217 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ {\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"status\": \"3\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"message\": \"\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"errCode\": \"0\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"data\": [\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     {\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"time\": \"2014-12-12 20:37\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"context\": \"到达：湖南湘潭公司 已收件\"\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     },\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     {\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"time\": \"2014-12-12 21:31\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"context\": \"到达：湖南湘潭公司 发往：福建厦门分拨中心\"\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     },\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     {\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"time\": \"2014-12-13 02:24\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"context\": \"到达：湖南长沙分拨中心\"\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     },\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     {\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"time\": \"2014-12-17 20:02\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"context\": \"到达：福建厦门公司国贸分部 发往：福建厦门公司国贸分部\"\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     },\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     {\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"time\": \"2014-12-17 20:33\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │       \"context\": \"到达：福建厦门公司国贸分部 由 图片 签收\"\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │     }\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   ],\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"html\": \"\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"mailNo\": \"1201519497579\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"expTextName\": \"韵达快递\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"expSpellName\": \"yunda\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"update\": \"1420006818\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"cache\": \"0\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"ord\": \"ASC\",\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │   \"tel\": \"021-39207888\"\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: │ }\n" +
            "08-03 10:45:39.218 15479-15479/com.oklib.demo D/PRETTY_LOGGER: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n";

}
