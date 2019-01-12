package com.oklib.view.fw;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.oklib.view.R;


/**
 * 时间：2017/10/24
 * 作者：蓝天
 * 描述：服务
 */

public class FloatVideoService extends Service {
    private Context context;
    private static ScrollView floatViewLayout;
    private ListView lv_loglist;
    private static TextView tv_log;
//    private static LvLoglistAdapter lvLoglistAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initFloatView();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addSurfaceView();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //初始化
    private void initFloatView() {
        //显示悬浮窗口
        floatViewLayout = (ScrollView) LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.float_layout, null);
        tv_log = (TextView) floatViewLayout.findViewById(R.id.tv_log);
//        lv_loglist = (ListView) floatViewLayout.findViewById(R.id.lv_loglist);
//        lv_loglist.setAdapter(lvLoglistAdapter = new LvLoglistAdapter());
    }

    //需要每次执行添加操作
    private void addSurfaceView() {
        //管理器
        final FloatViewManager floatViewManager = FloatViewManager.getInstance();
        floatViewManager.create(context.getApplicationContext(), floatViewLayout);
        floatViewManager.showFloatView();
    }

    private static StringBuilder sb = new StringBuilder();

    public static void updateLogData(String log) {
        if (null == tv_log) {
            return;
        }
//        if (null != list)
//            list.add(log);
//        if (null != lvLoglistAdapter)
//            lvLoglistAdapter.notifyDataSetChanged();
        sb.append(log + "\n");
        tv_log.setText(sb.toString());

//        Log.d("TAG", "getHeight:"+tv_log.getHeight());
//        Log.d("TAG", "getScrollY:"+tv_log.getScrollY());
//        Log.d("TAG", "floatViewLayoutgetScrollY:"+floatViewLayout.getHeight());
        //floatViewLayout.scrollTo(0, tv_log.getHeight());

        floatViewLayout.post(new Runnable() {
            public void run() {
                floatViewLayout.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

//    private static List<String> list = new ArrayList<>();
//
//    private static class LvLoglistAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            TextView tv = new TextView(parent.getContext());
//            tv.setText(list.get(position));
//            return tv;
//        }
//    }
}
