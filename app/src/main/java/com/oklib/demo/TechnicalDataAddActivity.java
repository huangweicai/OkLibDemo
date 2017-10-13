package com.oklib.demo;

import android.content.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bmoblib.BmobInsertHelp;
import com.bmoblib.bean.TechnicalData;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.util.SPUtils;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;
import com.oklib.view.EditTextWithDelete;

import cn.bmob.v3.exception.BmobException;

/**
 * 时间：2017/9/29
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：技术资料添加界面
 */

public class TechnicalDataAddActivity extends BaseAppActivity implements CompoundButton.OnCheckedChangeListener {
    private EditTextWithDelete ed_inviteCode;
    private CheckBox cb_remember;
    private EditTextWithDelete ed_headPortrait;
    private EditTextWithDelete ed_name;
    private EditTextWithDelete ed_introduce;
    private EditTextWithDelete ed_referralLinks;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_technical_data_add;
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
                .setRightTitle("提交", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//右标题监听
                    @Override
                    public void onClick(View v) {
                        //提交数据到Bmob
                        sumbitData();
                    }
                });
    }

    @Override
    protected void initView() {
        ed_inviteCode = findView(R.id.ed_inviteCode);
        cb_remember = findView(R.id.cb_remember);
        ed_headPortrait = findView(R.id.ed_headPortrait);
        ed_name = findView(R.id.ed_name);
        ed_introduce = findView(R.id.ed_introduce);
        ed_referralLinks = findView(R.id.ed_referralLinks);
        registerClipEvents();

        //邀请码
        ed_inviteCode.setText("" + SPUtils.get(context, "inviteCode", ""));
        ed_inviteCode.setSelection(((String)SPUtils.get(context, "inviteCode", "")).length());

        cb_remember.setOnCheckedChangeListener(this);
        //true会回调监听，false不回调监听
        cb_remember.setChecked((Boolean) SPUtils.get(context, "isRemember", false));
    }

    @Override
    protected void initNet() {

    }

    private void sumbitData() {
        final String inviteCode = ed_inviteCode.getText().toString();//邀请码
        final String headPortrait = ed_headPortrait.getText().toString();//头像
        final String productName = ed_name.getText().toString();//名称
        final String introduce = ed_introduce.getText().toString();//介绍
        final String referralLinks = ed_referralLinks.getText().toString();//推广地址

        if (TextUtils.isEmpty(inviteCode)) {
            ToastUtil.show(ed_inviteCode.getHint().toString());
            return;
        } else {
            if (!isExitInviteCode(inviteCode)) {
                ToastUtil.show("请输入正确的邀请码");
                return;
            }
        }
        if (TextUtils.isEmpty(headPortrait)) {
            ToastUtil.show(ed_headPortrait.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(productName)) {
            ToastUtil.show(ed_name.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(introduce)) {
            ToastUtil.show(ed_introduce.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(referralLinks)) {
            ToastUtil.show(ed_referralLinks.getHint().toString());
            return;
        }

        TechnicalData technicalData = new TechnicalData();
        technicalData.setInviteCode(inviteCode);
        technicalData.setHeadPortrait(headPortrait);
        technicalData.setName(productName);
        technicalData.setIntroduce(introduce);
        technicalData.setReferralLinks(referralLinks);
        BmobInsertHelp.insertTechnicalData(technicalData, new BmobInsertHelp.OnInsertSuccessListener() {
            @Override
            public void success(String objectId) {
                ToastUtil.show("数据提交成功");
                finish();
            }

            @Override
            public void fail(BmobException e) {
                ToastUtil.show("提交失败，请重新操作！");
            }
        });
    }

    private ClipboardManager manager;

    private void registerClipEvents() {
        manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        manager.addPrimaryClipChangedListener(clipboardManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //onDestroy记得注销掉，避免内存泄露
        manager.removePrimaryClipChangedListener(clipboardManager);
    }

    //系统剪切板，全局作用
    private ClipboardManager.OnPrimaryClipChangedListener clipboardManager = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String copiedText = addedText.toString();
                if (addedText != null) {
                    Log.d("TAG", "copied text: " + addedText);
                    //接收到复制内容，分隔符：空格
                    String[] copiedTexts = copiedText.split(" ");

                    //头像：https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3601388998,136244981&fm=27&gp=0.jpg/名称：黄伟财/介绍：我是测试介绍介绍介绍介绍啊/资料链接：https://www.baidu.com/
                    //头像：xxx/名称：xxx/介绍：xxx/资料链接：xxx
                    String headPortraitTag = "头像：";
                    String productNameTag = "名称：";
                    String introduceTag = "介绍：";
                    String referralLinksTag = "资料链接：";
                    for (int i = 0; i < copiedTexts.length; i++) {
                        String content = copiedTexts[i];
                        if (content.contains(headPortraitTag)) {
                            ed_headPortrait.setText(content.substring(headPortraitTag.length()));
                        } else if (content.contains(productNameTag)) {
                            ed_name.setText(content.substring(productNameTag.length()));
                        } else if (content.contains(introduceTag)) {
                            ed_introduce.setText(content.substring(introduceTag.length()));
                        } else if (content.contains(referralLinksTag)) {
                            ed_referralLinks.setText(content.substring(referralLinksTag.length()));
                        }
                    }
                }
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String inviteCode = ed_inviteCode.getText().toString();//邀请码
        if (isChecked) {
            SPUtils.put(context, "inviteCode", inviteCode);
        } else {
            SPUtils.put(context, "inviteCode", "");
        }
        SPUtils.put(context, "isRemember", isChecked);
    }

    //判断是否存在邀请码
    private boolean isExitInviteCode(String inviteCode) {
        for (int i = 0; i < INVITE_CODES.length; i++) {
            if (TextUtils.equals(inviteCode, INVITE_CODES[i])) {
                return true;
            }
        }
        return false;
    }

    private String[] INVITE_CODES = {
            "hwc",//自己邀请码
            "ViI5",
            "NAOo",
            "frUB",
            "XcmS",
            "sI7W",
            "UYWE",
            "T3tg",
            "1M3v",
            "szAn",
            "z3v1",
            "bY45",
            "LhJC",
            "UkPm",
            "OCbl",
            "zsCx",
            "qpH3",
            "FAhP",
            "ObDl",
            "P6JB",
            "8GfV",
            "rcaw",
    };
}
