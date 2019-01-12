package com.oklib.window;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.oklib.window.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2017/8/19
 * 作者：蓝天
 * 描述：居中窗口列表
 */
public class CenterWinListDialog<T> extends BaseDialogFragment {
    private ListView lv_title;
    private ListAdapter listAdapter;
    private List<T> dataList = new ArrayList<>();

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
    }

    /**
     * 追加数据
     */
    public CenterWinListDialog addDataList(final List<T> datas) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (null == lv_title) {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != datas) {
                            dataList.clear();
                            dataList.addAll(datas);
                            listAdapter.notifyDataSetChanged();
                            if (dataList.size() > 7) {
                                setWHSize(getScreenWidth(getContext()) - dp2px(getContext(), 20), getScreenHeight(getContext()) * 2 / 3);
                            }
                        }
                    }
                });
            }
        }).start();
        return this;
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
            if (null != onGetViewListener) {
                return onGetViewListener.getView(position, convertView, parent, dataList);
            }
            return null;


        }
    }

    @Override
    public void initData(View view) {

    }

    @Override
    protected void initNet() {

    }

    public interface OnGetViewListener<T> {
        View getView(int position, View convertView, ViewGroup parent, List<T> dataList);
    }

    public OnGetViewListener onGetViewListener;

    public CenterWinListDialog setOnGetViewListener(OnGetViewListener _onGetViewListener) {
        onGetViewListener = _onGetViewListener;
        return this;
    }

}
