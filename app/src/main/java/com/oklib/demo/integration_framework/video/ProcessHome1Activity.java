package com.oklib.demo.integration_framework.video;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oklib.demo.R;
import com.oklib.demo.integration_framework.video.adapter.VideoAdapter;
import com.oklib.demo.integration_framework.video.adapter.holder.VideoViewHolder;
import com.oklib.demo.integration_framework.video.base.CompatHomeKeyActivity;
import com.oklib.demo.integration_framework.video.util.DataUtil;
import com.oklib.util.videoplay.NiceVideoPlayer;
import com.oklib.util.videoplay.NiceVideoPlayerManager;


/**
 * 只需要集成自CompatHomeKeyActivity，按下Home键，暂停视频播放，回到此Activity后继续播放视频；
 * 如果离开次Activity（跳转到其他Activity或按下Back键），则释放视频播放器
 */
public class ProcessHome1Activity extends CompatHomeKeyActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_home1);
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        VideoAdapter adapter = new VideoAdapter(this, DataUtil.getVideoListData());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
    }

}
