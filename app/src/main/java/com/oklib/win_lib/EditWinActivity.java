package com.oklib.win_lib;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.window.EditDialog;
import com.oklib.window.base.BaseDialogFragment;


/**
 * 时间：2017/11/27
 * 作者：蓝天
 * 描述：修改昵称窗口
 */

public class EditWinActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_confirm_dialog;
    }

    @Override
    protected void initView() {
        ((TextView) findView(R.id.tv_showDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditDialog editDialog = EditDialog.create(getSupportFragmentManager());
                editDialog.setTitle("修改介绍");
                editDialog.setExplain("介绍：");
                editDialog.setLimit("最少输入12个字符");
                editDialog.setContent("一个让开发更加简单的工具库");
                editDialog.show();
                editDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                    @Override
                    public void confirm(View v) {
                        Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
