package com.oklib.view_lib;

import android.view.View;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.EmptyDataView;


/**
   * 时间：2017/9/8
   * 作者：蓝天
   * 描述：空数据view使用样例
   */
public class EmptyDataViewActivity extends BaseAppActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_empty_data_view;
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
                Toast.makeText(context, "重新加载", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "重新加载", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
