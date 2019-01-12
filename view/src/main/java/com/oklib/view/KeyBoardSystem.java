package com.oklib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
  * 时间：2017/8/2
  * 作者：蓝天
  * 描述：密码输入框，系统软键盘
  */
public class KeyBoardSystem extends View implements View.OnKeyListener, View.OnFocusChangeListener, KeyBoardCustomRandom.InputListener {
   private int mPwdNum;
   private boolean mNeedCursor;
   private boolean mPwdVisible;
   private int mBorderColor;
   private int mBorderWidth;
   private int mCircleColor;
   private int mCircleSize;
   private int mTextColor;
   private int mTextSize;

   private Context mContext;
   private int mWidth;
   private int mHeight;
   private Paint mPaint;
   private int mBorderWidthHalf;
   private int mSingleNoBorder;
   private ArrayList<Integer> mList;
   private OnPwdInputListener mOnPwdInputListener;
   private boolean mIsFirstComplete;
   private int mCurrentPosition;
   private long mDelayTime = 800;
   private final int[] mCursorAlpha = {0, 255};
   private int mCurrentAlpha = mCursorAlpha[0];
   private boolean mEndCursor;
   private Timer mTimer;
   private TimerTask mTimerTask;
   private boolean mIsCustomKeyboard;
   private KeyBoardCustomRandom mMagicKeyBoard;

   public KeyBoardSystem(Context context) {
       this(context, null);
   }

   public KeyBoardSystem(Context context, AttributeSet attrs) {
       this(context, attrs, -1);
   }

   public KeyBoardSystem(Context context, AttributeSet attrs, int defStyleAttr) {
       super(context, attrs, defStyleAttr);
       initAttr(context, attrs);
       initPaint();
       initKeyBoard();
   }

   private void initAttr(Context context, AttributeSet attrs) {
       mContext = context;
       TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.oklib_PasswordInputBox);
       mPwdNum = typedArray.getInteger(R.styleable.oklib_PasswordInputBox_oklib_pwdNum, 6);
       mIsCustomKeyboard = typedArray.getBoolean(R.styleable.oklib_PasswordInputBox_oklib_isCustomKeyboard, true);
       mPwdVisible = typedArray.getBoolean(R.styleable.oklib_PasswordInputBox_oklib_pwdVisible, false);
       mNeedCursor = typedArray.getBoolean(R.styleable.oklib_PasswordInputBox_oklib_needCursor, false);
       mBorderColor = typedArray.getColor(R.styleable.oklib_PasswordInputBox_oklib_borderColor, Color.parseColor("#aa888888"));
       mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.oklib_PasswordInputBox_oklib_borderWidth, dp2px(2));
       mCircleColor = typedArray.getColor(R.styleable.oklib_PasswordInputBox_oklib_circleColor, Color.parseColor("#000000"));
       mCircleSize = typedArray.getDimensionPixelSize(R.styleable.oklib_PasswordInputBox_oklib_circleSize, dp2px(10));
       mTextColor = typedArray.getColor(R.styleable.oklib_PasswordInputBox_oklib_textColor, Color.parseColor("#ACACAC"));
       mTextSize = typedArray.getDimensionPixelSize(R.styleable.oklib_PasswordInputBox_oklib_textSize, dp2px(16));
       mBorderWidthHalf = mBorderWidth / 2;
       mList = new ArrayList<>();
       mIsFirstComplete = true;
       mCurrentPosition = 0;
       mEndCursor = false;
       if (mNeedCursor) {
           mTimer = new Timer();
           mTimerTask = new TimerTask() {
               @Override
               public void run() {
                   postInvalidate();
                   if (mCurrentAlpha == mCursorAlpha[0]) {
                       mCurrentAlpha = mCursorAlpha[1];
                   } else if (mCurrentAlpha == mCursorAlpha[1]) {
                       mCurrentAlpha = mCursorAlpha[0];
                   }
               }
           };
       }
   }

   private void initPaint() {
       mPaint = new Paint();
       mPaint.setAntiAlias(true);
   }

   private void initKeyBoard() {
       if (!mIsCustomKeyboard) {
           setFocusable(true);
           setFocusableInTouchMode(true);
           setOnKeyListener(this);
           setOnFocusChangeListener(this);
       }
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       int widthMode = MeasureSpec.getMode(widthMeasureSpec);
       int widthSize = MeasureSpec.getSize(widthMeasureSpec);
       int heightSize = MeasureSpec.getSize(heightMeasureSpec);
       switch (widthMode) {
           case MeasureSpec.AT_MOST:
           case MeasureSpec.UNSPECIFIED:
               widthSize = widthSize * 8 / 10;
               break;
       }
       mSingleNoBorder = (widthSize - (mPwdNum + 1) * mBorderWidth) / mPwdNum;
       heightSize = mSingleNoBorder + 2 * mBorderWidth;
       mCircleSize = mCircleSize <= mSingleNoBorder / 2 ? mCircleSize : mSingleNoBorder / 2;
       mTextSize = mTextSize <= mSingleNoBorder ? mTextSize : mTextSize;
       setMeasuredDimension(widthSize, heightSize);
   }

   @Override
   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
       super.onSizeChanged(w, h, oldw, oldh);
       mWidth = w;
       mHeight = h;
   }

   @Override
   protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       drawRect(canvas);
       drawLine(canvas);
       if (mNeedCursor) {
           drawCursor(canvas);
       }
       if (mPwdVisible) {
           drawPwd(canvas);
       } else {
           drawCircle(canvas);
       }
   }

   /**
    * 画光标
    *
    * @param canvas
    */
   private void drawCursor(Canvas canvas) {
       mPaint.setStyle(Paint.Style.FILL);
       mPaint.setStrokeWidth(dp2px(2));
       mPaint.setColor(Color.BLACK);
       mPaint.setAlpha(mCurrentAlpha);
       if (!mEndCursor && hasFocus()) {
           canvas.drawLine(mBorderWidth + mSingleNoBorder / 2 + mCurrentPosition * (mBorderWidth + mSingleNoBorder),
                   mBorderWidth + mSingleNoBorder / 3,
                   mBorderWidth + mSingleNoBorder / 2 + mCurrentPosition * (mBorderWidth + mSingleNoBorder),
                   mHeight - mBorderWidth - mSingleNoBorder / 3,
                   mPaint);
       }
   }

   /**
    * 画矩形
    *
    * @param canvas
    */
   private void drawRect(Canvas canvas) {
       mPaint.setStyle(Paint.Style.STROKE);
       mPaint.setStrokeWidth(mBorderWidth);
       mPaint.setColor(mBorderColor);
       RectF rectF = new RectF(mBorderWidthHalf, mBorderWidthHalf, mWidth - mBorderWidthHalf, mHeight - mBorderWidthHalf);
       canvas.drawRect(rectF, mPaint);
   }

   /**
    * 画分割线
    *
    * @param canvas
    */
   private void drawLine(Canvas canvas) {
       for (int i = 0; i < mPwdNum - 1; i++) {
           canvas.drawLine(mBorderWidth + mSingleNoBorder + mBorderWidthHalf + i * (mSingleNoBorder + mBorderWidth),
                   mBorderWidthHalf,
                   mBorderWidth + mSingleNoBorder + mBorderWidthHalf + i * (mSingleNoBorder + mBorderWidth),
                   mHeight - mBorderWidthHalf,
                   mPaint);
       }
   }

   /**
    * 画圆点
    *
    * @param canvas
    */
   private void drawCircle(Canvas canvas) {
       mPaint.setStyle(Paint.Style.FILL);
       mPaint.setStrokeWidth(mBorderWidth);
       mPaint.setColor(mCircleColor);
       for (int i = 0; i < mList.size(); i++) {
           canvas.drawCircle(mBorderWidth + mSingleNoBorder / 2 + i * (mBorderWidth + mSingleNoBorder),
                   mBorderWidth + mSingleNoBorder / 2,
                   mCircleSize,
                   mPaint);
       }
   }

   /**
    * 画密码文本
    *
    * @param canvas
    */
   private void drawPwd(Canvas canvas) {
       mPaint.setStyle(Paint.Style.FILL);
       mPaint.setStrokeWidth(0);
       mPaint.setColor(mTextColor);
       mPaint.setTextSize(mTextSize);
       mPaint.setTextAlign(Paint.Align.CENTER);
       for (int i = 0; i < mList.size(); i++) {
           Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
           int baselineY = mBorderWidth + mSingleNoBorder / 2 + ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom);
           canvas.drawText(mList.get(i) + "",
                   mBorderWidth + mSingleNoBorder / 2 + i * (mBorderWidth + mSingleNoBorder),
                   baselineY,
                   mPaint);
       }
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
       switch (event.getActionMasked()) {
           case MotionEvent.ACTION_DOWN:
               if (!mIsCustomKeyboard) {
                   requestFocus();
                   showKeyBoard();
               }
               break;
       }
       return true;
   }

   @Override
   public boolean onKey(View v, int keyCode, KeyEvent event) {
       switch (event.getAction()) {
           case KeyEvent.ACTION_DOWN:
               Log.d("TAG", "keyCode："+keyCode);

               if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                   addPwd(keyCode);
               } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                   deleteForwardPwd();
               }
               break;
       }
       return true;
   }

   public void register(KeyBoardCustomRandom magicKeyBoard) {
       mMagicKeyBoard = magicKeyBoard;
       if (mIsCustomKeyboard) {
           addMagicKeyBoardListener();
       }
   }

   public void unregister() {
       mMagicKeyBoard = null;
   }

   private void addMagicKeyBoardListener() {
       if (mMagicKeyBoard != null) {
           mMagicKeyBoard.setInputListener(this);
       }
   }

   @Override
   public void onNumberKey(String number) {
       addPwd(Integer.parseInt(number) + 7);
   }

   @Override
   public void onBackspaceKey() {
       deleteForwardPwd();
   }

   public enum PwdInput {
       ADDSINGLE,
       DELETESINGLE
   }

   /**
    * 增加
    */
   private void addPwd(int keyCode) {
       if (mList.size() < mPwdNum) {
           if (mCurrentPosition >= 0 && mCurrentPosition < mPwdNum - 1) {
               mCurrentPosition++;
           }
           mList.add(keyCode - 7);
           postInvalidate();
           if (mOnPwdInputListener != null) {
               mOnPwdInputListener.pwdChange((keyCode - 7) + "", PwdInput.ADDSINGLE);
           }
       }
       if (mList.size() == mPwdNum && mIsFirstComplete) {
           mEndCursor = true;
           mIsFirstComplete = false;
           if (mOnPwdInputListener != null) {
               mOnPwdInputListener.pwdComplete(getPwd());
           }
       }
   }

   /**
    * 输入完成时/清空密码时获取密码
    *
    * @return 密码字符串
    */
   private String getPwd() {
       StringBuffer buffer = new StringBuffer();
       for (Integer single : mList) {
           buffer.append(single);
       }
       return buffer.toString();
   }

   /**
    * 退格
    */
   private void deleteForwardPwd() {
       if (mList.size() > 0 && mList.size() < mPwdNum) {
           if (mCurrentPosition > 0 && mCurrentPosition <= mPwdNum) {
               mCurrentPosition--;
               mEndCursor = false;
           }
           Integer removePwd = mList.remove(mList.size() - 1);
           if (mOnPwdInputListener != null) {
               mOnPwdInputListener.pwdChange(removePwd + "", PwdInput.DELETESINGLE);
           }
           postInvalidate();
       }
   }

   /**
    * 清空密码
    *
    * @return 清空的密码
    */
   public String clearPwd() {
       mCurrentPosition = 0;
       mEndCursor = false;
       mIsFirstComplete = true;
       String oldPwd = getPwd();
       mList.clear();
       postInvalidate();
       return oldPwd;
   }

   public void setOnPwdInputListener(OnPwdInputListener onPwdInputListener) {
       mOnPwdInputListener = onPwdInputListener;
   }

   @Override
   protected void onAttachedToWindow() {
       super.onAttachedToWindow();
       if (mTimer != null) {
           mTimer.scheduleAtFixedRate(mTimerTask, 0, mDelayTime);
       }
   }

   @Override
   protected void onDetachedFromWindow() {
       super.onDetachedFromWindow();
       if (mTimer != null) {
           mTimer.cancel();
       }
   }

   /**
    * 密码输入的监听
    */
   public interface OnPwdInputListener {
       void pwdChange(String pwd, PwdInput flag);

       void pwdComplete(String pwd);
   }

   @Override
   public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
       outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;
       return super.onCreateInputConnection(outAttrs);
   }

   @Override
   public void onFocusChange(View v, boolean hasFocus) {
       if (!hasFocus) {
           closeKeyBoard();
       }
   }

   /**
    * 弹出软键盘
    */
   private void showKeyBoard() {
       InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
       imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
   }

   /**
    * 收起软键盘
    */
   public void closeKeyBoard() {
       InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
       imm.hideSoftInputFromWindow(this.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
   }

   private int dp2px(float dp) {
       float scale = getContext().getResources().getDisplayMetrics().density;
       return (int) (dp * scale + 0.5f);
   }

}
