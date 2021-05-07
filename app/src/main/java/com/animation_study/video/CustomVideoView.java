package com.animation_study.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

/**
 * Created by Song on 2017/4/24.
 */

public class CustomVideoView extends VideoView implements View.OnTouchListener {

    private float lastX;
    private float lastY;
    private float downX;
    private float downY;
    private Context mContext;
    private StateListener mStateListener;
    private static final int HORIZONTAL = 1;
    private static final int VERTICAL = 2;
    private int ORIENTATION = 0;

    //接口回调
    public interface StateListener {
        void changeVolumnOrBrightness(float detlaY, float x);

        void changeBrightness(float detlaX);

        void hideHint();

        void onClick();

        void seekTo(int seek);
    }

    //设置监听
    public void setStateListener(StateListener stateListener) {
        this.mStateListener = stateListener;
    }

    public CustomVideoView(Context context) {
        this(context, null);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    public void onClick() {
        mStateListener.onClick();
    }

    /**
     * 还没判断完， 滑动事件不敏捷（已解决）
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                downX = lastX;
                downY = lastY;
                break;
            case MotionEvent.ACTION_MOVE:
                float detlaX = event.getX() - lastX;
                float detlaY = event.getY() - lastY;
                int x = (int) Math.abs(detlaX);
                int y = (int) Math.abs(detlaY);
                if (ORIENTATION == 0) {//用于判断手指从按下开始第一次运动的方向， 之判断一次
                    if (x > 10) {
                        ORIENTATION = HORIZONTAL;
                    } else if (y > 10) {
                        ORIENTATION = VERTICAL;
                    }
                } else {
                    //此时已经得到手指滑动的方向
                    switch (ORIENTATION) {
                        case HORIZONTAL:
                            mStateListener.changeBrightness(event.getX() - downX);

                            break;
                        case VERTICAL:
                            mStateListener.changeVolumnOrBrightness(event.getY() - downY, event.getX());

                            break;
                    }


                }
//
//                if (Math.abs(detlaX) > thresold && Math.abs(detlaY) < thresold) {
//                    mStateListener.changeBrightness(detlaX);
//                }
//                if (Math.abs(detlaX) < thresold && Math.abs(detlaY) > thresold) {
//                    mStateListener.changeVolumn(detlaY);
//                }

                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起后， 方向归于0
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (ORIENTATION == HORIZONTAL) {
                    mStateListener.seekTo((int) (upX - downX));
                }

                ORIENTATION = 0;

                if (Math.abs(upX - downX) < 20 && Math.abs(upY - downY) < 20) {
                    //点击
                    onClick();
                } else {
                    mStateListener.hideHint();
                }
                break;
        }
        //让事件继续传递
        return true;
    }
}
