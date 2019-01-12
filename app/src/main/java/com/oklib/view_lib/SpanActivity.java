package com.oklib.view_lib;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ImageSpan;
import android.util.Property;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.span.AnimatedColorSpan;
import com.oklib.view.span.FrameSpan;
import com.oklib.view.span.MutableForegroundColorSpan;
import com.oklib.view.span.RainbowSpan;
import com.oklib.view.span.TypeWriterSpanGroup;
import com.oklib.view.span.VerticalImageSpan;


/**
 * 时间：2018/2/10
 * 作者：蓝天
 * 描述：Span多场景使用样例
 */

public class SpanActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_spans;
    }

    @Override
    protected void initView() {
        use1();
        user2();
        user22();
        user3();
        use4();
        use5();
    }

    //加边框效果
    private void use1() {
        TextView tv = (TextView) findViewById(R.id.tv_framespan1);
        final SpannableString spannableString = new SpannableString(
                "1234567890，加边框效果");
        spannableString.setSpan(new FrameSpan(), 0, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
    }

    //垂直居中对齐文字和图片
    private void user2() {
        TextView tv = (TextView) findViewById(R.id.tv_framespan2);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_share);
        drawable.setBounds(0, 0, 50, 50);
        final SpannableString spannableString = new SpannableString(
                "1234567890，垂直居中VerticalImageSpan");
        spannableString.setSpan(new VerticalImageSpan(drawable), 3, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
    }

    private void user22() {
        TextView tv = (TextView) findViewById(R.id.tv_framespan22);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_share);
        drawable.setBounds(0, 0, 50, 50);
        final SpannableString spannableString = new SpannableString(
                "1234567890，图片文字底部对齐ImageSpan");
        spannableString.setSpan(new ImageSpan(drawable), 3, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
    }

    //彩虹样的Span（仅仅渐变色，没有滚动的动效）
    private void user3() {
        TextView tv = (TextView) findViewById(R.id.tv_framespan3);
        final SpannableString spannableString = new SpannableString(
                "1234567890，彩虹样的Span");
        int[] colors = new int[]{R.color.blue_colorDark, R.color.radio_group_bg_2, R.color.text_color_6, R.color.common_text_yellow,};
        spannableString.setSpan(new RainbowSpan(colors), 0, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
    }

    //字体多色渐变动画效果
    private void use4() {
        final TextView textView = (TextView) findViewById(R.id.tv_framespan4);
        String text = "oklib工具库测试字体多色渐变动画效果";
        int[] colors = new int[]{R.color.blue_colorDark, R.color.radio_group_bg_2, R.color.text_color_6, R.color.common_text_yellow,};
        AnimatedColorSpan span = new AnimatedColorSpan(colors);
        final SpannableString spannableString = new SpannableString(text);
        String substring = "字体多色渐变动画效果";
        int start = text.indexOf(substring);
        int end = start + substring.length();
        spannableString.setSpan(span, start, end, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                span, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0, 100);
        objectAnimator.setEvaluator(new FloatEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(spannableString);
            }
        });
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(DateUtils.MINUTE_IN_MILLIS * 3);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

    private static final Property<AnimatedColorSpan, Float> ANIMATED_COLOR_SPAN_FLOAT_PROPERTY
            = new Property<AnimatedColorSpan, Float>(Float.class, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY") {
        @Override
        public void set(AnimatedColorSpan span, Float value) {
            span.setTranslateXPercentage(value);
        }

        @Override
        public Float get(AnimatedColorSpan span) {
            return span.getTranslateXPercentage();
        }
    };

    //打字效果
    private void use5() {
        final TextView tv = (TextView) findViewById(R.id.tv_framespan5);
        String val = "oklib工具库测试打字效果";
        final SpannableString spannableString = new SpannableString(val);

        //渐变色
//        int[] colors = new int[]{R.color.blue_colorDark, R.color.radio_group_bg_2, R.color.text_color_6, R.color.common_text_yellow,};
//        spannableString.setSpan(new RainbowSpan(colors),
//                0, val.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        tv.setText(spannableString);

        // 添加Span
        final TypeWriterSpanGroup group = new TypeWriterSpanGroup(0);
        for (int index = 0; index <= val.length() - 1; index++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan();
            group.addSpan(span);
            spannableString.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 添加动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(group, TYPE_WRITER_GROUP_ALPHA_PROPERTY, 0.0f, 1.0f);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                tv.setText(spannableString);
            }
        });
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }

    private static final Property<TypeWriterSpanGroup, Float> TYPE_WRITER_GROUP_ALPHA_PROPERTY =
            new Property<TypeWriterSpanGroup, Float>(Float.class, "TYPE_WRITER_GROUP_ALPHA_PROPERTY") {
                @Override
                public void set(TypeWriterSpanGroup spanGroup, Float value) {
                    spanGroup.setAlpha(value);
                }

                @Override
                public Float get(TypeWriterSpanGroup spanGroup) {
                    return spanGroup.getAlpha();
                }
            };

}
