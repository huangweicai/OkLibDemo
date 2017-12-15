package com.hwc.oklib.login;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.http.HttpUtil;
import com.hwc.oklib.http.StateCode;
import com.hwc.oklib.http.UrlConfig;
import com.hwc.oklib.http.okgo.model.HttpParams;
import com.hwc.oklib.util.AES128;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.EditTextWithDelete;
import com.hwc.oklib.view.ImmersedStatusbarUtils;
import com.hwc.oklib.view.RecodeView;
import com.tpnet.tpautoverifycode.AutoVerifyCode;
import com.tpnet.tpautoverifycode.AutoVerifyCodeConfig;
import com.tpnet.tpautoverifycode.callback.PermissionCallBack;
import com.tpnet.tpautoverifycode.callback.SmsCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 时间：2017/10/19
 * 作者：黄伟才
 * 描述：忘记密码
 */
public class FindPwdActivity extends BaseAppActivity implements View.OnClickListener {

    @BindView(R.id.activity_login_radio_group)
    TextView activityLoginRadioGroup;
    @BindView(R.id.activity_forget_tv_username)
    TextView activityForgetTvUsername;
    @BindView(R.id.activity_forget_ed_username)
    EditTextWithDelete activityForgetEdUsername;
    @BindView(R.id.activity_forget_tv_pwd)
    TextView activityForgetTvPwd;
    @BindView(R.id.activity_forget_ed_pwd)
    AppCompatEditText activityForgetEdPwd;
    @BindView(R.id.activity_forget_tv_code)
    TextView activityForgetTvCode;
    @BindView(R.id.activity_forget_ed_code)
    AppCompatEditText activityForgetEdCode;
    @BindView(R.id.activity_forget_get_code)
    RecodeView activityForgetGetCode;
    @BindView(R.id.activity_login_panel_register)
    LinearLayout activityLoginPanelRegister;
    @BindView(R.id.activity_forget_btn_commit)
    Button activityForgetBtnCommit;
    @BindView(R.id.activity_forget)
    LinearLayout activityForget;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_findpwd;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersedStatusbarUtils.initAfterSetContentView(this, null, true);
        //设置点击事件
        setViewsClick(activityForgetGetCode, activityForgetBtnCommit);
    }

    @Override
    protected void initNet() {

    }

    public void back(View view) {
        finish();
    }

    /**
     * 多种属性设置
     */
    public void complex() {
        AutoVerifyCodeConfig config = new AutoVerifyCodeConfig.Builder()
                .codeLength(6) // 验证码长度
                .smsCodeType(AutoVerifyCodeConfig.CODE_TYPE_NUMBER) //验证码类型
                .smsSenderStart("1069") // 验证码发送者号码的前几位数字
//                .smsSender("6505551212") // 验证码发送者的号码
//                .smsBodyStartWith("【守护APP】") // 设置验证码短信开头文字，固定可以设置
                .smsBodyContains("【美的】") // 设置验证码短信内容包含文字，每个功能包含不一样，例如注册、重置密码
                .build();

        AutoVerifyCode.getInstance()
                .with(FindPwdActivity.this)
                .config(config) //验证码选项配置
                .smsCallback(new MessageCallBack()) //短信内容回调
                .permissionCallback(new PerCallBack()) //短信短信回调
                .into(activityForgetEdCode) //要输入的View
                .start();  //开始
    }


    /**
     * 获取短信回调接口
     */
    private class MessageCallBack extends SmsCallBack {
        @Override
        public void onGetCode(String code) {
            Log.e("@@", "验证码为：" + code);
        }

        @Override
        public void onGetMessage(String mess) {
            Log.e("@@", "短信内容为：" + mess);

        }

        @Override
        public void onGetSender(@Nullable String phoneNumber) {
            Log.e("@@", "发送者为：" + phoneNumber);

        }
    }


    private class PerCallBack implements PermissionCallBack {

        @Override
        public void onSuccess() {
            //获取短信权限成功
            Log.e("@@", "获取短信权限成功：");
        }

        @Override
        public boolean onFail() {
            //获取短信权限失败
            Toast.makeText(FindPwdActivity.this, "拒绝获取短信权限", Toast.LENGTH_SHORT).show();
            Log.e("@@", "获取短信权限失败,返回真则重试获取权限,或者你自己手动获取了之后再返回真也行");

            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        complex();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //因为一般只用一次，所以页面销毁就释放。
        AutoVerifyCode.getInstance().release();
    }


    private void setViewsClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_forget_btn_commit://提交
                findPws();
                break;
            case R.id.activity_forget_get_code://获取验证码
                final String phone = activityForgetEdUsername.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show("请输入手机号码");
                    return;
                }
                sendCode();
                break;
        }
    }

    private void findPws() {
        final String phone = activityForgetEdUsername.getText().toString();
        final String psw = activityForgetEdPwd.getText().toString();
        final String code = activityForgetEdCode.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            ToastUtil.show("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.show("请输入验证码");
            return;
        }

        HttpParams params = new HttpParams();
        params.put("mobile", phone);
        params.put("code", code);
        params.put("password", AES128.encrypt(psw, UrlConfig.APPKEY.substring(0, 16)));
        HttpUtil.getInstance().postRequest(UrlConfig.PASSWORD_RESET, params, new HttpUtil.OnHttpListener() {
            @Override
            public void onBefore() {
                showWaitDialog();
            }

            @Override
            public void onSuccess(Object result) {
                dismissWaitDialog();
                ToastUtil.show("修改成功，请重新登录");
                finish();
            }

            @Override
            public void onError(int code, Exception e) {
                dismissWaitDialog();
                ToastUtil.show(StateCode.getCodeMsg(code));
            }
        });
    }


    private void sendCode() {
        final String phone = activityForgetEdUsername.getText().toString();
        HttpParams params = new HttpParams();
        params.put("mobile", phone);
        HttpUtil.getInstance().postRequest(UrlConfig.RESET_PWD_SMS_CODE, params, new HttpUtil.OnHttpListener() {
            @Override
            public void onBefore() {
                showWaitDialog();
            }

            @Override
            public void onSuccess(Object result) {
                dismissWaitDialog();
                ToastUtil.show("验证码已发送，请稍候");
                activityForgetGetCode.startTime();
            }

            @Override
            public void onError(int code, Exception e) {
                dismissWaitDialog();
                ToastUtil.show(StateCode.getCodeMsg(code));
            }
        });
    }
}
