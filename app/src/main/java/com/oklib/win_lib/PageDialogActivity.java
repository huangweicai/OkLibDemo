package com.oklib.win_lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.util.FastJsonUtil;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonRefreshLayout;
import com.oklib.view.NoScrollViewPager;
import com.oklib.view.StateButton;
import com.oklib.view.base.BaseRcvAdapter;
import com.oklib.window.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 时间：2017/12/26
 * 作者：蓝天
 * 描述：分页dialog
 */

public class PageDialogActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_confirm_dialog;
    }

    String beanJson = "{\n" +
            "            \"MO_NAME\": \"SJ23274801\",\n" +
            "            \"MITEM_CODE\": \"12090000\",\n" +
            "            \"MITEM_DESC\": \"贴片电容 RoHS C-0603-224Z50-Y5V\",\n" +
            "            \"RATION\": 1,\n" +
            "            \"POSITION\": \"C25\"\n" +
            "    }";
    @Override
    protected void initView() {

        ((TextView)findView(R.id.tv_showDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<BOMCheckDialog.BOMCheckBean> mlist = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    mlist.add(FastJsonUtil.json2Bean(beanJson, BOMCheckDialog.BOMCheckBean.class));
                }

                BOMCheckDialog bomCheckDialog = BOMCheckDialog.create(getSupportFragmentManager());
                bomCheckDialog.show();
                bomCheckDialog.setWorkOrderOrder("111111");
                bomCheckDialog.setProductCode("22222");
                bomCheckDialog.setMaterialDescription("描述描述描述");
                bomCheckDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                    @Override
                    public void confirm(View v) {
                        Toast.makeText(context, "当前勾选状态已经记录", Toast.LENGTH_SHORT).show();
                    }
                });

                bomCheckDialog.refreshData(mlist);
            }
        });

    }




    public static class BOMCheckDialog extends BaseDialogFragment implements View.OnClickListener {
        private TextView tv_title;
        private TextView tv_work_order_order;
        private TextView tv_product_code;
        private TextView tv_material_description;
        private CommonRefreshLayout rv_layout;
        private TextView tv_left;
        private NoScrollViewPager vp_page;
        private TextView tv_right;
        private StateButton btn_confirm;

        private BOMCheckRvAdapter adapter;
        private MyPagerAdapter myPagerAdapter;

        private static int pageWidth = 40;
        private static int pageGap = 3;

        @Override
        public float initDimValue() {
            return 0.8f;
        }

        @Override
        public void initOnResume() {
            setWHSize(dp2px(getContext(), 720), dp2px(getContext(), 465));
        }

        @Override
        public boolean isCancel() {
            return true;
        }

        @Override
        public int gravity() {
            return 0;
        }

        @Override
        public int style() {
            return 0;
        }

        @Override
        public int initContentView() {
            return R.layout.dialog_bom_check;
        }

        @Override
        protected void argumentsDate() {
            pageWidth = dp2px(getContext(), 40);
            pageGap = dp2px(getContext(), 3);
        }

        @Override
        public void initActionBarView(View view) {

        }

        @Override
        public void initView(View view) {
            tv_title = findView(view, R.id.tv_title);
            tv_work_order_order = findView(view, R.id.tv_work_order_order);
            tv_product_code = findView(view, R.id.tv_product_code);
            tv_material_description = findView(view, R.id.tv_material_description);
            tv_left = findView(view, R.id.tv_left);
            vp_page = findView(view, R.id.vp_page);
            vp_page.isScroll(false);
            tv_right = findView(view, R.id.tv_right);
            btn_confirm = findView(view, R.id.btn_confirm);
            tv_left.setOnClickListener(this);
            tv_right.setOnClickListener(this);
            btn_confirm.setOnClickListener(this);
        }

        @Override
        public void initData(View view) {
            rv_layout = findView(view, R.id.rv_layout);
            rv_layout.setAdapter(adapter = new BOMCheckRvAdapter(getContext()));
            rv_layout.setAnimEnabled(false);
        }

        //外部传入数据源（全部数据），刷新数据界面
        public void refreshData(final List<BOMCheckBean> list) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (null == rv_layout || null == vp_page) {
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //刷新列表数据
                            netSuccess(list);

                            //刷新页码数据
                            myPagerAdapter = new MyPagerAdapter(getContext());
                            vp_page.setAdapter(myPagerAdapter);
                        }
                    });
                }
            }).start();
        }


        @Override
        protected void initNet() {
        }


        private List<List<BOMCheckBean>> pageRvList = new ArrayList<>();

        private void netSuccess(List<BOMCheckBean> list) {
            pageRvList.clear();
            if (!list.isEmpty()) {
                //12
                int listSize = list.size();
                if (listSize % 5 == 0) {
                    //整除
                    for (int i = 0; i < listSize / 5; i++) {
                        List<BOMCheckBean> subPageRvList = new ArrayList<>();
                        for (int j = 0; j < 5; j++) {
                            subPageRvList.add(list.get(i * 5 + j));
                        }
                        pageRvList.add(subPageRvList);
                    }
                } else {
                    //不整除，有余数
                    for (int i = 0; i < listSize / 5; i++) {
                        List<BOMCheckBean> subPageRvList = new ArrayList<>();
                        for (int j = 0; j < 5; j++) {
                            subPageRvList.add(list.get(i * 5 + j));
                        }
                        pageRvList.add(subPageRvList);
                    }

                    //多余的页码，只有一页
                    List<BOMCheckBean> subPageRvList = new ArrayList<>();
                    for (int i = 0; i < listSize % 5; i++) {
                        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(pageWidth, pageWidth);
                        tvParams.leftMargin = pageGap;
                        tvParams.rightMargin = 0;
                        if (i == listSize % 5 - 1) {
                            tvParams.leftMargin = pageGap;
                            tvParams.rightMargin = pageGap;
                        }
                        // 12/5==2 (int类型)
                        subPageRvList.add(list.get(listSize / 5 * 5 + i));
                    }
                    pageRvList.add(subPageRvList);
                }

            }

            if (pageRvList.isEmpty()) {
                return;
            }

            //清空数据源
            adapter.getDataList().clear();
            //停止刷新动画
            rv_layout.isShowRefreshAnim(false);
            //追加数据源
            adapter.addDataList(pageRvList.get(vp_page.getCurrentItem()));
            if (list.size() < adapter.LOAD_NUM) {//默认加载条数15，这个条数应该同请求一次的条数一致
                //完成加载更多状态，更新文本
                adapter.setLoadState(false, "没有更多数据了");
            } else {
                //必须，能再次触发加载更多，更新可以加载状态
                rv_layout.loadFinish();
            }
            //必须，无数据显示状态，请求失败或者网络异常，更新显示效果
            //备选：R.mipmap.no_net_icon, "暂无网络"
            adapter.setEmptyDataState(R.mipmap.oklib_rv_refresh_component_no_data_icon, "暂无数据");
        }


        /**
         * 设置标题
         */
//    public void setTitle(final String text) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (null == tv_title) {
//                }
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (TextUtils.isEmpty(text)) {
//                            tv_title.setText("");
//                        } else {
//                            tv_title.setText(text);
//                        }
//                    }
//                });
//            }
//        }).start();
//    }

        /**
         * 设置工单号
         */
        public void setWorkOrderOrder(final String text) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (null == tv_work_order_order) {
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(text)) {
                                tv_work_order_order.setText("");
                            } else {
                                tv_work_order_order.setText(text);
                            }
                        }
                    });
                }
            }).start();
        }

        /**
         * 设置产品编码
         */
        public void setProductCode(final String text) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (null == tv_product_code) {
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(text)) {
                                tv_product_code.setText("");
                            } else {
                                tv_product_code.setText(text);
                            }
                        }
                    });
                }
            }).start();
        }

        /**
         * 设置物料描述
         */
        public void setMaterialDescription(final String text) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (null == tv_material_description) {
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(text)) {
                                tv_material_description.setText("");
                            } else {
                                tv_material_description.setText(text);
                            }
                        }
                    });
                }
            }).start();
        }

        /**
         * 设置右按钮·确定
         */
        public void setConfirmText(final String text) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (null == btn_confirm) {
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(text)) {
                                btn_confirm.setText("");
                            } else {
                                btn_confirm.setText(text);
                            }
                        }
                    });
                }
            }).start();
        }


        private static FragmentManager fm;
        private static FragmentTransaction ft;
        private static BOMCheckDialog dialog;

        @SuppressLint("ValidFragment")
        private BOMCheckDialog() {
        }

        /**
         * 显示dialog
         */
        public static BOMCheckDialog create(FragmentManager _fm) {
            fm = _fm;
            dialog = new BOMCheckDialog();
            return dialog;
        }

        public void show() {
            ft = fm.beginTransaction();
            dialog.show(ft, "");
        }

        @Override
        public void onClick(View v) {
            if (v == btn_confirm) {
                if (null != confirmListener) {
                    dismiss();
                    confirmListener.confirm(v);
                }
            }

            int currentItemPage = vp_page.getCurrentItem();
            if (v == tv_left) {
                vp_page.setCurrentItem(currentItemPage - 1);

//        int mcurrentItemPage = vp_page.getCurrentItem();
                if (currentItemPage == 0) {
                    ToastUtil.show("已经是第一页了");
                }
            }

            if (v == tv_right) {
                vp_page.setCurrentItem(currentItemPage + 1);
                //注意：VP ChildCount 只会左中右最多3个，在最左和最右ChildCount=2
//        int mcurrentItemPage = vp_page.getCurrentItem();
                if ((currentItemPage + 1) * 5 - pageRvList.size() > 0) {
                    ToastUtil.show("已经是最后一页了");
                }
            }
        }


        //----------------列表适配器-------------------
        public class BOMCheckRvAdapter<T> extends BaseRcvAdapter<T> {

            public BOMCheckRvAdapter(Context context) {
                super(context);
                EXTRA_TYPES = 0;
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bom_list, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ViewHolderItem mHolder = (ViewHolderItem) holder;
                mHolder.processData(position);
            }


            private class ViewHolderItem extends RecyclerView.ViewHolder {
                LinearLayout ll_item_layout;
                TextView tv_material_code;
                TextView tv_material_description;
                TextView tv_single_unit_dosage;
                TextView tv_location;
                ImageView iv_sign;

                private ViewHolderItem(View itemView) {
                    super(itemView);
                    ll_item_layout = itemView.findViewById(R.id.ll_item_layout);
                    tv_material_code = itemView.findViewById(R.id.tv_material_code);
                    tv_material_description = itemView.findViewById(R.id.tv_material_description);
                    tv_single_unit_dosage = itemView.findViewById(R.id.tv_single_unit_dosage);
                    tv_location = itemView.findViewById(R.id.tv_location);
                    iv_sign = itemView.findViewById(R.id.iv_sign);
                }

                private void processData(final int position) {
                    final BOMCheckBean bean = (BOMCheckBean) dataList.get(position);

                    tv_material_code.setText(bean.getMITEM_CODE());
                    tv_material_description.setText(bean.getMITEM_DESC());
                    tv_single_unit_dosage.setText(""+bean.getRATION());
                    tv_location.setText(bean.getPOSITION());

                    if (position % 2 == 0) {
                        ll_item_layout.setBackgroundColor(context.getResources().getColor(R.color.white_color));
                    } else {
                        ll_item_layout.setBackgroundColor(context.getResources().getColor(R.color.gray_color));
                    }

                    if (bean.isSign) {
                        iv_sign.setImageResource(R.drawable.select_multiple_choice_icon);
                    } else {
                        iv_sign.setImageResource(R.drawable.default_multiple_choice_icon);
                    }

                    iv_sign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (bean.isSign) {
                                bean.setSign(false);
                            } else {
                                bean.setSign(true);
                            }
                            notifyDataSetChanged();

                            List<BOMCheckBean> mlist = new ArrayList<>();
                            for (int i = 0; i < pageRvList.size(); i++) {
                                mlist.addAll(pageRvList.get(i));
                            }
                        }
                    });
                }

            }

        }

        //    {
//            "MO_NAME": "SAASJ2327480101J",
//            "MITEM_CODE": "11201209000094",
//            "MITEM_DESC": "贴片电容 RoHS C-0603-224Z50-Y5V",
//            "RATION": 1,
//            "POSITION": "C25"
//    }
        //列表数据载体
        public static class BOMCheckBean {

            /**
             * MO_NAME : SAASJ2327480101J
             * MITEM_CODE : 11201209000094
             * MITEM_DESC : 贴片电容 RoHS C-0603-224Z50-Y5V
             * RATION : 1
             * POSITION : C25
             */

            private String MO_NAME;
            private String MITEM_CODE;
            private String MITEM_DESC;
            private float RATION;
            private String POSITION;
            private boolean isSign;//是否标记过

            public boolean isSign() {
                return isSign;
            }

            public void setSign(boolean sign) {
                isSign = sign;
            }

            public String getMO_NAME() {
                return MO_NAME;
            }

            public void setMO_NAME(String MO_NAME) {
                this.MO_NAME = MO_NAME;
            }

            public String getMITEM_CODE() {
                return MITEM_CODE;
            }

            public void setMITEM_CODE(String MITEM_CODE) {
                this.MITEM_CODE = MITEM_CODE;
            }

            public String getMITEM_DESC() {
                return MITEM_DESC;
            }

            public void setMITEM_DESC(String MITEM_DESC) {
                this.MITEM_DESC = MITEM_DESC;
            }

            public float getRATION() {
                return RATION;
            }

            public void setRATION(float RATION) {
                this.RATION = RATION;
            }

            public String getPOSITION() {
                return POSITION;
            }

            public void setPOSITION(String POSITION) {
                this.POSITION = POSITION;
            }
        }

        //--------------VP页码标识---------------

        public class MyPagerAdapter extends PagerAdapter {
            List<LinearLayout> viewList = new ArrayList<>();//每页view数量

            public MyPagerAdapter(Context context) {
                //12
                int listSize = pageRvList.size();//3
                if (listSize <= 0) {
                    return;
                }
                if (listSize % 5 == 0) {
                    //整除
                    viewList.clear();
                    tabList.clear();
                    for (int i = 0; i < listSize / 5; i++) {
                        LinearLayout pageLayout = new LinearLayout(context);
                        pageLayout.setOrientation(LinearLayout.HORIZONTAL);
                        for (int j = 0; j < 5; j++) {
                            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(pageWidth, pageWidth);
                            tvParams.leftMargin = pageGap;
                            tvParams.rightMargin = 0;
                            if (i == 4) {
                                tvParams.leftMargin = pageGap;
                                tvParams.rightMargin = pageGap;
                            }
                            TextView pageNumTextView = createPageTextView(i * 5 + (j + 1));
                            pageNumTextView.setLayoutParams(tvParams);
                            if (i == 0 && j == 0) {
                                pageNumTextView.setBackgroundResource(R.drawable.drawable_blue_side_line);
                            }

                            pageLayout.addView(pageNumTextView);
                            tabList.add(pageNumTextView);
                        }
                        viewList.add(pageLayout);
                    }
                } else {
                    //不整除，有余数
                    viewList.clear();
                    tabList.clear();
                    for (int i = 0; i < listSize / 5; i++) {
                        LinearLayout pageLayout = new LinearLayout(context);
                        pageLayout.setOrientation(LinearLayout.HORIZONTAL);
                        for (int j = 0; j < 5; j++) {
                            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(pageWidth, pageWidth);
                            tvParams.leftMargin = pageGap;
                            tvParams.rightMargin = 0;
                            if (i == 4) {
                                tvParams.leftMargin = pageGap;
                                tvParams.rightMargin = pageGap;
                            }
                            TextView pageNumTextView = createPageTextView(i * 5 + (j + 1));
                            pageNumTextView.setLayoutParams(tvParams);
                            if (i == 0 && j == 0) {
                                pageNumTextView.setBackgroundResource(R.drawable.drawable_blue_side_line);
                            }

                            pageLayout.addView(pageNumTextView);
                            tabList.add(pageNumTextView);
                        }
                        viewList.add(pageLayout);
                    }

                    //多余的页码，只有一页
                    LinearLayout pageLayout = new LinearLayout(context);
                    pageLayout.setOrientation(LinearLayout.HORIZONTAL);
                    for (int i = 0; i < listSize % 5; i++) {
                        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(pageWidth, pageWidth);
                        tvParams.leftMargin = pageGap;
                        tvParams.rightMargin = 0;
                        if (i == listSize % 5 - 1) {
                            tvParams.leftMargin = pageGap;
                            tvParams.rightMargin = pageGap;
                        }
                        // 12/5==2 (int类型)
                        TextView pageNumTextView = createPageTextView(listSize / 5 * 5 + (i + 1));
                        pageNumTextView.setLayoutParams(tvParams);

                        pageLayout.addView(pageNumTextView);
                        tabList.add(pageNumTextView);
                    }
                    viewList.add(pageLayout);
                }

            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
                container.removeView(viewList.get(position));
            }
        }

        private List<TextView> tabList = new ArrayList<>();
        private View.OnClickListener pageTvClicker = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //页码点击
                adapter.getDataList().clear();
                adapter.addDataList(pageRvList.get((int) view.getTag() - 1));

                for (int i = 0; i < tabList.size(); i++) {
                    TextView tabView = tabList.get(i);
                    if (view == tabView) {
                        tabView.setBackgroundResource(R.drawable.drawable_blue_side_line);
                    }else{
                        tabView.setBackgroundResource(R.drawable.drawable_gray_line);
                    }
                }
            }
        };

        private TextView createPageTextView(int pageNum) {
            TextView pageNumTextView = new TextView(getContext());
            pageNumTextView.setText("" + pageNum);//显示页码
            pageNumTextView.setTag(pageNum);
            pageNumTextView.setGravity(Gravity.CENTER);
            pageNumTextView.setTextColor(getContext().getResources().getColor(R.color.black_color));
            pageNumTextView.setBackgroundResource(R.drawable.drawable_gray_line);
            pageNumTextView.setOnClickListener(pageTvClicker);
            return pageNumTextView;
        }

    }
}
