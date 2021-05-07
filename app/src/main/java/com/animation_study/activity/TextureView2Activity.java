package com.animation_study.activity;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.Surface;
import android.view.TextureView;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;
import com.animation_study.utils.Constant;

import java.io.IOException;

/**
 * 播放视频
 * Created by css on 2017/10/16.
 */

public class TextureView2Activity extends BaseActiytiy implements TextureView.SurfaceTextureListener {
    private TextureView textureview_video1;
    private MediaPlayer mMediaPlayer;
    private Surface mSurface;

    @Override
    public Integer getLayout() {
        return R.layout.activity_textureview2activity;
    }

    /**
     * 想要获取到TextureView的SurfaceTexture就需要确保TextureView正确的初始化，
     * 而TextureView正好提供了一个监听TextureView.SurfaceTextureListener
     * 它可以监听SurfaceTexture的生成、变化、销毁和更新，如果你确定TextureView已经生成完成的话
     * 也可以直接用getSurfaceTexture来获取；
     * <p>
     * 在onSurfaceTextureAvailable回调中，添加MediaPlayer的初始化操作。
     * 想要获取到TextureView的SurfaceTexture就需要确保TextureView正确的初始化，
     * 而TextureView正好提供了一个监听TextureView.SurfaceTextureListener
     * 它可以监听SurfaceTexture的生成、变化、销毁和更新，如果你确定TextureView已经生成完成的话
     * 也可以直接用getSurfaceTexture来获取；
     * <p>
     * 在onSurfaceTextureAvailable回调中，添加MediaPlayer的初始化操作。想要获取到TextureView
     * 的SurfaceTexture就需要确保TextureView正确的初始化， 而TextureView正好提供了一个
     * 监听TextureView.SurfaceTextureListener 它可以监听SurfaceTexture的生成、变化、销毁和更新，
     * 如果你确定TextureView已经生成完成的话 也可以直接用getSurfaceTexture来获取；
     * <p>
     * 在onSurfaceTextureAvailable回调中，添加MediaPlayer的初始化操作。
     */
    @Override
    public void findView() {
        textureview_video1 = (TextureView) findViewById(R.id.textureview_video1);
        mMediaPlayer = new MediaPlayer();
        textureview_video1.setSurfaceTextureListener(this);
        try {
            mMediaPlayer.setDataSource(Constant.strVideoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = new Surface(surface);
        mMediaPlayer.setSurface(mSurface);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.start();
            }

        });

        mMediaPlayer.prepareAsync();

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        textureview_video1 = null;
        surface = null;
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
