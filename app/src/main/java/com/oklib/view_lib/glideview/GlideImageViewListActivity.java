//package com.oklib.view_lib.glideview;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.oklib.R;
//import com.oklib.base.BaseAppActivity;
//import com.oklib.view_lib.glideview.image.NineImageViewEventAdapter;
//import com.oklib.view_lib.glideview.model.ImageEntity;
//import com.oklib.view_lib.glideview.model.ModelUtil;
//import com.oklib.view_lib.glideview.view.ImageAttr;
//import com.oklib.view_lib.glideview.view.NineImageView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
///**
// * 时间：2017/8/28
// * 作者：蓝天
// * 描述：GlideImageView列表显示
// */
//
//public class GlideImageViewListActivity extends BaseAppActivity {
//    @Override
//    protected int initLayoutId() {
//        return R.layout.activity_glide_imageview_list;
//    }
//
//    @Override
//    protected void initView() {
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ModelUtil.getImages());
//        recyclerView.setAdapter(adapter);
//    }
//
//    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
//
//        private List<ImageEntity> list;
//
//        RecyclerViewAdapter(List<ImageEntity> list) {
//            this.list = list;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycle_view, viewGroup, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, int position) {
//            ImageEntity entity = list.get(position);
//
//            viewHolder.tvTitle.setText(entity.getTitle());
//
//            ArrayList<ImageAttr> imageAttrs = new ArrayList<>();
//            for (String url : entity.getImages()) {
//                ImageAttr attr = new ImageAttr();
//                attr.url = url;
//                imageAttrs.add(attr);
//            }
//            if (viewHolder.nineImageView.getAdapter() != null) {
//                viewHolder.nineImageView.setAdapter(viewHolder.nineImageView.getAdapter());
//            } else {
//                viewHolder.nineImageView.setAdapter(new NineImageViewEventAdapter(viewHolder.nineImageView.getContext(), imageAttrs));
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            TextView tvTitle;
//            NineImageView nineImageView;
//
//            ViewHolder(View view) {
//                super(view);
//                tvTitle = (TextView) view.findViewById(R.id.tv_title);
//                nineImageView = (NineImageView) view.findViewById(R.id.nineImageView);
//            }
//        }
//    }
//}
