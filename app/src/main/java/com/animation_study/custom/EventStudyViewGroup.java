package com.animation_study.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by css on 2017/11/29.
 */

public class EventStudyViewGroup extends ViewGroup {

    private boolean isIntercept;

    public boolean isIntercept() {
        return isIntercept;
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }

    public EventStudyViewGroup(Context context) {
        super(context);
    }

    public EventStudyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public EventStudyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return isIntercept;
    }

    @Override
    protected void onLayout(boolean bb, int l, int t, int r, int b) {
        if (bb) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                childAt.layout(0, (i + 1) * 200, 200, (i + 1) * 200 + 100);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


//        return super.dispatchTouchEvent(ev);
        if (onInterceptTouchEvent(ev)) {
            onTouchEvent(ev);
            super.dispatchTouchEvent(ev);

        } else {
            return super.dispatchTouchEvent(ev);
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(getContext(), "事件被父控件拦截", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}
