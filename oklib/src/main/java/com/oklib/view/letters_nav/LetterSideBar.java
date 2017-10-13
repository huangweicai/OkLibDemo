package com.oklib.view.letters_nav;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.view.letters_nav.adapter.BaseSortListViewAdapter;
import com.oklib.view.letters_nav.adapter.BaseSortRecyclerViewAdapter;
import com.oklib.view.letters_nav.widget.LetterNavRecyclerView;


/**
 * side bar like WeChat
 * Created by zz on 2016/5/12.
 */
public class LetterSideBar extends View {

    public static final String TAG = LetterSideBar.class.getSimpleName();

    private String[] letters;

    private OnLetterTouchListener letterTouchListener;
    private ListView lv;
    private LetterNavRecyclerView rv;
    private BaseSortListViewAdapter adapter;
    private BaseSortRecyclerViewAdapter rvAdapter;
    private TextView tvDialog;

    private float itemHeight = -1;

    private Paint paint;

    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    private Bitmap letterBitmap;

    public LetterSideBar(Context context) {
        super(context);

        init(context);
    }

    public LetterSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public LetterSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        setShowString(b);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (letters == null) {
            return;
        }

        if (itemHeight == -1) {
            itemHeight = getHeight() / letters.length;
        }

        if (paint == null) {
            paint = new Paint();
            paint.setTextSize(itemHeight - 4);
            paint.setColor(getResources().getColor(R.color.oklib_colorMsgText));
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);

            Canvas mCanvas = new Canvas();
            letterBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            mCanvas.setBitmap(letterBitmap);
            float widthCenter = getMeasuredWidth() / 2.0f;
            for (int i = 0; i < letters.length; i++) {
                mCanvas.drawText(letters[i], widthCenter - paint.measureText(letters[i]) / 2, itemHeight * i + itemHeight, paint);
            }
        }
        if (letterBitmap != null) {
            canvas.drawBitmap(letterBitmap, 0, 0, paint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (letterTouchListener == null || letters == null) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //set background gray
                setBackgroundResource(R.drawable.oklib_letters_nav_side_bar_bg);
            case MotionEvent.ACTION_MOVE:
                int position = (int) (event.getY() / itemHeight);
                if (position >= 0 && position < letters.length) {
                    //jump to the group
                    if (adapter != null && lv != null && adapter.getPositionForSection(letters[position].charAt(0)) != -1) {
                        lv.setSelection(adapter.getPositionForSection(letters[position].charAt(0)) + lv.getHeaderViewsCount());
                    }
                    if (rvAdapter != null && rv != null && rvAdapter.getPositionForSection(letters[position].charAt(0)) != -1) {
                        int pos = rvAdapter.getPositionForSection(letters[position].charAt(0));
                        Log.e("POS", pos+"");
                        rv.getLinearLayoutManager().scrollToPositionWithOffset(pos, 0);
                    }
                    //make dialog visible
                    if (tvDialog != null) {
                        tvDialog.setVisibility(View.VISIBLE);
                        tvDialog.setText(letters[position]);
                    }
                    //interface callback
                    letterTouchListener.onLetterTouch(letters[position], position);
                }
                return true;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                //set background transparent
                setBackgroundResource(android.R.color.transparent);
                //make dialog invisible
                if (tvDialog != null) {
                    tvDialog.setVisibility(View.GONE);
                }
                //interface callback
                letterTouchListener.onActionUp();
                return true;
        }
        return false;
    }


    public void setShowString(String[] letters) {
        this.letters = letters;
    }


    public void setLetterTouchListener(ListView lv, BaseSortListViewAdapter adapter, TextView tvDialog, OnLetterTouchListener letterTouchListener) {
        this.lv = lv;
        this.adapter = adapter;
        this.tvDialog = tvDialog;
        this.letterTouchListener = letterTouchListener;
    }

    public void setLetterTouchListener(LetterNavRecyclerView rv, BaseSortRecyclerViewAdapter adapter, TextView tvDialog, OnLetterTouchListener letterTouchListener) {
        this.rv = rv;
        this.rvAdapter = adapter;
        this.tvDialog = tvDialog;
        this.letterTouchListener = letterTouchListener;
    }
}
