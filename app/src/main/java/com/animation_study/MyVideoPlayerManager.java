package com.animation_study;

import com.animation_study.custom.MyVideoPlayer;

/**
 * Created by css on 2017/10/17.
 */

public class MyVideoPlayerManager {
    public static MyVideoPlayerManager videoPlayerManager;

    public static MyVideoPlayerManager instance() {
        if (videoPlayerManager == null) {
            videoPlayerManager = new MyVideoPlayerManager();
        }
        return videoPlayerManager;
    }

    //两个播放界面的管理
    private MyVideoPlayer currentTexture;

    public void setCurrentTexture(MyVideoPlayer currentTexture) {
        this.currentTexture = currentTexture;
    }

    public MyVideoPlayer getCurrentTexture() {
        if (currentTexture == null) {
            currentTexture = new MyVideoPlayer(CrashApplication.context);
        }
        return currentTexture;
    }

}
