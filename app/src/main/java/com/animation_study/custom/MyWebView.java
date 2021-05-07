package com.animation_study.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Jay on 2015/9/11 0011.
 */
public class MyWebView extends WebView {
    int nowTop = 0;
    private OnScrollChangedCallback mOnScrollChangedCallback;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        nowTop = t;
        if (mOnScrollChangedCallback != null && t == 0) {
            mOnScrollChangedCallback.onScroll(t);
        }
    }

    int y = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int nowY = (int) event.getY();
                if (nowTop > 0) {
                    mOnScrollChangedCallback.onTouch(nowY - y);
                }
                break;
        }

        return b;
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public static interface OnScrollChangedCallback {
        //这里的dx和dy代表的是x轴和y轴上的偏移量，你也可以自己把l, t, oldl, oldt四个参数暴露出来
        void onScroll(int top);

        void onTouch(int dy);
    }

}