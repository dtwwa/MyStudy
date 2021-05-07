package com.animation_study.video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.animation_study.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 视频控制
 * <p>
 * 封装基本完成
 * 需要优化的地方
 * 亮度  不够流畅
 * 控制面板的动画， 有时候可能会有错误
 * 视频左右滑动， 写的不好
 * <p>
 * 重大bug  无影响， 但是我找不到错误的地点，
 * 说明， 在程序没有完全退出， 再次进入播放界面，会报一个异常  是这个类中的异常
 * <p>
 * <p>
 * Created by Song on 2017/4/25.
 */
public class SuperVideoView extends RelativeLayout {

    private CustomVideoView videoView;
    private SeekBar seekbarProgress;
    private SeekBar seekbarProgress2;
    private ImageView btnController;
    private TextView tvCurrentProgress;
    private TextView tvTotalProgress;
    private ImageView btnScreen;
    private FrameLayout flVolume;
    private FrameLayout flLight;
    //控制条
    private LinearLayout llController;
    private RelativeLayout rlContainer;
    private AudioManager mAudioManager;
    private int screenWidth;
    private int screenHeight;
    private Context mContext;
    private View videoLayout;
    private Activity mActivity;
    private int videoPos;
    private int state = 0;
    private String mVideoPath;
    private boolean isVerticalScreen = true;

    private static final int UPDATE_PROGRESS = 1;
    private static final int DISPALY = 2;

    private ImageView iv_first_frame;
    private static boolean isFirstPlay = true;
    private static boolean isPause = true;
    private static boolean isDisplay = true;
    private static boolean isDoneDisplay = true;
    private static boolean isDoneDisappear = true;
    private Timer timer = null;
    private int top = 0;
    private int topBar = 0;
    private int topHeight = 0;
    private int topBarHeight = 0;
    int lastVolume = 0;
    float lastBrightness = 0;
    private int nowPlayTime = -2;

    public SuperVideoView(Context context) {
        super(context, null);
    }

    public SuperVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        initView();
        initData();
        initListener();
    }

    public void register(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 设置媒体路径
     *
     * @param path
     */
    public void setVideoPath(String path) {

        this.mVideoPath = path;
        if (path.startsWith("http") || path.startsWith("https")) {
//            videoView.setVideoURI(Uri.parse(path));
            videoView.setVideoPath(mVideoPath);
        } else {
            videoView.setVideoPath(mVideoPath);
        }

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        mmr.setDataSource(mVideoPath);
//        Bitmap bitmap = mmr.getFrameAtTime();//获取第一帧图片
//        iv_first_frame.setImageBitmap(bitmap);
        mmr.release();//释放资源
    }

    private void init() {
        screenWidth = ScreenUtils.getScreenWidth(mContext);
        screenHeight = ScreenUtils.getScreenHeight(mContext);
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        mContext.registerReceiver(new MyVolumeReceiver(), filter);

    }

    /**
     * UI 初始化
     */
    private void initView() {

        videoLayout = LayoutInflater.from(mContext).inflate(R.layout.video_layout, this, true);
        flVolume = (FrameLayout) videoLayout.findViewById(R.id.fl_volume);
        flLight = (FrameLayout) videoLayout.findViewById(R.id.fl_light);
        videoView = (CustomVideoView) videoLayout.findViewById(R.id.videoView);
        seekbarProgress = (SeekBar) videoLayout.findViewById(R.id.seekbar_progress);
        seekbarProgress2 = (SeekBar) videoLayout.findViewById(R.id.seekbar_progress2);
        btnController = (ImageView) videoLayout.findViewById(R.id.btn_controller);
        btnScreen = (ImageView) videoLayout.findViewById(R.id.btn_screen);
        tvCurrentProgress = (TextView) videoLayout.findViewById(R.id.tv_currentProgress);
        tvTotalProgress = (TextView) videoLayout.findViewById(R.id.tv_totalProgress);
        llController = (LinearLayout) videoLayout.findViewById(R.id.ll_controller);
        rlContainer = (RelativeLayout) videoLayout.findViewById(R.id.rl_container);
        iv_first_frame = (ImageView) videoLayout.findViewById(R.id.iv_first_frame);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        isFirstPlay = true;
        top = llController.getTop();
        topBar = seekbarProgress2.getTop();
        topHeight = ScreenUtils.dipToPx(getContext(), 50);
        topBarHeight = ScreenUtils.dipToPx(getContext(), 1);
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 注册事件
     */
    private void initListener() {

        btnScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVideoOrientation();
            }
        });

        btnController.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始播放视频按钮
                if (videoView.isPlaying()) {
                    if (timer != null) {
                        timer.cancel();

                    } else {
                    }
                    btnController.setImageResource(R.drawable.btn_play_style);
                    videoView.pause();
                    isPause = true;//视屏暂停， 做标记
                    mHandler.removeMessages(UPDATE_PROGRESS);
                } else {
                    palyVideo();
                }
            }
        });

        /**
         * 状态事件
         */
        videoView.setStateListener(new CustomVideoView.StateListener() {

            /**
             * 改变声音大小
             */
            @Override
            public void changeVolumnOrBrightness(float detlaY, float x) {
                if (x > (isVerticalScreen ? screenWidth / 2 : screenHeight / 2)) {//竖屏为屏幕宽度的一般， 横屏，屏幕高度的一半
                    if (Math.abs(lastVolume - (int) detlaY) >= 33) {
                        if (flVolume.getVisibility() == View.GONE) {
                            flVolume.setVisibility(View.VISIBLE);
                        }
                        //初始化音频管理器
                        AudioManager mAudioManager = (AudioManager) getContext().getSystemService(getContext().AUDIO_SERVICE);
                        //获取系统最大音量
                        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        // 获取设备当前音量
                        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, lastVolume > detlaY ? currentVolume + 1 : currentVolume - 1, 0);

                        lastVolume = (int) detlaY;
                    }
                } else {//改变屏幕亮度
                    if (Math.abs(lastBrightness - detlaY) >= 1) {
                        if (flLight.getVisibility() == View.GONE) {
                            flLight.setVisibility(View.VISIBLE);
                        }
                        WindowManager.LayoutParams wml = mActivity.getWindow().getAttributes();
                        float screenBrightness = wml.screenBrightness;

                        screenBrightness = detlaY > 0 ? screenBrightness - Math.abs(lastBrightness - detlaY) / 800 : screenBrightness + Math.abs(lastBrightness - detlaY) / 800;
                        if (screenBrightness > 1.0f) {
                            screenBrightness = 1.0f;
                        } else if (screenBrightness < 0.01f) {
                            screenBrightness = 0.01f;
                        }
                        wml.screenBrightness = screenBrightness;
                        mActivity.getWindow().setAttributes(wml);
                        lastBrightness = detlaY;
                    }
                }


//                int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                int index = (int) (detlaY / screenHeight * maxVolume * 3);
//                int volume = Math.max(0, currentVolume - index);
//                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
//                Log.d("y----", detlaY + "");
//
//
//                Log.d("当前音量", currentVolume + "");
            }

            /**
             * 改变视频进度
             * @param detlaX
             */
            @Override
            public void changeBrightness(float detlaX) {
//                if(detlaX>0)前进， else 后退

            }

            @Override
            public void hideHint() {
                if (flLight.getVisibility() == View.VISIBLE) {
                    flLight.setVisibility(GONE);
                }

                if (flVolume.getVisibility() == View.VISIBLE) {
                    flVolume.setVisibility(GONE);
                }
            }

            @Override
            public void onClick() {
                if (isFirstPlay) {//第一次点击封面  ， 做完动作直接退出不往下走
                    isFirstPlay = false;
                    palyVideo();
                    return;
                } else {
                }

                //整个video屏幕点击事件
                if (!isDisplay) {
                    if (!isDoneDisappear || !isDoneDisplay) {//动画未做完， 就不允许进入此函数
                        return;
                    }
                    //如果控制面板的透明度为 0 改为 1
                    if (btnController.getAlpha() != 1) {
                        displayController();
                        if (timer != null) {
                            timer.cancel();
                        }
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                mHandler.sendEmptyMessage(DISPALY);
                            }
                        }, 3000);
                    } else {
                    }
                } else {
                    //
                    if (timer != null) {
                        timer.cancel();

                    } else {
                    }
                    disappear();

                }
            }

            @Override
            public void seekTo(int seek) {//滑动视频seekTo
                if (nowPlayTime != -2) {//不等于-2说明已经开始播放视频
                    Log.d("sss", seek + ", " + nowPlayTime);
                    videoView.seekTo(nowPlayTime + seek * 3);//当前播放时间， 加上滑动距离*2 , 因为不*2距离太短
                }
            }
        });

        /**
         * 播放进度
         */
        seekbarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTextViewFormat(tvCurrentProgress, progress);
                nowPlayTime = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 暂停刷新
                mHandler.removeMessages(UPDATE_PROGRESS);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (state != 0) {
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                }
                videoView.seekTo(seekBar.getProgress());

            }
        });
    }


    /**
     * 开始播放视频
     */
    private void palyVideo() {
        btnController.setImageResource(R.drawable.btn_pause_style);
        videoView.start();
        if (iv_first_frame.getVisibility() != View.GONE) {
            iv_first_frame.setVisibility(View.GONE);
        }
        mHandler.sendEmptyMessage(UPDATE_PROGRESS);
        if (state == 0) state = 1;
        if (timer != null) {
            timer.cancel();
        }//在new之前 ， 先把之前的计时器关掉 ， 不然如果多次点击会出现之前的计时器无法关闭，
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(DISPALY);
            }
        }, 3000);
    }

    /**
     * 显示控制器面板
     */
    private void displayController() {
        if (!isDoneDisplay) {
            //如果动画还没做完
            return;
        }
        //两个都设为可见
        btnController.setVisibility(View.VISIBLE);
        llController.setVisibility(View.VISIBLE);
        //不透明透明动画
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnController, "alpha", 0.9f, 0.9f).setDuration(300);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(llController, "translationY", top + topHeight, top).setDuration(300);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(seekbarProgress2, "translationY", topBar - topBarHeight, topBar).setDuration(300);

        AnimatorSet as = new AnimatorSet();
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isDisplay = true;
                isDoneDisplay = true;//标记为动画结束这个函数可以进入
            }
        });
//        as.play(animator3).after(animator1);
        as.play(animator1).with(animator2);
        as.play(animator3).after(animator1);
        as.start();
        isDoneDisplay = false;//标记为动画开始
    }

    /**
     * 隐藏控制面板
     */
    private void disappear() {
        if (!isDoneDisappear) {
            //如果动画还没做完
            return;
        }
        btnController.setVisibility(View.GONE);//两个都设为不可见
//        llController.setVisibility(View.GONE);
        //不透明透明动画
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnController, "alpha", 0.9f, 0.9f).setDuration(300);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(llController, "translationY", top, top + topHeight).setDuration(300);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(seekbarProgress2, "translationY", topBar, topBar - topBarHeight).setDuration(300);

        AnimatorSet as = new AnimatorSet();
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isDisplay = false;
                isDoneDisappear = true;
            }
        });
        as.play(animator1).with(animator2);
        as.play(animator1).after(animator3);
//        as.play(animator1).after(animator3);
//        as.play(animator1);
        as.start();
        isDoneDisappear = false;

    }

    /**
     * 改变视频方向状态
     */
    public void changeVideoOrientation() {
        if (isVerticalScreen) {
            //设置横屏
            btnScreen.setImageResource(R.mipmap.narrow);
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            //设置竖屏
            btnScreen.setImageResource(R.mipmap.enlarge);
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 屏幕状态改变
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //现在是竖屏
            isVerticalScreen = true;
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dipToPx(mContext, 290));
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            isVerticalScreen = false;
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            // 移除半屏状态
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    /**
     * 切换尺寸
     *
     * @param width
     * @param height
     */
    public void setVideoViewScale(int width, int height) {

        ViewGroup.LayoutParams videoViewLayoutParams = videoView.getLayoutParams();
        videoViewLayoutParams.width = width;
        videoViewLayoutParams.height = height;
        videoView.setLayoutParams(videoViewLayoutParams);
        LayoutParams rlContainerLayoutParams = (LayoutParams) rlContainer.getLayoutParams();
        rlContainerLayoutParams.width = width;
        rlContainerLayoutParams.height = height;
        rlContainer.setLayoutParams(rlContainerLayoutParams);
    }

    /**
     * 格式化时间进度
     */
    private void updateTextViewFormat(TextView tv, int m) {

        String result;
        // 毫秒转成秒
        int second = m / 1000;
        int hour = second / 3600;
        int minute = second % 3600 / 60;
        int ss = second % 60;
        minute += hour * 60;

        result = String.format("%02d:%02d", minute, ss);

        tv.setText(result);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == UPDATE_PROGRESS) {

                // 获取当前时间
                int currentTime = videoView.getCurrentPosition();
                // 获取总时间
                int totalTime = videoView.getDuration() - 100;
                if (currentTime >= totalTime) {
                    videoView.pause();
                    videoView.seekTo(0);
                    seekbarProgress.setProgress(0);
                    btnController.setImageResource(R.drawable.btn_play_style);
                    updateTextViewFormat(tvCurrentProgress, 0);
                    mHandler.removeMessages(UPDATE_PROGRESS);
                } else {
                    seekbarProgress.setMax(totalTime);
                    seekbarProgress2.setMax(totalTime);
                    seekbarProgress.setProgress(currentTime);
                    seekbarProgress2.setProgress(currentTime);
                    updateTextViewFormat(tvCurrentProgress, currentTime);
                    updateTextViewFormat(tvTotalProgress, totalTime);
                    mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 100);
                }
            } else if (msg.what == DISPALY) {
                disappear();
            }
        }
    };

    public void onPause() {
        btnController.setImageResource(R.drawable.btn_play_style);
        btnController.setPressed(false);
        videoPos = videoView.getCurrentPosition();
        videoView.stopPlayback();
        mHandler.removeMessages(UPDATE_PROGRESS);
    }

    public void onResume() {
        videoView.seekTo(videoPos);
        videoView.resume();

    }

    /**
     * 设置播放进度条样式
     *
     * @param drawable
     */
    public void setProgressBg(Drawable drawable) {
        seekbarProgress.setProgressDrawable(drawable);
    }

    /**
     * 设置声音控制条样式
     *
     * @param drawable
     */
    public void setVolumeBg(Drawable drawable) {
//        seekbarVolume.setProgressDrawable(drawable);
    }

    class MyVolumeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                // 当前的媒体音量
                int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                seekbarVolume.setProgress(currentVolume);
            }
        }
    }

    public boolean isVerticalScreen() {
        return isVerticalScreen;
    }
}
