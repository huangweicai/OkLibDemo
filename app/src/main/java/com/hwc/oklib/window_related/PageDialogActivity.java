package com.hwc.oklib.window_related;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.util.FastJsonUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.widget.BOMCheckDialog;
import com.hwc.oklib.window.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2017/12/26
 * 作者：黄伟才
 * 描述：分页dialog
 */

public class PageDialogActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_confirm_dialog;
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
                        mBeans.add(new FunctionDetailBean("activity_confirm_dialog.xml", BASE_RES +"/layout/activity_confirm_dialog.xml"));
                        showDetail();
                    }
                });
    }

    String beanJson = "{\n" +
            "            \"MO_NAME\": \"SJ23274801\",\n" +
            "            \"MITEM_CODE\": \"12090000\",\n" +
            "            \"MITEM_DESC\": \"贴片电容 RoHS C-0603-224Z50-Y5V\",\n" +
            "            \"RATION\": 1,\n" +
            "            \"POSITION\": \"C25\"\n" +
            "    }";
    @Override
    protected void initView() {

        ((TextView)findView(R.id.tv_showDialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<BOMCheckDialog.BOMCheckBean> mlist = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    mlist.add(FastJsonUtil.json2Bean(beanJson, BOMCheckDialog.BOMCheckBean.class));
                }

                BOMCheckDialog bomCheckDialog = BOMCheckDialog.create(getSupportFragmentManager());
                bomCheckDialog.show();
                bomCheckDialog.setWorkOrderOrder("111111");
                bomCheckDialog.setProductCode("22222");
                bomCheckDialog.setMaterialDescription("描述描述描述");
                bomCheckDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                    @Override
                    public void confirm(View v) {
                        Toast.makeText(context, "当前勾选状态已经记录", Toast.LENGTH_SHORT).show();
                    }
                });

                bomCheckDialog.refreshData(mlist);
            }
        });

    }

    @Override
    protected void initNet() {

    }
}
