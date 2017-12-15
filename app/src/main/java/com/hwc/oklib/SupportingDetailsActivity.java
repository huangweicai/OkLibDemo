package com.hwc.oklib;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bmoblib.BmobInsertHelp;
import com.bmoblib.BmobPayHelp;
import com.bmoblib.bean.Supporter;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.fragment.SupportingDetailsFragment;
import com.hwc.oklib.fragment.TechnicalDataFragment;
import com.hwc.oklib.login.UserManager;
import com.hwc.oklib.util.SPUtils;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.window.EditDialog;
import com.hwc.oklib.window.base.BaseDialogFragment;

import cn.bmob.v3.exception.BmobException;


/**
 * 时间：2017/8/7
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：资源详细
 */

public class SupportingDetailsActivity extends BaseAppActivity implements ViewPager.OnPageChangeListener {
    private TabLayout toolbar_tl_tab;
    private ViewPager vp_container;
    private String[] titles = {"赞助", "打赏", "资料"};
    private int mPosition = 0;
    private String mOrderId;//支付成功后订单

    @Override
    protected int initLayoutId() {
        return R.layout.activity_supporting_details;
    }

    @Override
    protected void initVariable() {

    }

    private CommonToolBar tb_toolbar;
    @Override
    protected void initTitle() {
        tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("赞助", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setCancelable(false);
                        dialog.setMessage(getResources().getString(R.string.supporting_details_text));
                        dialog.setPositiveButton("支付宝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                if (mPosition == 0) {
                                    //赞助
                                    Intent intent = new Intent(context, PayActivity.class);
                                    intent.putExtra(Common.TITLE, "支付宝购买");
                                    intent.putExtra(PayActivity.PAY_TYPE_KEY, PayActivity.ALIPAY);
                                    startActivity(intent);
                                } else if (mPosition == 1) {
                                    //打赏
                                    final EditDialog editDialog = EditDialog.create(getSupportFragmentManager());
                                    editDialog.setTitle("打赏");
                                    editDialog.setExplain("金额：");
                                    editDialog.setLimit("￥1以上");
                                    editDialog.show();
                                    editDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                                        @Override
                                        public void confirm(View v) {
                                            double money = 0;
                                            try {
                                                money = Double.valueOf(editDialog.getContent());
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                                ToastUtil.show("请检查输入金额是否有误");
                                                return;
                                            }

                                            if (money < 1) {
                                                ToastUtil.show("输入金额￥1以上");
                                                return;
                                            }

                                            //支付宝购买
                                            final double finalMoney = money;
                                            BmobPayHelp.getInstance().alipay(context, "打赏", money, "oklib库打赏", new BmobPayHelp.OnPayListener() {
                                                @Override
                                                public void success() {
                                                    //打赏
                                                    Supporter supporter = new Supporter();
                                                    supporter.setHeadPortrait(UserManager.getUserBean().getHeadPicture());
                                                    supporter.setName(UserManager.getUserBean().getNickName());
                                                    supporter.setIntroduce(UserManager.getUserBean().getIntro());
                                                    supporter.setSum(finalMoney);
                                                    supporter.setReferralLinks("");
                                                    supporter.setOrderId(mOrderId);
                                                    BmobInsertHelp.insertSupporter(supporter, new BmobInsertHelp.OnInsertSuccessListener() {
                                                        @Override
                                                        public void success(String objectId) {
                                                            ToastUtil.show("打赏成功");
                                                        }

                                                        @Override
                                                        public void fail(BmobException e) {
                                                            ToastUtil.show("打赏失败");
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void fail() {
                                                }

                                                //订单回调先于支付成功方法回调
                                                @Override
                                                public void orderId(String orderId) {
                                                    mOrderId = orderId;
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
//                        dialog.setNegativeButton("微信", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(context, PayActivity.class);
//                                intent.putExtra(Common.TITLE, "微信购买");
//                                intent.putExtra(PayActivity.PAY_TYPE_KEY, PayActivity.WECHAT_PAY);
//                                startActivity(intent);
//                            }
//                        });
                        dialog.setNeutralButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void initView() {
        toolbar_tl_tab = findView(R.id.toolbar_tl_tab);
        vp_container = findView(R.id.vp_container);
        vp_container.setOffscreenPageLimit(2);
        vp_container.setOnPageChangeListener(this);
        toolbar_tl_tab.setupWithViewPager(vp_container);
        toolbar_tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0 ||position == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", position);
                    return SupportingDetailsFragment.getInstance(bundle);
                } else {
                    if (position == 2) {
                        return TechnicalDataFragment.getInstance(null);
                    }
                    return null;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });
        vp_container.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                tb_toolbar.isShowRightView(false);
                if (position == 0) {
                    tb_toolbar.isShowRightView(true);
                    tb_toolbar.setRightTitle("赞助", 14, R.color.app_white_color);
                } else if (position == 1) {
                    tb_toolbar.isShowRightView(true);
                    tb_toolbar.setRightTitle("打赏", 14, R.color.app_white_color);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int pagePos = (int) SPUtils.get(context, VP_PAGE, 0);
        vp_container.setCurrentItem(pagePos);
    }


    @Override
    protected void initNet() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private final String VP_PAGE = "vpPage";//页码
    @Override
    public void onPageSelected(int position) {
        SPUtils.put(context, VP_PAGE, position);//缓存页码，下次直接打开
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
