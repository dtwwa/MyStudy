package com.animation_study.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;

/**
 * Created by css on 2017/10/13.
 */

public class PropertyAnimationActivity extends BaseActiytiy {
    private ImageView iv_test;

    @Override
    public void findView() {
        iv_test = (ImageView) findViewById(R.id.iv_test);
    }

    @Override
    public void initData() {
        animator1 = ObjectAnimator.ofFloat(iv_test, "alpha", 1.0f, 0.0f, 1.0f);
        animator1.setDuration(5000);

        animator2 = ObjectAnimator//
                .ofFloat(iv_test, "translationX", 0.0F, -500, 0.0F, 500, 0)//
                .setDuration(1000);
        animator2.setInterpolator(new AnticipateInterpolator());//设置动画插入器

        animator3 = ObjectAnimator//
                .ofFloat(iv_test, "rotationX", 0.0F, 360.0F, 0.0F)//
                .setDuration(500);

        animator4 = ObjectAnimator.ofFloat(iv_test, "scaleY", 1f, 3f, 1f);
        animator4.setDuration(5000);
    }

    @Override
    public Integer getLayout() {
        return R.layout.activity_property;
    }

    public void test(View v) {
        Toast.makeText(getApplicationContext(), "点击", Toast.LENGTH_SHORT).show();
    }

    ObjectAnimator animator1;

    public void alpha(View v) {

        animator1.start();
    }

    ObjectAnimator animator2;

    public void translate(View v) {

        animator2.start();
    }

    ObjectAnimator animator3;//

    public void rotate(View v) {

        animator3.start();
    }

    ObjectAnimator animator4;

    public void scale(View v) {

        animator4.start();
    }

    public void together(View v) {
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator1).with(animator2);
        animSet.play(animator1).with(animator3);
        animSet.play(animator1).with(animator4);
        animSet.setDuration(5000);
        animSet.start();

    }
}
