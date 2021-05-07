package com.animation_study.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.View;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;
import com.animation_study.service.SongService;

/**
 * Created by css on 2017/10/14.
 */

public class SongActivity extends BaseActiytiy {
    MediaPlayer mediaPlayer;
    private SongService.MyBinder myBinder;

    private ServiceConnection bindService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (SongService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public Integer getLayout() {
        return R.layout.activity_song;
    }

    @Override
    public void findView() {

    }

    public void onActivityPlaySong(View v) {
        mediaPlayer = MediaPlayer.create(this, R.raw.song1);
        mediaPlayer.start();
    }

    public void onActivityPauseSong(View v) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void onActivityContinueSong(View v) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void onActivityStopSong(View v) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void onServicePlaySong(View v) {
        Intent intent = new Intent(getApplicationContext(), SongService.class);
        startService(intent);
    }

    public void closeService(View v) {
        Intent stopIntent = new Intent(this, SongService.class);
        stopService(stopIntent);
    }

    public void bindService(View v) {
        Intent intent = new Intent(getApplicationContext(), SongService.class);
        bindService(intent, bindService, BIND_AUTO_CREATE);
    }

    public void bindServicePlaySong(View v) {
        myBinder.playSong();
    }

    public void unbindService(View v) {
        unbindService(bindService);//通过绑定的方式打开的service， 关闭
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}
