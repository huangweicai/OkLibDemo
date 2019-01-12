package com.oklib.window.bean;

import java.util.List;

/**
 * 创建时间：2017/6/2
 * 编写者：蓝天
 * 功能描述：地区数据载体
 */

public class RegionBean {

    /**
     * name : 河北
     * city : [{"name":"石家庄","area":["长安区","桥东区","桥西区","新华区","郊  区","井陉矿区","井陉县","正定县","栾城县","行唐县","灵寿县","高邑县","深泽县","赞皇县","无极县","平山县","元氏县","赵  县","辛集市","藁","晋州市","新乐市","鹿泉市"]}]
     */

    private String name;
    private List<CityBean> city;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * name : 石家庄
         * area : ["长安区","桥东区","桥西区","新华区","郊  区","井陉矿区","井陉县","正定县","栾城县","行唐县","灵寿县","高邑县","深泽县","赞皇县","无极县","平山县","元氏县","赵  县","辛集市","藁","晋州市","新乐市","鹿泉市"]
         */

        private String name;
        private List<String> area;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
