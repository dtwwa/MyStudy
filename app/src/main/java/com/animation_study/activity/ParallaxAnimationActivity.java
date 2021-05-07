package com.animation_study.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.animation_study.R;
import com.animation_study.custom.ClockLoadingView;
import com.animation_study.custom.GooView;
import com.animation_study.custom.RotateSpread;
import com.animation_study.custom.ScanView;
import com.animation_study.custom.WaterRippleView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by css on 2017/10/23.
 */

public class ParallaxAnimationActivity extends AppCompatActivity {

    @ViewInject(R.id.ll_root)
    LinearLayout layoutView;
    //    @ViewInject(R.id.rotate_spread)
//    private RotateSpread mRotateSpread;
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;
    @ViewInject(R.id.imageview)
    private ImageView mImageview;
    List<View> views;
    ClockLoadingView mClockLoadingView;
    RotateSpread mRotateSpread;
    private WaterRippleView waterRippleView;
    private GooView mGooView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        findView();
    }

    public Integer getLayout() {
        return R.layout.activity_parallax_animation;
    }

    public void findView() {
        views = new ArrayList<>();
        x.view().inject(this);

        mRotateSpread = new RotateSpread(getApplicationContext());
        mRotateSpread.setAnimatiorEndListener(new RotateSpread.AnimatiorEndListener() {
            @Override
            public void end() {
                mViewPager.setVisibility(View.GONE);
                mImageview.setVisibility(View.VISIBLE);
                mImageview.setScaleX(0);
                mImageview.setScaleY(0);
                mImageview.invalidate();
                mImageview.post(new Runnable() {//扩散动画
                    @Override
                    public void run() {
                        // 圆形动画的x坐标  位于View的中心
                        int cx = (mImageview.getLeft() + mImageview.getRight()) / 2;

                        //圆形动画的y坐标  位于View的中心
                        int cy = (mImageview.getTop() + mImageview.getBottom()) / 2;

                        //起始大小半径
                        float startX = 0f;

                        //结束大小半径 大小为图片对角线的一半
                        float startY = (float) Math.sqrt(cx * cx + cy * cy);
                        Animator animator = ViewAnimationUtils.createCircularReveal(mImageview, cx, cy, startX, startY);

                        //在动画开始的地方速率改变比较慢,然后开始加速
                        animator.setInterpolator(new AccelerateInterpolator());
                        animator.setDuration(1000);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                mImageview.setScaleX(1);
                                mImageview.setScaleY(1);
                            }
                        });
                        animator.start();

                    }
                });
                mImageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setVisibility(View.VISIBLE);
                        mImageview.setVisibility(View.GONE);
                    }
                });

            }
        });
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRotateSpread.setLayoutParams(ll);

        mClockLoadingView = new ClockLoadingView(getApplicationContext());
        mClockLoadingView.setLayoutParams(ll);

        waterRippleView = new WaterRippleView(getApplicationContext());
        waterRippleView.setLayoutParams(ll);

        mGooView = new GooView(getApplicationContext());
        mGooView.setLayoutParams(ll);

        ScanView scanView = new ScanView(getApplicationContext());
        scanView.setLayoutParams(ll);

        views.add(mRotateSpread);
        views.add(mClockLoadingView);
        views.add(waterRippleView);
        views.add(mGooView);
        views.add(scanView);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(View view, int position, Object object)                       //销毁Item
            {
                ((ViewPager) view).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(View view, int position)                                //实例化Item
            {
                ((ViewPager) view).addView(views.get(position), 0);


                return views.get(position);
            }

        });

//        Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(600);
//        animation.setRepeatCount(-1);
//        mRotateSpread.startAnimation(animation);


    }


}
