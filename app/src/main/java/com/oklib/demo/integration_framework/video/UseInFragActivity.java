package com.oklib.demo.integration_framework.video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oklib.demo.R;
import com.oklib.util.videoplay.NiceVideoPlayerManager;


public class UseInFragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_in_frag);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new DemoFragenment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
