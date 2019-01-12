package com.oklib.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * 创建时间：2016/5/17
 * 编写者：蓝天
 * 功能描述：封装自己的刷新加载容器
 */
public class CommonRefreshLayout extends SwipeRefreshLayout {
    protected CustomRecycleView customRecycleView;
    private Context context;

    /**
     * 标记是否正在加载更多，防止再次调用加载更多接口
     */
    private boolean mIsLoadingMore;
    /**
     * 标记加载更多的position
     */
    private int mLoadMorePosition;

    public CommonRefreshLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CommonRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        customRecycleView = new CustomRecycleView(context);
        addView(customRecycleView);

        //头部刷新
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != mRefreshListener) {
                    mRefreshListener.onRefresh();
                }
                //恢复可以加载更多状态
                mIsLoadingMore = false;
            }
        });

        //滚动监听
        customRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //接口对象、标记是否正在加载更多，防止再次调用加载更多接口、垂直方向滚动>0
                if (null != mRefreshListener && !mIsLoadingMore && dy > 0) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition + 1 == customRecycleView.getAdapter().getItemCount()) {
                        //真实最后一个position
                        setLoadingMore(true);
                        mLoadMorePosition = lastVisiblePosition;
                        mRefreshListener.onLoadMore();
                    }
                }
            }

        });
    }

    /**
     * 动画颜色
     */
    public void setAnimColor(int... colorResIds) {
        this.setColorSchemeResources(colorResIds);
    }

    /**
     * 能否执行刷新动画
     */
    public void setAnimEnabled(boolean animEnabled) {
        this.setEnabled(animEnabled);
    }

    /**
     * 获取列表对象
     */
    public CustomRecycleView getRecycleView() {
        return customRecycleView;
    }

    /**
     * 设置适配器
     */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        customRecycleView.setAdapter(adapter);
    }

    /**
     * 滚动操作
     *
     * @param x
     * @param y
     */
    public void smoothScrollBy(int x, int y) {
        customRecycleView.smoothScrollBy(0, 0);
    }

    /**
     * 列表
     *
     * @param orientation
     */
    public void setLayoutManager(int orientation) {
        customRecycleView.setLayoutManager(orientation);
    }

    /**
     * 网格
     *
     * @param orientation
     * @param spanCount   列数
     */
    public void setLayoutManager(int orientation, int spanCount) {
        customRecycleView.setLayoutManager(orientation, spanCount, null);
    }

    /**
     * 网格
     *
     * @param orientation
     * @param spanCount   列数
     * @param lookup      横跨列数
     */
    public void setLayoutManager(int orientation, int spanCount, GridLayoutManager.SpanSizeLookup lookup) {
        customRecycleView.setLayoutManager(orientation, spanCount, lookup);
    }


    /**
     * 是否停止刷新动画
     * false：关闭动画
     * true：显示动画
     */
    public void isShowRefreshAnim(boolean refreshing) {
        isShowRefreshAnim(refreshing, -1);
    }

    /**
     * 重载方法
     *
     * @param refreshing
     * @param offsetHeight 偏移高度
     */
    public void isShowRefreshAnim(boolean refreshing, int offsetHeight) {
        if (offsetHeight > 0)
            setProgressViewOffset(false, 0, offsetHeight);
        setRefreshing(refreshing);
    }

    /**
     * 加载完成
     */
    public void loadFinish() {
        customRecycleView.getAdapter().notifyItemRemoved(mLoadMorePosition);
        //先移除了item，然后恢复没有正在加载的状态
        mIsLoadingMore = false;//false是显示加载更多，触发监听方法
    }


    /**
     * 获取第一条展示的位置
     *
     * @return
     */
    private int getFirstVisiblePosition() {
        int position;
        if (customRecycleView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) customRecycleView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (customRecycleView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) customRecycleView.getLayoutManager()).findFirstVisibleItemPosition();
        } else if (customRecycleView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) customRecycleView.getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }


    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (customRecycleView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) customRecycleView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (customRecycleView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) customRecycleView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (customRecycleView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) customRecycleView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = customRecycleView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }


    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    /**
     * 设置正在加载更多
     *
     * @param loadingMore
     */
    public void setLoadingMore(boolean loadingMore) {
        this.mIsLoadingMore = loadingMore;
    }


    /**
     * 数据刷新、加载监听
     */
    public interface OnRefreshListener {
        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();
    }

    private OnRefreshListener mRefreshListener;

    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

}
