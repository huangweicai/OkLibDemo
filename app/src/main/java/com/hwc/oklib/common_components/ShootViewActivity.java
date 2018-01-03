package com.hwc.oklib.common_components;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.shoot_refresh.ShootRefreshView;

import static com.hwc.oklib.Common.BASE_RES;

/**
   * 时间：2018/1/3
   * 作者：黄伟才
   * 描述：快门view
   */
public class ShootViewActivity extends BaseAppActivity {

    private ShootRefreshView mShootRefreshView;

    private SeekBar mPullProgressBar;

    private Button mResetView;
    private Button mLoadingView;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_shoot_view;
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
                        mBeans.add(new FunctionDetailBean("activity_shoot_view.xml", BASE_RES + "/layout/activity_shoot_view.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        mShootRefreshView = (ShootRefreshView) findViewById(R.id.shoot_refresh_view);
        mPullProgressBar = (SeekBar) findViewById(R.id.pull_progress_bar);
        mLoadingView = (Button) findViewById(R.id.loading_view);
        mResetView = (Button) findViewById(R.id.reset_view);

        mPullProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mShootRefreshView.pullProgress(0, ((float) progress) / ((float) seekBar.getMax()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPullProgressBar.setProgress(0);
                mShootRefreshView.refreshing();
            }
        });

        mResetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPullProgressBar.setProgress(0);
                mShootRefreshView.reset();
            }
        });
    }

}
