package com.animation_study.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.animation_study.custom.MyView;
import com.animation_study.R;

/**
 * Created by css on 2017/10/13.
 */

public class DrawingBoardActivity extends Activity {
    private MyView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        findView();
    }

    public int getLayout() {
        return R.layout.activity_draw_board;
    }

    public void findView() {
        view = (MyView) findViewById(R.id.myview);
    }

    public void click(View v) {
        view.clear();
    }

    public void save(View v) {
        view.save();
    }


}
