package com.animation_study.custom;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.animation_study.activity.RecycleTextureActivity;

import java.io.IOException;

/**
 * Created by css on 2017/10/16.
 */

public class MyTextureView extends TextureView implements TextureView.SurfaceTextureListener, View.OnClickListener {
    private String mVideoUrl = "";
    private String TAG = MyTextureView.class.getName();
    private MediaPlayer mMediaPlayer;
    private Surface mSurface;
    private MediaPlayer.OnPreparedListener preparedListener;
    private MediaPlayer.OnErrorListener errorListener;
    private MediaPlayer.OnCompletionListener completionListener;
    private MediaPlayer.OnInfoListener infoListener;
    private MediaPlayer.OnSeekCompleteListener seekCompleteListener;
    private boolean isVerticalScreen = true;

    public MyTextureView(Context context) {
        super(context);
        initView();
    }

    public MyTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        this.setOnClickListener(this);
        this.setSurfaceTextureListener(this);//设置回调监听
    }

    /**
     * 开始播放， 外部调用
     */
    public void startPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            Log.e(TAG, "startPlay");
        } else {
            Log.e(TAG, "start Error media is null");
        }
    }

    /**
     * 暂停视频 ， 外部调用
     */
    public void pausePlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            Log.e(TAG, "stopPlay");
        } else {
            Log.e(TAG, "pause Error media is null");
        }
    }

    /**
     * 重新播放 ， 外部调用
     */
    public void resetPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            Log.e(TAG, "resetPlay");
        } else {
            Log.e(TAG, "reset Error media is null");
        }
    }

    /**
     * 销毁
     */
    public void destory() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void setUrl(String path) {
        mMediaPlayer.reset();
        mVideoUrl = path;
        try {
            Log.d("path", path);

//                    FileInputStream fis = null;
//                    fis = new FileInputStream(new File(path));
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(path);

            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 先调 initView添加TextureView监听  然后回调onSurfaceTextureAvailable ， 然后掉这个函数
     *
     * @param surface
     */
    public void initMediaPlayer(SurfaceTexture surface) {
        if (surface == null) {
            return;
        }

        if (mMediaPlayer == null) {
            Log.d("ssss", "ssss//////////////////////////////////////");
            mMediaPlayer = new MediaPlayer();
        }
        mSurface = new Surface(surface);
        mMediaPlayer.setSurface(mSurface);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    public void setmMediaPlayer(MediaPlayer mMediaPlayer) {
        this.mMediaPlayer = mMediaPlayer;
//        initMediaPlayer(surface);
        mMediaPlayer.setSurface(new Surface(this.getSurfaceTexture()));//设置显示的控件
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setPreparedListener(MediaPlayer.OnPreparedListener preparedListener) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.setOnPreparedListener(preparedListener);
        this.preparedListener = preparedListener;
    }

    public void setErrorListener(MediaPlayer.OnErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public void setCompletionListener(MediaPlayer.OnCompletionListener completionListener) {
        this.completionListener = completionListener;
    }

    public void setInfoListener(MediaPlayer.OnInfoListener infoListener) {
        this.infoListener = infoListener;
    }

    public void setSeekCompleteListener(MediaPlayer.OnSeekCompleteListener seekCompleteListener) {
        this.seekCompleteListener = seekCompleteListener;
    }

    /**
     * 改变视频方向状态
     */
    public void changeVideoOrientation() {
        if (isVerticalScreen) {
            //设置横屏
            RecycleTextureActivity.getRecycleTextureActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isVerticalScreen = false;
        } else {
            //设置竖屏
            isVerticalScreen = true;
            RecycleTextureActivity.getRecycleTextureActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onClick(View v) {
        changeVideoOrientation();
        ViewGroup.LayoutParams videoViewLayoutParams = this.getLayoutParams();
        videoViewLayoutParams.width = 1920;
        videoViewLayoutParams.height = 1080;
        this.setLayoutParams(videoViewLayoutParams);
        RecycleTextureActivity.getLayoutManager().scrollToPositionWithOffset(RecycleTextureActivity.getPositionView(), 0);
        RecycleTextureActivity.getLayoutManager().setStackFromEnd(true);


    }
}
