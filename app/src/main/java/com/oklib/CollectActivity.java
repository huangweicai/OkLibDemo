package com.oklib;

import android.view.View;

import com.oklib.adapter.MainRvAdapter;
import com.oklib.base.BaseAppActivity;
import com.oklib.bean.MainBean;
import com.oklib.view.CommonRefreshLayout;
import com.oklib.view.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2018/6/16
 * 作者：蓝天
 * 描述：我的收藏
 */
public class CollectActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initTitle() {
        CommonToolBar toolBar = findViewById(R.id.toolBar);
        toolBar.setCenterTitle("我的收藏", 18, R.color.white)
                .setImmerseState(this).setNavIcon(R.drawable.white_back_icon)
                .setNavigationListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
    }

    private CommonRefreshLayout rv_layout;
    private MainRvAdapter adapter;
    private List<MainBean> mList = new ArrayList<>();
    private boolean isRefreshState = true;

    @Override
    protected void initView() {
        rv_layout = findViewById(R.id.rv_layout);
        rv_layout.setAdapter(adapter = new MainRvAdapter(context, true));
        rv_layout.setAnimEnabled(false);
//        rv_layout.setOnRefreshListener(this);
//        adapter.setOnFootItemClickListener(this);

        mList = MainBean.listAll(MainBean.class);
        processList();
    }

    private void processList() {
        if (isRefreshState) {
            //清空数据源
            adapter.getDataList().clear();
        }
        //停止刷新动画
        rv_layout.isShowRefreshAnim(false);
        //追加数据源
        adapter.addDataList(mList);
        //少于默认加载数则没有认为是没有更多数据了
        if (mList.size() < adapter.LOAD_NUM) {//默认加载条数15，这个条数应该同请求一次的条数一致
            //完成加载更多状态，更新文本
            adapter.setLoadState(false, "没有更多数据了");
        } else {
            //必须，能再次触发加载更多，更新可以加载状态
            rv_layout.loadFinish();
        }
        //必须，无数据显示状态，请求失败或者网络异常，更新显示效果
        //备选：R.mipmap.no_net_icon, "暂无网络"
        adapter.setEmptyDataState(R.mipmap.oklib_rv_refresh_component_no_data_icon, "暂无数据");
    }
}
