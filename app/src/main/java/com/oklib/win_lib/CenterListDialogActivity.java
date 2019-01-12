package com.oklib.win_lib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.util.toast.ToastUtil;
import com.oklib.window.CenterWinListDialog;

import java.util.ArrayList;
import java.util.List;



/**
 * 时间：2017/8/17
 * 作者：蓝天
 * 描述：列表自定义item窗口
 */

public class CenterListDialogActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_confirm_dialog;
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
                            "蓝天"+i,
                            "专注android客户端开发，提供技术辅导及技术源码支持"));
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
                                    holder.iv_headPortrait = (ImageView) convertView.findViewById(R.id.iv_headPortrait);
                                    holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                                    holder.tv_introduce = (TextView) convertView.findViewById(R.id.tv_introduce);
                                    convertView.setTag(holder);
                                } else {
                                    holder = (ViewHolder) convertView.getTag();
                                }
                                //处理逻辑
                                ListBean bean = dataList.get(position);
                                holder.tv_name.setText(""+bean.getName());
                                holder.tv_introduce.setText(""+bean.getIntroduce());
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

    private class ViewHolder {
        private ImageView iv_headPortrait;
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
