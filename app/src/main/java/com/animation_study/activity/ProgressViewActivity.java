package com.animation_study.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.animation_study.R;
import com.animation_study.custom.ProgressView;
import com.animation_study.utils.BarUtils;
import com.animation_study.utils.ViewUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by css on 2017/10/23.
 */

public class ProgressViewActivity extends Activity {
    @ViewInject(R.id.progressview)
    private ProgressView progressView;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        x.view().inject(this);
//        progressView.setBackgroundColor(Color.WHITE);
//        progressView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dip2px(5)));

        new Thread() {
            @Override
            public void run() {
                super.run();

                while (i <= 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressView.setProcess(i);
                            progressView.invalidate();
                        }
                    });
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    i++;

                }

            }
        }.start();

    }
}
