package com.oklib.win_lib;

import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.window.MultiSelectListPop;


/**
 * 时间：2017/8/17
 * 作者：蓝天
 * 描述：多选pop列表演示
 */

public class MultiSelectListPopActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_multi_select_listpop;
    }

    @Override
    protected void initVariable() {

    }

    private TextView tv_text;
    private StringBuilder stringBuilder;
    @Override
    protected void initView() {
        tv_text = findView(R.id.tv_text);
        stringBuilder = new StringBuilder();
    }

    @Override
    protected void initNet() {

    }

    public void doClick(View view) {
        String[] mLabels = new String[]{"标题1", "标题2", "标题3"};
        PopupWindow popupWindow = MultiSelectListPop.getInstance().create(this, true, mLabels);
        popupWindow.showAsDropDown(view);
        MultiSelectListPop.getInstance().updateBg(getResources().getColor(R.color.oklib_red));
        MultiSelectListPop.getInstance().updateGou(R.drawable.gougou_icon);
        MultiSelectListPop.getInstance().setOnMultiSelectClickListener(new MultiSelectListPop.OnMultiSelectClickListener() {
            @Override
            public void onMultiSelectClick(Integer... popPositions) {
                stringBuilder.setLength(0);
                for (int i = 0; i < popPositions.length; i++) {
                    Log.d("TAG", "popPositions:"+popPositions[i]);
                    stringBuilder.append(popPositions[i] + "、");
                }
                tv_text.setText(stringBuilder.toString());
            }
        });
    }
}
