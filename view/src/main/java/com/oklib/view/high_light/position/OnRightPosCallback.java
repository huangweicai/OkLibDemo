package com.oklib.view.high_light.position;

import android.graphics.RectF;

import com.oklib.view.high_light.HighLight;


/**
 * Created by caizepeng on 16/8/20.
 */
public class OnRightPosCallback extends OnBaseCallback {
    public OnRightPosCallback() {
    }

    public OnRightPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = rectF.right + offset;
        marginInfo.topMargin = rectF.top;
    }
}
