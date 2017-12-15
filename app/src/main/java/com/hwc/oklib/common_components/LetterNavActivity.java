package com.hwc.oklib.common_components;

import android.view.View;
import android.widget.TextView;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.letters_nav.LetterSideBar;
import com.hwc.oklib.view.letters_nav.OnLetterTouchListener;
import com.hwc.oklib.view.letters_nav.adapter.BaseSortRecyclerViewAdapter;
import com.hwc.oklib.view.letters_nav.adapter.LettersNavRcvAdapter;
import com.hwc.oklib.view.letters_nav.bean.LettersNavBean;
import com.hwc.oklib.view.letters_nav.widget.LetterNavRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2017/8/20
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：字母导航·RecyclerView
 */

public class LetterNavActivity extends BaseAppActivity implements BaseSortRecyclerViewAdapter.OnRecyclerViewClickListener, OnLetterTouchListener {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_letter_nav;
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
                        mBeans.add(new FunctionDetailBean("activity_letter_nav.xml", BASE_RES +"/layout/activity_letter_nav.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {
        initLettersNav();
    }

    private LetterNavRecyclerView rvLettersNavList;
    private List<LettersNavBean> mDatas;
    private LettersNavRcvAdapter adapter;
    private LetterSideBar sideBar;
    private TextView tvDialog;

    private void initLettersNav() {
        tvDialog = (TextView) findViewById(R.id.tv_dialog);
        sideBar = (LetterSideBar) findViewById(R.id.sidebar);
        rvLettersNavList = (LetterNavRecyclerView) findViewById(R.id.rvLettersNavList);
        rvLettersNavList.setAdapter(adapter = new LettersNavRcvAdapter(this, mDatas = new ArrayList<>()));

        //请求接口，刷新本地数据源
        List<LettersNavBean> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(new LettersNavBean("张三"));
        medicalRecordsList.add(new LettersNavBean("李四"));
        medicalRecordsList.add(new LettersNavBean("王五"));
        medicalRecordsList.add(new LettersNavBean("赵六"));
        medicalRecordsList.add(new LettersNavBean("飞散"));
        medicalRecordsList.add(new LettersNavBean("胡歌"));
        medicalRecordsList.add(new LettersNavBean("德华"));
        medicalRecordsList.add(new LettersNavBean("国荣"));
        medicalRecordsList.add(new LettersNavBean("菲菲"));
        updateList(medicalRecordsList);


        //item点击回调，可选
        adapter.setRecyclerViewClickListener(this);//依赖于implements BaseSortRecyclerViewAdapter.OnRecyclerViewClickListener

        //字母导航触碰事件回调，可选
        sideBar.setLetterTouchListener(rvLettersNavList, adapter, tvDialog, this);//依赖于implements OnLetterTouchListener
    }

    //刷新本地数据源
    private void updateList(List<LettersNavBean> medicalRecordsList) {
        mDatas = medicalRecordsList;
        adapter.updateRecyclerView(mDatas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLetterTouch(String letter, int position) {

    }

    @Override
    public void onActionUp() {

    }

    @Override
    public void onClick(View itemView, int pos) {

    }
}
