package com.animation_study.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by css on 2017/10/23.
 */

public class ClockLoadingView extends View implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    private Paint mPaint;
    private ValueAnimator mStartAnimator;
    private int mBigRadius;//外框
    private int mInnerRadius;//内框
    private RectF mInnerCircleRectF;
    private float mStartAngle = 0;
    private float mEndAngle = 0;
    private int mViewWidth;
    private int mViewHeight;
    private int mViewCenterX;
    private int mViewCenterY;
    private boolean mIsFirstState = true;

    public ClockLoadingView(Context context) {
        this(context, null);
    }

    public ClockLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setColor(Color.BLACK);//颜色
        mStartAnimator = ValueAnimator.ofFloat(0f, 360f);

        initValues();
    }

    private void initValues() {
        mBigRadius = 50;
        mInnerRadius = mBigRadius - 10;
        mInnerCircleRectF = new RectF();

        mStartAngle = 0;//初角度
        mEndAngle = 0;//终角度

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mInnerCircleRectF, mStartAngle, mEndAngle - mStartAngle, true, mPaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;
        mViewCenterX = w / 2;
        mViewCenterY = h / 2;

        mInnerCircleRectF.set(mViewCenterX - mInnerRadius, mViewCenterY - mInnerRadius, mViewCenterX + mInnerRadius, mViewCenterY + mInnerRadius);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mStartAnimator != null) {
            mStartAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mStartAnimator.setDuration(1000);
            mStartAnimator.addListener(this);
            mStartAnimator.addUpdateListener(this);
            mStartAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mStartAnimator != null) {
            mStartAnimator.setRepeatCount(0);
            mStartAnimator.setDuration(0);
            mStartAnimator.cancel();
            mStartAnimator.removeAllUpdateListeners();
            mStartAnimator.removeAllListeners();
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        mStartAngle = 0;
        mEndAngle = 0;
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        mIsFirstState = !mIsFirstState;
        if (mIsFirstState)//动画分为两个阶段，这里简单判断是否是第一阶段。
        {
            mStartAngle = 0;
            mEndAngle = 0;
        } else {
            mStartAngle = 0;
            mEndAngle = 360;
        }

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();

        if (mIsFirstState)
        {
            mEndAngle = value;
        }
        else
        {
            mStartAngle = value;
        }

        postInvalidate();

    }
}
