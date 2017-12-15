package com.hwc.oklib.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.util.StreamUtil;
import com.hwc.oklib.view.CommonToolBar;


/**
 * 创建时间：2017/5/31
 * 编写者：黄伟才
 * 功能描述：美的平台服务协议、法律声明、隐私权政策
 */
public class ProtocolActivity extends BaseAppActivity {
    public static final String PROTOCOL = "protocol";//隐私权政策
    public static final String CONTACT_US = "contact_us";//联系我们
    public static final String LEGAL_NOTICE = "legal_notice";//法律声明
    public static final String PRIVACY_POLICY = "privacy_policy";//隐私权政策
    public static String type_key = "type_key";
    private String type = "";
    private String title = "";

    private TextView mTv_Protocol;
    private CommonToolBar ctb_toolbar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login_protocol;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {
        ctb_toolbar = findView(R.id.ctb_toolbar);
        ctb_toolbar.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setImmerseState(this).setNavIcon(R.drawable.oklib_btn_back);
    }

    @Override
    protected void initView() {
        String showStr = "";
        type = getIntent().getStringExtra(type_key);
        if (TextUtils.equals(type, PROTOCOL)) {
            title = "美的平台服务协议";

            showStr = StreamUtil.getInstance().getString(getResources().openRawResource(R.raw.protocol));
        } else if (TextUtils.equals(type, CONTACT_US)) {
            title = "联系我们";
            showStr = StreamUtil.getInstance().getString(getResources().openRawResource(R.raw.contact_us));
        } else if (TextUtils.equals(type, LEGAL_NOTICE)) {
            title = "法律声明";
            showStr = StreamUtil.getInstance().getString(getResources().openRawResource(R.raw.legal_notice));
        } else if (TextUtils.equals(type, PRIVACY_POLICY)) {
            title = "隐私政策";
            showStr = StreamUtil.getInstance().getString(getResources().openRawResource(R.raw.privacy_policy));
        }
        ctb_toolbar.setCenterTitle(title, 18, R.color.white);

        //设置其他的属性
        mTv_Protocol = (TextView) findViewById(R.id.activity_login_protocol_des_tv);
        mTv_Protocol.setText(showStr);
    }

    @Override
    protected void initNet() {

    }

}
