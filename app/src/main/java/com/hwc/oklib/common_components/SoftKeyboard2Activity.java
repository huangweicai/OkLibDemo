//************************************
// 作者:Fay
// 邮箱:xiaofei1.xu@midea.com
//************************************
package com.hwc.oklib.common_components;


import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.window.DateTimeDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.hwc.oklib.Common.BASE_RES;

/**
   * 时间：2017/11/28
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：自定义软键盘StringBuild实现（手动添加用药记录）
   */
public class SoftKeyboard2Activity extends BaseAppActivity implements View.OnTouchListener, View.OnClickListener, DateTimeDialog.MyOnDateSetListener {
    private LinearLayout ll_date;
    private TextView tv_date;
    private TextView tv_time;
    private EditText et_count;
    private Button btn_save;

    // 日期 格式化 工具
    private SimpleDateFormat mDateFormatter = new SimpleDateFormat("yyyy-MM-d");
    private SimpleDateFormat mTimeFormatter = new SimpleDateFormat("HH:mm:ss");//HH：24小时 hh：12小时制
    private DateTimeDialog dateTimeDialog;

    //血压输入值最大长度
    private final int MAX_LENGTH = "2147483647".length() - 1;

     @Override
     protected int initLayoutId() {
         return R.layout.activity_softkeyboard_2;
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
                         mBeans.add(new FunctionDetailBean("activity_softkeyboard_2.xml", BASE_RES +"/layout/activity_softkeyboard_2.xml"));
                         showDetail();
                     }
                 });
     }

     @Override
     protected void initView() {
         stringBuilder = new StringBuilder();

         ll_date = findView(R.id.ll_date);
         tv_date = findView(R.id.tv_date);
         tv_time = findView(R.id.tv_time);
         et_count = findView(R.id.et_count);
         btn_save = findView(R.id.btn_save);

         et_count.setOnTouchListener(this);

         ll_date.setOnClickListener(this);
         btn_save.setOnClickListener(this);

         //初始化时间显示
         Calendar calendar = Calendar.getInstance();
         Date date = calendar.getTime();
         tv_date.setText(mDateFormatter.format(date));
         tv_time.setText(mTimeFormatter.format(date));

         dateTimeDialog = new DateTimeDialog(this, null, this);

         initKeyboard();
     }

     @Override
     protected void initNet() {

     }

    private StringBuilder stringBuilder;

    /**
     * 方法描述: 初始化键盘
     */
    private void initKeyboard() {
        TextView btn_0 = ((TextView) findViewById(R.id.btn_0));
        TextView btn_1 = ((TextView) findViewById(R.id.btn_1));
        TextView btn_2 = ((TextView) findViewById(R.id.btn_2));
        TextView btn_3 = ((TextView) findViewById(R.id.btn_3));
        TextView btn_4 = ((TextView) findViewById(R.id.btn_4));
        TextView btn_5 = ((TextView) findViewById(R.id.btn_5));
        TextView btn_6 = ((TextView) findViewById(R.id.btn_6));
        TextView btn_7 = ((TextView) findViewById(R.id.btn_7));
        TextView btn_8 = ((TextView) findViewById(R.id.btn_8));
        TextView btn_9 = ((TextView) findViewById(R.id.btn_9));
        View.OnClickListener ol_num = new View.OnClickListener() {//数字按键
            @Override
            public void onClick(View v) {
                stringBuilder.append(((TextView) v).getText());
                et_count.setText(stringBuilder);
                et_count.setSelection(stringBuilder.length());
            }
        };
        btn_0.setOnClickListener(ol_num);
        btn_1.setOnClickListener(ol_num);
        btn_2.setOnClickListener(ol_num);
        btn_3.setOnClickListener(ol_num);
        btn_4.setOnClickListener(ol_num);
        btn_5.setOnClickListener(ol_num);
        btn_6.setOnClickListener(ol_num);
        btn_7.setOnClickListener(ol_num);
        btn_8.setOnClickListener(ol_num);
        btn_9.setOnClickListener(ol_num);

        View btn_del = findViewById(R.id.btn_del);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除按键
                if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    et_count.setText(stringBuilder);
                    et_count.setSelection(stringBuilder.length());
                }
            }
        });
        View btn_point = findViewById(R.id.btn_point);
        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//小数点按键
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int inType = ((EditText) v).getInputType(); // backup the input type
        ((EditText) v).setInputType(InputType.TYPE_NULL); // disable soft input
        ((EditText) v).onTouchEvent(event); // call native handler
        ((EditText) v).setInputType(inType); // restore input type
        ((EditText) v).setSelection(((EditText) v).getText().length());
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date://选择日期时间
                dateTimeDialog.hideOrShow();
                //弹起窗口时分是12小时制，应该是24小时制，待修改
                break;
            case R.id.btn_save://保存
                if (TextUtils.isEmpty(et_count.getText().toString())) {
                    ToastUtil.show("请输入用药数量");
                    return;
                }
                if (et_count.getText().toString().length() > MAX_LENGTH) {
                    ToastUtil.show("输入用药数量超过限制：" + MAX_LENGTH);
                    return;
                }
                break;
        }
    }

    /**
     * 添加药品成功
     */
    public void addMedicineSuccess() {
        ToastUtil.show("添加药品记录成功");
        finish();
    }

    @Override
    public void onDateSet(Date date) {
        //选择时间回调
        tv_date.setText(mDateFormatter.format(date));
        tv_time.setText(mTimeFormatter.format(date));
    }

}