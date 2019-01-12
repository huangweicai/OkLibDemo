package com.oklib;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.oklib.util.Cockroach;
import com.orm.SugarApp;
import com.tencent.bugly.Bugly;

/**
 * 时间：2018/7/6
 * 作者：蓝天
 * 描述：入口
 */
public class OkLibApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //CrashReport.initCrashReport(getApplicationContext(), "55279b69e4", false);
        Bugly.init(getApplicationContext(), "58fa567006", false);//统一初始化方法，不会检测更新
        //CrashReport.testJavaCrash();
        initCrashHandler();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initCrashHandler() {
        Cockroach.install(new Cockroach.ExceptionHandler() {
            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
                //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
                //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                            Toast.makeText(OkLibApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }
}
