package com.oklib.view.letters_nav.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.view.letters_nav.bean.SortModel;
import com.oklib.view.letters_nav.utils.CharacterParser;
import com.oklib.view.letters_nav.utils.LetterUtil;
import com.oklib.view.letters_nav.utils.PinyinComparator;

import java.util.Collections;
import java.util.List;

/**
 * Created by zz on 2016/8/15.
 */
public abstract class BaseSortListViewAdapter<T extends SortModel, K extends BaseViewHolder> extends BaseAdapter implements SectionIndexer {

    /**
     * chinese to pinyin
     */
    public CharacterParser characterParser;
    public PinyinComparator pinyinComparator;

    protected LayoutInflater inflater;
    protected List<T> mDatas;

    public BaseSortListViewAdapter(Context ctx, List<T> datas) {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(ctx);
        generateLetters();
    }

    public void updateListView(List<T> t) {
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

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas == null ? null : mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        K viewHolder = null;
        if (view == null) {
            view = inflater.inflate(getLayoutId(), viewGroup, false);
            viewHolder = getViewHolder(view);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_letter);
            view.setTag(viewHolder);
        } else {
            viewHolder = (K) view.getTag();
        }
        bindValues(viewHolder, i);
        setVisibleLetters(i, viewHolder.tvLetter);
        return view;
    }

    public abstract int getLayoutId();

    public abstract K getViewHolder(View view);

    public abstract void bindValues(K viewHolder, int position);

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
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

    public int getPositionForSection(char sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
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


    @Override
    public int getSectionForPosition(int i) {
        return mDatas.get(i).getSortLetters() == null ? -1 : mDatas.get(i).getSortLetters().charAt(0);
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


    protected void setVisibleLetters(int position, TextView tvLetter) {
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(mDatas.get(position).getSortLetters());
        } else {
            tvLetter.setVisibility(View.GONE);
        }
    }
}
