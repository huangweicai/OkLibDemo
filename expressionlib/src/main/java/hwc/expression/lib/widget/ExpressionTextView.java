/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hwc.expression.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import hwc.expression.lib.R;
import hwc.expression.lib.core.ExpressionTransformEngine;


/**
 * Created by jian on 2016/6/24.
 * mabeijianxi@gmail.com
 */
public class ExpressionTextView extends TextView {
    private int mExpressionSize;
    private int mExpressionAlignment;
    private int mExpressionTextSize;
    private SpannableString mContent;

    public ExpressionTextView(Context context) {
        super(context);
        init(null);
    }

    public ExpressionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpressionTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mExpressionTextSize = (int) getTextSize();
        if (attrs == null) {
            mExpressionSize = (int) getTextSize();
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Expression);
            mExpressionSize = (int) a.getDimension(R.styleable.Expression_expressionSize, getTextSize());
            mExpressionAlignment = a.getInt(R.styleable.Expression_expressionAlignment, DynamicDrawableSpan.ALIGN_BASELINE);
            a.recycle();
        }
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text) && mContent == null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            ExpressionTransformEngine.transformExoression(getContext(), builder, mExpressionSize, mExpressionAlignment, mExpressionTextSize);
            text = builder;
        } else if (mContent != null) {
            ExpressionTransformEngine.transformExoression(getContext(), mContent, mExpressionSize, mExpressionAlignment, mExpressionTextSize);
            text=mContent;
        }
        super.setText(text, type);
    }
    public void setSpannableString(SpannableString content) {
        this.mContent = content;
    }
    public void setExpressionSize(int pixels) {
        mExpressionSize = pixels;
        super.setText(getText());
    }

}
