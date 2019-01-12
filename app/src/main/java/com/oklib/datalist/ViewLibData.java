package com.oklib.datalist;

import com.oklib.bean.MainBean;
import com.oklib.view_lib.AVLoadingIndicatorViewActivity;
import com.oklib.view_lib.AliLoadingViewActivity;
import com.oklib.view_lib.ClockViewActivity;
import com.oklib.view_lib.DragLayoutActivity;
import com.oklib.view_lib.EmptyDataViewActivity;
import com.oklib.view_lib.FllowerViewActivity;
import com.oklib.view_lib.FloatViewActivity;
import com.oklib.view_lib.HighLightActivity;
import com.oklib.view_lib.LabelViewActivity;
import com.oklib.view_lib.LauncherViewActivity;
import com.oklib.view_lib.LetterNavActivity;
import com.oklib.view_lib.MiClockViewActivity;
import com.oklib.view_lib.PatternLockViewActivity;
import com.oklib.view_lib.ProgressViewActivity;
import com.oklib.view_lib.ShapeSelectActivity;
import com.oklib.view_lib.ShootViewActivity;
import com.oklib.view_lib.SoftKeyboardCustomActivity;
import com.oklib.view_lib.TempControlViewActivity;
import com.oklib.view_lib.TextViewMarqueeActivity;
import com.oklib.view_lib.ToolbarActivity;
import com.oklib.view_lib.chart.ChartActivity;
import com.oklib.view_lib.drag_item.RecipeFavorActivity;
import com.oklib.view_lib.pickerview.PickerViewActivity;
import com.oklib.view_lib.ratingbar.RatingBarEntryActivity;
import com.oklib.view_lib.refresh.RefreshActivity;
import com.oklib.view_lib.span.SpanActivity;
import com.oklib.view_lib.tablayout.TabLayoutActivity;
import com.oklib.view_lib.vp_hss.VPHorizontalSlidingScaleActivity;

/**
 * 时间：2018/7/7
 * 作者：蓝天（主编）
 * 描述：组件库·数据源
 * 公号：技术微讯
 * 群主：wechat598
 */
public class ViewLibData {
    //常用组件
    public static final MainBean[] COMMON_COMPONENTS = {
        new MainBean("刷新组件", RefreshActivity.class),
        new MainBean("已定义图表框架", ChartActivity.class),
        new MainBean("拖拽布局", DragLayoutActivity.class),
        new MainBean("PickerView数据采集使用演示", PickerViewActivity.class),
        new MainBean("动态星星选择，五星好评", RatingBarEntryActivity.class),
        new MainBean("span字体多样式样例", SpanActivity.class),
        new MainBean("TabLayout组件样例", TabLayoutActivity.class),
        new MainBean("VP滑动缩放图片样例", VPHorizontalSlidingScaleActivity.class),
        new MainBean("列表拖拽排序item", RecipeFavorActivity.class),
        new MainBean("Span多场景使用样例", SpanActivity.class),
        new MainBean("标题组件CommonToolbar使用样例", ToolbarActivity.class),
        new MainBean("时钟view", ClockViewActivity.class),
        new MainBean("加载view大全使用样例", AVLoadingIndicatorViewActivity.class),
        new MainBean("支付宝加载动画使用演示", AliLoadingViewActivity.class),
        new MainBean("空数据view使用样例", EmptyDataViewActivity.class),
        new MainBean("显示高亮提示view", HighLightActivity.class),
        new MainBean("悬浮窗", FloatViewActivity.class),
        new MainBean("微信撒花动画", FllowerViewActivity.class),
        new MainBean("字母导航·RecyclerView", LetterNavActivity.class),
        new MainBean("应用启动动画使用演示", LauncherViewActivity.class),
        new MainBean("标签view", LabelViewActivity.class),
        new MainBean("仿小米时钟使用样例", MiClockViewActivity.class),
        new MainBean("锁屏view", PatternLockViewActivity.class),
        new MainBean("自定义进度条样例", ProgressViewActivity.class),
        new MainBean("快门view", ShootViewActivity.class),
        new MainBean("shape，select使用样例", ShapeSelectActivity.class),
        new MainBean("自定义软键盘StringBuild实现", SoftKeyboardCustomActivity.class),
        new MainBean("温度控制view", TempControlViewActivity.class),
        new MainBean("跑马灯（支持同时多个跑马灯）使用样例", TextViewMarqueeActivity.class),
    };
}
