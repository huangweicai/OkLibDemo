package com.oklib.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建时间：2017/4/10
 * 编写者：黄伟才
 * 功能描述：封装RecycleView
 */
public class CustomRecycleView extends RecyclerView {
    private Context context;

    public CustomRecycleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    /**
     * 初始化，如需自定义如下属性覆盖即可
     */
    private void init() {

        //设置item项动画效果
        setItemAnimator(new DefaultItemAnimator());

        //默认布局管理设置，默认垂直
        setLayoutManager(LinearLayoutManager.VERTICAL);

        //可以提高效率
        setHasFixedSize(true);
    }

    /**
     * 列表
     *
     * @param orientation
     */
    public void setLayoutManager(int orientation) {
        //布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //垂直方向
        manager.setOrientation(orientation);
        manager.setSmoothScrollbarEnabled(true);
        //设置管理器
        setLayoutManager(manager);
    }

    /**
     * 网格
     *
     * @param orientation
     * @param spanCount   列数
     */
    public void setLayoutManager(int orientation, int spanCount) {
        setLayoutManager(orientation, spanCount, null);
    }

    /**
     * 网格
     *
     * @param orientation
     * @param spanCount   列数
     * @param lookup      横跨列数
     */
    public void setLayoutManager(int orientation, int spanCount, GridLayoutManager.SpanSizeLookup lookup) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount <= 1 ? 2 : spanCount);
        manager.setOrientation(orientation);
        manager.setSmoothScrollbarEnabled(true);
        setLayoutManager(manager);

        if (null != lookup) {
            //特殊item设置全宽度   合并的列数
            manager.setSpanSizeLookup(lookup);
        }
    }

    public void setFulSpanLayoutParams(View view) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
    }


    /**
     * 网格布局头部，尾部，特殊item设置全宽度
     * 参考样例
     */
    public class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private int mSpanSize;//跨度大小，使用传入的列数来对比
        private Integer[] specialPositions;//特殊位置

        //暂时只支持4个特殊位置
        private int pos1, pos2, pos3;
        public int lastPos;

        public SpanSizeLookup(int columns, Integer[] _specialPositions) {
            mSpanSize = columns;
            specialPositions = _specialPositions;

            if (specialPositions.length >= 4) {
                pos1 = specialPositions[0];
                pos2 = specialPositions[1];
                pos3 = specialPositions[2];
                lastPos = specialPositions[3];
            }

        }

        @Override
        public int getSpanSize(int position) {
            if (position == pos1 || position == pos2 || position == pos3 || position == lastPos) {
                //符合特殊位置，跨屏幕宽
                return mSpanSize;
            } else {
                return 1;//跨1列，等于没有跨
            }
        }

    }
}
