package com.oklib.demo.window_related;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.view.CommonToolBar;
import com.oklib.widget.dialog.StyledDialog;
import com.oklib.widget.dialog.Tool;
import com.oklib.widget.dialog.adapter.SuperRcvAdapter;
import com.oklib.widget.dialog.adapter.SuperRcvHolder;
import com.oklib.widget.dialog.bottomsheet.BottomSheetBean;
import com.oklib.widget.dialog.config.ConfigBean;
import com.oklib.widget.dialog.interfaces.MyDialogListener;
import com.oklib.widget.dialog.interfaces.MyItemDialogListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/9/2
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：Dialog常用窗口大全使用演示
 */

public class DialogActivity extends BaseAppActivity implements View.OnClickListener {
    private Button btn_common_progress;
    private Button btn_context_progress;
    private Button btn_context_progress_h;
    private Button btn_context_progress_c;
    private Button btn_material_alert;
    private Button btn_multichoose;
    private Button btn_singlechoose;
    private Button btn_ios_alert;
    private Button btn_ios_alert_vertical;
    private Button btn_input;
    private Button btn_ios_bottom_sheet;
    private Button btn_ios_center_list;
    private Button btn_md_bs;
    private Button btn_md_bs_listview;
    private Button btn_md_bs_Gridview;
    private Button btn_customview;

    private Activity activity;
    private Context context;
    private Handler handler;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_dialog;
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
                        mBeans.add(new FunctionDetailBean("activity_dialog.xml", BASE_RES + "/layout/activity_dialog.xml"));
                        showDetail();
                    }
                });
    }


    @Override
    protected void initView() {
        handler = new Handler();
        activity = this;
        context = getApplication();

        btn_common_progress = findView(R.id.btn_common_progress);
        btn_context_progress = findView(R.id.btn_context_progress);
        btn_context_progress_h = findView(R.id.btn_context_progress_h);
        btn_context_progress_c = findView(R.id.btn_context_progress_c);
        btn_material_alert = findView(R.id.btn_material_alert);
        btn_multichoose = findView(R.id.btn_multichoose);
        btn_singlechoose = findView(R.id.btn_singlechoose);
        btn_ios_alert = findView(R.id.btn_ios_alert);
        btn_ios_alert_vertical = findView(R.id.btn_ios_alert_vertical);
        btn_input = findView(R.id.btn_input);
        btn_ios_bottom_sheet = findView(R.id.btn_ios_bottom_sheet);
        btn_ios_center_list = findView(R.id.btn_ios_center_list);
        btn_md_bs = findView(R.id.btn_md_bs);
        btn_md_bs_listview = findView(R.id.btn_md_bs_listview);
        btn_md_bs_Gridview = findView(R.id.btn_md_bs_Gridview);
        btn_customview = findView(R.id.btn_customview);

        btn_common_progress.setOnClickListener(this);
        btn_context_progress.setOnClickListener(this);
        btn_context_progress_h.setOnClickListener(this);
        btn_context_progress_c.setOnClickListener(this);
        btn_material_alert.setOnClickListener(this);
        btn_multichoose.setOnClickListener(this);
        btn_singlechoose.setOnClickListener(this);
        btn_ios_alert.setOnClickListener(this);
        btn_ios_alert_vertical.setOnClickListener(this);
        btn_input.setOnClickListener(this);
        btn_ios_bottom_sheet.setOnClickListener(this);
        btn_ios_center_list.setOnClickListener(this);
        btn_md_bs.setOnClickListener(this);
        btn_md_bs_listview.setOnClickListener(this);
        btn_md_bs_Gridview.setOnClickListener(this);
        btn_customview.setOnClickListener(this);
    }

    @Override
    protected void initNet() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_common_progress://菊花加载窗口
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast("----schedule----");
//                    }
//                }).run();
//                StyledDialog.dismissLoading();
//                showToast("dismissLoading() called ");

                StyledDialog.buildLoading("加载中...").show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        StyledDialog.updateLoadingMsg("jjjjj" + new Random().nextInt(100));
                    }
                }, 50, 2000);

                break;
            case R.id.btn_context_progress://圆形进度加载窗口（头尾风格）
                gloablDialog = StyledDialog.buildMdLoading().show();

                //StyledDialog.dismissLoading();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StyledDialog
                                .updateLoadingMsg("jjjjj" + new Random().nextInt(100));
                    }
                }, 3000);
                break;
            case R.id.btn_context_progress_h://水平进度加载窗口
                final ProgressDialog dialog = (ProgressDialog) StyledDialog.buildProgress("下载中...", true).setCancelable(false, false).show();
                final int[] progress = {0};
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        progress[0] += 10;
                        StyledDialog.updateProgress(dialog, progress[0], 100, "progress", true);
                        if (progress[0] > 100) {
                            timer.cancel();
                            dialog.dismiss();
                        }
                    }
                }, 500, 500);

                break;
            case R.id.btn_context_progress_c://圆形进度加载窗口（错位风格）
                final ProgressDialog dialog2 = (ProgressDialog) StyledDialog.buildProgress("下载中...", false).show();
                final int[] progress2 = {0};

                final Timer timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        progress2[0] += 10;
                        StyledDialog.updateProgress(dialog2, progress2[0], 100, "progress", false);
                        if (progress2[0] > 100) {
                            timer2.cancel();
                        }
                    }
                }, 500, 500);

                break;
            case R.id.btn_material_alert://material风格中立、取消、确定窗口
                StyledDialog.buildMdAlert("title", msg, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        showToast("onFirst");
                    }

                    @Override
                    public void onSecond() {
                        showToast("onSecond");
                    }

                    @Override
                    public void onThird() {
                        showToast("onThird");
                    }

                })
                        .setBtnSize(16)
                        .setBtnText("中立", "取消", "确定")
                        .setBtnColor(R.color.oklib_btn_alert, R.color.colorPrimaryDark, R.color.oklib_text_red)
                        .show();

                break;
            case R.id.btn_ios_alert://ios风格中立、取消、确定
                StyledDialog.buildIosAlert("title", msg, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        showToast("onFirst");
                    }

                    @Override
                    public void onSecond() {
                        showToast("onSecond");
                    }

                    @Override
                    public void onThird() {
                        showToast("onThird");
                    }


                }).setBtnText("sure", "cancle", "hhhh").show();
                break;
            case R.id.btn_ios_alert_vertical://ios风格竖向确定、取消窗口
                StyledDialog.buildIosAlertVertical("title", msg, new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        showToast("onFirst");
                    }

                    @Override
                    public void onSecond() {
                        showToast("onSecond");
                    }

                    @Override
                    public void onThird() {
                        showToast("onThird");
                    }

                }).show();
                break;
            case R.id.btn_ios_bottom_sheet: {//ios底部弹起可滚动窗口
                final List<String> strings = new ArrayList<>();
                strings.add("1");
                strings.add("2");
                strings.add(msg);
                strings.add("4");
                strings.add("5");
                strings.add(msg);
                strings.add("6");
                strings.add("7");
                strings.add(msg);
                strings.add("8");
                strings.add("9");
                strings.add(msg);
                strings.add("10");
                strings.add("11");
                strings.add(msg);
                strings.add("12");
                strings.add("13");
                strings.add(msg);

                StyledDialog.buildBottomItemDialog(strings, "cancle", new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text);
                    }

                    @Override
                    public void onBottomBtnClick() {
                        showToast("onItemClick");
                    }
                }).show();
            }
            break;
            case R.id.btn_ios_center_list://ios屏幕居中可滚动窗口

                final List<String> strings = new ArrayList<>();
                strings.add("1");
                strings.add("2");
                strings.add(msg);
                strings.add("4");
                strings.add("5");
                strings.add(msg);
                strings.add("6");
                strings.add("7");
                strings.add(msg);
                strings.add("8");
                strings.add("9");
                strings.add(msg);

                strings.add("10");
                strings.add("11");
                strings.add(msg);
                strings.add("12");
                strings.add("13");
                strings.add(msg);

                StyledDialog.buildIosSingleChoose(strings, new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text);
                    }

                    @Override
                    public void onBottomBtnClick() {
                        showToast("onItemClick");
                    }
                }).show();

                break;
            case R.id.btn_input://输入窗口
                StyledDialog.buildNormalInput("登录", "请输入用户名", "请输入密码", "登录", "取消", new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {

                    }

                    @Override
                    public void onGetInput(CharSequence input1, CharSequence input2) {
                        super.onGetInput(input1, input2);
                        showToast("input1:" + input1 + "--input2:" + input2);
                    }
                }).show();

                break;
            case R.id.btn_multichoose://多选窗口
                String[] words = new String[]{"12", "78", "45", "89", "88", "00"};

                //boolean[] choseDefault = new boolean[]{false,false,false,false,true,false};

                StyledDialog.buildMdMultiChoose("xuanze", words, new ArrayList<Integer>(), new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {

                    }

                    @Override
                    public void onChoosen(List<Integer> selectedIndex, List<CharSequence> selectedStrs, boolean[] states) {
                        super.onChoosen(selectedIndex, selectedStrs, states);
                    }

                    @Override
                    public void onGetChoose(boolean[] states) {
                        super.onGetChoose(states);
                    }
                }).show();
                break;
            case R.id.btn_singlechoose://单选窗口
                String[] words2 = new String[]{"12", "78", "45", "89", "88", "00"};
                StyledDialog.buildMdSingleChoose("单选", 2, words2, new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text + "--" + position);
                    }
                }).show();

                break;
            case R.id.btn_md_bs://material风格底部弹起可伸缩滑动窗口
                String[] words3 = new String[]{"12", "78", "45", "89", "88", "00"};
                List<String> datas = Arrays.asList(words3);

                // final BottomSheetDialog dialog = new BottomSheetDialog(this);
                RecyclerView recyclerView = new RecyclerView(this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                SuperRcvAdapter adapter = new SuperRcvAdapter(this) {
                    @Override
                    protected SuperRcvHolder generateCoustomViewHolder(int viewType) {

                        return new SuperRcvHolder<String>(inflate(R.layout.item_dialog_lv_text)) {

                            Button mButton;

                            @Override
                            public void assignDatasAndEvents(Activity context, final String data) {
                                if (mButton == null) {
                                    mButton = (Button) itemView.findViewById(R.id.btnee);
                                }
                                mButton.setText(data);
                                mButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showToast(data);
                                    }
                                });
                            }
                        };
                    }
                };
                recyclerView.setAdapter(adapter);
                adapter.addAll(datas);
                adapter.addAll(datas);
                adapter.addAll(datas);

                StyledDialog.buildCustomBottomSheet(recyclerView).show();//不好建立回调

                break;

            case R.id.btn_md_bs_listview: {//material风格底部弹起可伸缩上listview窗口
                List<BottomSheetBean> datas2 = new ArrayList<>();

                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "1"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "222"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "333333"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "444"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "55"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "666"));

                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "7777"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "fddsf"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "67gfhfg"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));

                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "7777"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "fddsf"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "67gfhfg"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));

                StyledDialog.buildBottomSheetLv("拉出来溜溜", datas2, "this is cancle button", new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text + "---" + position);
                    }
                }).show();
            }
            break;

            case R.id.btn_md_bs_Gridview://material风格底部弹起可伸缩上gridview窗口
                List<BottomSheetBean> datas2 = new ArrayList<>();

                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "1"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "222"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "333333"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "444"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "55"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "666"));

                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "7777"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "fddsf"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "67gfhfg"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));

                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "7777"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "fddsf"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "67gfhfg"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "7777"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "fddsf"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "67gfhfg"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "7777"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "fddsf"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "67gfhfg"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "7777"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "fddsf"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "67gfhfg"));
                datas2.add(new BottomSheetBean(R.mipmap.ic_launcher, "oooooppp"));

                StyledDialog.buildBottomSheetGv("拉出来溜溜", datas2, "this is cancle button", 3, new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text + "---" + position);
                    }
                }).show();
                break;
            case R.id.btn_customview://自定义显示web页面窗口
                ViewGroup customView = (ViewGroup) View.inflate(this, R.layout.dialog_custom_view, null);
                final ConfigBean bean = StyledDialog.buildCustom(customView, Gravity.CENTER).setHasShadow(false);
                final Dialog dialog1 = bean.show();
                WebView webView = (WebView) customView.findViewById(R.id.webview);
                final TextView textView = (TextView) customView.findViewById(R.id.tv_title);
                webView.loadUrl("http://www.jianshu.com/p/87e7392a16ff");

                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        Tool.adjustWH(dialog1, bean);
                    }
                });
                webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        textView.setText(title);
                    }
                });

                break;
        }
    }


    public void showToast(CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (gloablDialog != null && gloablDialog.isShowing()) {
            gloablDialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    private Dialog gloablDialog;
    private String msg = "如果你有心理咨询师般的敏锐，你会进一步发现——这个姑娘企图用考研来掩饰自己对于毕业的恐惧。";
           /* "\n" +
            "像琴姑娘这样的毕业生很多，她们一段时间内会认真地复习考研。可用不了多久，她们便会动摇，便会找出诸多借口给自己开脱，最后考研一事半途而废。\n" +
            "\n" +
            "原因，当事人根本不相信这件事能改变她的命运，能带给她想要的生活。她们相信自己不够努力，也愿意别人骂自己不努力。\n" +
            "\n" +
            "他们不愿意思考自己到底该干什么？他们抱着一个幻想，假如我真的努力就能解决问题了吧！于是他们把一个不可控的事件，在心理变成了可控，从而增加安全感。\n" +
            "\n" +
            "人真的可以为了逃避真正的思考，而做出任何你想象不到的事。\n" +
            "\n" +
            "这种目标是不重结果的，其实它跟刷微博是一个道理，它通过获取无用信息来给自己的生活制造一点喘息。\n" +
            "\n" +
            "只不过陷在“学习”中，要比陷在微博上更能安慰自己的内心，那个已经破了个大洞的内心。\n" +
            "\n" +
            "作者：剑圣喵大师\n" +
            "链接：https://www.zhihu.com/question/50126427/answer/119551026\n" +
            "来源：知乎\n" +
            "著作权归作者所有，转载请联系作者获得授权。";*/

}
