package com.oklib.widget.dialog.interfaces;

public abstract class MyItemDialogListener {


    /**
     * IosSingleChoose,BottomItemDialog的点击条目回调
     * @param text
     * @param position
     */
   public abstract void onItemClick(CharSequence text, int position);


    /**
     * BottomItemDialog的底部按钮(经常是取消)的点击回调
     */
   public void onBottomBtnClick(){}

}
