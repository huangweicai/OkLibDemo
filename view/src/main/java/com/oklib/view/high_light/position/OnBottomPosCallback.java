package com.oklib.view.high_light.position;

import android.graphics.RectF;

import com.oklib.view.high_light.HighLight;


/**
 * Created by caizepeng on 16/8/20.
 */
public  class OnBottomPosCallback extends OnBaseCallback{
    public OnBottomPosCallback() {
    }

    public OnBottomPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin;
        marginInfo.topMargin = rectF.top + rectF.height()+offset;
    }

}
