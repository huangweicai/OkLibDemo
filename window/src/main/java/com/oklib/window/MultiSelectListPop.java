package com.oklib.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2017/2/8
 * 编写者：蓝天
 * 功能描述：多选择列表pop
 * 备注：支持多选列表pop，目前支持点击立即回调，如需要在窗口消失回调实现PopupWindow.OnDismissListener接口并在其中处理逻辑即可
 */

public class MultiSelectListPop {//implements PopupWindow.OnDismissListener

    private View contentView;
    private Context context;
    private PopupWindow popWindow;
    private boolean isMultiSelect = false;//是否多选状态
    private View itemView;

    private int bgId = -1;
    private int gouId = -1;

    public PopupWindow create(Context context, boolean isMultiSelect, String... list) {
        this.context = context;
        this.isMultiSelect = isMultiSelect;
        popWindow = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        contentView = View.inflate(context, R.layout.oklib_pop_multi_select_list_layout, null);
        contentView.setFocusableInTouchMode(true);//收敛焦点
        popWindow.setContentView(contentView);
        popWindow.setFocusable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //backgroundAlpha(0.68f);
        //popWindow.setOnDismissListener(this);

        initView(list);

        return popWindow;
    }


    public void updateBg(int bgId) {
        this.bgId = bgId;
    }

    public void updateBg(String bgColor) {
        this.bgId = Color.parseColor(bgColor);
    }

    public void updateGou(int gouId) {
        this.gouId = gouId;
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    private ListView lv_list;
    private String[] labels;

    private void initView(String... list) {
        this.labels = list;
        mList.clear();
        for (int i = 0; i < labels.length; i++) {
            if (i == 0) {
                mList.add(new DataBean(0xff000000, labels[i], false));
            } else {
                mList.add(new DataBean(0xff000000, labels[i], false));
            }
        }

        lv_list = findView(contentView, R.id.lv_list);
        lv_list.setAdapter(adapter);

        if (mList.size() > 6) {
            setListMaxShowCount(6);//默认显示6个
        }
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return labels.length;
        }

        @Override
        public Object getItem(int position) {
            return itemView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.oklib_pop_item_multi_select_list, parent, false);
                holder = new ViewHolder();
                holder.ll_item_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_layout);
                holder.tv_tip = (TextView) convertView.findViewById(R.id.tv_tip);
                holder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
                holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
                holder.iv_gou = (ImageView) convertView.findViewById(R.id.iv_gou);
                holder.v_line = convertView.findViewById(R.id.v_line);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            itemView = convertView;
            holder.processDate(position);
            return convertView;
        }
    };

    private class ViewHolder {
        private LinearLayout ll_item_layout;
        private TextView tv_tip;
        private RelativeLayout rl_item;
        private TextView tv_text;
        private ImageView iv_gou;
        private View v_line;

        private void processDate(final int position) {
            DataBean bean = mList.get(position);
            tv_text.setText(bean.text);
            tv_text.setTextColor(bean.color);

//            if (bgId != -1) {
//                GradientDrawable drawable = (GradientDrawable) tv_text.getBackground();
//                drawable.setColor(bgId);
//            }

            if (gouId != -1) {
                iv_gou.setImageResource(gouId);
            }

            if (bean.isSelect) {
                iv_gou.setVisibility(View.VISIBLE);
            } else {
                iv_gou.setVisibility(View.INVISIBLE);
            }

            if (position == 0) {
                rl_item.setBackgroundResource(R.drawable.oklib_selector_grey_tltr);
                v_line.setVisibility(View.VISIBLE);
                tv_tip.setVisibility(View.VISIBLE);
            } else if (position == labels.length - 1) {
                rl_item.setBackgroundResource(R.drawable.oklib_selector_grey_blbr);
                v_line.setVisibility(View.GONE);
                tv_tip.setVisibility(View.GONE);
            } else {
                if (position == 1) {
                    v_line.setVisibility(View.VISIBLE);
                } else {
                    v_line.setVisibility(View.GONE);
                }
                rl_item.setBackgroundResource(R.drawable.oklib_selector_grey1);
                tv_tip.setVisibility(View.GONE);
            }

            ll_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isMultiSelect) {
                        //多选状态
                        for (int i = 0; i < mList.size(); i++) {
                            DataBean bean = mList.get(i);
                            if (i == position) {
                                if (bean.isSelect) {
                                    bean.isSelect = false;
                                } else {
                                    bean.isSelect = true;
                                }
                            }
                        }

                        adapter.notifyDataSetChanged();

                        //回调
                        List<Integer> posList = new ArrayList<>();
                        for (int i = 0; i < mList.size(); i++) {
                            DataBean bean = mList.get(i);
                            if (bean.isSelect) {
                                posList.add(i);
                            }
                        }
                        if (null != onMultiSelectClickListener) {
                            onMultiSelectClickListener.onMultiSelectClick(posList.toArray(new Integer[posList.size()]));
                        }
                    } else {
                        if (null != onMultiSelectClickListener) {
                            popWindow.dismiss();
                            onMultiSelectClickListener.onMultiSelectClick(position);
                        }
                    }
                }
            });
        }
    }

    private List<DataBean> mList = new ArrayList<>();

    private class DataBean {
        private int color;
        private String text;
        private boolean isSelect;

        public DataBean(int color, String text, boolean isSelect) {
            this.color = color;
            this.text = text;
            this.isSelect = isSelect;
        }
    }

//    @Override
//    public void onDismiss() {
//        List<Integer> posList = new ArrayList<>();
//        for (int i = 0; i < mList.size(); i++) {
//            DataBean bean = mList.get(i);
//            if (bean.isSelect) {
//                posList.add(i);
//            }
//        }
//        if (null != onMultiSelectClickListener) {
//            onMultiSelectClickListener.onMultiSelectClick(posList.toArray(new Integer[posList.size()]));
//        }
//    }

    //更新宽度
    public void updateItemWidth(int width) {
//        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) lv_list.getLayoutParams();
//        if (width > 0) {
//            llParams.width = width;
//        }
//        lv_list.setLayoutParams(llParams);


        //可以根据position对某Item进行高度设置
//        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);//高度不能小于1,测试发现设置??0时不生效
//        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) itemView.getLayoutParams();
//        layoutParams.width = width;
//        itemView.setLayoutParams(layoutParams);
    }

    //设置最大显示条数
    public void setListMaxShowCount(int count) {
        int listViewHeight = getListViewHeight(lv_list, count);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lv_list.getLayoutParams();
        params.height = listViewHeight + 10;
        lv_list.setLayoutParams(params);
    }

    /**
     * 获取某个数量的高??
     *
     * @param listView
     */
    public int getListViewHeight(ListView listView, int count) {
        if (listView == null)
            return 0;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(50, 50);//置空，注意：这里的listview的子项的??大布??必须是LinearLayout布局
            totalHeight += listItem.getMeasuredHeight(); //循环得到listview的所有item高度的???和
        }
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }

    private int[] getMaxMin(List<Integer> list) {
        int i, min, max;
        min = max = list.get(0);
        for (i = 0; i < list.size(); i++) {
            if (list.get(i) > max)
                max = list.get(i);
            if (list.get(i) < min)
                min = list.get(i);
        }
        return new int[]{max, min};
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnMultiSelectClickListener {
        void onMultiSelectClick(Integer... popPositions);
    }

    private OnMultiSelectClickListener onMultiSelectClickListener;

    public void setOnMultiSelectClickListener(OnMultiSelectClickListener _onMultiSelectClickListener) {
        this.onMultiSelectClickListener = _onMultiSelectClickListener;
    }

    protected <T> T findView(View view, int viewId) {
        return (T) view.findViewById(viewId);
    }

    protected void showToast(String msg) {
        if (null != context) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private static MultiSelectListPop cardOperatePop;

    private MultiSelectListPop() {
    }

    //唯一实例入口
    public static MultiSelectListPop getInstance() {
        if (null == cardOperatePop) {
            synchronized (MultiSelectListPop.class) {
                if (null == cardOperatePop) {
                    cardOperatePop = new MultiSelectListPop();
                }
            }
        }
        return cardOperatePop;
    }
}
