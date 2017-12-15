package com.hwc.oklib;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.hwc.oklib.base.BaseAppTabLayoutActivity;
import com.hwc.oklib.fragment.BackgroundUserFragment;
import com.hwc.oklib.util.SPUtils;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.widget.BackgroudDialog;


/**
 * 时间：2017/8/25
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：后台显示界面
 */

public class BackgroundActivity extends BaseAppTabLayoutActivity {
    private final String BACKGROUND_PSW = "background_psw";
    private boolean isFinish = true;
    @Override
    protected void initVariable() {
        String backgroundPsw = (String) SPUtils.get(context, BACKGROUND_PSW, "");
        if (TextUtils.isEmpty(backgroundPsw)) {
            BackgroudDialog.create(getSupportFragmentManager())
                    .setOnPassWordConfirmListener(new BackgroudDialog.OnPassWordConfirmListener() {
                        @Override
                        public void pwConfirm(BackgroudDialog dialog, String inputText) {
                            if (TextUtils.equals(inputText, "cc1b75dc6ec775cae8528b252d33bd5d")) {
                                ToastUtil.success("密码正确");
                                SPUtils.put(context, BACKGROUND_PSW, "cc1b75dc6ec775cae8528b252d33bd5d");
                                isFinish = false;
                                dialog.dismiss();
                            } else {
                                isFinish = true;
                                ToastUtil.error("密码错误，请重新再试");
                            }
                        }
                    })
                    .setOnDialogDismissListener(new BackgroudDialog.OnDialogDismissListener() {
                        @Override
                        public void dismiss() {
                            if (isFinish) {
                                finish();
                            }
                        }
                    }).show();
        }else{
            if (!TextUtils.equals(backgroundPsw, "cc1b75dc6ec775cae8528b252d33bd5d")) {
                BackgroudDialog.create(getSupportFragmentManager())
                        .setOnPassWordConfirmListener(new BackgroudDialog.OnPassWordConfirmListener() {
                            @Override
                            public void pwConfirm(BackgroudDialog dialog, String inputText) {
                                if (TextUtils.equals(inputText, "cc1b75dc6ec775cae8528b252d33bd5d")) {
                                    ToastUtil.success("密码正确");
                                    SPUtils.put(context, BACKGROUND_PSW, "cc1b75dc6ec775cae8528b252d33bd5d");
                                    isFinish = false;
                                    dialog.dismiss();
                                } else {
                                    isFinish = true;
                                    ToastUtil.error("密码错误，请重新再试");
                                }
                            }
                        })
                        .setOnDialogDismissListener(new BackgroudDialog.OnDialogDismissListener() {
                            @Override
                            public void dismiss() {
                                if (isFinish) {
                                    finish();
                                }
                            }
                        }).show();
            }
        }
    }

    @Override
    protected void init() {
    }

    @Override
    protected void requestNet() {
        tb_toolbar.setNavIcon(R.drawable.white_back_icon);
    }

    @Override
    protected String centerTitle() {
        return getIntent().getStringExtra(Common.TITLE);
    }

    @Override
    protected String[] tabTitles() {
        return new String[]{"用户列表"};
    }

    @Override
    protected Fragment[] tabFragments() {
        //backgroundSMSFragment = new BackgroundSMSFragment();//这种形式，后面调用sendSMS方法，里面内容null，没被实例化
        return new Fragment[]{new BackgroundUserFragment()};
    }
}
