package com.hwc.oklib.common_components;

import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.menu_view.YMenu;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/9/27
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：菜单view使用
 */

public class MenuViewActivity extends BaseAppActivity implements YMenu.OnOptionsClickListener {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_menu_view;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_menu_view.xml", BASE_RES +"/layout/activity_menu_view.xml"));
                        showDetail();
                    }
                });
    }

    private YMenu mYMenu1;
    private YMenu mYMenu2;
    private YMenu mYMenu3;
    private YMenu mYMenu4;
    private YMenu mYMenu5;
    private YMenu mYMenu6;
    @Override
    protected void initView() {
        mYMenu1 = (YMenu) findViewById(R.id.ymv1);
        mYMenu1.setOnOptionsClickListener(this);
//        mYMenu.setBanArray(3,4,7,5,6);
        mYMenu1.setOptionDrawableIds(R.drawable.oklib_zero, R.drawable.oklib_one, R.drawable.oklib_two
                , R.drawable.oklib_three, R.drawable.oklib_four, R.drawable.oklib_five, R.drawable.oklib_six
                , R.drawable.oklib_seven, R.drawable.oklib_eight);
//        mYMenu.setMenuOpenAnimation(null);
//        mYMenu.setMenuCloseAnimation(null);

        mYMenu2 = (YMenu) findViewById(R.id.ymv2);
        mYMenu2.setOnOptionsClickListener(this);
//        mYMenu.setBanArray(3,4,7,5,6);
        mYMenu2.setOptionDrawableIds(R.drawable.oklib_zero, R.drawable.oklib_one, R.drawable.oklib_two
                , R.drawable.oklib_three, R.drawable.oklib_four, R.drawable.oklib_five, R.drawable.oklib_six
                , R.drawable.oklib_seven, R.drawable.oklib_eight);

        mYMenu3 = (YMenu) findViewById(R.id.ymv3);
        mYMenu3.setOnOptionsClickListener(this);
//        mYMenu.setBanArray(3,4,7,5,6);
        mYMenu3.setOptionDrawableIds(R.drawable.oklib_zero, R.drawable.oklib_one, R.drawable.oklib_two
                , R.drawable.oklib_three, R.drawable.oklib_four, R.drawable.oklib_five, R.drawable.oklib_six
                , R.drawable.oklib_seven, R.drawable.oklib_eight);

        mYMenu4 = (YMenu) findViewById(R.id.ymv4);
        mYMenu4.setOnOptionsClickListener(this);
//        mYMenu.setBanArray(3,4,7,5,6);
        mYMenu4.setOptionDrawableIds(R.drawable.oklib_zero, R.drawable.oklib_one, R.drawable.oklib_two
                , R.drawable.oklib_three, R.drawable.oklib_four, R.drawable.oklib_five, R.drawable.oklib_six
                , R.drawable.oklib_seven, R.drawable.oklib_eight);

        mYMenu5 = (YMenu) findViewById(R.id.ymv5);
        mYMenu5.setOnOptionsClickListener(this);
//        mYMenu.setBanArray(3,4,7,5,6);
        mYMenu5.setOptionDrawableIds(R.drawable.oklib_zero, R.drawable.oklib_one, R.drawable.oklib_two
                , R.drawable.oklib_three, R.drawable.oklib_four, R.drawable.oklib_five, R.drawable.oklib_six
                , R.drawable.oklib_seven, R.drawable.oklib_eight);

        mYMenu6 = (YMenu) findViewById(R.id.ymv6);
        mYMenu6.setOnOptionsClickListener(this);
//        mYMenu.setBanArray(3,4,7,5,6);
        mYMenu6.setOptionDrawableIds(R.drawable.oklib_zero, R.drawable.oklib_one, R.drawable.oklib_two
                , R.drawable.oklib_three, R.drawable.oklib_four, R.drawable.oklib_five, R.drawable.oklib_six
                , R.drawable.oklib_seven, R.drawable.oklib_eight);
    }

    @Override
    protected void initNet() {

    }

    @Override
    public void onOptionsClick(int index) {
        switch (index){
            case 0:
                ToastUtil.show("0");
                break;
            case 1:
                ToastUtil.show("1");
                break;
            case 2:
                ToastUtil.show("2");
                break;
            case 3:
                ToastUtil.show("3");
                break;
            case 4:
                ToastUtil.show("4");
                break;
            case 5:
                ToastUtil.show("5");
                break;
            case 6:
                ToastUtil.show("6");
                break;
            case 7:
                ToastUtil.show("7");
                break;
            case 8:
                ToastUtil.show("8");
                break;
        }
    }
}
