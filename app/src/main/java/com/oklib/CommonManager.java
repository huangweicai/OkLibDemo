package com.oklib;

import com.oklib.bean.MainBean;
import com.oklib.datalist.MixLibData;
import com.oklib.datalist.ViewLibData;
import com.oklib.datalist.WinLibData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 时间：2017/8/1
 * 作者：蓝天
 * 描述：通用模块管理
 */

public final class CommonManager {
    public static String[] TITLES = {"集成库", "组件动画库", "窗口库"};
    public static final String TITLE = "title";
    public static final String URL = "url";

    public static List<MainBean> getDatas(int type) {
        switch (type) {
            case 0:
                List<MainBean> mixlibList = new ArrayList<>();
                mixlibList.addAll(Arrays.asList(MixLibData.INTEGRATION_FRAMEWORK));
                return mixlibList;
            case 1:
                List<MainBean> viewlibList = new ArrayList<>();
                viewlibList.addAll(Arrays.asList(ViewLibData.COMMON_COMPONENTS));
                return viewlibList;
            case 2:
                List<MainBean> winlibList = new ArrayList<>();
                winlibList.addAll(Arrays.asList(WinLibData.WINDOW_RELATED));
                return winlibList;
        }
        return null;
    }


}
