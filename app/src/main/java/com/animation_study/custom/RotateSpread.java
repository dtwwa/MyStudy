package com.animation_study.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;

import com.animation_study.R;
import com.animation_study.utils.ViewUtil;

/**
 * Created by css on 2017/10/23.
 */

public class RotateSpread extends View implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
    private Paint paint;
    private Point point;

    private int width;
    private int height;
    private ValueAnimator mValueAnimator;
    private ValueAnimator msmallAnimator;
    private float rangle = 60;

    private int x = 45;
    /**
     * 小球直径dp
     */
    private int diameter = 10;
    /**
     * 6个小球
     */
    private int count = 6;

    private boolean isEnd = false;

    public RotateSpread(Context context) {
        this(context, null);
    }

    public RotateSpread(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intitView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        int colors[] = {0, getResources().getColor(R.color.first)
                , getResources().getColor(R.color.second)
                , getResources().getColor(R.color.threth)
                , getResources().getColor(R.color.fourth)
                , getResources().getColor(R.color.fifth)
                , getResources().getColor(R.color.sixth)
        };
        canvas.rotate(rangle);
        for (int i = 1; i <= count; i++) {
            paint.setColor(colors[i]);

//            canvas.drawOval(0, 0, ViewUtil.dip2px(diameter), ViewUtil.dip2px(diameter), paint);
            canvas.drawCircle(ViewUtil.dip2px(x), 0, ViewUtil.dip2px(diameter), paint);

            canvas.rotate(360f / count);
        }

    }

    // fraction：当前的估值器计算值,startValue:起始值,endValue:终点值
    private Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        return (int) (startValue + fraction * (endValue - startValue));
    }


    private void intitView() {
        initData();
    }

    private void initData() {
        mValueAnimator = ValueAnimator.ofFloat(0, 359f);
        msmallAnimator = ValueAnimator.ofFloat(x, 0);
        point = ViewUtil.getScreenSize();
        paint = new Paint();
        paint.setDither(true);//防抖动
        paint.setAntiAlias(true);
        width = point.x;
        height = point.y;
        paint.setColor(Color.RED);
    }

    public void rotate() {
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mValueAnimator != null) {
//            mValueAnimator.setRepeatCount(2);
            mValueAnimator.setDuration(2000);
            mValueAnimator.setInterpolator(new CycleInterpolator(getContext(), null));
            mValueAnimator.addListener(this);
            mValueAnimator.addUpdateListener(this);
            mValueAnimator.start();
        }
    }

    /**
     * 停止动画
     */
    public void stop() {

    }

    @Override
    public void onAnimationStart(Animator animation) {
        rangle = 0;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        rangle = 0;
        if (msmallAnimator != null) {
//            msmallAnimator.setRepeatCount(1);
            msmallAnimator.setDuration(1000);
            msmallAnimator.setInterpolator(new AnticipateInterpolator());
            msmallAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //停止监听回调
                    animatiorEndListener.end();
                }
            });
            msmallAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    x = (int) value;
                    postInvalidate();
                }
            });
            msmallAnimator.start();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        rangle = 0;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        rangle = 0;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        rangle = value;
        postInvalidate();
    }

    private AnimatiorEndListener animatiorEndListener;

    public void setAnimatiorEndListener(AnimatiorEndListener animatiorEndListener) {
        this.animatiorEndListener = animatiorEndListener;

    }

    public interface AnimatiorEndListener {
        void end();
    }
}
