package com.oklib.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.oklib.base.BaseFragment;
import com.oklib.demo.adapter.MainListAdapter;
import com.oklib.demo.commo_tools.LoggerActivity;
import com.oklib.demo.commo_tools.ToastActivity;
import com.oklib.demo.common_components.GlideImageViewActivity;
import com.oklib.demo.common_components.ShapeSelectActivity;
import com.oklib.demo.common_components.TextViewActivity;
import com.oklib.demo.common_components.ToolbarActivity;
import com.oklib.demo.common_components.refresh.RefreshActivity;
import com.oklib.demo.integration_framework.CameraActivity;
import com.oklib.demo.integration_framework.PermissionActivity;
import com.oklib.demo.window_related.ConfirmDialogActivity;
import com.oklib.demo.window_related.LoadingDialogActivity;
import com.oklib.demo.window_related.MultiSelectListPopActivity;

/**
 * 时间：2017/8/5
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：首页碎片，用于内容列表展示
 */

public class MainFragment extends BaseFragment {

    private ListView lv_main_list;
    private MainListAdapter mainListAdapter;
    private Context context;
    private int type;

    public static MainFragment getInstance(Bundle bundle) {
        MainFragment mainFragment = new MainFragment();
        if (null != bundle) {
            mainFragment.setArguments(bundle);
        }
        return mainFragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initVariable() {
        type = getArguments().getInt("type");
    }

    @Override
    protected void initView(View view) {
        context = getContext();
        lv_main_list = findView(view, R.id.lv_main_list);
        lv_main_list.setAdapter(mainListAdapter = new MainListAdapter(type));
        lv_main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (type) {
                    case 0:
                        integrationFramework(position);
                        break;
                    case 1:
                        commonComponents(position);
                        break;
                    case 2:
                        commoTools(position);
                        break;
                    case 3:
                        windowRelated(position);
                        break;
                }
            }
        });
    }

    @Override
    protected void initNet() {

    }

    //集成框架
    private void integrationFramework(int position) {
        Intent intent = null;
        switch (mainListAdapter.getTitle(position)) {//jdk1.7之后支持，整形，枚举类型，boolean，字符串
            case "6.0动态权限统一封装框架":
                intent = new Intent(context, PermissionActivity.class);
                intent.putExtra(Common.TITLE, "6.0动态权限统一封装框架");
                break;
            case "拍照选择、相册选择":
                intent = new Intent(context, CameraActivity.class);
                intent.putExtra(Common.TITLE, "拍照选择、相册选择");
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
    //常用组件
    private void commonComponents(int position) {
        Intent intent = null;
        switch (mainListAdapter.getTitle(position)) {//jdk1.7之后支持，整形，枚举类型，boolean，字符串
            case "刷新组件":
                intent = new Intent(context, RefreshActivity.class);
                intent.putExtra(Common.TITLE, "刷新组件");
                break;
            case "toolbar封装统一菜单栏":
                intent = new Intent(context, ToolbarActivity.class);
                intent.putExtra(Common.TITLE, "toolbar封装统一菜单栏");
                break;
            case "GlideImageView，补充高斯模糊、默认无按下效果等":
                intent = new Intent(context, GlideImageViewActivity.class);
                intent.putExtra(Common.TITLE, "GlideImageView");
                break;
            case "动态shape，select":
                intent = new Intent(context, ShapeSelectActivity.class);
                intent.putExtra(Common.TITLE, "动态shape，select");
                break;
            case "TextView炫酷效果":
                intent = new Intent(context, TextViewActivity.class);
                intent.putExtra(Common.TITLE, "TextView炫酷效果");
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }

    }
    //常用工具
    private void commoTools(int position) {
        Intent intent = null;
        switch (mainListAdapter.getTitle(position)) {//jdk1.7之后支持，整形，枚举类型，boolean，字符串
            case "日志logger":
                intent = new Intent(context, LoggerActivity.class);
                intent.putExtra(Common.TITLE, "日志logger");
                break;
            case "拓展Toast，Snackbar":
                intent = new Intent(context, ToastActivity.class);
                intent.putExtra(Common.TITLE, "拓展Toast，Snackbar");
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
    //窗口相关
    private void windowRelated(int position) {
        Intent intent = null;
        switch (mainListAdapter.getTitle(position)) {//jdk1.7之后支持，整形，枚举类型，boolean，字符串
            case "居中确定取消窗口":
                intent = new Intent(context, ConfirmDialogActivity.class);
                intent.putExtra(Common.TITLE, "居中确定取消窗口");
                break;
            case "加载等待窗口":
                intent = new Intent(context, LoadingDialogActivity.class);
                intent.putExtra(Common.TITLE, "加载等待窗口");
                break;
            case "多选pop列表演示":
                intent = new Intent(context, MultiSelectListPopActivity.class);
                intent.putExtra(Common.TITLE, "多选pop列表演示");
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }


}
