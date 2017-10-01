package hwc.loadsir.target;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import hwc.loadsir.PostUtil;
import hwc.loadsir.R;
import hwc.loadsir.callback.LoadingCallback;
import hwc.loadsir.callback.TimeoutCallback;
import hwc.loadsir.lib.callback.Callback;
import hwc.loadsir.lib.core.LoadService;
import hwc.loadsir.lib.core.LoadSir;


/**
 * Description:TODO
 * Create Time:2017/9/3 11:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class ViewTargetActivity extends AppCompatActivity {

    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new TimeoutCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        loadService = loadSir.register(imageView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                //do retry logic...

                //callback
                PostUtil.postSuccessDelayed(loadService);
            }
        });
        PostUtil.postCallbackDelayed(loadService, TimeoutCallback.class);
    }

}
