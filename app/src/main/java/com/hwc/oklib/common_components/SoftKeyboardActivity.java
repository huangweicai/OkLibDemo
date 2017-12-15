package com.hwc.oklib.common_components;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.KeyBoardCustomRandom;
import com.hwc.oklib.view.KeyBoardSystem;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2017/11/27
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：自定义view及系统软键盘
 */

public class SoftKeyboardActivity extends BaseAppActivity implements KeyBoardSystem.OnPwdInputListener, KeyBoardCustomRandom.InputListener, View.OnClickListener {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_soft_keyboard;
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
                        mBeans.add(new FunctionDetailBean("activity_soft_keyboard.xml", BASE_RES +"/layout/activity_soft_keyboard.xml"));
                        showDetail();
                    }
                });
    }

    private static final String TAG = "MainActivity";
    private KeyBoardSystem pwdInputBox;
    private Button clearbutton;
    private KeyBoardCustomRandom magicKeyBoard;
    @Override
    protected void initView() {
        pwdInputBox = (KeyBoardSystem) findViewById(R.id.pwdinputbox);
        clearbutton = (Button) findViewById(R.id.clearbutton);
        magicKeyBoard = (KeyBoardCustomRandom) findViewById(R.id.magickeyboard);
        pwdInputBox.setOnPwdInputListener(this);
        clearbutton.setOnClickListener(this);
        magicKeyBoard.setInputListener(this);
        pwdInputBox.register(magicKeyBoard);
    }

    @Override
    protected void initNet() {

    }


    @Override
    public void pwdChange(String pwd, KeyBoardSystem.PwdInput flag) {
        Log.i(TAG, "pwdChange: " + pwd + "      flag：" + flag);
    }

    @Override
    public void pwdComplete(String pwd) {
        Log.i(TAG, "pwdComplete: " + pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearbutton:
                pwdInputBox.clearPwd();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pwdInputBox.unregister();
    }



    //------------自定义软键盘回调 start----------------
    @Override
    public void onNumberKey(String number) {
        Log.d("TAG", "number："+number);
    }

    @Override
    public void onBackspaceKey() {

    }
    //------------自定义软键盘回调 end----------------
}
