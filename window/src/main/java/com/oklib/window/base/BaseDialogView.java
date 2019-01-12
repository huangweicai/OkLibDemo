package com.oklib.window.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * 用View模拟Dialog，在android.R.id.content中插入view<br>
 * 尽量模仿BaseDialogFragment，使得子类尽量少修改，不过构造函数不可避免地修改了<br>
 * gravity() 方法默认为Gravity.CENTER，如返回0则不居中
 */
@SuppressWarnings("unused")
public abstract class BaseDialogView {

    private Activity mActivity;
    private Bundle mArguments;

    private ViewGroup mDecorContent;
    private FrameLayout mContainer;
    private View mLayout;
    private FrameLayout.LayoutParams mLayoutParams;

    public BaseDialogFragment.OnConfirmListener confirmListener;
    private boolean mCancelable;

//    @Deprecated
//    public BaseDialogView() {
//        throw new RuntimeException("Do not surport anymore!");
//    }  

    public BaseDialogView(Activity activity) {
        mActivity = activity;
        mDecorContent = (ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (mDecorContent == null) {
            showToast("Can not find content view!");
            return;
        }

        mContainer = new FrameLayout(activity);
        mContainer.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        mContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCancelable) {
                    dismiss();
                }
            }
        });
        mLayoutParams = new FrameLayout.LayoutParams(-2, -2);

        onStart();
    }

    @Deprecated
    public void onStart() {
        this.setWindowDim(this.initDimValue());
    }

    @Deprecated
    public void onResume() {
        this.initOnResume();
    }

    @Deprecated
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCancelable = isCancel();
        this.setGravity(this.gravity());
        View view = inflater.inflate(this.initContentView(), container, false);
        this.argumentsDate();
        this.initActionBarView(view);
        this.initView(view);
        this.initData(view);
        this.initNet();
        return view;
    }

    final public Bundle getArguments() {
        return mArguments;
    }

    public void setArguments(Bundle args) {
        mArguments = args;
    }

    @Deprecated
    public Dialog getDialog() {
        return null;
    }

    final public Activity getActivity() {
        return mActivity;
    }

    final public Context getContext() {
        return mActivity;
    }

    public BaseDialogView setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return this;
    }

    @Deprecated
    public void show(FragmentManager manager, String tag) {
        show();
    }

    @Deprecated
    public int show(FragmentTransaction transaction, String tag) {
        show();
        return 0;
    }

    public void show() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLayout == null) {
                    mLayout = onCreateView(LayoutInflater.from(mActivity), mContainer, null);
                    if (mLayout != null) {
                        mLayout.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //do nothing
                            }
                        });
                        mContainer.addView(mLayout, mLayoutParams);
                    }
                }
                if (mDecorContent != null) {
                    mDecorContent.addView(mContainer);
                }

                onResume();
            }
        });
    }

    public void dismiss() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDecorContent != null) {
                    mDecorContent.removeView(mContainer);
                }
            }
        });
    }

    public float initDimValue() {
        return 0.8f;
    }

    @Deprecated
    public void initOnResume() {
    }

    public boolean isCancel() {
        return true;
    }

    public int gravity() {
        return Gravity.CENTER;
    }

    @Deprecated
    public int style() {
        return 0;
    }

    public abstract int initContentView();

    protected abstract void argumentsDate();

    public void initActionBarView(View var1) {}

    public abstract void initView(View var1);

    public abstract void initData(View var1);

    protected void initNet() {}

    protected void setWHSize(int width, int height) {
        mLayoutParams.width = width;
        mLayoutParams.height = height;
        if (mLayout != null) {
            mLayout.setLayoutParams(mLayoutParams);
        }
    }

    protected void setGravity(int gravity) {
        mLayoutParams.gravity = gravity;
        if (mLayout != null) {
            mLayout.setLayoutParams(mLayoutParams);
        }
    }

    protected void setWindowDim(float dimValue) {
        int alpha = ((int) (0xff * dimValue)) & 0xff;
        mContainer.setBackgroundColor(alpha << 24);
    }

    protected <T extends View> T findView(View view, int viewId) {
        return view.findViewById(viewId);
    }

    protected void showToast(String msg) {
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    protected int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    protected int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, context.getResources().getDisplayMetrics());
    }

    protected int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(2, spVal, context.getResources().getDisplayMetrics());
    }

    public BaseDialogView setOnConfirmListener(BaseDialogFragment.OnConfirmListener _confirmListener) {
        this.confirmListener = _confirmListener;
        return this;
    }
} 
