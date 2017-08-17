package com.oklib.demo.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.oklib.base.BaseDialogFragment;
import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.WebViewActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.util.SysShareUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建时间：2017/2/15
 * 编写者：黄伟才
 * 功能描述：居中窗口列表
 */

public class CenterWinListDialog extends BaseDialogFragment implements AdapterView.OnItemClickListener {
    private ListView lv_title;
    private ListAdapter listAdapter;
    private List<FunctionDetailBean> dataList = new ArrayList<>();
    private int topMargin = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != onItemClickListener) {
            onItemClickListener.onItemClick(view, position);
        }
    }

    @Override
    public float initDimValue() {
        return 0.8f;
    }

    @Override
    public void initOnResume() {
        setWHSize(getScreenWidth(getContext()) * 8 / 9, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        return R.layout.item_common_dialog_list;
    }

    @Override
    protected void argumentsDate() {

    }

    @Override
    public void initActionBarView(View view) {

    }

    @Override
    public void initView(View view) {
        topMargin = dp2px(getContext(), 15);

        lv_title = findView(view, R.id.lv_title);
        lv_title.setAdapter(listAdapter = new ListAdapter());
        lv_title.setOnItemClickListener(this);
    }

    /**
     * 追加数据
     */
    public void addDataList(final FunctionDetailBean... titles) {
        addDataList(getActivity(), titles);
    }
    //注意：多个窗口切换显示，使用该方法，getActivity()在多次切换容易null
//    public void addDataList(final Activity activity, final FunctionDetailBean... titles) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (null == lv_title) {
//                }
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (null != titles) {
//                            dataList.clear();
//                            dataList.addAll(Arrays.asList(titles));
//                            listAdapter.notifyDataSetChanged();
//                            if (dataList.size() > 7) {
//                                setWHSize(getScreenWidth(getContext()) * 4 / 5, getScreenWidth(getContext()));
//                            }
//                        }
//                    }
//                });
//            }
//        }).start();
//    }
    public void addDataList(final Context context, final FunctionDetailBean... titles) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == lv_title) {
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != titles) {
                            dataList.clear();
                            dataList.addAll(Arrays.asList(titles));
                            listAdapter.notifyDataSetChanged();
                            if (dataList.size() > 7) {
                                setWHSize(getScreenWidth(getContext()) * 4 / 5, getScreenWidth(getContext()));
                            }
                        }
                    }
                });
            }
        }).start();
    }


    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private static CenterWinListDialog dialog;
    @SuppressLint("ValidFragment")
    private CenterWinListDialog() {}

    /**
     * 显示dialog
     */
    public static CenterWinListDialog create(FragmentManager _fm) {
        fm = _fm;
        dialog = new CenterWinListDialog();
        return dialog;
    }

    public void show() {
        ft = fm.beginTransaction();
        dialog.show(ft, "");
    }

    private class ViewHolder {
        private TextView tv_name;//类名或相关资源名
        private TextView tv_send_url;//发送手机内应用
        private TextView tv_directly_open;//直接打开
    }

    private class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_center_win_list, parent, false);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_send_url = (TextView) view.findViewById(R.id.tv_send_url);
                holder.tv_directly_open = (TextView) view.findViewById(R.id.tv_directly_open);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            final FunctionDetailBean bean = dataList.get(position);
            holder.tv_name.setText(bean.getName());
            holder.tv_send_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //发送手机内应用
                    SysShareUtil.getInstance().shareText(getContext(), bean.getUrl());
                }
            });
            holder.tv_directly_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //直接打开
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra(Common.TITLE, bean.getName());
                    intent.putExtra(Common.URL, bean.getUrl());
                    getContext().startActivity(intent);
                }
            });
            return view;
        }
    }

    @Override
    public void initData(View view) {

    }

    @Override
    protected void initNet() {

    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        onItemClickListener = _onItemClickListener;
    }
}
