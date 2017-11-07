package com.oklib.view.letters_nav.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.view.letters_nav.bean.LettersNavBean;

import java.util.List;


/**
 * 创建时间: 2017/5/23
 * 编写者：黄伟才
 * 功能描述：字母导航
 */
public class LettersNavRcvAdapter extends BaseSortRecyclerViewAdapter<LettersNavBean, BaseRecyclerViewHolder> {

    private Context context;

    public LettersNavRcvAdapter(Context ctx, List<LettersNavBean> mDatas) {
        super(ctx, mDatas);
        this.context = ctx;
    }

    //return your itemView layoutRes id
    @Override
    public int getItemLayoutId() {
        return R.layout.oklib_letters_nav_list_item;
    }

    //bloodpressure_add a header ,optional, if no need, return 0
    @Override
    public int getHeaderLayoutId() {
        return 0;//如需使用头部布局，这里添加
    }

    //bloodpressure_add a footer, optional, if no need, return 0
    @Override
    public int getFooterLayoutId() {
        return 0;//如需使用尾部布局，这里添加
    }

    @Override
    public BaseRecyclerViewHolder getViewHolder(View itemView, int type) {
        switch (type) {
            case BaseSortRecyclerViewAdapter.TYPE_HEADER:
                return new HeaderHolder(itemView);
            case BaseSortRecyclerViewAdapter.TYPE_FOOT:
                return new FooterHolder(itemView);
        }
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerViewHolder holder, final int position) {

        if (holder instanceof ItemViewHolder) {
            final int mPos = position - getHeadViewSize();
            if (mPos < mDatas.size()) {
                initLetter(holder, mPos);
                ((ItemViewHolder) holder).processData(mPos);
                initClickListener(holder, mPos);
            }
        } else if (holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).processData();
        } else if (holder instanceof FooterHolder) {
            ((FooterHolder) holder).tvFoot.setText(getContentCount() + "条记录");
        }

    }

    public class ItemViewHolder extends BaseRecyclerViewHolder {
        private TextView tv_drug_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_drug_name = (TextView) itemView.findViewById(R.id.tv_drug_name);
        }

        private void processData(int mPos) {
            setText(tv_drug_name, mDatas.get(mPos).getLettersNavTitle());
        }

        protected void setText(View view, String text) {
            if (view instanceof TextView) {
                ((TextView) view).setText(""+text);
            } else if (view instanceof Button) {
                ((TextView) view).setText("" + text);
            } else {
                //还有如下追加
            }
        }

    }

    public class HeaderHolder extends BaseRecyclerViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }

        private void processData() {
        }
    }

    public static class FooterHolder extends BaseRecyclerViewHolder {

        protected TextView tvFoot;

        public FooterHolder(View itemView) {
            super(itemView);
            //tvFoot = (TextView) itemView.findViewById(R.id.tv_foot);
        }
    }

}
