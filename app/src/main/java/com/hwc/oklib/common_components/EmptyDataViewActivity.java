package com.hwc.oklib.common_components;

import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.EmptyDataView;

import static com.hwc.oklib.Common.BASE_RES;


/**
   * 时间：2017/9/8
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：空数据view使用演示
   */
public class EmptyDataViewActivity extends BaseAppActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_empty_data_view;
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
                        mBeans.add(new FunctionDetailBean("activity_empty_data_view.xml", BASE_RES +"/layout/activity_empty_data_view.xml"));
                        showDetail();
                    }
                });
    }

    private EmptyDataView emptyDataView;
    @Override
    protected void initView() {
        emptyDataView = findView(R.id.emptyDataView);
    }

    public void duanwang(View view) {
        emptyDataView.updateEmptyState(true, R.drawable.oklib_photo_error, "网络访问或数据出错");
        emptyDataView.updateHintTextState(14, getResources().getColor(R.color.lock_view_white));
        emptyDataView.updateReloadBtnState(14, getResources().getColor(R.color.lock_view_white), R.drawable.round_blood_pressure);
        emptyDataView.setBackgroundResource(R.color.blue);
        emptyDataView.setOnReloadListener(new EmptyDataView.OnReloadListener() {
            @Override
            public void reload() {
                ToastUtil.show("重新加载");
            }
        });
    }

    public void kongshuju(View view) {
        emptyDataView.updateEmptyState(false, R.drawable.oklib_photo_empty, "一条数据都没有噢，赶紧去测量吧~");
        emptyDataView.updateHintTextState(14, getResources().getColor(R.color.lock_view_white));
        emptyDataView.updateReloadBtnState(14, getResources().getColor(R.color.lock_view_white), R.drawable.round_blood_pressure);
        emptyDataView.setBackgroundResource(R.color.orange);
        emptyDataView.setOnReloadListener(new EmptyDataView.OnReloadListener() {
            @Override
            public void reload() {
                ToastUtil.show("重新加载");
            }
        });
    }



    @Override
    protected void initNet() {

    }

}
