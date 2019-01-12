package com.oklib.view_lib.drag_item;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 时间：2018/1/16
 * 作者：蓝天
 * 描述：列表拖拽排序item
 */
public class RecipeFavorActivity extends BaseAppActivity implements IRecipeFavorView {

    @BindView(R.id.tb_toolbar)
    CommonToolBar tbToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.btn_confirm)
    View confirmView;

    private List<Recipe> recipes;
    private RecipeAdapter adapter;
    private IRecipeFavorPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new RecipeFavorPresenter(this));
        presenter.loadFavorites();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_recipe_favor;
    }

    @Override
    protected void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        recipes = new ArrayList<>();
        adapter = new RecipeAdapter(this);
        adapter.setData(recipes);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int flag, Object... params) {
                if (flag == 1) {
                    int min = 4;
                    if (recipes.size() <= min) {
                        //ToastUtil.show(getString(R.string.recipe_favor_deletetip, min));
                    } else {
                        presenter.removeFavorite(recipes.get(position));
                        recipes.remove(position);
                        adapter.notifyItemRemoved(position);
                        if (position < min) {
                            recipes.get(min - 1).setTop(true);
                            adapter.notifyItemChanged(min - 1);
                            //TODO save
                        }
                    }
                } else {
                    //startActivity(RecipeDetailActivity.newIntent(RecipeFavorActivity.this, recipes.get(position)));
                }
            }
        });
        recyclerView.setAdapter(adapter);

        //处理拖拽事件
        new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;   //不支持swipe
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;    //支持长按拖拽
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int c = viewHolder.getAdapterPosition();    //拖拽item下标
                int t = target.getAdapterPosition();        //目标item下标
                int d = Math.abs(t - c) / (t - c);          //差值符号，向后拖+1，向前拖-1
                Recipe current = recipes.get(c);            //拖拽item
                for (int i = c; i != t; i += d) {
                    recipes.set(i, recipes.get(i + d));     //移动c到t间的item
                    adapter.notifyItemMoved(i + d, i);
                }
                recipes.set(t, current);                    //把current放到target
                //adapter.notifyItemMoved(c, t);            //不要，否则出错

                //TODO save

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    adapter.setDraggingItem(viewHolder);
                    if (!adapter.isEditing()) {
                        confirmView.setVisibility(View.VISIBLE);
                        ObjectAnimator.ofFloat(confirmView, "translationY", confirmView.getLayoutParams().height, 0).setDuration(200).start();
                    }
                    adapter.setEditing(true);
                    //confirmView.setVisibility(View.VISIBLE);
                }
                if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    adapter.setDraggingItem(null);
                    for (int i = 0; i < recipes.size(); i++) {  //前4个设home标识
                        recipes.get(i).setTop(i < 4);
                        adapter.notifyDataSetChanged();
                        //TODO save
                    }
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.setEditing(false);
    }

    @OnClick(R.id.btn_confirm)
    void clickConfirm(View view) {
        adapter.setEditing(false);
        ObjectAnimator.ofFloat(confirmView, "translationY", 0, confirmView.getLayoutParams().height).setDuration(200).start();
    }

    @Override
    public void setPresenter(IRecipeFavorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setRecipes(List<Recipe> list) {
        recipes.clear();
        if (list != null && !list.isEmpty()) {
            recipes.addAll(list);
            //前4个加上home标识
            for (int i = Math.min(recipes.size(), 4); --i >= 0; ) {
                recipes.get(i).setTop(true);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
