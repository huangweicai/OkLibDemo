//package com.oklib.view_lib.glideview;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.ActivityOptionsCompat;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
//import com.bumptech.glide.request.RequestOptions;
//import com.oklib.R;
//import com.oklib.base.BaseAppActivity;
//import com.oklib.glideview.GlideImageLoader;
//import com.oklib.glideview.GlideImageView;
//import com.oklib.glideview.ShapeImageView;
//import com.oklib.glideview.progress.OnGlideImageViewListener;
//import com.oklib.glideview.progress.OnProgressListener;
//import com.oklib.view.progress.CircleProgressView;
//import com.oklib.view_lib.glideview.image.SingleImageActivity;
//
//import java.util.Random;
//
//
///**
// * 时间：2017/8/2
// * 作者：蓝天
// * 描述：GlideImageView使用演示
// */
//
//public class GlideImageViewActivity extends BaseAppActivity {
//    private GlideImageView image00;
//    private GlideImageView image01;
//    private GlideImageView image02;
//    private GlideImageView image03;
//
//    private GlideImageView image10;
//    private GlideImageView image11;
//    private GlideImageView image12;
//    private GlideImageView image13;
//
//    private GlideImageView image20;
//    private GlideImageView image21;
//    private GlideImageView image22;
//    private GlideImageView image23;
//
//    private GlideImageView image30;
//    private GlideImageView image31;
//    private GlideImageView image32;
//    private GlideImageView image33;
//
//    private GlideImageView image41;
//    private CircleProgressView progressView1;
//    private GlideImageView image42;
//    private CircleProgressView progressView2;
//    public static final String KEY_IMAGE_URL = "image_url";
//    public static final String KEY_IMAGE_URL_THUMBNAIL = "image_url_thumbnail";
//
//    public static boolean isLoadAgain = false; // Just for fun when oklib_loading images!
//
//    public static final String cat = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/cat.jpg";
//    public static final String cat_thumbnail = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/cat_thumbnail.jpg";
//
//    public static final String girl = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/girl.jpg";
//    public static final String girl_thumbnail = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/girl_thumbnail.jpg";
//
//    @Override
//    protected int initLayoutId() {
//        return R.layout.activity_glide_imageview;
//    }
//
//    @Override
//    protected void initView() {
////        ImageView iv_image = (ImageView) findViewById(R.id.iv_image);
////        ImageLoader.getInstance().loadImage(iv_image, url,
////                new LoaderOptions.Builder().build());
//
//        image00 = (GlideImageView) findViewById(R.id.image00);
//        image01 = (GlideImageView) findViewById(R.id.image01);
//        image02 = (GlideImageView) findViewById(R.id.image02);
//        image03 = (GlideImageView) findViewById(R.id.image03);
//
//        image10 = (GlideImageView) findViewById(R.id.image14);
//        image11 = (GlideImageView) findViewById(R.id.image11);
//        image12 = (GlideImageView) findViewById(R.id.image12);
//        image13 = (GlideImageView) findViewById(R.id.image13);
//
//        image20 = (GlideImageView) findViewById(R.id.image24);
//        image21 = (GlideImageView) findViewById(R.id.image21);
//        image22 = (GlideImageView) findViewById(R.id.image22);
//        image23 = (GlideImageView) findViewById(R.id.image23);
//
//        image30 = (GlideImageView) findViewById(R.id.image34);
//        image31 = (GlideImageView) findViewById(R.id.image31);
//        image32 = (GlideImageView) findViewById(R.id.image32);
//        image33 = (GlideImageView) findViewById(R.id.image33);
//
//        image41 = (GlideImageView) findViewById(R.id.image41);
//        image42 = (GlideImageView) findViewById(R.id.image42);
//        progressView1 = (CircleProgressView) findViewById(R.id.progressView1);
//        progressView2 = (CircleProgressView) findViewById(R.id.progressView2);
//
//        line0();
//        line1();
//        line2();
//        line3();
//        line41();
//        line42();
//    }
//
//    @Override
//    protected void initNet() {
//
//    }
//
//    String url = "http://img5.imgtn.bdimg.com/it/u=194685546,499818437&fm=26&gp=0.jpg";
//    private void line0() {
//        //矩形+遮罩层
//        image00.loadImage(url, R.mipmap.oklib_maotouying_icon).listener(new OnProgressListener() {
//            @Override
//            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
//                Log.d("--->image11", "bytesRead: " + bytesRead + " totalBytes: " + totalBytes + " isDone: " + isDone);
//            }
//        });
//        //矩形
//        image02.setPressedAlpha(0f);
//        image01.loadImage(url, R.mipmap.oklib_maotouying_icon).listener(new OnProgressListener() {
//            @Override
//            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
//                Log.d("--->image11", "bytesRead: " + bytesRead + " totalBytes: " + totalBytes + " isDone: " + isDone);
//            }
//        });
//        //圆形+模糊+遮罩层
//        image02.setPressedAlpha(0.3f);
//        image02.loadBlurCircleImage(url, R.mipmap.oklib_maotouying_icon, 13, 2).listener(new OnProgressListener() {
//            @Override
//            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
//                Log.d("--->image11", "bytesRead: " + bytesRead + " totalBytes: " + totalBytes + " isDone: " + isDone);
//            }
//        });
//        //圆形+模糊
//        image03.setPressedAlpha(0f);
//        image03.loadBlurCircleImage(url, R.mipmap.oklib_maotouying_icon, 13, 2).listener(new OnProgressListener() {
//            @Override
//            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
//                Log.d("--->image11", "bytesRead: " + bytesRead + " totalBytes: " + totalBytes + " isDone: " + isDone);
//            }
//        });
//    }
//
//    private String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497688355699&di=ea69a930b82ce88561c635089995e124&imgtype=0&src=http%3A%2F%2Fcms-bucket.nosdn.127.net%2Ff84e566bcf654b3698363409fbd676ef20161119091503.jpg";
//    private String url2 = "http://img1.imgtn.bdimg.com/it/u=4027212837,1228313366&fm=23&gp=0.jpg";
//    private String gif1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496754078616&di=cc68338a66a36de619fa11d0c1b2e6f3&imgtype=0&src=http%3A%2F%2Fapp.576tv.com%2FUploads%2Foltz%2F201609%2F25%2F1474813626468299.gif";
//    private String gif2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497276275707&di=57c8c7917e91afc1bc86b1b57e743425&imgtype=0&src=http%3A%2F%2Fimg.haatoo.com%2Fpics%2F2016%2F05%2F14%2F9%2F4faf3f52b8e8315af7a469731dc7dce5.jpg";
//    private String gif3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497276379533&di=71435f66d66221eb36dab266deb9d6d2&imgtype=0&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201608%2F02%2F190418bmy9zqm94qxlmqf4.gif";
//
//    private void line1() {
//        image11.loadImage(url1, R.color.placeholder_color).listener(new OnProgressListener() {
//            @Override
//            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
//                Log.d("--->image11", "bytesRead: " + bytesRead + " totalBytes: " + totalBytes + " isDone: " + isDone);
//            }
//        });
//
//        image12.setBorderWidth(3);
//        image12.setBorderColor(R.color.transparent20);
//        image12.loadCircleImage(url1, R.color.placeholder_color);
//
//        image13.setRadius(15);
//        image13.setBorderWidth(3);
//        image13.setBorderColor(R.color.oklib_blue);
//        image13.setPressedAlpha(0.3f);
//        image13.setPressedColor(R.color.oklib_blue);
//        image13.loadImage(url1, R.color.placeholder_color);
//
//        image10.setShapeType(ShapeImageView.ShapeType.CIRCLE);
//        image10.setBorderWidth(3);
//        image10.setBorderColor(R.color.orange);
//        image10.setPressedAlpha(0.2f);
//        image10.setPressedColor(R.color.orange);
//        image10.loadImage(url1, R.color.placeholder_color);
//    }
//
//    private void line2() {
//        image21.loadImage(url2, R.color.placeholder_color);
//        image22.loadImage(url2, R.color.placeholder_color);
//        image23.loadImage(url2, R.color.placeholder_color);
//        image20.loadImage(url2, R.color.placeholder_color);
//    }
//
//    private void line3() {
//        image31.loadLocalImage(R.drawable.gif_robot_walk, R.mipmap.ic_launcher);
//
//        image32.loadCircleImage(gif1, R.mipmap.ic_launcher).listener(new OnGlideImageViewListener() {
//            @Override
//            public void onProgress(int percent, boolean isDone, GlideException exception) {
//                Log.d("--->image32", "percent: " + percent + " isDone: " + isDone);
//            }
//        });
//
//        image33.loadImage(gif2, R.mipmap.ic_launcher);
//        image30.loadImage(gif3, R.mipmap.ic_launcher);
//    }
//
//
//    private void line41() {
//        image41.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //转场动画
//                Intent intent = new Intent(context, SingleImageActivity.class);
//                intent.putExtra(KEY_IMAGE_URL, cat);
//                intent.putExtra(KEY_IMAGE_URL_THUMBNAIL, cat_thumbnail);
//                ActivityOptionsCompat compat = ActivityOptionsCompat
//                        .makeSceneTransitionAnimation((Activity) context, image41, "the image of transition");
//                ActivityCompat.startActivity(context, intent, compat.toBundle());
//            }
//        });
//
//        RequestOptions requestOptions = image41.requestOptions(R.color.placeholder_color).centerCrop();
//        if (isLoadAgain) {
//            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
//        }
//
//        // 第一种方式加载
//        image41.load(cat_thumbnail, requestOptions).listener(new OnGlideImageViewListener() {
//            @Override
//            public void onProgress(int percent, boolean isDone, GlideException exception) {
//                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
//                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                }
//                progressView1.setProgress(percent);
//                progressView1.setVisibility(isDone ? View.GONE : View.VISIBLE);
//            }
//        });
//
//        isLoadAgain = new Random().nextInt(3) == 1;
//    }
//
//
//
//    private void line42() {
//        image42.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            Intent intent = new Intent(context, SingleImageActivity.class);
//            intent.putExtra(KEY_IMAGE_URL, girl);
//            intent.putExtra(KEY_IMAGE_URL_THUMBNAIL, girl_thumbnail);
//            ActivityOptionsCompat compat = ActivityOptionsCompat
//                    .makeSceneTransitionAnimation((Activity) context, image42, "the image of transition");
//            ActivityCompat.startActivity(context, intent, compat.toBundle());
//            }
//        });
//
//        RequestOptions requestOptions = image42.requestOptions(R.color.placeholder_color).centerCrop();
//        if (isLoadAgain) {
//            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);
//        }
//
//        // 第二种方式加载：可以解锁更多功能
//        GlideImageLoader imageLoader = image42.getImageLoader();
//        imageLoader.setOnGlideImageViewListener(girl_thumbnail, new OnGlideImageViewListener() {
//            @Override
//            public void onProgress(int percent, boolean isDone, GlideException exception) {
//                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
//                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                }
//                progressView2.setProgress(percent);
//                progressView2.setVisibility(isDone ? View.GONE : View.VISIBLE);
//            }
//        });
//        imageLoader.requestBuilder(girl_thumbnail, requestOptions)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(image42);
//    }
//}
