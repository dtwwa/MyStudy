package com.animation_study.manager;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.animation_study.CrashApplication;
import com.animation_study.MyVideoPlayerManager;
import com.animation_study.custom.SJCTexterView;

import java.io.IOException;

/**
 * Created by css on 2017/10/17.
 */

public class SJCVideoManager implements TextureView.SurfaceTextureListener {
    public static SJCVideoManager sjcVideoManager;
    public static SJCTexterView sjcTexterView;
    public static SurfaceTexture surfaceTexture;
    public MediaPlayer mediaPlayer;
    public static String videoUrl;
    private String path = Environment.getExternalStorageDirectory().getPath() + "/joke_essay/1493638382153.mp4";
    public int videoWidth = 0;
    public int videoHeight = 0;
    public float videoProportion = 0;

    public static SJCVideoManager instance() {
        if (sjcVideoManager == null) {
            sjcVideoManager = new SJCVideoManager();
        }
        return sjcVideoManager;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (surfaceTexture == null) {
            Log.d("异步准备", "进入有");
            surfaceTexture = surface;
            getMediaPlayer().reset();
            try {
                getMediaPlayer().setDataSource(path);
                getMediaPlayer().setOnPreparedListener(MyVideoPlayerManager.instance().getCurrentTexture());
                getMediaPlayer().prepareAsync();
                Log.d("异步准备", MyVideoPlayerManager.instance().getCurrentTexture().toString());
                getMediaPlayer().setSurface(new Surface(surface));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("sss", "surfaceTexture不为空");
//            getMediaPlayer().setSurface(new Surface(surface));
            sjcTexterView.setSurfaceTexture(surfaceTexture);
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.d("onSurfaceTextureSizeChanged", "onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return surfaceTexture == null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        Log.d("onSurfaceTextureUpdated", "onSurfaceTextureUpdated");
    }

    public void setSurfaceTextureListener(String path) {
        this.path = path;
        Log.d("天添加setSurfaceTextureListener", path);
        sjcTexterView = new SJCTexterView(CrashApplication.context);
        sjcTexterView.setSurfaceTextureListener(this);
        sjcTexterView.invalidate();
    }

    /**
     * 单一mediaplayer这样做， 一个应用只会有一个mediaplayer， 不会出现多个视频同时播放的情况
     *
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
