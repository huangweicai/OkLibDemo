package com.hwc.oklib.window_related;

import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.window.MultiSelectListPop;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/17
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
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

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_multi_select_listpop.xml", BASE_RES +"/layout/activity_multi_select_listpop.xml"));
                        showDetail();
                    }
                });
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
