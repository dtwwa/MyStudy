package com.animation_study.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.animation_study.R;
import com.animation_study.utils.Constant;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by css on 2017/10/16.
 */

public class GSYVIdeoActibity extends Activity {
    StandardGSYVideoPlayer standardGSYVideoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsy_video);
        standardGSYVideoPlayer = (StandardGSYVideoPlayer) findViewById(R.id.standardGSYVideoPlayer);
//设置播放url，第一个url，第二个开始缓存，第三个使用默认缓存路径，第四个设置title
        standardGSYVideoPlayer.setUp(Constant.strVideoUrl, true, null, "这是title");

        //非全屏下，不显示title
        standardGSYVideoPlayer.getTitleTextView().setVisibility(View.GONE);

//非全屏下不显示返回键
        standardGSYVideoPlayer.getBackButton().setVisibility(View.GONE);

//打开非全屏下触摸效果
        standardGSYVideoPlayer.setIsTouchWiget(true);
//开启自动旋转
        standardGSYVideoPlayer.setRotateViewAuto(true);
        standardGSYVideoPlayer.startPlayLogic();
    }
}
