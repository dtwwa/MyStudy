package com.animation_study.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * Created by css on 2017/10/13.
 */

public abstract class BaseActiytiy extends Activity {
    private SlidrConfig mSlidrConfig;
    private SlidrConfig.Builder mBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer LayoutId = getLayout();
        if (LayoutId != null) {
            setContentView(getLayout());
        }
        mBuilder = new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(1.5f)//滑动速度
                .distanceThreshold(.10f)//滑动距离
                .edge(false)
                .edgeSize(0.18f)//距离左边界占屏幕大小的18%
                .sensitivity(1f);// 响应的敏感度,0-1f,默认值是1f
        mSlidrConfig = mBuilder.build();
        Slidr.attach(this, mSlidrConfig);//右滑返回上一级

        findView();
        initData();//在初始化控件, 先初始化数据，
    }

    public abstract Integer getLayout();

    public void findView() {
        //先调findview 后调initdata;
    }

    public void initData() {
    }


}
