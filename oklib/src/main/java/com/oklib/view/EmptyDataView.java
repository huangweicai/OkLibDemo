package com.oklib.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.util.DensityUtils;


/**
 * 时间：2017/9/8
 * 作者：黄伟才
 * 描述：空数据view，动态切换，断网超时状态、业务成功空数据状态
 */

public class EmptyDataView extends LinearLayout {
    public EmptyDataView(Context context) {
        super(context);
        init();
    }

    public EmptyDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EmptyDataView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private ImageView emptyImage;
    private TextView emptyText;
    private TextView reloadBtn;
    private void init() {
        emptyImage = new ImageView(getContext());
        emptyText = new TextView(getContext());
        reloadBtn = new TextView(getContext());

        emptyImage.setImageResource(R.drawable.oklib_photo_error);
        emptyText.setText("网络访问或数据出错");
        reloadBtn.setText("重新加载");
        emptyText.setPadding(0, DensityUtils.dp2px(getContext(), 15), 0, DensityUtils.dp2px(getContext(), 15));
        reloadBtn.setPadding(DensityUtils.dp2px(getContext(), 15), DensityUtils.dp2px(getContext(), 5), DensityUtils.dp2px(getContext(), 15), DensityUtils.dp2px(getContext(), 5));

        addView(emptyImage);
        addView(emptyText);
        addView(reloadBtn);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        reloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onReloadListener) {
                    onReloadListener.reload();
                }
            }
        });
    }

    public interface OnReloadListener{
        void reload();
    }

    public OnReloadListener onReloadListener;

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    /**
     * 更新网络状态界面
     * @param isBrokenNetwork
     * @param emptyImageRes
     * @param hintText
     */
    public void updateEmptyState(boolean isBrokenNetwork, int emptyImageRes, String hintText) {
        if (isBrokenNetwork) {
            //断网
            emptyImage.setImageResource(emptyImageRes);
            emptyText.setText(hintText);
            reloadBtn.setVisibility(View.VISIBLE);
        }else{
            //业务空
            emptyImage.setImageResource(emptyImageRes);
            emptyText.setText(hintText);
            reloadBtn.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 更新提示文本状态
     * @param textSize
     * @param textColor
     */
    public void updateHintTextState(int textSize, int textColor) {
        emptyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        emptyText.setTextColor(textColor);
    }

    /**
     * 更新重新加载按钮状态
     * @param textSize
     * @param textColor
     * @param btnBg
     */
    public void updateReloadBtnState(int textSize, int textColor, int btnBg) {
        reloadBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        reloadBtn.setTextColor(textColor);
        reloadBtn.setBackgroundResource(btnBg);
    }

}
