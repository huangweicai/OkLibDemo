package hwc.loadsir;

import hwc.loadsir.callback.CustomCallback;
import hwc.loadsir.callback.EmptyCallback;
import hwc.loadsir.callback.ErrorCallback;
import hwc.loadsir.callback.LoadingCallback;
import hwc.loadsir.callback.TimeoutCallback;
import hwc.loadsir.lib.core.LoadSir;


/**
 * Description:TODO
 * Create Time:2017/9/3 14:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadSirApp {
    public static void onCreate() {
//        if (setupLeakCanary()) return;

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

//    private boolean setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return true;
//        }
//        LeakCanary.install(this);
//        return false;
//    }
}
