package hwc.loadsir.target;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import hwc.loadsir.PostUtil;
import hwc.loadsir.R;
import hwc.loadsir.callback.AnimateCallback;
import hwc.loadsir.callback.EmptyCallback;
import hwc.loadsir.callback.LoadingCallback;
import hwc.loadsir.lib.callback.Callback;
import hwc.loadsir.lib.core.LoadService;
import hwc.loadsir.lib.core.LoadSir;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class AnimateActivity extends AppCompatActivity {


    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        // Your can change the callback on sub thread directly.
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new AnimateCallback())
                .setDefaultCallback(AnimateCallback.class)
                .build();
        loadService = loadSir.register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                // Your can change the status out of Main thread.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadService.showCallback(LoadingCallback.class);
                        //do retry logic...
                        SystemClock.sleep(500);
                        //callback on sub thread
                        loadService.showSuccess();
                    }
                }).start();
            }
        });
        PostUtil.postCallbackDelayed(loadService, EmptyCallback.class, 2500);
    }

}
