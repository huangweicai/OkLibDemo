package com.oklib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.oklib.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

 /**
   * 时间：2017/8/2
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：自定义键盘，随机键盘位置
   */
public class KeyBoardCustomRandom extends View {

    private Context mContext;
    private float mLineWidth;
    private int mLineColor;
    private float mNumbersSize;
    private int mNumbersColor;
    private int mNumbersNormalBgColor;
    private int mNumbersPressBgColor;
    private int mDeleteNormalBgColor;
    private int mDeletePressBgColor;
    private int mScreenWidth;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private float mLineWidthHalf;
    private float mCellWidth;
    private float mCellHeight;
    private float mBaselineY;
    private String[] numberArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    private List<String> mNumberList;
    private InputListener mListener;
    private int mCurrentIndex = -1;
    private boolean mRandomKeyBoard;

    public KeyBoardCustomRandom(Context context) {
        this(context, null);
    }

    public KeyBoardCustomRandom(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public KeyBoardCustomRandom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initParam();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.oklib_MagicKeyBoard);
        mRandomKeyBoard = typedArray.getBoolean(R.styleable.oklib_MagicKeyBoard_oklib_randomKeyBoard, false);
        mLineWidth = typedArray.getDimension(R.styleable.oklib_MagicKeyBoard_oklib_lineWidth, dp2px(1));
        mLineColor = typedArray.getColor(R.styleable.oklib_MagicKeyBoard_oklib_lineColor, Color.parseColor("#aa888888"));
        mNumbersSize = typedArray.getDimension(R.styleable.oklib_MagicKeyBoard_oklib_numbersSize, dp2px(18));
        mNumbersColor = typedArray.getColor(R.styleable.oklib_MagicKeyBoard_oklib_numbersColor, Color.parseColor("#000000"));
        mNumbersNormalBgColor = typedArray.getColor(R.styleable.oklib_MagicKeyBoard_oklib_numbersNormalBgColor, Color.parseColor("#ffffff"));
        mNumbersPressBgColor = typedArray.getColor(R.styleable.oklib_MagicKeyBoard_oklib_numbersPressBgColor, Color.parseColor("#bfbfbf"));
        mDeleteNormalBgColor = typedArray.getColor(R.styleable.oklib_MagicKeyBoard_oklib_deleteNormalBgColor, Color.parseColor("#E0E0E0"));
        mDeletePressBgColor = typedArray.getColor(R.styleable.oklib_MagicKeyBoard_oklib_deletePressBgColor, Color.parseColor("#bfbfbf"));
        mLineWidthHalf = mLineWidth / 2;
    }

    private void initParam() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mNumberList = Arrays.asList(numberArray);
        if (mRandomKeyBoard) {
            Collections.shuffle(mNumberList);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                heightSize = dp2px(250);
                break;
        }
        setMeasuredDimension(mScreenWidth, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mCellWidth = (mWidth - mLineWidth * 2) / 3;
        mCellHeight = (mHeight - mLineWidth * 4) / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mNumbersNormalBgColor);
        drawSplitLine(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        if (mCurrentIndex != -1) {
            drawPressBg(canvas);
        }
        drawOneRawElement(canvas);
        drawTwoRawElement(canvas);
        drawThreeRawElement(canvas);
        drawBottomCenterElement(canvas);
        drawBottomRightElement(canvas);
    }

    private void drawPressBg(Canvas canvas) {
        switch (mCurrentIndex) {
            case 0:
            case 1:
            case 2:
                canvas.save();
                canvas.clipRect((mCellWidth + mLineWidth) * mCurrentIndex, mLineWidth, mCellWidth + (mCellWidth + mLineWidth) * mCurrentIndex, mLineWidth + mCellHeight);
                canvas.drawColor(mNumbersPressBgColor);
                canvas.restore();
                break;
            case 3:
            case 4:
            case 5:
                canvas.save();
                canvas.clipRect((mCellWidth + mLineWidth) * (mCurrentIndex - 3), mLineWidth + mCellHeight + mLineWidth, mCellWidth + (mCellWidth + mLineWidth) * (mCurrentIndex - 3), (mLineWidth + mCellHeight) * 2);
                canvas.drawColor(mNumbersPressBgColor);
                canvas.restore();
                break;
            case 6:
            case 7:
            case 8:
                canvas.save();
                canvas.clipRect((mCellWidth + mLineWidth) * (mCurrentIndex - 6), mLineWidth + (mCellHeight + mLineWidth) * 2, (mCellWidth + mLineWidth) * (mCurrentIndex - 6) + mCellWidth, (mLineWidth + mCellHeight) * 3);
                canvas.drawColor(mNumbersPressBgColor);
                canvas.restore();
                break;
            case 9:
            case 10:
                canvas.save();
                canvas.clipRect((mCellWidth + mLineWidth) * (mCurrentIndex - 8), mLineWidth + (mCellHeight + mLineWidth) * 3, (mCellWidth + mLineWidth) * (mCurrentIndex - 8) + mCellWidth, (mLineWidth + mCellHeight) * 4);
                canvas.drawColor(mDeletePressBgColor);
                canvas.restore();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x;
        int y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                findIndex(x, y);
                break;
            case MotionEvent.ACTION_UP:
                mCurrentIndex = -1;
                postInvalidate();
                break;
        }
        return true;
    }

    /**
     * 根据按下的坐标查找集合中的索引（列的形式）
     *
     * @param x
     * @param y
     */
    private void findIndex(int x, int y) {
        int whichX = x / (int) (mCellWidth + mLineWidth);
        int whichY = y / (int) (mCellHeight + mLineWidth);
        switch (whichX) {
            case 0:
                selectZero(whichY);
                break;
            case 1:
                selectOne(whichY);
                break;
            case 2:
                selectTwo(whichY);
                break;
        }
    }

    private void selectZero(int whichY) {
        if (whichY == 0) {
            mCurrentIndex = 0;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(0));
            }
        } else if (whichY == 1) {
            mCurrentIndex = 3;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(3));
            }
        } else if (whichY == 2) {
            mCurrentIndex = 6;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(6));
            }
        }
    }

    private void selectOne(int whichY) {
        if (whichY == 0) {
            mCurrentIndex = 1;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(1));
            }
        } else if (whichY == 1) {
            mCurrentIndex = 4;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(4));
            }
        } else if (whichY == 2) {
            mCurrentIndex = 7;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(7));
            }
        } else if (whichY == 3) {
            mCurrentIndex = 9;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(9));
            }
        }
    }

    private void selectTwo(int whichY) {
        if (whichY == 0) {
            mCurrentIndex = 2;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(2));
            }
        } else if (whichY == 1) {
            mCurrentIndex = 5;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(5));
            }
        } else if (whichY == 2) {
            mCurrentIndex = 8;
            postInvalidate();
            if (mListener != null) {
                mListener.onNumberKey(mNumberList.get(8));
            }
        } else if (whichY == 3) {   //退格键
            mCurrentIndex = 10;
            postInvalidate();
            if (mListener != null) {
                mListener.onBackspaceKey();
            }
        }
    }

    public interface InputListener {
        void onNumberKey(String number);

        void onBackspaceKey();
    }

    public void setInputListener(InputListener listener) {
        mListener = listener;
    }

    /**
     * 第一行元素
     *
     * @param canvas
     */
    private void drawOneRawElement(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mNumbersColor);
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(mNumbersSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        mBaselineY = mLineWidth + mCellHeight / 2 + ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom);
        for (int i = 0; i < 3; i++) {
            canvas.drawText(mNumberList.get(i), mCellWidth / 2 + (mCellWidth + mLineWidth) * i, mBaselineY, mPaint);
        }
    }

    /**
     * 第二行元素
     *
     * @param canvas
     */
    private void drawTwoRawElement(Canvas canvas) {
        for (int i = 3; i < 6; i++) {
            canvas.drawText(mNumberList.get(i),
                    mCellWidth / 2 + (mCellWidth + mLineWidth) * (i - 3),
                    mBaselineY + mCellHeight + mLineWidth,
                    mPaint);
        }
    }

    /**
     * 第三行元素
     *
     * @param canvas
     */
    private void drawThreeRawElement(Canvas canvas) {
        for (int i = 6; i < 9; i++) {
            canvas.drawText(mNumberList.get(i),
                    mCellWidth / 2 + (mCellWidth + mLineWidth) * (i - 6),
                    mBaselineY + (mCellHeight + mLineWidth) * 2,
                    mPaint);
        }
    }

    /**
     * 底部中间元素
     *
     * @param canvas
     */
    private void drawBottomCenterElement(Canvas canvas) {
        canvas.drawText(mNumberList.get(9), mCellWidth + mLineWidth + mCellWidth / 2, mBaselineY + (mCellHeight + mLineWidth) * 3, mPaint);
    }

    /**
     * 底部右边元素
     *
     * @param canvas
     */
    private void drawBottomRightElement(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.oklib_keyboard_backspace);
        canvas.drawBitmap(bitmap,
                2 * (mCellWidth + mLineWidth) + mCellWidth / 2 - bitmap.getWidth() / 2,
                4 * mLineWidth + 3 * mCellHeight + mCellHeight / 2 - bitmap.getHeight() / 2,
                mPaint);
    }

    /**
     * 画分割线
     *
     * @param canvas
     */
    private void drawSplitLine(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineWidth);
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(0,
                    mLineWidthHalf + (mLineWidth + mCellHeight) * i,
                    mWidth,
                    mLineWidthHalf + (mLineWidth + mCellHeight) * i,
                    mPaint);
        }
        for (int i = 0; i < 2; i++) {
            canvas.drawLine(mCellWidth + mLineWidthHalf + (mLineWidth + mCellWidth) * i,
                    mLineWidthHalf,
                    mCellWidth + mLineWidthHalf + (mLineWidth + mCellWidth) * i,
                    mHeight,
                    mPaint);
        }
    }

    /**
     * 画底部左端
     *
     * @param canvas
     */
    private void drawBottomLeft(Canvas canvas) {
        canvas.save();
        canvas.clipRect(new RectF(0, mHeight - mCellHeight, mCellWidth, mHeight));
        canvas.drawColor(mDeleteNormalBgColor);
        canvas.restore();
    }

    /**
     * 画底部右端
     *
     * @param canvas
     */
    private void drawBottomRight(Canvas canvas) {
        canvas.save();
        canvas.clipRect(new RectF(mWidth - mCellWidth, mHeight - mCellHeight, mWidth, mHeight));
        canvas.drawColor(mDeleteNormalBgColor);
        canvas.restore();
    }

    private int dp2px(float dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
