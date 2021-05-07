package com.animation_study;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.animation_study.activity.AutoTextActivity;
import com.animation_study.activity.BitmapActivity;
import com.animation_study.activity.BrowserActivity;
import com.animation_study.activity.DrawingBoardActivity;
import com.animation_study.activity.EncVideoViewActivity;
import com.animation_study.activity.GSYVIdeoActibity;
import com.animation_study.activity.ListVideoActivity;
import com.animation_study.activity.MappingActivity;
import com.animation_study.activity.MaskFilterActivity;
import com.animation_study.activity.ParallaxAnimationActivity;
import com.animation_study.activity.ProgressViewActivity;
import com.animation_study.activity.PropertyAnimationActivity;
import com.animation_study.activity.RecyAnimationActivity;
import com.animation_study.activity.RecycleTextureActivity;
import com.animation_study.activity.RetrofitActivity;
import com.animation_study.activity.SongActivity;
import com.animation_study.activity.StudyJCVideoActivity;
import com.animation_study.activity.SystemSetActivity;
import com.animation_study.activity.TextureView2Activity;
import com.animation_study.activity.TextureViewActivity;
import com.animation_study.activity.TweenAnimationActivity;
import com.animation_study.activity.VideoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
//                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    public void tween_animation(View v) {
        Intent intent = new Intent(this, TweenAnimationActivity.class);
        startActivity(intent);


    }

    public void value_animation(View v) {
        Intent intent = new Intent(this, PropertyAnimationActivity.class);
        startActivity(intent);
    }

    public void dialog1(View v) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // import android.support.v7.app.AlertDialog;
        dialog = builder
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("标题")
                .setMessage("内容")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(false)
                .create();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notification(View v) {
        /**
         * Icon/Photo：大图标
         Title/Name：标题
         Message：内容信息
         Timestamp：通知时间，默认是系统发出通知的时间，也可以通过setWhen()来设置
         Secondary Icon：小图标
         内容文字，在小图标的左手边的一个文字
         */
        /**
         * 使用的基本流程：
         Step 1. 获得NotificationManager对象：
         NotificationManager mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
         Step 2. 创建一个通知栏的Builder构造类：
         Notification.Builder mBuilder = new Notification.Builder(this);
         Step 3. 对Builder进行相关的设置，比如标题，内容，图标，动作等！
         Step 4.调用Builder的build()方法为notification赋值
         Step 5.调用NotificationManager的notify()方法发送通知！
         PS:另外我们还可以调用NotificationManager的cancel()方法取消通知
         */

        /**
         * setContentTitle(CharSequence)：设置标题
         setContentText(CharSequence)：设置内容
         setSubText(CharSequence)：设置内容下面一小行的文字
         setTicker(CharSequence)：设置收到通知时在顶部显示的文字信息
         setWhen(long)：设置通知时间，一般设置的是收到通知时的System.currentTimeMillis()
         setSmallIcon(int)：设置右下角的小图标，在接收到通知的时候顶部也会显示这个小图标
         setLargeIcon(Bitmap)：设置左边的大图标
         setAutoCancel(boolean)：用户点击Notification点击面板后是否让通知取消(默认不取消)
         setDefaults(int)：向通知添加声音、闪灯和振动效果的最简单、
         使用默认（defaults）属性，可以组合多个属性，
         Notification.DEFAULT_VIBRATE(添加默认震动提醒)；
         Notification.DEFAULT_SOUND(添加默认声音提醒)；
         Notification.DEFAULT_LIGHTS(添加默认三色灯提醒)
         Notification.DEFAULT_ALL(添加默认以上3种全部提醒)
         setVibrate(long[])：设置振动方式，比如：
         setVibrate(new long[] {0,300,500,700});延迟0ms，然后振动300ms，在延迟500ms，
         接着再振动700ms，关于Vibrate用法后面会讲解！
         setLights(int argb, int onMs, int offMs)：设置三色灯，参数依次是：灯光颜色，
         亮持续时间，暗的时间，不是所有颜色都可以，这跟设备有关，有些手机还不带三色灯；
         另外，还需要为Notification设置flags为Notification.FLAG_SHOW_LIGHTS才支持三色灯提醒！
         setSound(Uri)：设置接收到通知时的铃声，可以用系统的，也可以自己设置，例子如下:
         .setDefaults(Notification.DEFAULT_SOUND) //获取默认铃声
         .setSound(Uri.parse(“file:///sdcard/xx/xx.mp3”)) //获取自定义铃声
         .setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, “5”))
         //获取Android多媒体库内的铃声
         setOngoing(boolean)：设置为ture，表示它为一个正在进行的通知。他们通常是用来表示
         一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,
         同步操作,主动网络连接)
         setProgress(int,int,boolean)：设置带进度条的通知
         参数依次为：进度条最大数值，当前进度，进度是否不确定
         如果为确定的进度条：调用setProgress(max, progress, false)来设置通知，
         在更新进度的时候在此发起通知更新progress，并且在下载完成后要移除进度条
         ，通过调用setProgress(0, 0, false)既可。如果为不确定（持续活动）的进度条，
         这是在处理进度无法准确获知时显示活动正在持续，所以调用setProgress(0, 0, true)
         ，操作结束时，调用setProgress(0, 0, false)并更新通知以移除指示条
         setContentIntent(PendingIntent)：PendingIntent和Intent略有不同，它可以设置执行次数，
         主要用于远程服务通信、闹铃、通知、启动器、短信中，在一般情况下用的比较少。比如这里通过
         Pending启动Activity：getActivity(Context, int, Intent, int)，当然还可以启动Service或者Broadcast
         PendingIntent的位标识符(第四个参数)：
         FLAG_ONE_SHOT 表示返回的PendingIntent仅能执行一次，执行完后自动取消
         FLAG_NO_CREATE 表示如果描述的PendingIntent不存在，并不创建相应的PendingIntent，而是返回NULL
         FLAG_CANCEL_CURRENT 表示相应的PendingIntent已经存在，则取消前者，然后创建新的PendingIntent，
         这个有利于数据保持为最新的，可以用于即时通信的通信场景
         FLAG_UPDATE_CURRENT 表示更新的PendingIntent
         使用示例：
         */


        NotificationManager mNManager;
        Notification notify1;
        Bitmap LargeBitmap = null;
        //创建大图标的Bitmap
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final int NOTIFYID_1 = 1;

//设置图片,通知标题,发送时间,提示方式等属性
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle("叶良辰")                        //标题
                .setContentText("我有一百种方法让你呆不下去~")      //内容
                .setSubText("——记住我叫叶良辰")                    //内容下面的一小段文字
                .setTicker("收到叶良辰发送过来的信息~")             //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setSmallIcon(R.mipmap.test)            //设置小图标
                .setLargeIcon(LargeBitmap)                     //设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
//                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                .setAutoCancel(true);                         //设置点击后取消Notification

        notify1 = mBuilder.build();
        mNManager.notify(NOTIFYID_1, notify1);

    }

    public void bitmap(View v) {
        Intent intent = new Intent(getApplicationContext(), BitmapActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);
    }

    public void mapping(View v) {
        Intent intent = new Intent(getApplicationContext(), MappingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);
    }

    public void drawingBoard(View v) {
        Intent intent = new Intent(getApplicationContext(), DrawingBoardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);
    }

    public void maskFilter(View v) {
        Intent intent = new Intent(getApplicationContext(), MaskFilterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);
    }

    public void browser(View v) {
        Intent intent = new Intent(getApplicationContext(), BrowserActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);
    }

    public void song(View v) {
        Intent intent = new Intent(getApplicationContext(), SongActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);
    }

    public void video(View v) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void textureViewCamera(View v) {
        Intent intent = new Intent(this, TextureViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void encVideoView(View v) {
        Intent intent = new Intent(this, EncVideoViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void listVideoView1(View v) {
        Intent intent = new Intent(this, ListVideoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void systemSeting(View v) {
        Intent intent = new Intent(this, SystemSetActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void textureView(View v) {
        Intent intent = new Intent(this, TextureView2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void recycleTextureView(View v) {
        Intent intent = new Intent(this, RecycleTextureActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void GSYVideo(View v) {
        Intent intent = new Intent(this, GSYVIdeoActibity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void jcVideo(View v) {
        Intent intent = new Intent(this, StudyJCVideoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void recyAnimation(View v) {
        Intent intent = new Intent(this, RecyAnimationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void autoText(View v) {
        Intent intent = new Intent(this, AutoTextActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void progressView(View v) {
        Intent intent = new Intent(this, ProgressViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void parallaxAnimation(View v) {
        Intent intent = new Intent(this, ParallaxAnimationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
    }

    public void bottomDialog(View v) {
//        Intent intent = new Intent(this, ParallaxAnimationActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.activity_enter, 0);//0表示没有退出动画
//
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setView(View.inflate(getApplicationContext(), R.layout.dialog_bottom, null));


// = builder.setTitle("nihao1")
//                .setMessage("我是弹窗")
//                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).setCancelable(false)
//                .create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.show();

    }

    public void retrofit(View v) {
        Intent intent = new Intent(this, RetrofitActivity.class);
        startActivity(intent);
    }

    public void login(View v) {

    }

}
