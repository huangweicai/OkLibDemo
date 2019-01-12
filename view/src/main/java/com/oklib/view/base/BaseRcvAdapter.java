package com.oklib.view.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oklib.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2016/5/27
 * 编写者：蓝天
 * 功能描述：Rcv适配器基类
 */
public abstract class BaseRcvAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //默认存在尾部布局码值
    public final int FOOTER_TYPE = -100;
    //没数据类型
    public final int EXTRA_NODATA_TYPES = -101;
    //正常列表item类型
    public final int ITEM_TYPE = -102;

    //推荐使用的默认加载值，尽量避免因为条目过少，导致列表最后一项无法超过屏幕底部，会导致加载更多回调问题，框架设计暂时无法有效解决
    //推荐方案：1.尽可能增加加载条目数，使得最后一条超过屏幕底部即可 2.如无法超过屏幕，配置通过点击最后item进行更多数据加载
    public final int LOAD_NUM = 15;

    //上下文
    public Context context;

    //默认存在尾部item或空数据item，该属性定义多个额外的不依赖数据源的item，如一个尾部，两个头部则 EXTRA_TYPES = 3
    public int EXTRA_TYPES = 1;

    //能否执行数据填充
    public boolean canFillingData = false;

    //数据源
    public final List<T> dataList = new ArrayList<>();

    public BaseRcvAdapter(Context context) {
        this.context = context;
    }

    public BaseRcvAdapter(Context context, List<T> _list) {
        this.context = context;
        dataList.addAll(_list == null ? new ArrayList<T>() : _list);
    }

    /**
     * 刷新数据源·提供多种操作数据方法
     */
    public void addDataList(List<T> _list) {
        dataList.addAll(_list == null ? new ArrayList<T>() : _list);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据源·提供多种操作数据方法，指定插入下标
     */
    public void addDataList(int index, List<T> _list) {
        dataList.addAll(index, _list == null ? new ArrayList<T>() : _list);
        notifyDataSetChanged();
    }

    /**
     * 追加单个bean
     *
     * @param bean
     */
    public void addDataItem(T bean) {
        dataList.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 根据位置加入bean
     *
     * @param position
     * @param bean
     */
    public void addDataItem(int position, T bean) {
        dataList.add(position, bean);
        notifyDataSetChanged();
    }

    public void removeDataItem(int position) {
        dataList.remove(position);
        notifyDataSetChanged();
    }
    public void removeDataItem(Object bean) {
        dataList.remove(bean);
        notifyDataSetChanged();
    }

    /**
     * 返回数据源
     *
     * @return
     */
    public List<T> getDataList() {
        return dataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 1) {
            if (getItemCount() - 1 == position) {
                return FOOTER_TYPE;
            } else {
                return ITEM_TYPE;
            }
        } else {
            return EXTRA_NODATA_TYPES;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //空数据或最后一项不执行数据填充
        if (getItemCount() > 1) {
            if (getItemCount() - 1 == position) {
                canFillingData = false;
            } else {
                canFillingData = true;
            }
        } else {
            canFillingData = false;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + EXTRA_TYPES;
    }

    private ImageView iv_empty;
    private TextView tv_empty;

    public class EmptyDataViewHolder extends RecyclerView.ViewHolder {
        public EmptyDataViewHolder(View itemView) {
            super(itemView);
            iv_empty = (ImageView) itemView.findViewById(R.id.iv_empty);
            tv_empty = (TextView) itemView.findViewById(R.id.tv_empty);
            iv_empty.setVisibility(View.INVISIBLE);
            tv_empty.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 无数据显示状态，请求失败或者网络异常
     */
    public void setEmptyDataState(final int resId, final String emptyText) {
        if (null == iv_empty || null == tv_empty) {
            emptyDataStateRunnable = new Runnable() {
                int count = 0;
                @Override
                public void run() {
                    if (null == iv_empty || null == tv_empty) {
                        count++;
                        if (count == 10) {
                            handler.removeCallbacks(emptyDataStateRunnable);
                            emptyDataStateRunnable = null;
                            count = 0;
                        }else{
                            handler.postDelayed(this, 200);
                        }
                    }else{
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_empty.setImageResource(resId);
                                tv_empty.setText(emptyText);
                                if (getItemCount() > 1) {
                                    iv_empty.setVisibility(View.INVISIBLE);
                                    tv_empty.setVisibility(View.INVISIBLE);
                                } else {
                                    iv_empty.setVisibility(View.VISIBLE);
                                    tv_empty.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        handler.removeCallbacks(emptyDataStateRunnable);
                        emptyDataStateRunnable = null;
                    }
                }
            };
            handler.postDelayed(emptyDataStateRunnable, 200);
        }else{
            iv_empty.setImageResource(resId);
            tv_empty.setText(emptyText);
            if (getItemCount() > 1) {
                iv_empty.setVisibility(View.INVISIBLE);
                tv_empty.setVisibility(View.INVISIBLE);
            } else {
                iv_empty.setVisibility(View.VISIBLE);
                tv_empty.setVisibility(View.VISIBLE);
            }
        }

    }


    private ProgressBar pb_load_bar;
    private TextView tv_load_text;

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(final View itemView) {
            super(itemView);
            //注意：这些布局对象只会在调用onCreateView时回调一次，以后就复用对象
            pb_load_bar = (ProgressBar) itemView.findViewById(R.id.pb_load_bar);
            tv_load_text = (TextView) itemView.findViewById(R.id.tv_load_text);
            setLoadState(isLoading, loadText);
            if (null == pb_load_bar || null == tv_load_text) {
                footerViewHolderRunnable = new Runnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        if (null == pb_load_bar || null == tv_load_text) {
                            count++;
                            if (count == 10) {
                                handler.removeCallbacks(footerViewHolderRunnable);
                                footerViewHolderRunnable = null;
                                count = 0;
                            }else{
                                handler.postDelayed(this, 200);
                            }
                        }else{
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (null != onFootItemClickListener) {
                                                onFootItemClickListener.onFootItemClick(itemView, getItemCount() - 1);
                                            }
                                        }
                                    });
                                }
                            });
                            handler.removeCallbacks(footerViewHolderRunnable);
                            footerViewHolderRunnable = null;
                        }
                    }
                };
                handler.postDelayed(footerViewHolderRunnable, 200);
            }else{
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != onFootItemClickListener) {
                            onFootItemClickListener.onFootItemClick(itemView, getItemCount() - 1);
                        }
                    }
                });
            }

        }
    }

    /**
     * 是否是加载状态
     *
     * @param isLoading
     */
    public void setLoadState(final boolean isLoading) {
        setLoadState(isLoading, isLoading ? "加载中···" : "没有更多数据了");
    }
    private boolean isLoading = true;
    private String loadText = "加载中···";
    public void setLoadState(final boolean isLoading, final String loadText) {
        this.isLoading = isLoading;
        this.loadText = loadText;
        if (null == pb_load_bar || null == tv_load_text) {
            loadStateRunnable = new Runnable() {
                int count = 0;
                @Override
                public void run() {
                    if (null == pb_load_bar || null == tv_load_text) {
                        count++;
                        if (count == 10) {
                            handler.removeCallbacks(loadStateRunnable);
                            loadStateRunnable = null;
                            count = 0;
                        }else{
                            handler.postDelayed(this, 200);
                        }
                    }else{
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isLoading) {
                                    pb_load_bar.setVisibility(View.VISIBLE);
                                    tv_load_text.setText(loadText);
                                } else {
                                    pb_load_bar.setVisibility(View.GONE);
                                    tv_load_text.setText(loadText);
                                }
                            }
                        });
                        handler.removeCallbacks(loadStateRunnable);
                        loadStateRunnable = null;
                    }
                }
            };
            handler.postDelayed(loadStateRunnable, 200);
        }else{
            if (isLoading) {
                pb_load_bar.setVisibility(View.VISIBLE);
                tv_load_text.setText(loadText);
            } else {
                pb_load_bar.setVisibility(View.GONE);
                tv_load_text.setText(loadText);
            }
        }
    }

    //延迟处理
    private Handler handler = new Handler();
    private Runnable loadStateRunnable;
    private Runnable footerViewHolderRunnable;
    private Runnable emptyDataStateRunnable;


    //如下是常用接口配置
    private OnFootItemClickListener onFootItemClickListener;

    public void setOnFootItemClickListener(OnFootItemClickListener onFootItemClickListener) {
        this.onFootItemClickListener = onFootItemClickListener;
    }

    public interface OnFootItemClickListener {
        void onFootItemClick(View v, int position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
