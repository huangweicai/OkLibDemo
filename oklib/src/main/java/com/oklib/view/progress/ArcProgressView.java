package com.oklib.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.oklib.R;

import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_angle;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_bottom_text;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_bottom_text_size;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_finished_color;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_max;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_progress;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_stroke_width;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_suffix_text;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_suffix_text_padding;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_suffix_text_size;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_text_color;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_text_size;
import static com.oklib.R.styleable.oklib_ArcProgressView_oklib_arc_unfinished_color;

/**
 * 时间：2017/8/28
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：弧形内存占比进度
 */

public class ArcProgressView extends View {
    private Paint paint;
    protected Paint textPaint;
    private RectF rectF;
    private float strokeWidth;
    private float suffixTextSize;
    private float bottomTextSize;
    private String bottomText;
    private float textSize;
    private int textColor;
    private int progress;
    private int max;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private float arcAngle;
    private String suffixText;
    private float suffixTextPadding;
    private float arcBottomHeight;
    private final int default_finished_color;
    private final int default_unfinished_color;
    private final int default_text_color;
    private final float default_suffix_text_size;
    private final float default_suffix_padding;
    private final float default_bottom_text_size;
    private final float default_stroke_width;
    private final String default_suffix_text;
    private final int default_max;
    private final float default_arc_angle;
    private float default_text_size;
    private final int min_size;
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
    private static final String INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size";
    private static final String INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding";
    private static final String INSTANCE_BOTTOM_TEXT_SIZE = "bottom_text_size";
    private static final String INSTANCE_BOTTOM_TEXT = "bottom_text";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_ARC_ANGLE = "arc_angle";
    private static final String INSTANCE_SUFFIX = "suffix";

    public ArcProgressView(Context context) {
        this(context, (AttributeSet)null);
    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.rectF = new RectF();
        this.progress = 0;
        this.suffixText = "%";
        this.default_finished_color = -1;
        this.default_unfinished_color = Color.rgb(72, 106, 176);
        this.default_text_color = Color.rgb(66, 145, 241);
        this.default_max = 100;
        this.default_arc_angle = 288.0F;
        this.default_text_size = Utils.sp2px(this.getResources(), 18.0F);
        this.min_size = (int)Utils.dp2px(this.getResources(), 100.0F);
        this.default_text_size = Utils.sp2px(this.getResources(), 40.0F);
        this.default_suffix_text_size = Utils.sp2px(this.getResources(), 15.0F);
        this.default_suffix_padding = Utils.dp2px(this.getResources(), 4.0F);
        this.default_suffix_text = "%";
        this.default_bottom_text_size = Utils.sp2px(this.getResources(), 10.0F);
        this.default_stroke_width = Utils.dp2px(this.getResources(), 4.0F);
//        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, styleable.ArcProgress, defStyleAttr, 0);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.oklib_ArcProgressView);
        this.initByAttributes(attributes);
        attributes.recycle();
        this.initPainters();
    }

    protected void initByAttributes(TypedArray attributes) {
        this.finishedStrokeColor = attributes.getColor(oklib_ArcProgressView_oklib_arc_finished_color, -1);
        this.unfinishedStrokeColor = attributes.getColor(oklib_ArcProgressView_oklib_arc_unfinished_color, this.default_unfinished_color);
        this.textColor = attributes.getColor(oklib_ArcProgressView_oklib_arc_text_color, this.default_text_color);
        this.textSize = attributes.getDimension(oklib_ArcProgressView_oklib_arc_text_size, this.default_text_size);
        this.arcAngle = attributes.getFloat(oklib_ArcProgressView_oklib_arc_angle, 288.0F);
        this.setMax(attributes.getInt(oklib_ArcProgressView_oklib_arc_max, 100));
        this.setProgress(attributes.getInt(oklib_ArcProgressView_oklib_arc_progress, 0));
        this.strokeWidth = attributes.getDimension(oklib_ArcProgressView_oklib_arc_stroke_width, this.default_stroke_width);
        this.suffixTextSize = attributes.getDimension(oklib_ArcProgressView_oklib_arc_suffix_text_size, this.default_suffix_text_size);
        this.suffixText = TextUtils.isEmpty(attributes.getString(oklib_ArcProgressView_oklib_arc_suffix_text))?this.default_suffix_text:attributes.getString(oklib_ArcProgressView_oklib_arc_suffix_text);
        this.suffixTextPadding = attributes.getDimension(oklib_ArcProgressView_oklib_arc_suffix_text_padding, this.default_suffix_padding);
        this.bottomTextSize = attributes.getDimension(oklib_ArcProgressView_oklib_arc_bottom_text_size, this.default_bottom_text_size);
        this.bottomText = attributes.getString(oklib_ArcProgressView_oklib_arc_bottom_text);
    }

    protected void initPainters() {
        this.textPaint = new TextPaint();
        this.textPaint.setColor(this.textColor);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setAntiAlias(true);
        this.paint = new Paint();
        this.paint.setColor(this.default_unfinished_color);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(this.strokeWidth);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void invalidate() {
        this.initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public float getSuffixTextSize() {
        return this.suffixTextSize;
    }

    public void setSuffixTextSize(float suffixTextSize) {
        this.suffixTextSize = suffixTextSize;
        this.invalidate();
    }

    public String getBottomText() {
        return this.bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
        this.invalidate();
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if(this.progress > this.getMax()) {
            this.progress %= this.getMax();
        }

        this.invalidate();
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        if(max > 0) {
            this.max = max;
            this.invalidate();
        }

    }

    public float getBottomTextSize() {
        return this.bottomTextSize;
    }

    public void setBottomTextSize(float bottomTextSize) {
        this.bottomTextSize = bottomTextSize;
        this.invalidate();
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.invalidate();
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getFinishedStrokeColor() {
        return this.finishedStrokeColor;
    }

    public void setFinishedStrokeColor(int finishedStrokeColor) {
        this.finishedStrokeColor = finishedStrokeColor;
        this.invalidate();
    }

    public int getUnfinishedStrokeColor() {
        return this.unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int unfinishedStrokeColor) {
        this.unfinishedStrokeColor = unfinishedStrokeColor;
        this.invalidate();
    }

    public float getArcAngle() {
        return this.arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
        this.invalidate();
    }

    public String getSuffixText() {
        return this.suffixText;
    }

    public void setSuffixText(String suffixText) {
        this.suffixText = suffixText;
        this.invalidate();
    }

    public float getSuffixTextPadding() {
        return this.suffixTextPadding;
    }

    public void setSuffixTextPadding(float suffixTextPadding) {
        this.suffixTextPadding = suffixTextPadding;
        this.invalidate();
    }

    protected int getSuggestedMinimumHeight() {
        return this.min_size;
    }

    protected int getSuggestedMinimumWidth() {
        return this.min_size;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.rectF.set(this.strokeWidth / 2.0F, this.strokeWidth / 2.0F, (float)width - this.strokeWidth / 2.0F, (float)MeasureSpec.getSize(heightMeasureSpec) - this.strokeWidth / 2.0F);
        float radius = (float)width / 2.0F;
        float angle = (360.0F - this.arcAngle) / 2.0F;
        this.arcBottomHeight = radius * (float)(1.0D - Math.cos((double)(angle / 180.0F) * 3.141592653589793D));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 270.0F - this.arcAngle / 2.0F;
        float finishedSweepAngle = (float)this.progress / (float)this.getMax() * this.arcAngle;
        float finishedStartAngle = startAngle;
        if(this.progress == 0) {
            finishedStartAngle = 0.01F;
        }

        this.paint.setColor(this.unfinishedStrokeColor);
        canvas.drawArc(this.rectF, startAngle, this.arcAngle, false, this.paint);
        this.paint.setColor(this.finishedStrokeColor);
        canvas.drawArc(this.rectF, finishedStartAngle, finishedSweepAngle, false, this.paint);
        String text = String.valueOf(this.getProgress());
        float bottomTextBaseline;
        float angle;
        if(!TextUtils.isEmpty(text)) {
            this.textPaint.setColor(this.textColor);
            this.textPaint.setTextSize(this.textSize);
            bottomTextBaseline = this.textPaint.descent() + this.textPaint.ascent();
            angle = ((float)this.getHeight() - bottomTextBaseline) / 2.0F;
            canvas.drawText(text, ((float)this.getWidth() - this.textPaint.measureText(text)) / 2.0F, angle, this.textPaint);
            this.textPaint.setTextSize(this.suffixTextSize);
            float suffixHeight = this.textPaint.descent() + this.textPaint.ascent();
            canvas.drawText(this.suffixText, (float)this.getWidth() / 2.0F + this.textPaint.measureText(text) + this.suffixTextPadding, angle + bottomTextBaseline - suffixHeight, this.textPaint);
        }

        if(this.arcBottomHeight == 0.0F) {
            bottomTextBaseline = (float)this.getWidth() / 2.0F;
            angle = (360.0F - this.arcAngle) / 2.0F;
            this.arcBottomHeight = bottomTextBaseline * (float)(1.0D - Math.cos((double)(angle / 180.0F) * 3.141592653589793D));
        }

        if(!TextUtils.isEmpty(this.getBottomText())) {
            this.textPaint.setTextSize(this.bottomTextSize);
            bottomTextBaseline = (float)this.getHeight() - this.arcBottomHeight - (this.textPaint.descent() + this.textPaint.ascent()) / 2.0F;
            canvas.drawText(this.getBottomText(), ((float)this.getWidth() - this.textPaint.measureText(this.getBottomText())) / 2.0F, bottomTextBaseline, this.textPaint);
        }

    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("saved_instance", super.onSaveInstanceState());
        bundle.putFloat("stroke_width", this.getStrokeWidth());
        bundle.putFloat("suffix_text_size", this.getSuffixTextSize());
        bundle.putFloat("suffix_text_padding", this.getSuffixTextPadding());
        bundle.putFloat("bottom_text_size", this.getBottomTextSize());
        bundle.putString("bottom_text", this.getBottomText());
        bundle.putFloat("text_size", this.getTextSize());
        bundle.putInt("text_color", this.getTextColor());
        bundle.putInt("progress", this.getProgress());
        bundle.putInt("max", this.getMax());
        bundle.putInt("finished_stroke_color", this.getFinishedStrokeColor());
        bundle.putInt("unfinished_stroke_color", this.getUnfinishedStrokeColor());
        bundle.putFloat("arc_angle", this.getArcAngle());
        bundle.putString("suffix", this.getSuffixText());
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle) {
            Bundle bundle = (Bundle)state;
            this.strokeWidth = bundle.getFloat("stroke_width");
            this.suffixTextSize = bundle.getFloat("suffix_text_size");
            this.suffixTextPadding = bundle.getFloat("suffix_text_padding");
            this.bottomTextSize = bundle.getFloat("bottom_text_size");
            this.bottomText = bundle.getString("bottom_text");
            this.textSize = bundle.getFloat("text_size");
            this.textColor = bundle.getInt("text_color");
            this.setMax(bundle.getInt("max"));
            this.setProgress(bundle.getInt("progress"));
            this.finishedStrokeColor = bundle.getInt("finished_stroke_color");
            this.unfinishedStrokeColor = bundle.getInt("unfinished_stroke_color");
            this.suffixText = bundle.getString("suffix");
            this.initPainters();
            super.onRestoreInstanceState(bundle.getParcelable("saved_instance"));
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
