package com.animation_study.custom;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.animation_study.CrashApplication;
import com.animation_study.MyVideoPlayerManager;
import com.animation_study.R;
import com.animation_study.activity.StudyJCVideoActivity;
import com.animation_study.manager.SJCVideoManager;

/**
 * 写不了了， 太难， 不知道错在哪里,就这样吧， 技术不行
 * Created by css on 2017/10/17.
 */

public class MyVideoPlayer extends FrameLayout implements View.OnClickListener, MediaPlayer.OnPreparedListener {
    //当前播放状态        暂停、 播放、 播放完成、
    public final static int VIDEOSTATELESS = -1;//无状态
    public final static int VIDEOPAUSE = 0;
    public final static int VIDEOPAYLING = 1;
    public final static int VIDEOFINISH = 2;

    public int VIDEOSTATE = VIDEOSTATELESS;//默认无状态
    //当前屏幕状态        垂直、 垂直
    public final static int VERTICAL = 0;//垂直
    public final static int HORIZONTAL = 1;//水平
    public static int SCREEN_ORIENTATION = VERTICAL;//默认垂直

    //当前在那一层播放     1、2
    public final static int FIRST = 1;
    public final static int SECOND = 2;
    public static int FLOOR = FIRST;//默认第一层

    private int currentPosition = 0;

    private String videoPath = "";
    private ViewGroup vp;
    private ViewGroup surface_container;
    private Button bt_full_screen;
    private ImageView startPlay;
    private ImageView videoThumb;
    private ProgressBar pb_waiting;

    public MyVideoPlayer(@NonNull Context context) {
        super(context);
        initView();
    }

    public MyVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_my_video, this);
        findView();
    }

    private void findView() {
        surface_container = (ViewGroup) findViewById(R.id.surface_container);
        bt_full_screen = (Button) findViewById(R.id.bt_full_screen);
        startPlay = (ImageView) findViewById(R.id.iv_myvideoplayer_player);
        videoThumb = (ImageView) findViewById(R.id.iv_myvideoplayer_thumb);
        pb_waiting = (ProgressBar) findViewById(R.id.pb_waiting);
//        surface_container.setOnClickListener(this);
        startPlay.setOnClickListener(this);
        bt_full_screen.setOnClickListener(this);
    }

    public void setUp(String videoPath, int postition) {
        this.videoPath = videoPath;
        this.currentPosition = postition;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_myvideoplayer_player:
                playVideo();
                break;
            case R.id.bt_full_screen:
                if (VIDEOSTATE != VIDEOSTATELESS) {
                    fullScreen();
                } else {

                }
                break;
        }
    }

    public void playVideo() {
        if (!this.equals(MyVideoPlayerManager.instance().getCurrentTexture())) {//如果返回值为true， 说明点击的是当前的视频
            //进入这里说明， 点击的是另一个视频,
            if (MyVideoPlayerManager.instance().getCurrentTexture() != null) {
                //把视频状态重新设为无状态
                MyVideoPlayerManager.instance().getCurrentTexture().VIDEOSTATE = MyVideoPlayerManager.instance().getCurrentTexture().VIDEOSTATELESS;
            }
            pb_waiting.setVisibility(View.VISIBLE);
//            removeTextureView();
            MyVideoPlayerManager.instance().setCurrentTexture(this);//切换
            removeTextureView();

            //TODO  每一次播放都 new  不然会出错， 不知道为神魔， 解决不了
            //每一次播放都 new  不然会出错， 不知道为神魔
            SJCVideoManager.instance().getMediaPlayer().stop();
            SJCVideoManager.instance().getMediaPlayer().release();
            SJCVideoManager.instance().setMediaPlayer(null);
            SJCVideoManager.instance().getMediaPlayer().reset();

            Log.d("sss", "点击");

//            SJCVideoManager.sjcTexterView.setSurfaceTextureListener(SJCVideoManager.instance());
            SJCVideoManager.instance().setSurfaceTextureListener(videoPath);
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            CrashApplication.screenHeight * SJCVideoManager.instance().videoWidth / CrashApplication.screenWidth);//充满屏幕

            surface_container.addView(SJCVideoManager.sjcTexterView, layoutParams);//添加到帧布局
            VIDEOSTATE = VIDEOPAYLING;

        } else {
            Log.d("sss", "暂停_开始");
            if (SJCVideoManager.instance().getMediaPlayer().isPlaying()) {
                SJCVideoManager.instance().getMediaPlayer().pause();
            } else {
                SJCVideoManager.instance().getMediaPlayer().start();
            }
        }
    }

    public void fullScreen() {
        //表示在在最上层播放
        FLOOR = SECOND;
        //列表不可滚动
        StudyJCVideoActivity.studyJCVideoActivity.setScrollEnabled(false);
        //先设置横屏， 然后添加到父布局， 可以避免黑屏
        vp = (ViewGroup) StudyJCVideoActivity.studyJCVideoActivity.getWindow().getDecorView();
        surface_container.removeView(SJCVideoManager.sjcTexterView);
        int width = 0;
        int height = 0;
        if (SJCVideoManager.instance().videoHeight > SJCVideoManager.instance().videoWidth) {
            //竖屏
            SCREEN_ORIENTATION = VERTICAL;//标记当前video方向为水平
            if (SJCVideoManager.instance().videoProportion > CrashApplication.screenProportion) {
                width = (int) (CrashApplication.screenHeight / SJCVideoManager.instance().videoProportion);
                height = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                width = ViewGroup.LayoutParams.MATCH_PARENT;
                height = (int) (CrashApplication.screenWidth * SJCVideoManager.instance().videoProportion);
            }

        } else {
            //横屏
            SCREEN_ORIENTATION = HORIZONTAL;//标记当前video方向为水平
            StudyJCVideoActivity.studyJCVideoActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            width = ViewGroup.LayoutParams.MATCH_PARENT;
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        }


        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);//定义布局
        lp.gravity = Gravity.CENTER;
        Log.d("videoheight", "" + SJCVideoManager.instance().getMediaPlayer().getVideoHeight());
        vp.addView(SJCVideoManager.sjcTexterView, lp);//在父控件中， 封装过的videoview

//        //设置横屏
//        StudyJCVideoActivity.studyJCVideoActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//        ObjectAnimator oaAnimator = ObjectAnimator.ofFloat(SJCVideoManager.sjcTexterView, "rotation", 0, 90)
//                                    .setDuration(500);
//        oaAnimator.start();
//        ObjectAnimator scAnimator = ObjectAnimator.ofFloat(SJCVideoManager.sjcTexterView)

    }

    public void returnVerticalScreen() {
        //表示在列表中播放
        FLOOR = FIRST;
        //列表可以滚动
        StudyJCVideoActivity.studyJCVideoActivity.setScrollEnabled(true);
        //标记当前video方向为垂直
        SCREEN_ORIENTATION = VERTICAL;

        vp.removeView(SJCVideoManager.sjcTexterView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//定义布局
        surface_container.addView(SJCVideoManager.sjcTexterView, lp);//在父控件中， 封装过的videoview
        //设置竖屏
        StudyJCVideoActivity.studyJCVideoActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void removeTextureView() {
        SJCVideoManager.surfaceTexture = null;//清除SurfaceTexture
        if (SJCVideoManager.sjcTexterView != null && SJCVideoManager.sjcTexterView.getParent() != null) {
            ((ViewGroup) SJCVideoManager.sjcTexterView.getParent()).removeView(SJCVideoManager.sjcTexterView);//得到父view, 删除纹理也要清除
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("视频准备完成", "1");

        //设置item高度
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                400);
        Log.d("sss", CrashApplication.screenHeight + "::" + CrashApplication.screenWidth + "::" + SJCVideoManager.instance().videoWidth + "::"
                + SJCVideoManager.instance().videoHeight
        );

        surface_container.setLayoutParams(rlp);
        pb_waiting.setVisibility(View.GONE);
        SJCVideoManager.instance().videoWidth = SJCVideoManager.instance().getMediaPlayer().getVideoWidth();
        SJCVideoManager.instance().videoHeight = SJCVideoManager.instance().getMediaPlayer().getVideoHeight();
        SJCVideoManager.instance().videoProportion = SJCVideoManager.instance().videoHeight * 1.0f / SJCVideoManager.instance().videoWidth;
        videoThumb.setVisibility(View.GONE);
        SJCVideoManager.instance().getMediaPlayer().start();
        invalidate();
        Log.d("视频准备完成", "2");
    }
}
