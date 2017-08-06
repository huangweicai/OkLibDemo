package com.oklib.demo.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.oklib.demo.AppOkLib;
import com.squareup.picasso.Callback;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;


/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/oklib
 * 描述：
 */

public class PicassoLoader implements ILoaderProxy {
    private volatile static Picasso sPicassoSingleton;
    private final String PICASSO_CACHE = "picasso-cache";
    private static LruCache sLruCache = new LruCache(AppOkLib.application);

    protected PicassoLoader() {

    }

    private static Picasso getPicasso() {
        if (sPicassoSingleton == null) {
            synchronized (PicassoLoader.class) {
                if (sPicassoSingleton == null) {
                    sPicassoSingleton = new Picasso.Builder(AppOkLib.application).memoryCache(sLruCache).build();
                }
            }
        }
        return sPicassoSingleton;
    }


    @Override
    public void loadImage(View view, String path, LoaderOptions options) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            RequestCreator requestCreator = getPicasso().load(path);
            loadOptions(requestCreator, options).into(imageView);
        }
    }

    @Override
    public void loadImage(View view, int drawable, LoaderOptions options) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            RequestCreator requestCreator = getPicasso().load(drawable);
            loadOptions(requestCreator, options).into(imageView);
        }
    }

    @Override
    public void loadImage(View view, File file, LoaderOptions options) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            RequestCreator requestCreator = getPicasso().load(file);
            loadOptions(requestCreator, options).into(imageView);
        }
    }

    @Override
    public void saveImage(String url, final File destFile, final Callback callback) {
        getPicasso().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                //FileUtil.saveBitmap(bitmap, destFile.getAbsolutePath());
                AppOkLib.application.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destFile)));
                //ToastUtils.showWarnMsg("图片保存成功！");
                if (callback != null) {
                    callback.onSuccess();
                }
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                //ToastUtils.showWarnMsg("图片保存失败！");
                if (callback != null) {
                    callback.onError();
                }
            }

//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                //ToastUtils.showWarnMsg("图片保存失败！");
//                if (callback != null) {
//                    callback.onError(e);
//                }
//            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    @Override
    public void clearMemoryCache() {
        sLruCache.clear();
    }

    @Override
    public void clearDiskCache() {
        File diskFile = new File(AppOkLib.application.getCacheDir(), PICASSO_CACHE);
        if (diskFile.exists()) {
            //FileUtil.deleteFile(diskFile);
        }
    }

    private RequestCreator loadOptions(RequestCreator requestCreator, LoaderOptions options) {
        if (options == null) {
            return requestCreator;
        }
        if (options.targetHeight > 0 && options.targetWidth > 0) {
            requestCreator.resize(options.targetWidth, options.targetHeight);
        }
        if (options.isCenterInside) {
            requestCreator.centerInside();
        } else if (options.isCenterCrop) {
            requestCreator.centerCrop();
        }
        if (options.config != null) {
            requestCreator.config(options.config);
        }
        if (options.errorResId != 0) {
            requestCreator.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            requestCreator.placeholder(options.placeholderResId);
        }
        if (options.bitmapAngle != 0) {
            requestCreator.transform(new PicassoTransformation(options.bitmapAngle));
        }
        return requestCreator;
    }

    class PicassoTransformation implements Transformation {
        private float bitmapAngle;

        protected PicassoTransformation(float corner){
            this.bitmapAngle = corner;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            float roundPx = bitmapAngle;//角度
            Bitmap output = Bitmap.createBitmap(source.getWidth(),
                    source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, source.getWidth(),source.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rect, paint);
            source.recycle();
            return output;
        }

        @Override
        public String key() {
            return "bitmapAngle()";
        }
    }

}
