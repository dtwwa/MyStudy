package com.animation_study.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.animation_study.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {
    private ViewPager vp_banner;
    private List<ImageView> imageViews;
    private TextView tv_current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        vp_banner = (ViewPager) findViewById(R.id.vp_banner);
        tv_current = (TextView) findViewById(R.id.tv_current);
        imageViews = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            if (i == 4) {
                imageView.setImageDrawable(getDrawable(R.drawable.have));
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.timg));
            }
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            imageViews.add(imageView);
        }
        vp_banner.setAdapter(new MyAdapter());
        vp_banner.setCurrentItem(Integer.MAX_VALUE / 2);
        vp_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_current.setText("" + position % 5);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position % 5));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position % 5));
            return imageViews.get(position % 5);
        }
    }

}
