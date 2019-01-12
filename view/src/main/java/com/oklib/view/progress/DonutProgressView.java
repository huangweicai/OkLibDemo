package com.oklib.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.oklib.view.R;


/**
 * 时间：2017/8/28
 * 作者：蓝天
 * 描述：圆形灌水进度
 */

public class DonutProgressView extends View {
    private Paint finishedPaint;
    private Paint unfinishedPaint;
    private Paint innerCirclePaint;
    protected Paint textPaint;
    protected Paint innerBottomTextPaint;
    private RectF finishedOuterRect;
    private RectF unfinishedOuterRect;
    private int attributeResourceId;
    private boolean showText;
    private float textSize;
    private int textColor;
    private int innerBottomTextColor;
    private float progress;
    private int max;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private int startingDegree;
    private float finishedStrokeWidth;
    private float unfinishedStrokeWidth;
    private int innerBackgroundColor;
    private String prefixText;
    private String suffixText;
    private String text;
    private float innerBottomTextSize;
    private String innerBottomText;
    private float innerBottomTextHeight;
    private final float default_stroke_width;
    private final int default_finished_color;
    private final int default_unfinished_color;
    private final int default_text_color;
    private final int default_inner_bottom_text_color;
    private final int default_inner_background_color;
    private final int default_max;
    private final int default_startingDegree;
    private final float default_text_size;
    private final float default_inner_bottom_text_size;
    private final int min_size;
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT = "text";
    private static final String INSTANCE_INNER_BOTTOM_TEXT_SIZE = "inner_bottom_text_size";
    private static final String INSTANCE_INNER_BOTTOM_TEXT = "inner_bottom_text";
    private static final String INSTANCE_INNER_BOTTOM_TEXT_COLOR = "inner_bottom_text_color";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width";
    private static final String INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width";
    private static final String INSTANCE_BACKGROUND_COLOR = "inner_background_color";
    private static final String INSTANCE_STARTING_DEGREE = "starting_degree";
    private static final String INSTANCE_INNER_DRAWABLE = "inner_drawable";

    public DonutProgressView(Context context) {
        this(context, (AttributeSet)null);
    }

    public DonutProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DonutProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.finishedOuterRect = new RectF();
        this.unfinishedOuterRect = new RectF();
        this.attributeResourceId = 0;
        this.progress = 0.0F;
        this.prefixText = "";
        this.suffixText = "%";
        this.text = null;
        this.default_finished_color = Color.rgb(66, 145, 241);
        this.default_unfinished_color = Color.rgb(204, 204, 204);
        this.default_text_color = Color.rgb(66, 145, 241);
        this.default_inner_bottom_text_color = Color.rgb(66, 145, 241);
        this.default_inner_background_color = 0;
        this.default_max = 100;
        this.default_startingDegree = 0;
        this.default_text_size = Utils.sp2px(this.getResources(), 18.0F);
        this.min_size = (int)Utils.dp2px(this.getResources(), 100.0F);
        this.default_stroke_width = Utils.dp2px(this.getResources(), 10.0F);
        this.default_inner_bottom_text_size = Utils.sp2px(this.getResources(), 18.0F);
        //TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, styleable.DonutProgress, defStyleAttr, 0);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.oklib_DonutProgressView);
        this.initByAttributes(attributes);
        attributes.recycle();
        this.initPainters();
    }

    protected void initPainters() {
        if(this.showText) {
            this.textPaint = new TextPaint();
            this.textPaint.setColor(this.textColor);
            this.textPaint.setTextSize(this.textSize);
            this.textPaint.setAntiAlias(true);
            this.innerBottomTextPaint = new TextPaint();
            this.innerBottomTextPaint.setColor(this.innerBottomTextColor);
            this.innerBottomTextPaint.setTextSize(this.innerBottomTextSize);
            this.innerBottomTextPaint.setAntiAlias(true);
        }

        this.finishedPaint = new Paint();
        this.finishedPaint.setColor(this.finishedStrokeColor);
        this.finishedPaint.setStyle(Paint.Style.STROKE);
        this.finishedPaint.setAntiAlias(true);
        this.finishedPaint.setStrokeWidth(this.finishedStrokeWidth);
        this.unfinishedPaint = new Paint();
        this.unfinishedPaint.setColor(this.unfinishedStrokeColor);
        this.unfinishedPaint.setStyle(Paint.Style.STROKE);
        this.unfinishedPaint.setAntiAlias(true);
        this.unfinishedPaint.setStrokeWidth(this.unfinishedStrokeWidth);
        this.innerCirclePaint = new Paint();
        this.innerCirclePaint.setColor(this.innerBackgroundColor);
        this.innerCirclePaint.setAntiAlias(true);
    }

    protected void initByAttributes(TypedArray attributes) {
//        this.finishedStrokeColor = attributes.getColor(oklib_DonutProgressView_oklib_donut_finished_color, this.default_finished_color);
//        this.unfinishedStrokeColor = attributes.getColor(oklib_DonutProgressView_oklib_donut_unfinished_color, this.default_unfinished_color);
//        this.showText = attributes.getBoolean(oklib_DonutProgressView_oklib_donut_show_text, true);
//        this.attributeResourceId = attributes.getResourceId(oklib_DonutProgressView_oklib_donut_inner_drawable, 0);
//        this.setMax(attributes.getInt(oklib_DonutProgressView_oklib_donut_max, 100));
//        this.setProgress(attributes.getFloat(oklib_DonutProgressView_oklib_donut_progress, 0.0F));
//        this.finishedStrokeWidth = attributes.getDimension(oklib_DonutProgressView_oklib_donut_finished_stroke_width, this.default_stroke_width);
//        this.unfinishedStrokeWidth = attributes.getDimension(oklib_DonutProgressView_oklib_donut_unfinished_stroke_width, this.default_stroke_width);
//        if(this.showText) {
//            if(attributes.getString(oklib_DonutProgressView_oklib_donut_prefix_text) != null) {
//                this.prefixText = attributes.getString(oklib_DonutProgressView_oklib_donut_prefix_text);
//            }
//
//            if(attributes.getString(oklib_DonutProgressView_oklib_donut_suffix_text) != null) {
//                this.suffixText = attributes.getString(oklib_DonutProgressView_oklib_donut_suffix_text);
//            }
//
//            if(attributes.getString(oklib_DonutProgressView_oklib_donut_text) != null) {
//                this.text = attributes.getString(oklib_DonutProgressView_oklib_donut_text);
//            }
//
//            this.textColor = attributes.getColor(oklib_DonutProgressView_oklib_donut_text_color, this.default_text_color);
//            this.textSize = attributes.getDimension(oklib_DonutProgressView_oklib_donut_text_size, this.default_text_size);
//            this.innerBottomTextSize = attributes.getDimension(oklib_DonutProgressView_oklib_donut_inner_bottom_text_size, this.default_inner_bottom_text_size);
//            this.innerBottomTextColor = attributes.getColor(oklib_DonutProgressView_oklib_donut_inner_bottom_text_color, this.default_inner_bottom_text_color);
//            this.innerBottomText = attributes.getString(oklib_DonutProgressView_oklib_donut_inner_bottom_text);
//        }
//
//        this.innerBottomTextSize = attributes.getDimension(oklib_DonutProgressView_oklib_donut_inner_bottom_text_size, this.default_inner_bottom_text_size);
//        this.innerBottomTextColor = attributes.getColor(oklib_DonutProgressView_oklib_donut_inner_bottom_text_color, this.default_inner_bottom_text_color);
//        this.innerBottomText = attributes.getString(oklib_DonutProgressView_oklib_donut_inner_bottom_text);
//        this.startingDegree = attributes.getInt(oklib_DonutProgressView_oklib_donut_circle_starting_degree, 0);
//        this.innerBackgroundColor = attributes.getColor(oklib_DonutProgressView_oklib_donut_background_color, 0);
    }

    public void invalidate() {
        this.initPainters();
        super.invalidate();
    }

    public boolean isShowText() {
        return this.showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public float getFinishedStrokeWidth() {
        return this.finishedStrokeWidth;
    }

    public void setFinishedStrokeWidth(float finishedStrokeWidth) {
        this.finishedStrokeWidth = finishedStrokeWidth;
        this.invalidate();
    }

    public float getUnfinishedStrokeWidth() {
        return this.unfinishedStrokeWidth;
    }

    public void setUnfinishedStrokeWidth(float unfinishedStrokeWidth) {
        this.unfinishedStrokeWidth = unfinishedStrokeWidth;
        this.invalidate();
    }

    private float getProgressAngle() {
        return this.getProgress() / (float)this.max * 360.0F;
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        if(this.progress > (float)this.getMax()) {
            this.progress %= (float)this.getMax();
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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        this.invalidate();
    }

    public String getSuffixText() {
        return this.suffixText;
    }

    public void setSuffixText(String suffixText) {
        this.suffixText = suffixText;
        this.invalidate();
    }

    public String getPrefixText() {
        return this.prefixText;
    }

    public void setPrefixText(String prefixText) {
        this.prefixText = prefixText;
        this.invalidate();
    }

    public int getInnerBackgroundColor() {
        return this.innerBackgroundColor;
    }

    public void setInnerBackgroundColor(int innerBackgroundColor) {
        this.innerBackgroundColor = innerBackgroundColor;
        this.invalidate();
    }

    public String getInnerBottomText() {
        return this.innerBottomText;
    }

    public void setInnerBottomText(String innerBottomText) {
        this.innerBottomText = innerBottomText;
        this.invalidate();
    }

    public float getInnerBottomTextSize() {
        return this.innerBottomTextSize;
    }

    public void setInnerBottomTextSize(float innerBottomTextSize) {
        this.innerBottomTextSize = innerBottomTextSize;
        this.invalidate();
    }

    public int getInnerBottomTextColor() {
        return this.innerBottomTextColor;
    }

    public void setInnerBottomTextColor(int innerBottomTextColor) {
        this.innerBottomTextColor = innerBottomTextColor;
        this.invalidate();
    }

    public int getStartingDegree() {
        return this.startingDegree;
    }

    public void setStartingDegree(int startingDegree) {
        this.startingDegree = startingDegree;
        this.invalidate();
    }

    public int getAttributeResourceId() {
        return this.attributeResourceId;
    }

    public void setAttributeResourceId(int attributeResourceId) {
        this.attributeResourceId = attributeResourceId;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(this.measure(widthMeasureSpec), this.measure(heightMeasureSpec));
        this.innerBottomTextHeight = (float)(this.getHeight() - this.getHeight() * 3 / 4);
    }

    private int measure(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result;
        if(mode == 1073741824) {
            result = size;
        } else {
            result = this.min_size;
            if(mode == -2147483648) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float delta = Math.max(this.finishedStrokeWidth, this.unfinishedStrokeWidth);
        this.finishedOuterRect.set(delta, delta, (float)this.getWidth() - delta, (float)this.getHeight() - delta);
        this.unfinishedOuterRect.set(delta, delta, (float)this.getWidth() - delta, (float)this.getHeight() - delta);
        float innerCircleRadius = ((float)this.getWidth() - Math.min(this.finishedStrokeWidth, this.unfinishedStrokeWidth) + Math.abs(this.finishedStrokeWidth - this.unfinishedStrokeWidth)) / 2.0F;
        canvas.drawCircle((float)this.getWidth() / 2.0F, (float)this.getHeight() / 2.0F, innerCircleRadius, this.innerCirclePaint);
        canvas.drawArc(this.finishedOuterRect, (float)this.getStartingDegree(), this.getProgressAngle(), false, this.finishedPaint);
        canvas.drawArc(this.unfinishedOuterRect, (float)this.getStartingDegree() + this.getProgressAngle(), 360.0F - this.getProgressAngle(), false, this.unfinishedPaint);
        if(this.showText) {
            String bitmap = this.text != null?this.text:this.prefixText + this.progress + this.suffixText;
            float bottomTextBaseline;
            if(!TextUtils.isEmpty(bitmap)) {
                bottomTextBaseline = this.textPaint.descent() + this.textPaint.ascent();
                canvas.drawText(bitmap, ((float)this.getWidth() - this.textPaint.measureText(bitmap)) / 2.0F, ((float)this.getWidth() - bottomTextBaseline) / 2.0F, this.textPaint);
            }

            if(!TextUtils.isEmpty(this.getInnerBottomText())) {
                this.innerBottomTextPaint.setTextSize(this.innerBottomTextSize);
                bottomTextBaseline = (float)this.getHeight() - this.innerBottomTextHeight - (this.textPaint.descent() + this.textPaint.ascent()) / 2.0F;
                canvas.drawText(this.getInnerBottomText(), ((float)this.getWidth() - this.innerBottomTextPaint.measureText(this.getInnerBottomText())) / 2.0F, bottomTextBaseline, this.innerBottomTextPaint);
            }
        }

        if(this.attributeResourceId != 0) {
            Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), this.attributeResourceId);
            canvas.drawBitmap(bitmap1, (float)(this.getWidth() - bitmap1.getWidth()) / 2.0F, (float)(this.getHeight() - bitmap1.getHeight()) / 2.0F, (Paint)null);
        }

    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("saved_instance", super.onSaveInstanceState());
        bundle.putInt("text_color", this.getTextColor());
        bundle.putFloat("text_size", this.getTextSize());
        bundle.putFloat("inner_bottom_text_size", this.getInnerBottomTextSize());
        bundle.putFloat("inner_bottom_text_color", (float)this.getInnerBottomTextColor());
        bundle.putString("inner_bottom_text", this.getInnerBottomText());
        bundle.putInt("inner_bottom_text_color", this.getInnerBottomTextColor());
        bundle.putInt("finished_stroke_color", this.getFinishedStrokeColor());
        bundle.putInt("unfinished_stroke_color", this.getUnfinishedStrokeColor());
        bundle.putInt("max", this.getMax());
        bundle.putInt("starting_degree", this.getStartingDegree());
        bundle.putFloat("progress", this.getProgress());
        bundle.putString("suffix", this.getSuffixText());
        bundle.putString("prefix", this.getPrefixText());
        bundle.putString("text", this.getText());
        bundle.putFloat("finished_stroke_width", this.getFinishedStrokeWidth());
        bundle.putFloat("unfinished_stroke_width", this.getUnfinishedStrokeWidth());
        bundle.putInt("inner_background_color", this.getInnerBackgroundColor());
        bundle.putInt("inner_drawable", this.getAttributeResourceId());
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle) {
            Bundle bundle = (Bundle)state;
            this.textColor = bundle.getInt("text_color");
            this.textSize = bundle.getFloat("text_size");
            this.innerBottomTextSize = bundle.getFloat("inner_bottom_text_size");
            this.innerBottomText = bundle.getString("inner_bottom_text");
            this.innerBottomTextColor = bundle.getInt("inner_bottom_text_color");
            this.finishedStrokeColor = bundle.getInt("finished_stroke_color");
            this.unfinishedStrokeColor = bundle.getInt("unfinished_stroke_color");
            this.finishedStrokeWidth = bundle.getFloat("finished_stroke_width");
            this.unfinishedStrokeWidth = bundle.getFloat("unfinished_stroke_width");
            this.innerBackgroundColor = bundle.getInt("inner_background_color");
            this.attributeResourceId = bundle.getInt("inner_drawable");
            this.initPainters();
            this.setMax(bundle.getInt("max"));
            this.setStartingDegree(bundle.getInt("starting_degree"));
            this.setProgress(bundle.getFloat("progress"));
            this.prefixText = bundle.getString("prefix");
            this.suffixText = bundle.getString("suffix");
            this.text = bundle.getString("text");
            super.onRestoreInstanceState(bundle.getParcelable("saved_instance"));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public void setDonut_progress(String percent) {
        if(!TextUtils.isEmpty(percent)) {
            this.setProgress((float)Integer.parseInt(percent));
        }

    }
}

