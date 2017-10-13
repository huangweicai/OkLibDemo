package com.oklib.view.letters_nav.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oklib.R;


/**
 * Created by zz on 2016/8/17.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView tvLetter;
    public View rootView;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        tvLetter = (TextView) itemView.findViewById(R.id.tv_letter);
    }

}
