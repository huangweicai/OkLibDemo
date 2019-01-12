package com.oklib.view_lib;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.shoot_refresh.ShootRefreshView;


/**
   * 时间：2018/1/3
   * 作者：蓝天
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
