package com.hwc.oklib.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.oklib.R;
import com.hwc.oklib.view.chart.StackBarChartView;


/**
 * 时间：2017/9/25
 * 作者：黄伟才
 * 描述：止鼾枕柱状图
 */

public class SnoreBarChartView extends LinearLayout {
    StackBarChartView stackBarChartView;
    HorizontalScrollView hsvLayout;
    TextView tvYTop;
    TextView tvYCenter;
    TextView tvYBottom;
    TextView tvLeftColor;
    TextView tvLeft;
    TextView tvCenterColor;
    TextView tvCenter;
    TextView tvRightColor;
    TextView tvRight;

    public SnoreBarChartView(Context context) {
        super(context);
        init();
    }

    public SnoreBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnoreBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SnoreBarChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_snore_layout, this, true);
        stackBarChartView = (StackBarChartView) view.findViewById(R.id.snoreBarChartView);
        hsvLayout = (HorizontalScrollView) view.findViewById(R.id.hsv_layout);
        tvYTop = (TextView) view.findViewById(R.id.tv_y_top);
        tvYCenter = (TextView) view.findViewById(R.id.tv_y_center);
        tvYBottom = (TextView) view.findViewById(R.id.tv_y_bottom);
        tvLeftColor = (TextView) view.findViewById(R.id.tv_left_color);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvCenterColor = (TextView) view.findViewById(R.id.tv_center_color);
        tvCenter = (TextView) view.findViewById(R.id.tv_center);
        tvRightColor = (TextView) view.findViewById(R.id.tv_right_color);
        tvRight = (TextView) view.findViewById(R.id.tv_right);

        //y轴刻度
        tvYTop.setText(yMaxValue + yTextUnit);
        tvYCenter.setText(yMaxValue * 2 / 3 + yTextUnit);
        tvYBottom.setText(yMaxValue * 1 / 3 + yTextUnit);
        tvYTop.setTextColor(yTextColor);
        tvYCenter.setTextColor(yTextColor);
        tvYBottom.setTextColor(yTextColor);

        tvLeftColor.setBackgroundColor(Color.parseColor(leftContent[0]));
        tvLeft.setText(leftContent[1]);
        tvCenterColor.setBackgroundColor(Color.parseColor(centerContent[0]));
        tvCenter.setText(centerContent[1]);
        tvRightColor.setBackgroundColor(Color.parseColor(rightContent[0]));
        tvRight.setText(rightContent[1]);
    }

    protected int yMaxValue = 150;//y最大值，用于计算比例
    protected int yTextColor = 0xff222222;//y最大值，用于计算比例
    private String yTextUnit = "min";//y刻度文本单位
    private String[] leftContent = {"#FFFD7636", "高强度"};
    private String[] centerContent = {"#FF22A9DF", "中强度"};
    private String[] rightContent = {"#FF19D0C4", "低强度"};

    public void setChartViewWidth(int width) {
        LayoutParams llParams = (LayoutParams) stackBarChartView.getLayoutParams();
        llParams.width = width;
        stackBarChartView.setLayoutParams(llParams);
        stackBarChartView.width = width;
        stackBarChartView.invalidate();
    }

    public void setyMaxValue(int yMaxValue) {
        this.yMaxValue = yMaxValue;
        tvYTop.setText(yMaxValue + yTextUnit);
        tvYCenter.setText(yMaxValue * 2 / 3 + yTextUnit);
        tvYBottom.setText(yMaxValue * 1 / 3 + yTextUnit);
    }

    public int getyMaxValue() {
        return yMaxValue;
    }

    public void setyTextColor(int yTextColor) {
        this.yTextColor = yTextColor;
    }

    public void setyTextUnit(String yTextUnit) {
        this.yTextUnit = yTextUnit;
    }

    public void setLeftContent(String[] leftContent) {
        this.leftContent = leftContent;
    }

    public void setCenterContent(String[] centerContent) {
        this.centerContent = centerContent;
    }

    public void setRightContent(String[] rightContent) {
        this.rightContent = rightContent;
    }

    public StackBarChartView getStackBarChartView() {
        return stackBarChartView;
    }

}
