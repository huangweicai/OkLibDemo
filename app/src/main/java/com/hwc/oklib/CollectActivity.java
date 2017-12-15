package com.hwc.oklib;

import android.support.v4.app.Fragment;

import com.hwc.oklib.base.BaseAppTabLayoutActivity;
import com.hwc.oklib.fragment.CollectFunctionFragment;
import com.hwc.oklib.fragment.CollectPublishFragment;


/**
 * 时间：2017/8/25
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：收藏
 */

public class CollectActivity extends BaseAppTabLayoutActivity {

    @Override
    protected void initVariable() {
    }

    @Override
    protected void init() {

    }

    @Override
    protected void requestNet() {
        tb_toolbar.setNavIcon(R.drawable.white_back_icon);
    }

    @Override
    protected String centerTitle() {
        return getIntent().getStringExtra(Common.TITLE);
    }

    @Override
    protected String[] tabTitles() {
        return new String[]{"功能收藏", "文章收藏"};
    }

    @Override
    protected Fragment[] tabFragments() {
        return new Fragment[]{new CollectFunctionFragment(), new CollectPublishFragment()};
    }

}
