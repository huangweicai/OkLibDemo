package com.oklib.view_lib.drag_item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * RecyclerView 基础adapter，结合{@link BaseRecyclerHolder}使用<br>
 * 设置数据源 {@link #setData(List)}<br>
 * 设置item点击监听器 {@link #setOnItemClickListener(OnItemClickListener)}<br>
 * 已实现 getItemCount，返回data的数量，有需要则重载<br>
 * 已实现 onBindViewHolder，如果ViewHolder是BaseRecyclerHolder，则调用 {@link BaseRecyclerHolder#bind(Context, int, Object)}<br>
 * 已实现 onViewRecycled，如果ViewHolder是BaseRecyclerHolder，则调用 {@link BaseRecyclerHolder#recycle()}<br>
 *
 * @author Storm
 */
@SuppressWarnings("unchecked")
public abstract class BaseRecyclerAdapter<T> extends Adapter<RecyclerView.ViewHolder> {

    protected Context context;

    protected LayoutInflater inflater;

    /** item点击监听器 */
    protected OnItemClickListener onItemClickListener;

    /** 数据源 */
    protected List<T> data;

    public BaseRecyclerAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置数据源，并调用{@link #notifyDataSetChanged()}
     */
    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 设置item点击监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    /**
     * 返回item点击监听器
     */
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public T getItem(int position) {
        return getItemCount() > position ? data.get(position) : null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseRecyclerHolder<?>) {
            ((BaseRecyclerHolder<T>) holder).bind(context, position, getItem(position));
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof BaseRecyclerHolder<?>) {
            ((BaseRecyclerHolder<T>) holder).recycle();
        }
    }

    /**
     * RecyclerView 基础ViewHolder<br>
     * 调用绑定，设置item点击监听器
     */
    public abstract static class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

        public BaseRecyclerHolder(View view, final OnItemClickListener onItemClickListener) {
            super(view);
            if (onItemClickListener != null) {
                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(itemView, getLayoutPosition(), 0);
                    }
                });
            }
        }

        /**
         * 绑定数据源
         *
         * @param context
         * @param position
         * @param item
         */
        public abstract void bind(Context context, int position, T item);

        /**
         * 回收
         */
        public void recycle() {
        }

    }

    /**
     * RecyclerView 特殊ViewHolder<br>
     * 设置item点击监听器
     */
    public static class BaseSpecialHolder extends RecyclerView.ViewHolder {

        public BaseSpecialHolder(View view, final OnItemClickListener onItemClickListener) {
            super(view);
            if (onItemClickListener != null) {
                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(itemView, getLayoutPosition(), 0);
                    }
                });
            }
        }

        public BaseSpecialHolder(View view, final OnItemClickListener onItemClickListener, final int position) {
            super(view);
            if (onItemClickListener != null) {
                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(itemView, position, 0);
                    }
                });
            }
        }
    }

    /**
     * item点击监听器
     */
    public interface OnItemClickListener {

        /**
         * item点击
         *
         * @param view     item view
         * @param position item position
         * @param flag     自定义参数。例如一个item里有子view可点击，此时可用flag区分
         * @param params   自定义参数。
         */
        public void onItemClick(View view, int position, int flag, Object... params);
    }

    /**
     * item长按监听器
     */
    public interface OnItemLongClickListener {

        /**
         * item长按
         *
         * @param view     item view
         * @param position item position
         * @param flag     自定义参数。例如一个item里有子view可点击，此时可用flag区分
         * @param params   自定义参数。
         */
        public void onItemLongClick(View view, int position, int flag, Object... params);
    }

}
