package com.oklib.view_lib.drag_item;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.oklib.R;

import java.util.ArrayList;

/**
 * 食谱adapter
 *
 * @author Storm
 */
public class RecipeAdapter extends BaseRecyclerAdapter<Recipe> {

    /** 是否在编辑状态 */
    private boolean editing;

    /** 当前正在拖拽的item */
    private RecipeHolder dragging;

    /** 所有可视的item */
    private ArrayList<RecipeHolder> holders = new ArrayList<>();

    private ValueAnimator animator;

    public RecipeAdapter(Context context) {
        super(context);
        init();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeHolder(inflater.inflate(R.layout.item_recipe, parent, false), onItemClickListener);
    }

    /** @see #editing */
    public boolean isEditing() {
        return editing;
    }

    /** @see #editing */
    public void setEditing(boolean editing) {
        if (this.editing == editing) {
            return;
        }
        this.editing = editing;

        if (editing) {
            animator.start();
        } else {
            animator.end();
        }

        for (RecipeHolder holder : holders) {
            holder.deleteView.setVisibility(editing ? View.VISIBLE : View.GONE);    //这里不用notifyXXX方法 ，它们都会使得drag事件中断。手动改view状态
            if (!editing) {
                holder.itemView.setRotation(0);     //抖动还原
            }
        }
    }

    public void setDraggingItem(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder == null) {
            dragging = null;
        } else if (viewHolder instanceof RecipeHolder) {
            dragging = (RecipeHolder) viewHolder;
            dragging.itemView.setRotation(0);
        }
    }

    private void init() {
        animator = ValueAnimator.ofFloat(0f, (float) (Math.PI * 2));
        animator.setDuration(200);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float input = (float) animation.getAnimatedValue();             //0 ~ 2π
                for (RecipeHolder holder : holders) {
                    if (holder != dragging) {
                        float value = (float) Math.sin(input + holder.phase);   //-1 ~ 1
                        holder.itemView.setRotation(value * 2);                 //-2 ~ 2
                    }
                }
            }
        });
    }

    private class RecipeHolder extends BaseRecyclerHolder<Recipe> {

        private ImageView imageView;
        private TextView titleView;
        private TextView durationView;
        private TextView timeLabelView;
        private ImageView flagView;
        private View deleteView;
//        private Request request;
        private double phase = Math.random() * Math.PI * 2;     //随机添加一个相位，使得各个item的抖动不一致

        private RecipeHolder(View view, final OnItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            imageView = view.findViewById(R.id.iv_image);
            titleView = view.findViewById(R.id.tv_title);
            durationView = view.findViewById(R.id.tv_duration);
            timeLabelView = view.findViewById(R.id.tv_time_label);
            flagView = view.findViewById(R.id.iv_flag);
            deleteView = view.findViewById(R.id.iv_delete);
            if (onItemClickListener != null) {
                deleteView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(itemView, getLayoutPosition(), 1);
                    }
                });
            }
        }

        @Override
        public void bind(Context context, int position, Recipe item) {
            holders.add(this);

//            if (item.getImageRes() > 0) {
//                imageView.setImageResource(item.getImageRes());
//            } else if (!TextUtils.isEmpty(item.getImageUrl())) {
//                request = Glide.with(context).load(item.getImageUrl()).asBitmap()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.drawable.icon_loading).error(R.drawable.icon_attention)
//                        .listener(new RequestListener<String, Bitmap>() {
//                            @Override
//                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                                request = null;
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                request = null;
//                                return false;
//                            }
//                        })
//                        .into(imageView).getRequest();
//            }

            titleView.setText(item.getTitle());

            if ("0".equals(item.getId())) {
                CustomImageSpan span = new CustomImageSpan(context, R.drawable.icon_shouchang, CustomImageSpan.ALIGN_CENTER);
                SpannableStringBuilder sb = new SpannableStringBuilder("  ");
                sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                durationView.setText(sb);
                durationView.setVisibility(View.VISIBLE);
                timeLabelView.setVisibility(View.GONE);
            } else if (item.getDuration() > 0) {
                int d = item.getDuration();
                if (d >= 120) {
                    durationView.setText(String.valueOf(d / 60));
                    timeLabelView.setText("分");
                } else {
                    durationView.setText(String.valueOf(d));
                    timeLabelView.setText("秒");
                }
                durationView.setVisibility(View.VISIBLE);
                timeLabelView.setVisibility(View.VISIBLE);
            } else {
                durationView.setVisibility(View.GONE);
                timeLabelView.setVisibility(View.GONE);
            }

            if (item.isFresh()) {
                flagView.setVisibility(View.VISIBLE);
                flagView.setImageResource(R.drawable.superscript_new);
            } else if (item.isTop()) {
                flagView.setVisibility(View.VISIBLE);
                flagView.setImageResource(R.drawable.superscript_collection);
            } else {
                flagView.setVisibility(View.GONE);
            }

            deleteView.setVisibility(editing ? View.VISIBLE : View.GONE);
        }

        @Override
        public void recycle() {
            holders.remove(this);
            itemView.setRotation(0);
            imageView.setImageResource(R.drawable.pic_shoucang1);
        }
    }

}
