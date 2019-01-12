package com.oklib.view_lib;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.fw.FloatVideoService;
import com.oklib.view.fw.FloatViewManager;
import com.oklib.view.fw.PermissionCheckUntil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 时间：2017/12/20
 * 作者：黄伟才
 * 描述：悬浮窗
 */

public class FloatViewActivity extends BaseAppActivity implements CompoundButton.OnCheckedChangeListener{

    @BindView(R.id.sw_isopen)
    Switch swIsopen;
    @BindView(R.id.sw_gun)
    Switch swGun;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_float;
    }

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
