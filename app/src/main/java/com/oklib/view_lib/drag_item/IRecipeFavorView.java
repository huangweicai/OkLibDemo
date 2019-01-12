package com.oklib.view_lib.drag_item;

import java.util.List;

/**
 * @author Storm
 */
public interface IRecipeFavorView {

    void setPresenter(IRecipeFavorPresenter presenter);

    void setRecipes(List<Recipe> list);
}
