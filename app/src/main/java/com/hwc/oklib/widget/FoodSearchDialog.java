package com.hwc.oklib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hwc.oklib.R;
import com.hwc.oklib.util.WidgetUtil;
import com.hwc.oklib.view.EditTextWithDelete;
import com.hwc.oklib.window.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 创建时间：2017/6/2
 * 编写者：黄伟才
 * 功能描述：搜索dialog
 * 备注：显示item需要根据需求自定义布局即可
 */

public class FoodSearchDialog extends BaseDialogFragment implements TextView.OnEditorActionListener, View.OnClickListener {
    private EditTextWithDelete et_search_content;
    private TextView tv_cancel;
    private ListView lv_list;
    private TextView tv_report;

    private List<String> dataList = new ArrayList<>();
    private String highlightTag = "";//不能为null

    @Override
    public float initDimValue() {
        return 0.5f;
    }

    @Override
    public void initOnResume() {
        setWHSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean isCancel() {
        return false;
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
        return R.layout.dialog_food_search;
    }

    @Override
    protected void argumentsDate() {

    }

    @Override
    public void initActionBarView(View view) {

    }

    @Override
    public void initView(View view) {
        et_search_content = (EditTextWithDelete) view.findViewById(R.id.et_search_content);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_report = (TextView) view.findViewById(R.id.tv_report);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        lv_list.setAdapter(searchAdapter);
        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                //注意：当条目显示不到底部时，上滑下滑会触发加载
                switch (i) {
                    case SCROLL_STATE_IDLE:
                        isSlideLastItem = isListViewReachBottomEdge(absListView);

                        if (isSlideLastItem) {
                            //到尾部了，加载更多
                            pageIndex += PAGE_COUNT;
                            recipeQuery(true);
                        }

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });

        tv_cancel.setOnClickListener(this);
        tv_report.setOnClickListener(this);
        et_search_content.addTextChangedListener(textWatcher);
        et_search_content.setOnEditorActionListener(this);
        et_search_content.setFilters(new InputFilter[]{filter});

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //子线程
                InputMethodManager m = (InputMethodManager) et_search_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);

    }

    private boolean isSlideLastItem;

    public boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }

    @Override
    public void initData(View view) {

    }


    @Override
    protected void initNet() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel://取消
                dismiss();
                break;
            case R.id.tv_report://汇报
                if (null != onReportListener) {
                    onReportListener.onReport(view, highlightTag);
                }
                break;
        }
    }

    //搜索输入框监听
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (null == s) return;
            highlightTag = s.toString();
            pageIndex = 0;//每次搜索，条目初始化为0
            //菜谱搜索
            recipeQuery(false);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String regEx = "[`~!@#$%^&*()+=～|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？|^a-zA-Z0-9_|]";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        }
    };


    private int pageIndex = 0;//条目下标
    private final int PAGE_COUNT = 15;//每页条数

    //菜谱搜索
    private void recipeQuery(final boolean isLoadMore) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //模拟数据源
                List<String> foodSearchList = new ArrayList<>();
                foodSearchList.add("鱼头豆腐汤");
                foodSearchList.add("鱼香肉丝");
                foodSearchList.add("手撕包菜");
                foodSearchList.add("酸菜鱼");
                foodSearchList.add("娃娃鱼肉汤");

                if (!isLoadMore) {
                    dataList.clear();
                }
                dataList.addAll(foodSearchList);
                searchAdapter.notifyDataSetChanged();
            }
        }, 500);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH://搜索

                break;
        }
        return true;
    }

    private BaseAdapter searchAdapter = new BaseAdapter() {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                final LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.food_search_item, parent, false);
                holder = new ViewHolder();
                holder.tv_food_name = (TextView) convertView.findViewById(R.id.tv_food_name);
                holder.tv_confirm = (TextView) convertView.findViewById(R.id.tv_confirm);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String foodName = dataList.get(position);
            holder.tv_food_name.setText(foodName);
            holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != confirmListener) {
                        confirmListener.confirm(v);
                    }
                }
            });
            WidgetUtil.changeTextStyle(holder.tv_food_name, foodName, highlightTag, 0xff000000, true);
            return convertView;
        }
    };

    private static class ViewHolder {
        TextView tv_food_name;
        TextView tv_confirm;
    }

    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private static FoodSearchDialog dialog;

    @SuppressLint("ValidFragment")
    private FoodSearchDialog() {
    }

    /**
     * 显示dialog
     */
    public static FoodSearchDialog create(FragmentManager _fm) {
        fm = _fm;
        dialog = new FoodSearchDialog();
        return dialog;
    }

    public void show() {
        ft = fm.beginTransaction();
        dialog.show(ft, "");
    }

    /**
     * 多个回调这里追加
     */
    public interface OnReportListener {
        void onReport(View v, String reportText);
    }

    public OnReportListener onReportListener;

    public void setOnOnReportListener(OnReportListener _onReportListener) {
        onReportListener = _onReportListener;
    }
}
