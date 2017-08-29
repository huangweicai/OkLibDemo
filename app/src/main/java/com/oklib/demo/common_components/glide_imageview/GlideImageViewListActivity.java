package com.oklib.demo.common_components.glide_imageview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oklib.demo.Common;
import com.oklib.demo.R;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.bean.FunctionDetailBean;
import com.oklib.demo.common_components.glide_imageview.image.NineImageViewEventAdapter;
import com.oklib.demo.common_components.glide_imageview.model.ImageEntity;
import com.oklib.demo.common_components.glide_imageview.model.ModelUtil;
import com.oklib.demo.common_components.glide_imageview.view.ImageAttr;
import com.oklib.demo.common_components.glide_imageview.view.NineImageView;
import com.oklib.view.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

import static com.oklib.demo.Common.BASE_RES;

/**
 * 时间：2017/8/28
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：GlideImageView列表显示
 */

public class GlideImageViewListActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_glide_imageview_list;
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
                        mBeans.add(new FunctionDetailBean("activity_glide_imageview_list.xml", BASE_RES +"/layout/activity_glide_imageview_list.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ModelUtil.getImages());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initNet() {

    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private List<ImageEntity> list;

        RecyclerViewAdapter(List<ImageEntity> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycle_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ImageEntity entity = list.get(position);

            viewHolder.tvTitle.setText(entity.getTitle());

            ArrayList<ImageAttr> imageAttrs = new ArrayList<>();
            for (String url : entity.getImages()) {
                ImageAttr attr = new ImageAttr();
                attr.url = url;
                imageAttrs.add(attr);
            }
            if (viewHolder.nineImageView.getAdapter() != null) {
                viewHolder.nineImageView.setAdapter(viewHolder.nineImageView.getAdapter());
            } else {
                viewHolder.nineImageView.setAdapter(new NineImageViewEventAdapter(viewHolder.nineImageView.getContext(), imageAttrs));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            NineImageView nineImageView;

            ViewHolder(View view) {
                super(view);
                tvTitle = (TextView) view.findViewById(R.id.tv_title);
                nineImageView = (NineImageView) view.findViewById(R.id.nineImageView);
            }
        }
    }
}
