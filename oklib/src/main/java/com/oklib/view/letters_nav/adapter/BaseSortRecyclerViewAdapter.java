package com.oklib.view.letters_nav.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oklib.view.letters_nav.bean.SortModel;
import com.oklib.view.letters_nav.utils.CharacterParser;
import com.oklib.view.letters_nav.utils.LetterUtil;
import com.oklib.view.letters_nav.utils.PinyinComparator;

import java.util.Collections;
import java.util.List;


/**
 * Created by zz on 2016/8/17.
 */
public abstract class BaseSortRecyclerViewAdapter<T extends SortModel, K extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<K> {

    public static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;

    private int headViewSize = 0;
    private int footViewSize = 0;
    private View header;
    private View footer;

    protected LayoutInflater inflater;
    protected List<T> mDatas;
    public CharacterParser characterParser;
    public PinyinComparator pinyinComparator;

    protected OnRecyclerViewClickListener clickListener;

    public BaseSortRecyclerViewAdapter(Context ctx, List<T> mDatas) {
        inflater = LayoutInflater.from(ctx);
        this.mDatas = mDatas;
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        generateLetters();
        addHeaderAndFooter();
    }

    //set click event
    public void setRecyclerViewClickListener(OnRecyclerViewClickListener listener) {
        this.clickListener = listener;
    }

    private void addHeaderAndFooter() {
        if (getHeaderLayoutId() > 0) {
            //add header (optional)
            View header = inflater.inflate(getHeaderLayoutId(), null);
            addHeadView(header);
        }
        if (getFooterLayoutId() > 0) {
            //add footer (optional)
            View footer = inflater.inflate(getFooterLayoutId(), null);
            addFootView(footer);
        }
    }

    public void updateRecyclerView(List<T> t) {
        this.mDatas = t;
        generateLetters();
        notifyDataSetChanged();
    }

    private void generateLetters() {
        if (mDatas != null) {
            for (T t : mDatas) {
                setSortLetters(t, LetterUtil.getSortValue(t));
            }
            Collections.sort(mDatas, pinyinComparator);
        }
    }

    protected void setSortLetters(SortModel sortModel, String sortName) {
        if (sortName != null) {
            String pinyin = characterParser.getSelling(sortName);
            if (pinyin != null && pinyin.length() > 0) {
                String sortString = pinyin.substring(0, 1).toUpperCase();
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                } else {
                    sortModel.setSortLetters("#");
                }
            } else {
                sortModel.setSortLetters("#");
            }
        }
    }

    //must add to method
    public void initLetter(BaseRecyclerViewHolder holder, int position) {
        holder.tvLetter.setText(mDatas.get(position).getSortLetters());
        setVisibleLetters(position, holder.tvLetter);
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_HEADER:
                itemView = header;
                break;
            case TYPE_ITEM:
                itemView = inflater.inflate(getItemLayoutId(), parent, false);
                break;
            case TYPE_FOOT:
                itemView = footer;
                break;
        }
        return getViewHolder(itemView, viewType);
    }


    public abstract int getItemLayoutId();

    public abstract int getHeaderLayoutId();

    public abstract int getFooterLayoutId();

    public abstract K getViewHolder(View itemView, int type);

    @Override
    public int getItemCount() {
        return mDatas == null ? (headViewSize + footViewSize) : (mDatas.size() + headViewSize + footViewSize);
    }

    public int getContentCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public int getPositionForSection(char sectionIndex) {
        for (int i = 0; i < getContentCount(); i++) {
            String sortStr = mDatas.get(i).getSortLetters();
            if (sortStr != null) {
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == sectionIndex) {
                    return i + headViewSize;
                }
            }
        }
        return -1;
    }

    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getContentCount(); i++) {
            String sortStr = mDatas.get(i).getSortLetters();
            if (sortStr != null) {
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getSectionForPosition(int i) {
        return mDatas.get(i).getSortLetters() == null ? -1 : mDatas.get(i).getSortLetters().charAt(0);
    }

    protected void setVisibleLetters(int position, TextView tvLetter) {
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(mDatas.get(position).getSortLetters());
        } else {
            tvLetter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (headViewSize == 1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footViewSize == 1 && position == getItemCount() - 1) {
            type = TYPE_FOOT;
        }
        return type;
    }

    private void addHeadView(View view) {
        header = view;
        headViewSize = 1;
    }

    private void addFootView(View view) {
        footer = view;
        footViewSize = 1;
    }

    public int getFootViewSize() {
        return footViewSize;
    }

    public int getHeadViewSize() {
        return headViewSize;
    }


    public interface OnRecyclerViewClickListener {
        void onClick(View itemView, int pos);
    }

    public void initClickListener(final BaseRecyclerViewHolder holder, final int mPos) {
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onClick(holder.rootView, mPos);
                }
            }
        });
    }

}