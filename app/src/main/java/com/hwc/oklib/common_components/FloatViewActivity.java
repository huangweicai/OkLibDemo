package com.hwc.oklib.common_components;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.fw.FloatVideoService;
import com.hwc.oklib.view.fw.FloatViewManager;
import com.hwc.oklib.view.fw.PermissionCheckUntil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2017/12/20
 * 作者：黄伟才
 * 描述：悬浮窗
 */

public class FloatViewActivity extends BaseAppActivity implements CompoundButton.OnCheckedChangeListener{
    @Override
    protected int initLayoutId() {
        return R.layout.activity_float;
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
                        mBeans.add(new FunctionDetailBean("activity_float.xml", BASE_RES +"/layout/activity_float.xml"));
                        showDetail();
                    }
                });
    }


    @BindView(R.id.sw_isopen)
    Switch swIsopen;
    @BindView(R.id.sw_gun)
    Switch swGun;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        swIsopen.setOnCheckedChangeListener(this);
        swGun.setOnCheckedChangeListener(this);

        ((TextView) findView(R.id.tv_addLog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String log = "测试日志、测试日志、测试日志、测试日志、测试日志、测试日志";
                FloatVideoService.updateLogData(log);
                FloatVideoService.updateLogData(log);
            }
        });
    }

    @Override
    protected void initNet() {

    }

    private boolean isOpened = true;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == swIsopen) {
            if (isChecked) {
                if (PermissionCheckUntil.getAppOps(FloatViewActivity.this)) {
                    if (isOpened) {
                        isOpened = false;
                        //启动服务显示悬浮窗口
                        Intent intent = new Intent(FloatViewActivity.this, FloatVideoService.class);
                        FloatViewActivity.this.startService(intent);
                    }
                } else {
                    Toast.makeText(FloatViewActivity.this, "悬浮权限未打开，请到应用设置中打开", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(buttonView == swGun){
            FloatViewManager.getInstance().isCanTouch = isChecked;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FloatViewManager.getInstance().removeFloatView();
    }
}
