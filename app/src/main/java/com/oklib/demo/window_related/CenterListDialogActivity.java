package com.oklib.demo.window_related;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;
import com.oklib.view.gimage.GlideImageView;
import com.oklib.widget.CenterWinListDialog;

import java.util.ArrayList;
import java.util.List;

import static com.oklib.demo.Common.BASE_RES;
import static com.oklib.demo.R.id.iv_headPortrait;
import static com.oklib.demo.R.id.tv_introduce;
import static com.oklib.demo.R.id.tv_name;

/**
 * 时间：2017/8/17
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：居中列表显示窗口
 */

public class CenterListDialogActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_confirm_dialog;
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
                        mBeans.add(new FunctionDetailBean("activity_confirm_dialog.xml", BASE_RES +"/layout/activity_confirm_dialog.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        ((TextView) findView(R.id.tv_showDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ListBean> mBeans = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    mBeans.add(new ListBean(
                            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2003988139,3010807873&fm=117&gp=0.jpg",
                            "天鬼"+i,
                            "专注android客户端开发，微信小程序开发，提供技术辅导及技术源码支持"));
                }
                CenterWinListDialog.create(getSupportFragmentManager())
                        .addDataList(mBeans)
                        .setOnGetViewListener(new CenterWinListDialog.OnGetViewListener<ListBean>() {
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent, List<ListBean> dataList) {

                                ViewHolder holder;
                                if (convertView == null) {
                                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_list, parent, false);
                                    holder = new ViewHolder();
                                    holder.iv_headPortrait = (GlideImageView) convertView.findViewById(iv_headPortrait);
                                    holder.tv_name = (TextView) convertView.findViewById(tv_name);
                                    holder.tv_introduce = (TextView) convertView.findViewById(tv_introduce);
                                    convertView.setTag(holder);
                                } else {
                                    holder = (ViewHolder) convertView.getTag();
                                }
                                //处理逻辑
                                ListBean bean = dataList.get(position);
                                holder.tv_name.setText(""+bean.getName());
                                holder.tv_introduce.setText(""+bean.getIntroduce());
                                holder.iv_headPortrait.loadImage(""+bean.getHeadPortrait(), R.color.placeholder_color);
                                convertView.setBackgroundColor(0xffffffff);
                                convertView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ToastUtil.show("position："+position);
                                    }
                                });
                                return convertView;
                            }
                        }).show();

            }
        });
    }

    @Override
    protected void initNet() {

    }

    private class ViewHolder {
        private GlideImageView iv_headPortrait;
        private TextView tv_name;
        private TextView tv_introduce;
    }


    public class ListBean {
        private String headPortrait;//头像
        private String name;//名称
        private String introduce;//介绍

        public ListBean() {

        }
        public ListBean(String headPortrait,String name,String introduce) {
            this.headPortrait = headPortrait;
            this.name = name;
            this.introduce = introduce;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
    }


}
