package com.hwc.oklib.common_components;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.musicwave.MusicWave;

import static com.hwc.oklib.Common.BASE_RES;

/**
 * 时间：2018/1/3
 * 作者：黄伟才
 * 描述：音频波动view
 */

public class MusicwaveActivity extends BaseAppActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 9976;
    FloatingActionButton fab;
    private MediaPlayer mMediaPlayer;
    private Visualizer mVisualizer;
    private MusicWave musicWave;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_musicwave;
    }

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_musicwave.xml", BASE_RES +"/layout/activity_musicwave.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
        } else {
            initialise();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && mMediaPlayer != null) {
            mVisualizer.release();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void initialise() {
        musicWave = (MusicWave) findViewById(R.id.musicWave);
        mMediaPlayer = MediaPlayer.create(this, R.raw.unrelenting);
        prepareVisualizer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVisualizer.setEnabled(false);
                fab.setImageResource(android.R.drawable.ic_media_play);
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status;
                if (mMediaPlayer.isPlaying()) {
                    status = "Sound Paused";
                    mMediaPlayer.pause();
                    fab.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    status = "Sound Started";
                    mVisualizer.setEnabled(true);
                    mMediaPlayer.start();
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                }
                Snackbar.make(view, status, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    private void prepareVisualizer() {
        mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        musicWave.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);
        mVisualizer.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_color_solid) {
            Log.e("action_color_solid", "" + musicWave.getConfig().getColorGradient());
            musicWave.getConfig().setColorGradient(false);
            return true;
        }
        if (id == R.id.action_color_gradient) {
            Log.e("action_color_gradient", "" + musicWave.getConfig().getColorGradient());
            musicWave.getConfig().setColorGradient(true);
            return true;
        }
        if (id == R.id.action_sound_usage) {
            Snackbar.make(fab, "Sound used “Unrelenting” by Jay Man www.ourmusicbox.com", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initialise();
                } else {
                    Toast.makeText(MusicwaveActivity.this, "Allow Permission from settings", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
