package com.oklib.view_lib.drag_item;

import java.util.List;

/**
 * @author Storm
 */
public interface IRecipeModel {

    /**
     * 加载本地食谱一级分类
     */
    void loadLocalMajorCategories(SyncCallback<List<String>> callback);

    /**
     * 加载本地食谱二级分类
     *
     * @param majorCategory
     */
    void loadLocalMinorCategories(String majorCategory, SyncCallback<List<String>> callback);

    /**
     * 加载本地食谱
     *
     * @param majorCategory
     * @param minorCategory
     * @param page
     * @param pagesize
     * @param callback
     */
    void loadLocalRecipes(String majorCategory, String minorCategory, int page, int pagesize, SyncCallback<List<Recipe>> callback);

    /**
     * 加载收藏食谱
     *
     * @param page
     * @param pagesize
     * @param callback
     */
    void loadFavorRecipes(int page, int pagesize, SyncCallback<List<Recipe>> callback);

    /**
     * 保存本地食谱
     */
    void saveLocalRecipes(List<Recipe> list, SyncCallback<List<Boolean>> callback);

    /**
     * 添加本地食谱
     */
    void addLocalRecipe(Recipe recipe, SyncCallback<List<Boolean>> callback);

    /**
     * 删除本地食谱
     */
    void deleteLocalRecipes(String id, SyncCallback<List<Boolean>> callback);

    /**
     * 加载云食谱
     *
     * @param majorCategory
     * @param minorCategory
     * @param page
     * @param pagesize
     * @param callback
     */
    void loadRemoteRecipes(String majorCategory, String minorCategory, int page, int pagesize, SyncCallback<List<Recipe>> callback);
}
