package com.animation_study.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.animation_study.R;
import com.animation_study.custom.ScrollBigView;

public class ScrollBigActivity extends AppCompatActivity {
    private ScrollBigView ScrollBigView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_big);
        ScrollBigView = (com.animation_study.custom.ScrollBigView) findViewById(R.id.sbv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ScrollBigView.onTouch(event);
        return super.onTouchEvent(event);
    }
}
