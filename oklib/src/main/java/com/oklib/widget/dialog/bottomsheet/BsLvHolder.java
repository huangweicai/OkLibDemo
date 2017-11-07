package com.oklib.widget.dialog.bottomsheet;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.widget.dialog.adapter.SuperLvHolder;



public class BsLvHolder extends SuperLvHolder<BottomSheetBean> {
    public ImageView ivIcon;
    public TextView mTextView;

    public BsLvHolder(Context context){
        super(context);
        ivIcon = (ImageView) rootView.findViewById(R.id.iv_icon);
        mTextView = (TextView) rootView.findViewById(R.id.tv_msg);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.oklib_item_bottomsheet_lv;
    }

    @Override
    public void assingDatasAndEvents(Context context, BottomSheetBean bean) {
        if (bean.icon<=0){
            ivIcon.setVisibility(View.GONE);
        }else {
            ivIcon.setImageResource(bean.icon);
            ivIcon.setVisibility(View.VISIBLE);
        }

        mTextView.setText(bean.text);

       /* rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }
}
