package com.animation_study.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.animation_study.R;
import com.animation_study.utils.Constant;
import com.animation_study.video.SuperVideoView;

/**
 * Created by css on 2017/10/15.
 */

public class EncVideoViewActivity extends Activity {

    private SuperVideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encvideo);
        String path = Constant.strVideoUrl;
        videoView = (SuperVideoView) findViewById(R.id.container);
        videoView.register(this);
        videoView.setVideoPath(path);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!videoView.isVerticalScreen()) {//如果是横屏， 不退出程序 改为竖屏
            videoView.changeVideoOrientation();
        } else {
            super.onBackPressed();
        }
    }
}
