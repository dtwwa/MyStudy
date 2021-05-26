package com.animation_study.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by css on 2017/11/29.
 */

@SuppressLint("AppCompatCustomView")
public class EventStudyView extends Button {
    public EventStudyView(Context context) {
        super(context);
    }

    public EventStudyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventStudyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(getContext(), "得到事件", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onTouchEvent(event);
    }
}
