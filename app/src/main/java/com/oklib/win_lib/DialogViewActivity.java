package com.oklib.win_lib;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.util.toast.ToastUtil;
import com.oklib.window.base.BaseDialogFragment;
import com.oklib.window.base.BaseDialogView;


/**
 * 时间：2018/1/26
 * 作者：蓝天
 * 描述：结束dialog
 */

public class DialogViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_multi_select_listpop;
    }

    private TextView tv_text;
    @Override
    protected void initView() {
        tv_text = findView(R.id.tv_text);
    }

    public void doClick(View view) {
        FinishDialog finishDialog = new FinishDialog(this);
        finishDialog.setArguments(new Bundle());
        finishDialog.show();
        finishDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
            @Override
            public void confirm(View view) {
                ToastUtil.show("是");
            }
        });
    }


    public class FinishDialog extends BaseDialogView {
        private ImageView iv_hint_icon;
        private TextView tv_content;
        private TextView tv_hint_text;

        private boolean isShowHintIcon = true;
        private boolean isShowHintText = true;
        private String contentText = "";
        private String hintText = "";
        private String highlightText = "";
        private int color = 0xFFBC8F29;//黄色

        @Override
        public float initDimValue() {
            return 0.8f;
        }

        @Override
        public void initOnResume() {
            setWHSize(dp2px(getContext(), 250), dp2px(getContext(), 400));
        }

        @Override
        public boolean isCancel() {
            return true;
        }

        @Override
        public int style() {
            return 0;
        }

        @Override
        public int initContentView() {
            return R.layout.dialog_finish;
        }

        @Override
        protected void argumentsDate() {
            contentText = getArguments().getString("contentText");
            hintText = getArguments().getString("hintText");
            highlightText = getArguments().getString("highlightText");
            color = getArguments().getInt("color");
            isShowHintIcon = getArguments().getBoolean("isShowHintIcon");
            isShowHintText = getArguments().getBoolean("isShowHintText");
        }

        @Override
        public void initActionBarView(View view) {

        }

        @Override
        public void initView(View view) {
            iv_hint_icon = findView(view, R.id.iv_hint_icon);
            tv_content = findView(view, R.id.tv_content);
            tv_hint_text = findView(view, R.id.tv_hint_text);

        }

        @Override
        public void initData(View view) {
            if (isShowHintIcon) {
                iv_hint_icon.setVisibility(View.VISIBLE);
            } else {
                iv_hint_icon.setVisibility(View.GONE);
            }

            if (isShowHintText) {
                tv_hint_text.setVisibility(View.VISIBLE);
                //setHighLightText(tv_hint_text, hintText, highlightText, color);
            } else {
                tv_hint_text.setVisibility(View.GONE);
            }

            tv_content.setText(contentText);
            tv_hint_text.setText(hintText);
        }

        @Override
        protected void initNet() {

        }

        /**
         * 设置高亮关键字
         * @param textView
         * @param hintText
         * @param highlightText
         */
        private void setHighLightText(TextView textView, String hintText, String highlightText, int color) {
            String str = hintText;
            int fStart = str.indexOf(highlightText);
            int fEnd = fStart + highlightText.length();
            SpannableStringBuilder myStyleStr = new SpannableStringBuilder(str);
            myStyleStr.setSpan(new ForegroundColorSpan(color), fStart, fEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            textView.setText(myStyleStr);
        }


        public FinishDialog(Activity activity) {
            super(activity);
        }


    }

    public interface OnCancelListener {
        void onCance(View v);
    }

    public OnCancelListener onCancelListener;

    public void setOnCancelListener(OnCancelListener _onCancelListener) {
        onCancelListener = _onCancelListener;
    }
}
