package com.oklib.view_lib.drag_item;


import java.util.List;

/**
 * @author Storm
 */
public interface IRecipeFavorPresenter {

    void loadFavorites();

    void saveFavorites(List<Recipe> list);

    void removeFavorite(Recipe item);
}
