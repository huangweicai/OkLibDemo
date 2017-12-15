package com.hwc.oklib.widget;

import android.annotation.SuppressLint;
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

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.WebViewActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.util.SysShareUtil;
import com.hwc.oklib.window.base.BaseDialogFragment;

import java.util.ArrayList;
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
        if (dataList.size() > 7) {
            setWHSize(getScreenWidth(getContext()) - dp2px(getContext(), 20), getScreenHeight(getContext()) * 2 / 3);
        }else{
            setWHSize(getScreenWidth(getContext()) - dp2px(getContext(), 20), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
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
        return R.layout.oklib_item_common_dialog_list;
    }

    @Override
    protected void argumentsDate() {

    }

    @Override
    public void initActionBarView(View view) {

    }

    @Override
    public void initView(View view) {
        lv_title = findView(view, R.id.lv_title);
        lv_title.setAdapter(listAdapter = new ListAdapter());
        lv_title.setOnItemClickListener(this);
    }

    private List<FunctionDetailBean> titles;

    /**
     * 追加数据
     */
    public void addDataList(final List<FunctionDetailBean> titles) {
        this.titles = titles;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == lv_title) {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != titles) {
                            dataList.clear();
                            dataList.addAll(titles);
                            listAdapter.notifyDataSetChanged();
                            if (dataList.size() > 7) {
                                setWHSize(getScreenWidth(getContext()) - dp2px(getContext(), 20), getScreenHeight(getContext()) * 2 / 3);
                            }
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onStop() {
        super.onStop();
        titles.clear();
    }

    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private static CenterWinListDialog dialog;

    @SuppressLint("ValidFragment")
    private CenterWinListDialog() {
    }

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
        private TextView tv_browse;//浏览
    }

    private class ListAdapter extends BaseAdapter {

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
                holder.tv_browse = (TextView) view.findViewById(R.id.tv_browse);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            final FunctionDetailBean bean = dataList.get(position);
            holder.tv_name.setText(bean.getName());
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //直接打开
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra(Common.TITLE, bean.getName());
                    intent.putExtra(Common.URL, bean.getUrl());
                    intent.putExtra(WebViewActivity.IS_SHOW_WEB_URL, true);
                    getContext().startActivity(intent);
                }
            });
            holder.tv_browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //发送，分享
                    SysShareUtil.getInstance().shareText(getContext(), bean.getUrl());
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
