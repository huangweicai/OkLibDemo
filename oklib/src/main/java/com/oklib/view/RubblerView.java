package com.oklib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.oklib.R;


/**
 * 创建时间：2017/5/2
 * 编写者：黄伟才
 * 功能描述：刮刮乐自定义view
 */

public class RubblerView extends View {

    private float TOUCH_TOLERANCE; // 填充距离，使线条更自然，柔和,值越小，越柔和。

    // private final int bgColor;
    // 位图
    private Bitmap mBitmap;

    private Bitmap mCoverBitmap; //覆盖图  刮奖钱的页面

    // 画布
    private Canvas mCanvas;
    // 画笔
    private Paint mPaint;
    private Path mPath;
    private float mX, mY;
    private Paint mTextPaint;
    private final int TEXT_SIZE = 60;
    private String mText;
    private boolean isDraw = false;
    private int WIDTH;
    private int HEIGHT;
    private int openSize;
    private Context mContext;
    private boolean mHasOpen = false;


    public RubblerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RubblerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public RubblerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.oklib_rubbler, defStyle, 0);
        int imageRes = array.getResourceId(R.styleable.oklib_rubbler_oklib_foreground, R.drawable.oklib_logo);

        //由于我们无法在代码里直接对资源文件作修改,故需要得到资源文件的副本
        //mCoverBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.oklib_logo).copy(Bitmap.Config.ARGB_8888, true);
        mCoverBitmap = BitmapFactory.decodeResource(getResources(), imageRes).copy(Bitmap.Config.ARGB_8888, true);

        mBitmap = Bitmap.createBitmap(mCoverBitmap.getWidth(),
                mCoverBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBitmap);

        WIDTH = mBitmap.getWidth();
        HEIGHT = mBitmap.getHeight();

        //mCanvas.drawColor(getContext().getResources().getColor(bgColorResource));
        mCanvas.drawBitmap(mCoverBitmap, 0, 0, new Paint());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isDraw) {
            canvas.drawText(mText, (mCoverBitmap.getWidth() - TEXT_SIZE * mText.length()) / 2,
                    (mCoverBitmap.getHeight() + TEXT_SIZE) / 2, mTextPaint); //绘制中奖文字
            mCanvas.drawPath(mPath, mPaint);
            canvas.drawBitmap(mBitmap, 0, 0, null); //绘制刮奖图层
        }
    }

    //解决自定义view wrap_content属性失效的问题
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = WIDTH;
        int desiredHeight = HEIGHT;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    /**
     * 开启檫除功能
     *
     * @param paintStrokeWidth 触点（橡皮）宽度
     * @param touchTolerance   填充距离,值越小，越柔和。
     */
    public void beginRubbler(final int paintStrokeWidth,
                             float touchTolerance, String text) {
        mText = text;
        TOUCH_TOLERANCE = touchTolerance;
        // 设置画笔
        mPaint = new Paint();
        // mPaint.setAlpha(0);
        // 画笔划过的痕迹就变成透明色了
        mPaint.setColor(Color.BLACK); // 此处不能为透明色
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 前圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 后圆角
        mPaint.setStrokeWidth(paintStrokeWidth); // 笔宽
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(TEXT_SIZE);

        // 痕迹
        mPath = new Path();
        isDraw = true;
        Thread thread = new Thread(mRunnable);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isDraw) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 触点按下
                touchDown(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE: // 触点移动
                touchMove(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP: // 触点弹起
                touchUp(event.getX(), event.getY());
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private void touchDown(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

    }

    private void touchUp(float x, float y) {
        mPath.lineTo(x, y);
        mCanvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {

            while (!mHasOpen) {

                SystemClock.sleep(100);

                float wipeArea = 0;
                float totalArea = WIDTH * HEIGHT;

                for (int i = 0; i < WIDTH; i++) {
                    for (int j = 0; j < HEIGHT; j++) {
                        int pixel = mBitmap.getPixel(i, j);
                        if (pixel == 0) {
                            openSize++;
                        }

                    }
                }
                //当刮开区域的像素占整个可刮区域的50%时,展示结果
                if (openSize * 100 / totalArea > 50) {
                    mHandler.sendEmptyMessage(0);
                }
                openSize = 0; //刮开区域归零
            }

        }
    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Toast.makeText(mContext, "已经刮开了", Toast.LENGTH_SHORT).show();
            mHasOpen = true;
        }
    };


}
