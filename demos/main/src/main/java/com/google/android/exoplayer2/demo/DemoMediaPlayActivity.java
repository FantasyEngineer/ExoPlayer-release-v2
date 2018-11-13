package com.google.android.exoplayer2.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tiantian on 2018/8/27.
 */

public class DemoMediaPlayActivity extends Activity implements VideoView.SurfaceCallback {
    private VideoView videoView;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private static final int OPEN_FILE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_demo_mediaplayer);
        videoView = (VideoView) findViewById(R.id.surfaceView);

        videoView.initialize(this, this);
        //initMediaPlayer();

    }

    private void initMediaPlayer() {
        try {
            File file = new File("/storage/emulated/0/demosys-4k/USB 4K/动态画面对比演示.mp4");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            mediaPlayer.setLooping(true);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
        Log.d("log", "onSurfaceCreated");
        handler.sendEmptyMessage(OPEN_FILE);
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case OPEN_FILE:
                    openFile();
                    break;
            }

        }

    };

    private void openFile() {
        if (videoView != null) {
            mediaPlayer.setDisplay(videoView.getHolder());
        }
        try {
            File file = new File("/storage/emulated/0/demosys-4k/USB 4K/动态画面对比演示.mp4");
//            File file = new File("/storage/emulated/0/超凡蜘蛛侠_2K.mp4");
            mediaPlayer.setDataSource(this, Uri.fromFile(file));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
