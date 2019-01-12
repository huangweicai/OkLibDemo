package com.oklib.win_lib;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.window.RegionSelectionDialog;


/**
 * 时间：2017/8/20
 * 作者：蓝天
 * 描述：地区选择窗口
 */

public class RegionSelectActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_multi_select_listpop;
    }

    private TextView tv_text;
    @Override
    protected void initView() {
        tv_text = findView(R.id.tv_text);
    }

    public void doClick(View view) {
        RegionSelectionDialog dialog = RegionSelectionDialog.create(getSupportFragmentManager());
        dialog.show();
        dialog.setOnSelectListener(new RegionSelectionDialog.OnSelectListener() {
            @Override
            public void onSelect(String selectRegion) {
                //选中回调
                Toast.makeText(context, selectRegion, Toast.LENGTH_LONG).show();
            }
        });
    }
}
