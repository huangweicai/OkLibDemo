package com.hwc.oklib.common_components;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.debugkit.DebugFunction;
import com.hwc.oklib.view.debugkit.DevTool;
import com.hwc.oklib.view.debugkit.DevToolFragment;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2018/1/3
 * 作者：黄伟才
 * 描述：日志悬浮窗view
 */

public class DebugKitActivity extends BaseAppActivity implements AdapterView.OnItemSelectedListener {
    private int mTextSize = 12;
    private SeekBar mSeekbar;
    private DevToolFragment.DevToolTheme mTheme = DevToolFragment.DevToolTheme.DARK;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_debug_kit;
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
                        mBeans.add(new FunctionDetailBean("activity_debug_kit.xml", BASE_RES + "/layout/activity_debug_kit.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final TextView functionNumber = (TextView) findViewById(R.id.functions_number);
        final Spinner themeSpinner = (Spinner) findViewById(R.id.theme_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.debugkit_themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);
        themeSpinner.setOnItemSelectedListener(this);

        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                functionNumber.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        functionNumber.setText(Integer.toString(mSeekbar.getProgress()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DevTool.Builder builder = new DevTool.Builder(DebugKitActivity.this);

                if (mSeekbar != null) {
                    for (int i = 0; i < mSeekbar.getProgress(); i++) {
                        builder.addFunction(doSomeStuff());
                    }
                }

                builder.addFunction(new DebugFunction("Do some stuff") {
                    @Override
                    public String call() throws Exception {
                        return "This function has a title";
                    }
                });

                builder.setTextSize(mTextSize)
                        .setTheme(mTheme)
                        .build();
                // After the tool has been built, you can set:
                // builder.getTool().changeConsoleTextSize(mTextSize);
            }
        });
    }

    private DebugFunction doSomeStuff() {
        return new DebugFunction() {
            @Override
            public String call() throws Exception {
                // Do some kind of really debugging stuff...
                return "Some stuff was done.";
            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mTheme = position == 0 ? DevToolFragment.DevToolTheme.DARK : DevToolFragment.DevToolTheme.LIGHT;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mTheme = DevToolFragment.DevToolTheme.DARK;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
