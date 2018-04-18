package com.jinkun_innovation.pastureland.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.utils.PrefUtils;

/**
 * Created by Guan on 2018/3/17.
 */

public class VideoContainerActivity extends AppCompatActivity {

    private static final String TAG = VideoContainerActivity.class.getSimpleName();
    private VideoView videoView;
    private Uri mUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_container);

        String v1 = PrefUtils.getString(getApplicationContext(), "v1", null);
        if (!TextUtils.isEmpty(v1)) {
            Log.d(TAG, v1);
            Log.d(TAG, Environment.getExternalStorageDirectory().getPath());
            mUri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/2018-03-17 16:55:49.mp4");

        }

        //本地的视频 需要在手机SD卡根目录添加一个 fl1234.mp4 视频
//        String videoUrl1 = Environment.getExternalStorageDirectory().getPath() + "/fl1234.mp4";

//        Uri uri = Uri.parse(videoUrl1);

        videoView = (VideoView) this.findViewById(R.id.videoView);

        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(mUri);

        //开始播放视频
        videoView.start();


    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(getApplicationContext(), "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }


}
