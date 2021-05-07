package com.animation_study.activity;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;

/**
 * 补间动画只会改变显示位置， 不会改变真实坐标， 点击事件会在原位置
 * <p>
 * Created by css on 2017/10/13.
 */

public class TweenAnimationActivity extends BaseActiytiy {
    ImageView iv_test;
    AnimationSet as;

    @Override
    public Integer getLayout() {
        return R.layout.activity_tween;
    }

    @Override
    public void findView() {
        iv_test = (ImageView) findViewById(R.id.iv_test);
    }

    @Override
    public void initData() {
        as = new AnimationSet(true);
    }

    public void test(View v) {
        Toast.makeText(getApplicationContext(), "点击", Toast.LENGTH_SHORT).show();
    }

    public void alpha(View v) {
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        startMation(animation);
    }

    public void translate(View v) {
        Animation animation = new TranslateAnimation(0, 500, 0, 0);
        startMation(animation);
    }

    public void rotate(View v) {
        Animation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        startMation(animation);
    }

    public void scale(View v) {
        Animation animation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        startMation(animation);
    }

    private void startMation(Animation animation) {
        //CycleIinterpolator
        animation.setDuration(1000);//设置动画持续时间为500毫秒
        animation.setInterpolator(new LinearInterpolator());//设置动画插入器
//        animation.setInterpolator(this, android.R.anim.cycle_interpolator);//设置动画插入器
        animation.setFillAfter(true);//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）
        iv_test.startAnimation(animation);
        as.addAnimation(animation);
    }

    public void together(View v) {
//        as.setInterpolator(new LinearInterpolator());//设置动画插入器
        iv_test.startAnimation(as);
    }
}
