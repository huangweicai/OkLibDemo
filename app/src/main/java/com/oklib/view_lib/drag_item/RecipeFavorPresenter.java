package com.oklib.view_lib.drag_item;

import android.content.Context;

import java.util.List;


/**
 * @author Storm
 */
public class RecipeFavorPresenter implements IRecipeFavorPresenter {

    private IRecipeFavorView view;
    private IRecipeModel model;

    public RecipeFavorPresenter(IRecipeFavorView view) {
        this.view = view;
        model = new RecipeModel((Context) view);
    }

    @Override
    public void loadFavorites() {
        model.loadFavorRecipes(1, 1000, new SyncCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> data) {
                view.setRecipes(data);
            }
        });
    }

    @Override
    public void saveFavorites(List<Recipe> list) {

    }

    @Override
    public void removeFavorite(Recipe item) {

    }
}
