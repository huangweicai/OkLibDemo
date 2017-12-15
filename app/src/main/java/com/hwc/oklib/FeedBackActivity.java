package com.hwc.oklib;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bmoblib.BmobInsertHelp;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.EditTextWithDelete;
import com.hwc.oklib.util.toast.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;

/**
 * 时间：2017/8/8
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：反馈界面
 */

public class FeedBackActivity extends BaseAppActivity {
    private EditTextWithDelete et_input_content;
    private EditTextWithDelete et_contact_way;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_feedback;
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
                .setRightTitle("提交", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        String content = et_input_content.getText().toString();
                        String contactWay = et_contact_way.getText().toString();
                        if (TextUtils.isEmpty(content)) {
                            ToastUtil.show("反馈内容不可为空");
                            return;
                        }
                        BmobInsertHelp.insertFeedBack(content, contactWay, contactType, new BmobInsertHelp.OnInsertSuccessListener() {
                            @Override
                            public void success(String objectId) {
                                ToastUtil.show("提交成功，作者会及时查阅，谢谢！");
                                finish();
                            }

                            @Override
                            public void fail(BmobException e) {
                                ToastUtil.show("提交失败，请重新操作！");
                            }
                        });
                    }
                });
    }


    private List<String> data_list;
    private Spinner spinner;
    private ArrayAdapter arr_adapter;
    private String contactType;

    @Override
    protected void initView() {
        et_input_content = findView(R.id.et_input_content);
        et_contact_way = findView(R.id.et_contact_way);
        et_contact_way.setFilters(new InputFilter[]{filter});

        spinner = findView(R.id.spinner);
        data_list = new ArrayList();
        data_list.add("手机");
        data_list.add("微信");
        data_list.add("QQ");
        data_list.add("邮箱");
        //适配器
        arr_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data_list);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //只能设置项选择监听，不能设置项点击监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //默认第一次会被执行，contactType默认就会被赋值角标为0的元素
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                contactType = data_list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initNet() {

    }

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String regEx =   "[`~!@#$%^&*()+=～|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？-]";//|^a-zA-Z0-9_|
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        }
    };
}
