package com.hwc.oklib.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bmoblib.BmobQueryHelp;
import com.bmoblib.bean.UserBean;
import com.hwc.mobsmslib.MobSMSUtil;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.http.UrlConfig;
import com.hwc.oklib.util.AES128;
import com.hwc.oklib.util.ActivityManager;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.EditTextWithDelete;
import com.hwc.oklib.view.ImmersedStatusbarUtils;
import com.hwc.oklib.view.RecodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * 时间：2017/10/20
 * 作者：黄伟才
 * 描述：登录界面
 */
public class SignInActivity extends BaseAppActivity implements View.OnClickListener, Handler.Callback {

    @BindView(R.id.activity_login_signin_tv_username)
    TextView activityLoginSigninTvUsername;
    @BindView(R.id.activity_login_signin_ed_username)
    EditTextWithDelete activityLoginSigninEdUsername;
    @BindView(R.id.activity_login_signin_tv_pwd)
    TextView activityLoginSigninTvPwd;
    @BindView(R.id.activity_login_signin_ed_pwd)
    AppCompatEditText activityLoginSigninEdPwd;
    @BindView(R.id.icon_see)
    ImageView iconSee;
    @BindView(R.id.txt_version)
    TextView txtVersion;
    @BindView(R.id.activity_login_signin_tv_forgetpwd)
    TextView activityLoginSigninTvForgetpwd;
    @BindView(R.id.activity_login_panel_login)
    LinearLayout activityLoginPanelLogin;
    @BindView(R.id.activity_login_register_tv_username)
    TextView activityLoginRegisterTvUsername;
    @BindView(R.id.activity_login_register_ed_username)
    EditTextWithDelete activityLoginRegisterEdUsername;
    @BindView(R.id.activity_login_register_tv_pwd)
    TextView activityLoginRegisterTvPwd;
    @BindView(R.id.activity_login_register_ed_pwd)
    AppCompatEditText activityLoginRegisterEdPwd;
    @BindView(R.id.activity_login_register_tv_code)
    TextView activityLoginRegisterTvCode;
    @BindView(R.id.activity_login_register_ed_code)
    AppCompatEditText activityLoginRegisterEdCode;
    @BindView(R.id.activity_login_get_code)
    RecodeView activityLoginGetCode;
    @BindView(R.id.activity_login_register_protocol_check)
    CheckBox activityLoginRegisterProtocolCheck;
    @BindView(R.id.activity_login_register_protocol)
    TextView activityLoginRegisterProtocol;
    @BindView(R.id.activity_login_register_midea_protocol)
    TextView activityLoginRegisterMideaProtocol;
    @BindView(R.id.activity_login_panel_register)
    LinearLayout activityLoginPanelRegister;
    @BindView(R.id.activity_login_signin_btn_signin)
    Button activityLoginSigninBtnSignin;
    @BindView(R.id.btn_tourist)
    LinearLayout btnTourist;
    @BindView(R.id.activity_login_radio_1)
    RadioButton activityLoginRadio1;
    @BindView(R.id.activity_login_radio_2)
    RadioButton activityLoginRadio2;
    @BindView(R.id.activity_login_radio_group)
    RadioGroup activityLoginRadioGroup;

    private boolean isRegister;

    private static Context mContext;
    private static boolean isToLogin = true;

    public static void intentToLogin() {
        if (isToLogin) {
            isToLogin = false;
            //清空缓存状态
            UserManager.clearCacheState(mContext);
            Intent intent = new Intent(mContext, SignInActivity.class);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (!isToLogin) {
            //关闭所有界面
            ActivityManager.getInstance().popAllActivity();
        } else {
            //关闭当前页面
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        MobSMSUtil.unregisterAllEventHandler();
        super.onDestroy();
        isToLogin = true;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariable() {
        mContext = context;
    }

    @Override
    protected void initTitle() {
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersedStatusbarUtils.initAfterSetContentView(this, null, true);
        registerEventHandler(this);

        initUI();

        UserManager.updateUserBeanFromCache(context);
        //存在token不再登录
        UserBean userBean = UserManager.getUserBean();
        if (TextUtils.isEmpty(userBean.getToken())) {
            //手机号码回显处理
            String phone = UserManager.getCachePhone(context);
            String pws = UserManager.getCachePws(context);
            activityLoginSigninEdUsername.setText(phone);
            activityLoginSigninEdUsername.setSelection(phone.length());
            activityLoginSigninEdPwd.setText(pws);
            activityLoginSigninEdPwd.setSelection(pws.length());
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pws)) {
                login();//登录
            }
        } else {
            Intent intent = new Intent(context, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void initNet() {

    }

    private void initUI() {
        activityLoginRegisterProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        activityLoginRegisterProtocol.getPaint().setAntiAlias(true);//抗锯齿
        activityLoginRegisterMideaProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        activityLoginRegisterMideaProtocol.getPaint().setAntiAlias(true);//抗锯齿

        //设置点击事件
        setViewsClick(
                activityLoginRegisterProtocol,//跳转隐私
                activityLoginRegisterMideaProtocol,//跳转美的协议
                activityLoginSigninTvForgetpwd,//忘记密码
                activityLoginSigninBtnSignin,//确定按键
                btnTourist,//确定游客
                activityLoginGetCode//获取验证码
        );

        //注册登录切换
        activityLoginRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                changeCheck();
            }
        });

        //密码可见性切换按钮
        iconSee.setOnClickListener(new View.OnClickListener() {
            private boolean eyeOpen = false;

            @Override
            public void onClick(View view) {
                if (eyeOpen) {
                    //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
                    activityLoginSigninEdPwd.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iconSee.setImageResource(R.drawable.btn_eye_p);
                    eyeOpen = false;
                } else {
                    //明文
                    activityLoginSigninEdPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iconSee.setImageResource(R.drawable.btn_eye);
                    eyeOpen = true;
                }
            }
        });

        //隐私权条款选中切换
        //未选中则不允许注册
        activityLoginRegisterProtocolCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                activityLoginSigninBtnSignin.setClickable(isChecked);
            }
        });

    }

    private void setViewsClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    private void changeCheck() {
        if (activityLoginRadio1.isChecked()) {
            isRegister = false;
            activityLoginRadio1.setTextColor(getResources().getColor(R.color.white));
            activityLoginRadio2.setTextColor(getResources().getColor(R.color.white_70));
            activityLoginPanelLogin.setVisibility(View.VISIBLE);
            btnTourist.setVisibility(View.VISIBLE);
            activityLoginPanelRegister.setVisibility(View.GONE);
            activityLoginSigninBtnSignin.setClickable(true);
        } else {
            isRegister = true;
            activityLoginRadio1.setTextColor(getResources().getColor(R.color.white_70));
            activityLoginRadio2.setTextColor(getResources().getColor(R.color.white));
            activityLoginPanelLogin.setVisibility(View.GONE);
            btnTourist.setVisibility(View.GONE);
            activityLoginPanelRegister.setVisibility(View.VISIBLE);
            activityLoginSigninBtnSignin.setClickable(activityLoginRegisterProtocolCheck.isChecked());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.activity_login_register_protocol://隐私权政策
                intent = new Intent(this, ProtocolActivity.class);
                intent.putExtra(ProtocolActivity.type_key, ProtocolActivity.PRIVACY_POLICY);
                startActivity(intent);
                break;
            case R.id.activity_login_register_midea_protocol://美的协议
                intent = new Intent(this, ProtocolActivity.class);
                intent.putExtra(ProtocolActivity.type_key, ProtocolActivity.PROTOCOL);
                startActivity(intent);
                break;
            case R.id.activity_login_signin_tv_forgetpwd://忘记密码
                intent = new Intent(this, FindPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_login_signin_btn_signin://登录、注册
                if (isRegister) {
                    register();//注册
                } else {
                    login();//登录
                }
                break;
            case R.id.btn_tourist://游客
                intent = new Intent(context, SplashActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.activity_login_get_code://请求验证码 activityLoginGetCode
                getCode();
                break;
            default:
                break;
        }
    }

    private void getCode() {
        String phone = activityLoginRegisterEdUsername.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("请输入手机号码");
            return;
        }

        //请求发送验证码
        MobSMSUtil.getVerificationCode("86", phone);
    }

    //注册短信及消息回调
    public void registerEventHandler(Activity activity) {
        final Handler handler = new Handler((Handler.Callback) activity);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;

        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
            //提交验证码成功回调

            if (result == SMSSDK.RESULT_COMPLETE) {
                //验证码验证成功
                registerNet();
            } else {
                //验证码验证失败
                //验证码不正确
                ((Throwable) data).printStackTrace();
                String message = ((Throwable) data).getMessage();
                ToastUtil.show("验证码不正确");
            }

        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
            //获取验证码成功回调
            ToastUtil.show("验证码发送成功");
        }
        return false;
    }


    private String phone = "";
    private String psw = "";
    private void register() {
        phone = activityLoginRegisterEdUsername.getText().toString();
        psw = activityLoginRegisterEdPwd.getText().toString();
        String code = activityLoginRegisterEdCode.getText().toString();

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

        //进行验证码判断，通过再进行注册行为
        //验证验证码
        MobSMSUtil.sumbitVerificationCode("86", phone, code);
    }

    private void registerNet() {
        //注册行为
        BmobQueryHelp.insertUserBean(phone, AES128.encrypt(psw, UrlConfig.APPKEY.substring(0, 16)), new BmobQueryHelp.OnRegisterListener() {
            @Override
            public void before() {
                showWaitDialog();
            }

            @Override
            public void success(UserBean userBean) {
                dismissWaitDialog();
                ToastUtil.show("注册成功");
                UserManager.cachePhone(context, phone);
                UserManager.cachePws(context, psw);
                try {
                    UserManager.cacheUserBean(context, userBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(context, SplashActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage, int code) {
                dismissWaitDialog();
                if (code == -100) {
                    //用户已经存在
                    ToastUtil.show("用户已存在，请登录");
                }else{
                    ToastUtil.show("注册失败，请稍后再试");
                }
            }
        });
    }


    private void login() {
        final String phone = activityLoginSigninEdUsername.getText().toString();
        final String psw = activityLoginSigninEdPwd.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            ToastUtil.show("请输入密码");
            return;
        }

        //登陆行为
        BmobQueryHelp.queryUserBean(phone, AES128.encrypt(psw, UrlConfig.APPKEY.substring(0, 16)), new BmobQueryHelp.OnLoginListener() {
            @Override
            public void before() {
                showWaitDialog();
            }

            @Override
            public void success(UserBean userBean) {
                Log.d("TAG", "userBean:" + userBean.toString());
                dismissWaitDialog();

                UserManager.cachePhone(context, phone);
                UserManager.cachePws(context, psw);
                try {
                    UserManager.cacheUserBean(context, userBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //正常登陆逻辑
                Intent intent = new Intent(context, SplashActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage, int code) {
                Log.d("TAG", "errorMessage:" + errorMessage);
                dismissWaitDialog();
                ToastUtil.show("登录失败，账号或密码不对");
            }
        });

    }


}
