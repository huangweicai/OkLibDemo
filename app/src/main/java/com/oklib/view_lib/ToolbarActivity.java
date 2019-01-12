package com.oklib.view_lib;

import android.view.View;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.CommonToolBar;


/**
 * 时间：2017/8/3
 * 作者：蓝天
 * 描述：CommonToolbar使用样例
 */

public class ToolbarActivity extends BaseAppActivity {
    private CommonToolBar tb_toolbar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_toolbar;
    }

    @Override
    protected void initView() {
        //注意：高度一定要用wrap_content属性
        tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle("中间标题", 16, R.color.oklib_white)//中间标题
                .setCenterDraw(R.mipmap.oklib_maotouying_icon, CommonToolBar.LEFT, 10)//中间标题内设置图标
                .setCenterTitleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "中间标题", Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightTitle("确定", 14, R.color.oklib_red)//右标题
                .setRightImage(R.color.oklib_blue, true)//有标题背景图或背景色 R.mipmap.oklib_maotouying_icon
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "右边菜单栏", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void initNet() {

    }
}
