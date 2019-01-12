package com.oklib.util;

import android.os.Handler;
import android.os.Looper;

/**
 * 时间：2018/1/25
 * 作者：蓝天
 * 描述：避免应用因为异常闪退处理
 */
//在application中执行
//private void initCrashHandler() {
//        Cockroach.install(new Cockroach.ExceptionHandler() {
//// handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException
//@Override
//public void handlerException(final Thread thread, final Throwable throwable) {
//        //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
//        //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
//        //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
//        //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//@Override
//public void run() {
//        try {
//        //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
//        Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
//        Toast.makeText(MainApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
////                        throw new RuntimeException("..."+(i++));
//        } catch (Throwable e) {
//
//        }
//        }
//        });
//        }
//        });
//        }

public final class Cockroach {

    public interface ExceptionHandler {
        void handlerException(Thread thread, Throwable throwable);
    }

    private Cockroach() {
    }

    private static ExceptionHandler sExceptionHandler;
    private static Thread.UncaughtExceptionHandler sUncaughtExceptionHandler;
    private static boolean sInstalled = false;//标记位，避免重复安装卸载

    /**
     * 初始化使用
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     * <p>
     * exceptionHandler.handlerException可能运行在非UI线程中。
     * <p>
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     *
     * @param exceptionHandler
     */
    public static synchronized void install(ExceptionHandler exceptionHandler) {
        if (sInstalled) {
            return;
        }
        sInstalled = true;
        sExceptionHandler = exceptionHandler;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
//                        Binder.clearCallingIdentity();
                        if (e instanceof QuitCockroachException) {
                            return;
                        }
                        if (sExceptionHandler != null) {
                            //Unable to start activity
                            sExceptionHandler.handlerException(Looper.getMainLooper().getThread(), e);
//                            sUncaughtExceptionHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //所有线程异常拦截，由于主线程的异常都被我们catch住了，所以下面的代码拦截到的都是子线程的异常
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (sExceptionHandler != null) {
                    sExceptionHandler.handlerException(t, e);
                }
            }
        });

    }

    /**
     * 卸载调用
     */
    public static synchronized void uninstall() {
        if (!sInstalled) {
            return;
        }
        sInstalled = false;
        sExceptionHandler = null;
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new QuitCockroachException("Quit Cockroach.....");//主线程抛出异常，迫使 while (true) {}结束
            }
        });

    }

    static final class QuitCockroachException extends RuntimeException {
        public QuitCockroachException(String message) {
            super(message);
        }
    }
}
