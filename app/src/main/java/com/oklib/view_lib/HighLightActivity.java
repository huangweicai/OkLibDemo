package com.oklib.view_lib;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.high_light.HighLight;
import com.oklib.view.high_light.interfaces.HighLightInterface;
import com.oklib.view.high_light.position.OnBottomPosCallback;
import com.oklib.view.high_light.position.OnLeftPosCallback;
import com.oklib.view.high_light.position.OnRightPosCallback;
import com.oklib.view.high_light.position.OnTopPosCallback;
import com.oklib.view.high_light.shape.BaseLightShape;
import com.oklib.view.high_light.shape.CircleLightShape;
import com.oklib.view.high_light.shape.OvalLightShape;
import com.oklib.view.high_light.shape.RectLightShape;
import com.oklib.view.high_light.view.HightLightView;


/**
 * 时间：2018/2/7
 * 作者：蓝天
 * 描述：显示高亮提示view
 */

public class HighLightActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_high_light;
    }

    private HighLight mHightLight;
    @Override
    protected void initView() {
        showNextTipViewOnCreated();
    }

    /**
     * 备注：执行场景，在进入界面执行，通过setOnLayoutCallback判断show的时机
     *
     * 当界面布局完成显示next模式提示布局
     * 显示方法必须在onLayouted中调用
     * 适用于Activity及Fragment中使用
     * 可以直接在onCreated方法中调用
     *
     * @author isanwenyu@163.com
     */
    public void showNextTipViewOnCreated() {
        mHightLight = new HighLight(HighLightActivity.this)//
                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
                .autoRemove(false)
                .enableNext()
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {
                    @Override
                    public void onLayouted() {
                        //界面布局完成添加tipview
                        mHightLight.addHighLight(R.id.btn_rightLight, R.layout.info_gravity_left_down, new OnLeftPosCallback(45), new RectLightShape())
                                .addHighLight(R.id.btn_light, R.layout.info_gravity_left_down, new OnRightPosCallback(5), new CircleLightShape())
                                .addHighLight(R.id.btn_bottomLight, R.layout.info_gravity_left_down, new OnTopPosCallback(), new CircleLightShape());
                        //然后显示高亮布局
                        mHightLight.show();
                    }
                })
                .setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        Toast.makeText(HighLightActivity.this, "clicked and show next tip view by yourself", Toast.LENGTH_SHORT).show();
                        mHightLight.next();
                    }
                });
    }


    /**
     * 备注：执行场景，进入界面一段时间，手动显示，一次性显示所有提示内容
     * 显示我知道了提示高亮布局
     *
     * @param view id为R.id.iv_known的控件
     * @author isanwenyu@163.com
     */
    public void showKnownTipView(View view) {
        mHightLight = new HighLight(HighLightActivity.this)//
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
                .intercept(false)//设置拦截属性为false 高亮布局不影响后面布局的滑动效果 而且使下方点击回调失效
                .setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        Toast.makeText(HighLightActivity.this, "clicked and remove HightLight view by yourself", Toast.LENGTH_SHORT).show();
                        remove(null);
                    }
                })
                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
                .addHighLight(R.id.btn_rightLight, R.layout.info_known, new OnLeftPosCallback(45), new RectLightShape())
                .addHighLight(R.id.btn_light, R.layout.info_known, new OnRightPosCallback(5), new CircleLightShape(0, 0, 0))
                .addHighLight(R.id.btn_bottomLight, R.layout.info_known, new OnTopPosCallback(), new CircleLightShape())
                .addHighLight(view, R.layout.info_known, new OnBottomPosCallback(10), new OvalLightShape(5, 5, 20));
        mHightLight.show();

//        //added by isanwenyu@163.com 设置监听器只有最后一个添加到HightLightView的knownView响应了事件
//        //优化在布局中声明onClick方法 {@link #clickKnown(view)}响应所有R.id.iv_known的控件的点击事件
//        View decorLayout = mHightLight.getHightLightView();
//        ImageView knownView = (ImageView) decorLayout.findViewById(R.id.iv_known);
//        knownView.setOnClickListener(new View.OnClickListener()
//          {
//            @Override
//            public void onClick(View view) {
//                remove(null);
//            }
//        });
    }

    /**
     * 备注：执行场景，进入界面一段时间，手动显示，一次性显示所有提示，并点击界面任意位置关闭
     * @param view
     */
    public void showTipView(View view) {
        mHightLight = new HighLight(HighLightActivity.this)//
                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
                .addHighLight(R.id.btn_rightLight, R.layout.info_gravity_left_down, new OnLeftPosCallback(45), new RectLightShape())
                .addHighLight(R.id.btn_light, R.layout.info_gravity_left_down, new OnRightPosCallback(5), new CircleLightShape())
                .addHighLight(R.id.btn_bottomLight, R.layout.info_gravity_left_down, new OnTopPosCallback(), new CircleLightShape())
                .addHighLight(view, R.layout.info_gravity_left_down, new OnBottomPosCallback(60), new CircleLightShape());
        mHightLight.show();
    }


    /**
     * 备注：执行场景，进入界面一段时间，手动显示，点击“知道了”切换下一个提示
     * 显示 next模式 我知道了提示高亮布局
     *
     * @param view id为R.id.iv_known的控件
     * @author isanwenyu@163.com
     */
    public void showNextKnownTipView(View view) {
        mHightLight = new HighLight(HighLightActivity.this)//
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
//                .intercept(false)//设置拦截属性为false 高亮布局不影响后面布局的滑动效果
                .intercept(true)//拦截属性默认为true 使下方ClickCallback生效
                .enableNext()//开启next模式并通过show方法显示 然后通过调用next()方法切换到下一个提示布局，直到移除自身
//                .setClickCallback(new HighLight.OnClickCallback() {
//                    @Override
//                    public void onClick() {
//                        Toast.makeText(HighLightActivity.this, "clicked and remove HightLight view by yourself", Toast.LENGTH_SHORT).show();
//                        remove(null);
//                    }
//                })
                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
                .addHighLight(R.id.btn_rightLight, R.layout.info_known, new OnLeftPosCallback(45), new RectLightShape(0, 0, 15, 0, 0))//矩形去除圆角
                .addHighLight(R.id.btn_light, R.layout.info_known, new OnRightPosCallback(5), new BaseLightShape(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0) {
                    @Override
                    protected void resetRectF4Shape(RectF viewPosInfoRectF, float dx, float dy) {
                        //缩小高亮控件范围
                        viewPosInfoRectF.inset(dx, dy);
                    }

                    @Override
                    protected void drawShape(Bitmap bitmap, HighLight.ViewPosInfo viewPosInfo) {
                        //custom your hight light shape 自定义高亮形状
                        Canvas canvas = new Canvas(bitmap);
                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        paint.setDither(true);
                        paint.setAntiAlias(true);
                        //blurRadius必须大于0
                        if (blurRadius > 0) {
                            paint.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.SOLID));
                        }
                        RectF rectF = viewPosInfo.rectF;
                        canvas.drawOval(rectF, paint);
                    }
                })
                .addHighLight(R.id.btn_bottomLight, R.layout.info_known, new OnTopPosCallback(), new CircleLightShape())
                .addHighLight(view, R.layout.info_known, new OnBottomPosCallback(10), new OvalLightShape(5, 5, 20))
                .setOnRemoveCallback(new HighLightInterface.OnRemoveCallback() {//监听移除回调
                    @Override
                    public void onRemove() {
                        Toast.makeText(HighLightActivity.this, "The HightLight view has been removed", Toast.LENGTH_SHORT).show();

                    }
                })
                .setOnShowCallback(new HighLightInterface.OnShowCallback() {//监听显示回调
                    @Override
                    public void onShow(HightLightView hightLightView) {
                        Toast.makeText(HighLightActivity.this, "The HightLight view has been shown", Toast.LENGTH_SHORT).show();
                    }
                }).setOnNextCallback(new HighLightInterface.OnNextCallback() {
                    @Override
                    public void onNext(HightLightView hightLightView, View targetView, View tipView) {
                        // targetView 目标按钮 tipView添加的提示布局 可以直接找到'我知道了'按钮添加监听事件等处理
                        Toast.makeText(HighLightActivity.this, "The HightLight show next TipView，targetViewID:" + (targetView == null ? null : targetView.getId()) + ",tipViewID:" + (tipView == null ? null : tipView.getId()), Toast.LENGTH_SHORT).show();
                    }
                });
        mHightLight.show();
    }


    /**
     * 备注：执行场景，进入界面一段时间，手动显示，点击显示界面切换下一个提示
     * 显示next模式提示布局
     *
     * @param view
     * @author isanwenyu@163.com
     */
    public void showNextTipView(View view) {
        mHightLight = new HighLight(HighLightActivity.this)//
                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
                .addHighLight(R.id.btn_rightLight, R.layout.info_gravity_left_down, new OnLeftPosCallback(45), new RectLightShape())
                .addHighLight(R.id.btn_light, R.layout.info_gravity_left_down, new OnRightPosCallback(5), new CircleLightShape())
                .addHighLight(R.id.btn_bottomLight, R.layout.info_gravity_left_down, new OnTopPosCallback(), new CircleLightShape())
                .addHighLight(view, R.layout.info_gravity_left_down, new OnBottomPosCallback(60), new CircleLightShape())
                .autoRemove(false)
                .enableNext()
                .setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        Toast.makeText(HighLightActivity.this, "clicked and show next tip view by yourself", Toast.LENGTH_SHORT).show();
                        mHightLight.next();
                    }
                });
        mHightLight.show();
    }


    /**
     * 响应所有R.id.iv_known的控件的点击事件
     * <p>
     * 移除高亮布局
     * </p>
     *
     * @param view
     */
    public void clickKnown(View view) {
        if (mHightLight.isShowing() && mHightLight.isNext())//如果开启next模式
        {
            mHightLight.next();
        } else {
            remove(null);
        }
    }

    private void showTipMask() {
//        mHightLight = new HighLight(HighLightActivity.this)//
//                .anchor(findViewById(R.id.id_container))
        //如果是Activity上增加引导层，不需要设置anchor
//                .addHighLight(R.id.id_btn_important, R.layout.info_up,
//                        new HighLight.OnPosCallback()
//                        {
//                            @Override
//                            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo)
//                            {
//                                marginInfo.leftMargin = rectF.right - rectF.width() / 2;
//                                marginInfo.topMargin = rectF.bottom;
//                            }
//                        })//
//                .addHighLight(R.id.id_btn_amazing, R.layout.info_down, new HighLight.OnPosCallback()
//                {
//                    /**
//                     * @param rightMargin 高亮view在anchor中的右边距
//                     * @param bottomMargin 高亮view在anchor中的下边距
//                     * @param rectF 高亮view的l,t,r,b,w,h都有
//                     * @param marginInfo 设置你的布局的位置，一般设置l,t或者r,b
//                     */
//                    @Override
//                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo)
//                    {
//                        marginInfo.rightMargin = rightMargin + rectF.width() / 2;
//                        marginInfo.bottomMargin = bottomMargin + rectF.height();
//                    }
//
//                });
//        .addHighLight(R.id.id_btn_important_right,R.layout.info_gravity_right_up, new HighLight.OnPosCallback(){
//
//
//            @Override
//            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
//                marginInfo.rightMargin = rightMargin;
//                marginInfo.topMargin = rectF.top + rectF.height();
//            }
//        })
//        .addHighLight(R.id.id_btn_whoami, R.layout.info_gravity_left_down, new HighLight.OnPosCallback() {
//
//
//            @Override
//            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
//                marginInfo.leftMargin = rectF.right - rectF.width()/2;
//                marginInfo.bottomMargin = bottomMargin + rectF.height();
//            }
//        })
//        .setClickCallback(new HighLight.OnClickCallback() {
//            @Override
//            public void onClick() {
//                Toast.makeText(HighLightActivity.this,"clicked",Toast.LENGTH_SHORT).show();
//            }
//        });

//        mHightLight.show();
//        mHightLight = new HighLight(HighLightActivity.this)//
//                .anchor(findViewById(R.id.id_container))//如果是Activity上增加引导层，不需要设置anchor
//                .addHighLight(R.id.btn_rightLight,R.layout.info_left, new OnLeftPosCallback(10),new RectLightShape())
//                .addHighLight(R.id.btn_light,R.layout.info_right,new OnRightPosCallback(),new CircleLightShape())
//                .addHighLight(R.id.btn_bottomLight,R.layout.info_up,new OnTopPosCallback(46),new CircleLightShape())
//                .addHighLight(R.id.id_btn_amazing,R.layout.info_up,new OnBottomPosCallback(46),new CircleLightShape());
//        mHightLight.show();
    }


    public void remove(View view) {
        mHightLight.remove();
    }

    public void add(View view) {
        mHightLight.show();
    }


}
