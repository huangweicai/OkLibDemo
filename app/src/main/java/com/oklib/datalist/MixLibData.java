package com.oklib.datalist;

import com.oklib.bean.MainBean;
import com.oklib.mix_lib.CameraActivity;
import com.oklib.mix_lib.PermissionActivity;
import com.oklib.mix_lib.SugarActivity;
import com.oklib.mix_lib.http.HttpActivity;
import com.oklib.mix_lib.qrcode.QRCodeEntryActivity;

/**
 * 时间：2018/7/7
 * 作者：蓝天（主编）
 * 描述：集成库·数据源
 * 公号：技术微讯
 * 群主：wechat598
 */
public class MixLibData {

    //集成框架
    public static final MainBean[] INTEGRATION_FRAMEWORK = {
            new MainBean("6.0动态权限统一封装框架", PermissionActivity.class),
            new MainBean("拍照选择、相册选择", CameraActivity.class),
            new MainBean("okhttp封装网络请求使用样例", HttpActivity.class),
            new MainBean("sugar数据库", SugarActivity.class),
            new MainBean("二维码扫码及生成", QRCodeEntryActivity.class),
    };


}
