package com.oklib.demo.commo_tools;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.demo.integration_framework.PermissionActivity;
import com.oklib.demo.window_related.CenterWinListDialog;
import com.oklib.util.toast.SnackbarUtil;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;
import com.sdsmdg.tastytoast.TastyToast;

import static com.oklib.demo.AppOkLib.application;

/**
 * 时间：2017/8/3
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：Toast及Snackbar使用演示
 */

public class ToastActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_toast;
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
                        FunctionDetailBean[] beans = {
                                new FunctionDetailBean(context.getClass().getSimpleName(), "https://github.com/huangweicai/OkLibDemo"),
                        };
                        final CenterWinListDialog centerWinListDialog = CenterWinListDialog.create(getSupportFragmentManager());
                        centerWinListDialog.show();
                        centerWinListDialog.addDataList(ToastActivity.this, beans);
                    }
                });
    }

    @Override
    protected void initView() {
        findViewById(R.id.button_error_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.error("This is an error toast.");
            }
        });
        findViewById(R.id.button_success_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toasty.success(MainActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
                ToastUtil.success("Success!");
            }
        });
        findViewById(R.id.button_info_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toasty.info(MainActivity.this, "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
                ToastUtil.info("Here is some info for you.");
            }
        });
        findViewById(R.id.button_warning_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toasty.warning(MainActivity.this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
                ToastUtil.warn("Beware of the dog.");
            }
        });
        findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toasty.show(MainActivity.this, "Normal toast w/o icon").show();
                ToastUtil.show("Normal toast w/o icon");
            }
        });
        findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Drawable icon = getResources().getDrawable(R.drawable.ic_pets_white_48dp);
                Toasty.normal(MainActivity.this, "Normal toast w/ icon", icon).show();*/
                ToastUtil.show("Normal toast w/ icon", R.drawable.ic_pets_white_48dp);
            }
        });
        findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toasty.info(MainActivity.this, getFormattedMessage()).show();
                ToastUtil.info(getFormattedMessage());
            }
        });

        //-------------------Snackbar----------------------

        final View mView = View.inflate(context, R.layout.activity_logger, null);
        findViewById(R.id.button_snackbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //一下入参的view最好使用CoordinatorLayout的对象
                //简单用例
                //SnackbarUtil.ShortSnackbar(view, "妹子向你发来一条消息", SnackbarUtil.Info).show();

                //几种固定背景场景
//                Snackbar snackbar = SnackbarUtil.ShortSnackbar(view, "我是弹出的信息", SnackbarUtil.Confirm)
//                        .setActionTextColor(Color.RED)
//                        .setAction("再次发送", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                SnackbarUtil.LongSnackbar(view, "怎么你又弹出来了", SnackbarUtil.Alert).setActionTextColor(Color.WHITE).show();
//                            }
//                        });
//                SnackbarUtil.SnackbarAddView(snackbar, R.layout.item_snackbar_addview, 1);
//                snackbar.show();

                //自定义背景，提示文本，文本颜色，时间等
                //SnackbarUtil.IndefiniteSnackbar(view, "我是弹出的信息", 5000, 0xffffffff, 0xff000000).show();
                SnackbarUtil.IndefiniteSnackbar(view, "我是弹出的信息", 5000, getResources().getColor(R.color.white), getResources().getColor(R.color.blue)).show();
            }
        });
    }

    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @Override
    protected void initNet() {

    }

    public void onButton1Clicked(View v) {
        TastyToast.makeText(application, "Hello World !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
    }

    public void onButton2Clicked(View v) {
        TastyToast.makeText(application, "Hello World !", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
    }

    public void onButton3Clicked(View v) {
        TastyToast.makeText(application, "Hello World !", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
    }

    public void onButton4Clicked(View v) {
        TastyToast.makeText(application, "Hello World !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }

    public void onButton5Clicked(View v) {
        TastyToast.makeText(application, "Hello World !", TastyToast.LENGTH_LONG, TastyToast.INFO);
    }
}
