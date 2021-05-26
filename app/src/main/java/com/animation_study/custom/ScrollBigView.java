package com.animation_study.custom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;

/**
 * Created by css on 2017/12/4.
 */

public class ScrollBigView extends android.support.v7.widget.AppCompatImageView {
    public ScrollBigView(Context context) {
        this(context, null);
    }

    public ScrollBigView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    int downY = 0;
    float scale = 0;


    public boolean onTouch(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getY();

                scale = (float) ((moveY - downY) / 1000.0);
                if (scale < 0.8) {
                    Log.d("ss" + "  " + downY + "  " + moveY + " " + scale, "" + (float) (scale * 1.0 / 1000.0));

                    setScaleX(scale + 1);
                    setScaleY(scale + 1);
                }
                break;
            case MotionEvent.ACTION_UP:
                AnimatorSet animatorSet = new AnimatorSet();//组合动画
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1 + scale, 1.0f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1 + scale, 1.0f);
                scaleX.setDuration(300);
                scaleY.setDuration(300);
                animatorSet.play(scaleX).with(scaleY);
                animatorSet.setInterpolator(new OvershootInterpolator());
                animatorSet.start();
                break;
        }
        return false;
    }

}
