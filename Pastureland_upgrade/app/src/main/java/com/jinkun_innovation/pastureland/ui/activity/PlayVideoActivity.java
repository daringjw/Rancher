package com.jinkun_innovation.pastureland.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.VideoView;

import com.jinkun_innovation.pastureland.R;

/**
 * Created by Guan on 2018/4/8.
 */

public class PlayVideoActivity extends Activity{

    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_video);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = getIntent();
        String path = intent.getStringExtra("filename");
        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setVideoPath(path);
        videoView.setMediaController(new MediaController(this));
        videoView.start();

    }


}
