package com.animation_study.activity;

import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;

/**
 * Created by css on 2017/10/16.
 */

public class SystemSetActivity extends BaseActiytiy implements SeekBar.OnSeekBarChangeListener {
    private SeekBar sb_activity_brightness;
    private SeekBar sb_system_brightness;
    private SeekBar sb_media_volume;
    private int maxVolume = -2;
    private int nowVolume = -2;
    private int nowSystemScreenBrightness = -2;
    private AudioManager mAudioManager;
    private WindowManager.LayoutParams lp;
    private Window window;

    @Override
    public Integer getLayout() {
        return R.layout.activity_system_setting;
    }

    @Override
    public void findView() {
        sb_activity_brightness = (SeekBar) findViewById(R.id.sb_activity_brightness);
        sb_system_brightness = (SeekBar) findViewById(R.id.sb_system_brightness);
        sb_media_volume = (SeekBar) findViewById(R.id.sb_media_volume);
        sb_activity_brightness.setOnSeekBarChangeListener(this);//添加滑动条进度改变监听
        sb_system_brightness.setOnSeekBarChangeListener(this);
        sb_media_volume.setOnSeekBarChangeListener(this);
    }

    @Override
    public void initData() {
        //初始化音频管理器
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(getApplicationContext().AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        nowVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb_media_volume.setMax(maxVolume);
        sb_media_volume.setProgress(nowVolume);


        window = this.getWindow();
        lp = window.getAttributes();
        try {
            nowSystemScreenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            sb_activity_brightness.setMax(255);
            sb_activity_brightness.setProgress(nowSystemScreenBrightness);
            sb_system_brightness.setMax(255);
            sb_system_brightness.setProgress(nowSystemScreenBrightness);
            Log.d("sss", nowSystemScreenBrightness + "");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {

            case R.id.sb_system_brightness:
                Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
                this.getContentResolver().notifyChange(uri, null);

            case R.id.sb_activity_brightness://1~255
                if (nowSystemScreenBrightness == -1) {
                    lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                } else {
                    lp.screenBrightness = (progress <= 0 ? 1 : progress) / 255f;
                }
                sb_activity_brightness.setProgress(progress);
                window.setAttributes(lp);
                break;
            case R.id.sb_media_volume:
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                break;


        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setDormancy(View v) {
        Settings.System.putInt(getContentResolver(), android.provider.Settings.System.SCREEN_OFF_TIMEOUT, 5);

    }

    public void onDormancy(View v) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void offDormancy(View v) {
        //关掉禁止屏幕休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
}
