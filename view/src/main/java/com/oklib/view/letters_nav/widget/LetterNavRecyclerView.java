package com.oklib.view.letters_nav.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by zz on 2016/8/17.
 */
public class LetterNavRecyclerView extends RecyclerView {

    private LinearLayoutManager mLinearLayoutManager;

    public LetterNavRecyclerView(Context context) {
        super(context);
        this.initRecyclerView(context);
    }

    public LetterNavRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initRecyclerView(context);
    }

    public LetterNavRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initRecyclerView(context);
    }


    private void initRecyclerView(Context context) {
        // init LinearLayoutManager
        this.mLinearLayoutManager = new LinearLayoutManager(context);
        // set the VERTICAL layout
        this.mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // set layout manager
        this.setLayoutManager(this.mLinearLayoutManager);
        // set item animator
        this.setItemAnimator(new DefaultItemAnimator());
        // keep recyclerview fixed size
        this.setHasFixedSize(true);
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return mLinearLayoutManager;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }
}
