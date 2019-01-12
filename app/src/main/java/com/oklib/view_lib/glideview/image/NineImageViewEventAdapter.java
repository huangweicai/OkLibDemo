//package com.oklib.view_lib.glideview.image;
//
//import android.content.Context;
//import android.view.View;
//
//import com.oklib.view_lib.glideview.view.ImageAttr;
//import com.oklib.view_lib.glideview.view.NineImageView;
//import com.oklib.view_lib.glideview.view.NineImageViewAdapter;
//
//import java.util.List;
//
//
//public class NineImageViewEventAdapter extends NineImageViewAdapter {
//
//    public NineImageViewEventAdapter(Context context, List<ImageAttr> imageAttr) {
//        super(context, imageAttr);
//    }
//
//    @Override
//    protected void onImageItemClick(Context context, NineImageView nineImageView, int index, List<ImageAttr> images) {
//        for (int i = 0; i < images.size(); i++) {
//            ImageAttr attr = images.get(i);
//            View imageView = nineImageView.getChildAt(i);
//            if (i >= nineImageView.getMaxSize()) {
//                imageView = nineImageView.getChildAt(nineImageView.getMaxSize() - 1);
//            }
//            attr.width = imageView.getWidth();
//            attr.height = imageView.getHeight();
//            int[] points = new int[2];
//            imageView.getLocationInWindow(points);
//            attr.left = points[0];
//            attr.top = points[1];
//        }
//
////        Intent intent = new Intent(context, ImagesActivity.class);
////        Bundle bundle = new Bundle();
////        bundle.putSerializable(ImagesActivity.IMAGE_ATTR, (Serializable) images);
////        bundle.putInt(ImagesActivity.CUR_POSITION, index);
////        intent.putExtras(bundle);
////        context.startActivity(intent);
////        ((Activity) context).overridePendingTransition(0, 0);
//    }
//
//}
