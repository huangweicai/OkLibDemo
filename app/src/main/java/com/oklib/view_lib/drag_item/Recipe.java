package com.oklib.view_lib.drag_item;

import android.support.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * 食谱
 *
 * @author Storm
 */
@Keep
@SuppressWarnings("unused")
public class Recipe implements Serializable {

    /** 食谱id */
    private String id;

    /** 食谱名 */
    private String title;

    /** 烹饪时间（秒） */
    private int duration;

    /** 图片资源id */
    private int imageRes;

    /** 图片url */
    private String imageUrl;

    /** 排序号 */
    private int orderno;

    /** 一级分类 */
    private String majorCategory;

    /** 二级分类 */
    private String minorCategory;

    /** 是否置顶 */
    private boolean top;

    /** 是否收藏 */
    private boolean favor;

    /** 是否本地菜单 */
    private boolean local;

    /** 是否最新 */
    private boolean fresh;

    /** 是否简单食谱，例如汤类，不显示详情 */
    private boolean simple = false;

    /** 描述详情 */
    private String description;

    /** 难度 */
    private String difficulty = "简单";

    /** 热量，卡/人份 */
    private String calorie = "0";

    /** 适合n人食用 */
    private String suitableNumber;

    /** 演示视频 */
    private String demo;

    /** 口感 0爽脆 1适中 2软烂 */
    private transient int texture = 1;        //do not save

    /** 份数 */
    private transient int quantity = 1;       //do not save

    /** 烹饪步骤 */
    private List<String> steps;

    /** 配料 */
    private List<Ingredient> ingredients;

    //电饭煲首页item补充-----------------------
    /** 状态说明，保温中/3.5碗 */
    private String stateHint;
    /** 是否详细时间显示，00:32:05 */
    private boolean isShowDetailDuration;

    /* ******************************************************************* */

    /** @see #stateHint */
    public String getStateHint() {
        return stateHint;
    }

    /** @see #stateHint */
    public void setStateHint(String stateHint) {
        this.stateHint = stateHint;
    }

    /** @see #isShowDetailDuration */
    public boolean isShowDetailDuration() {
        return isShowDetailDuration;
    }

    /** @see #isShowDetailDuration */
    public void setShowDetailDuration(boolean showDetailDuration) {
        isShowDetailDuration = showDetailDuration;
    }

    /** @see #id */
    public String getId() {
        return id;
    }

    /** @see #id */
    public void setId(String id) {
        this.id = id;
    }

    /** @see #title */
    public String getTitle() {
        return title;
    }

    /** @see #title */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @see #duration */
    public int getDuration() {
        return duration;
    }

    /** @see #duration */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /** @see #imageRes */
    public int getImageRes() {
        return imageRes;
    }

    /** @see #imageRes */
    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    /** @see #imageUrl */
    public String getImageUrl() {
        return imageUrl;
    }

    /** @see #imageUrl */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** @see #orderno */
    public int getOrderno() {
        return orderno;
    }

    /** @see #orderno */
    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    /** @see #majorCategory */
    public String getMajorCategory() {
        return majorCategory;
    }

    /** @see #majorCategory */
    public void setMajorCategory(String majorCategory) {
        this.majorCategory = majorCategory;
    }

    /** @see #minorCategory */
    public String getMinorCategory() {
        return minorCategory;
    }

    /** @see #minorCategory */
    public void setMinorCategory(String minorCategory) {
        this.minorCategory = minorCategory;
    }

    /** @see #top */
    public boolean isTop() {
        return top;
    }

    /** @see #top */
    public void setTop(boolean top) {
        this.top = top;
    }

    /** @see #favor */
    public boolean isFavor() {
        return favor;
    }

    /** @see #favor */
    public void setFavor(boolean favor) {
        this.favor = favor;
    }

    /** @see #local */
    public boolean isLocal() {
        return local;
    }

    /** @see #local */
    public void setLocal(boolean local) {
        this.local = local;
    }

    /** @see #fresh */
    public boolean isFresh() {
        return fresh;
    }

    /** @see #fresh */
    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    /** @see #simple */
    public boolean isSimple() {
        return simple;
    }

    /** @see #simple */
    public void setSimple(boolean simple) {
        this.simple = simple;
    }

    /** @see #description */
    public String getDescription() {
        return description;
    }

    /** @see #description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @see #difficulty */
    public String getDifficulty() {
        return difficulty;
    }

    /** @see #difficulty */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /** @see #calorie */
    public String getCalorie() {
        return calorie;
    }

    /** @see #calorie */
    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    /** @see #suitableNumber */
    public String getSuitableNumber() {
        return suitableNumber;
    }

    /** @see #suitableNumber */
    public void setSuitableNumber(String suitableNumber) {
        this.suitableNumber = suitableNumber;
    }

    /** @see #demo */
    public String getDemo() {
        return demo;
    }

    /** @see #demo */
    public void setDemo(String demo) {
        this.demo = demo;
    }

    /** @see #texture */
    public int getTexture() {
        return texture;
    }

    /** @see #texture */
    public void setTexture(int texture) {
        this.texture = texture;
    }

    /** @see #quantity */
    public int getQuantity() {
        return quantity;
    }

    /** @see #quantity */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** @see #steps */
    public List<String> getSteps() {
        return steps;
    }

    /** @see #steps */
    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    /** @see #ingredients */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /** @see #ingredients */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /* ******************************************************************* */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        return id != null ? id.equals(recipe.id) : recipe.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /* ******************************************************************* */

    /**
     * 烹饪原料
     */
    @Keep
    public static class Ingredient implements Serializable {

        /** 名称 */
        private String name;

        /** 数量 */
        private int value;

        /** 单位（默认克） */
        private String unit = "g";

        /** @see #name */
        public String getName() {
            return name;
        }

        /** @see #name */
        public void setName(String name) {
            this.name = name;
        }

        /** @see #value */
        public int getValue() {
            return value;
        }

        /** @see #value */
        public void setValue(int value) {
            this.value = value;
        }

        /** @see #unit */
        public String getUnit() {
            return unit;
        }

        /** @see #unit */
        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
