package com.animation_study.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;
import com.animation_study.activity.FullScreenActivity;
import com.animation_study.utils.Constant;

/**
 * Created by css on 2017/10/14.
 */

public class VideoActivity extends BaseActiytiy implements View.OnClickListener, SurfaceHolder.Callback {
    public static MediaPlayer mediaPlayer;
    String path;
    private VideoView videoView;
    SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    public Integer getLayout() {
        return R.layout.activity_video;
    }

    @Override
    public void findView() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        //初始化SurfaceHolder类，SurfaceView的控制器
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        path = Constant.strVideoUrl;
        mediaPlayer = new MediaPlayer();
        // 设置多媒体流类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();

        } catch (Exception e) {
            Log.i("通知", "播放过程中出现了错误哦");
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {


            }
        });

        videoView = (VideoView) findViewById(R.id.videoview);
        videoView.setVideoPath(path);
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));
        videoView.start();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        surfaceCreated(surfaceHolder);
    }

    public void play(View v) {
        mediaPlayer.start();

    }

    public void pause(View v) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void _continue(View v) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

    }

    public void stop(View v) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void fullSceen(View v) {
        Intent intent = new Intent(this, FullScreenActivity.class);
        startActivity(intent);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(surfaceHolder);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mediaPlayer.getVideoHeight());
        surfaceView.setLayoutParams(ll);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}
/**
 * 错误说明      使用MediaPlayer出现The surface has been released 异常处理方法
 * <p>
 * 用MediaPlayer播放视频或者音频的时候总出现这个错误，网上查找了很多方法，
 * 都说是调用MediaPlayer.start()方法的时候surfaceHolder还没有初始化好，
 * 但是debug的时候一般指示错误的地方都在MediaPlayer.setDisplay(surfaceHolder)这一句上面，
 * 解决方法很容易，就是把MediaPlayer.setDisplay(surfaceHolder);和MediaPlayer.start()方法
 * 都放到MediaPlayer.setOnPreparedListener这个函数里面，这个函数会在surfaceHolder准备好后调用，
 * 所以保证了使用surfaceHolder的时候已经准备好了
 */
