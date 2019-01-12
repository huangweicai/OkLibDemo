package com.oklib.view.base;

import android.widget.Toast;

/**
 * 时间：2017/11/21
 * 作者：蓝天
 * 描述：该类是Activity常用补充类
 */

public abstract class BaseCommonUseActivity extends BaseAbstractActivity {

    //通用
    protected boolean ifShowExit = false;
    private long preKeyBackTime = 0L;
    @Override
    public void onBackPressed() {
        if (this.ifShowExit) {
            if (System.currentTimeMillis() - this.preKeyBackTime < 2000L) {
                finish();
                System.exit(0);
            } else {
                this.preKeyBackTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次,将退出应用", Toast.LENGTH_LONG);
            }
        } else {
            finish();
        }
    }
}
