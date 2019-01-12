package com.oklib.datalist;

import com.oklib.bean.MainBean;
import com.oklib.win_lib.CenterListDialogActivity;
import com.oklib.win_lib.ConfirmDialogActivity;
import com.oklib.win_lib.DateTimeActivity;
import com.oklib.win_lib.DialogViewActivity;
import com.oklib.win_lib.EditWinActivity;
import com.oklib.win_lib.LoadingDialogActivity;
import com.oklib.win_lib.MultiSelectListPopActivity;
import com.oklib.win_lib.PageDialogActivity;
import com.oklib.win_lib.RegionSelectActivity;
import com.oklib.win_lib.SearchDialogActivity;

/**
 * 时间：2018/7/7
 * 作者：蓝天（主编）
 * 描述：窗口库·数据源
 * 公号：技术微讯
 * 群主：wechat598
 */
public class WinLibData {
    //窗口相关
    public static final MainBean[] WINDOW_RELATED = {
            new MainBean("居中确定取消窗口", ConfirmDialogActivity.class),
            new MainBean("多选pop列表", MultiSelectListPopActivity.class),
            new MainBean("列表自定义item窗口", CenterListDialogActivity.class),
            new MainBean("日期选择器窗口", DateTimeActivity.class),
            new MainBean("结束dialog", DialogViewActivity.class),
            new MainBean("修改昵称窗口", EditWinActivity.class),
            new MainBean("加载等待窗口", LoadingDialogActivity.class),
            new MainBean("分页dialog", PageDialogActivity.class),
            new MainBean("地区选择窗口", RegionSelectActivity.class),
            new MainBean("标志tag搜索窗口", SearchDialogActivity.class),
    };
}
