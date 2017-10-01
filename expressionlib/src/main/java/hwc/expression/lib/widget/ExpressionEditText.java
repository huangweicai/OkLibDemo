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
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import hwc.expression.lib.R;
import hwc.expression.lib.core.ExpressionTransformEngine;


/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class ExpressionEditText extends EditText {
    private int mExpressionSize;
    private int mExpressionAlignment;
    private int mExpressionTextSize;

    public ExpressionEditText(Context context) {
        super(context);
        mExpressionSize = (int) getTextSize();
        mExpressionTextSize = (int) getTextSize();
    }

    public ExpressionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpressionEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Expression);
        mExpressionSize = (int) a.getDimension(R.styleable.Expression_expressionSize, getTextSize());
        mExpressionAlignment = a.getInt(R.styleable.Expression_expressionAlignment, DynamicDrawableSpan.ALIGN_BASELINE);
        a.recycle();
        mExpressionTextSize = (int) getTextSize();
        setText(getText());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        updateText();
    }

    public void setExpressionSize(int pixels) {
        mExpressionSize = pixels;

        updateText();
    }

    private void updateText() {

        ExpressionTransformEngine.transformExoression(getContext(), getText(), mExpressionSize, mExpressionAlignment, mExpressionTextSize);
    }

}
