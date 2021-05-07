package com.animation_study;

import android.media.MediaPlayer;

/**
 * Created by css on 2017/10/16.
 */

public class MyStaticMediaplay {
    public static MyStaticMediaplay instance;
    private MediaPlayer mediaPlayer;

    public static MyStaticMediaplay getInstance() {
        if (instance == null) {
            instance = new MyStaticMediaplay();
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        return mediaPlayer;

    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

}
