package com.animation_study.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animation_study.MyVideoPlayerManager;
import com.animation_study.R;
import com.animation_study.custom.MyVideoPlayer;
import com.animation_study.manager.SJCVideoManager;
import com.animation_study.utils.Constant;

import java.util.ArrayList;

/**
 * Created by css on 2017/10/17.
 */

public class StudyJCVideoActivity extends Activity {
    private ArrayList<String> listVideoPaths;
    public RecyclerView sjc_recycle;
    LinearLayoutManager layoutManager;
    private boolean isPlay = false;
    public static StudyJCVideoActivity studyJCVideoActivity;
    private int firstView = 0;
    private int lasttView = 0;
    private static int currentPosition = 0;
    private boolean isScrollEnabled = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jc_video);
        sjc_recycle = (RecyclerView) findViewById(R.id.sjc_recycle);
        initData();
        studyJCVideoActivity = this;
        //设置布局管理器， 长时间不用容易忘
        layoutManager = new LinearLayoutManager(getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return isScrollEnabled;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sjc_recycle.setLayoutManager(layoutManager);
        sjc_recycle.setAdapter(new MyAdapter());
        sjc_recycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstView = layoutManager.findFirstVisibleItemPosition();
                lasttView = layoutManager.findLastVisibleItemPosition();
                currentPosition = MyVideoPlayerManager.instance().getCurrentTexture().getCurrentPosition();
                if (currentPosition < firstView || currentPosition > lasttView) {//判断item不可见状态
                    SJCVideoManager.instance().getMediaPlayer().pause();
                }
            }
        });
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    private void initData() {
        listVideoPaths = new ArrayList<>();
        String path = Constant.strVideoUrl;
        String path2 = Constant.strVideoUrl;

        listVideoPaths.add(path);
        listVideoPaths.add(path2);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
        listVideoPaths.add(path);
    }

    class MyAdapter extends RecyclerView.Adapter<StudyJCVideoActivity.MyAdapter.MyViewHolder> {


        @Override
        public StudyJCVideoActivity.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            StudyJCVideoActivity.MyAdapter.MyViewHolder holder = new StudyJCVideoActivity.MyAdapter.MyViewHolder(LayoutInflater.from(
                    getApplicationContext()).inflate(R.layout.item_studyjc_video, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(StudyJCVideoActivity.MyAdapter.MyViewHolder holder, int position) {
            holder.myVideoPlayer.setUp(listVideoPaths.get(position), position);
        }

        @Override
        public int getItemCount() {
            return listVideoPaths.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            MyVideoPlayer myVideoPlayer;

            public MyViewHolder(View itemView) {
                super(itemView);
                myVideoPlayer = (MyVideoPlayer) itemView.findViewById(R.id.my_video_player);

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (MyVideoPlayerManager.instance().getCurrentTexture() != null) {
            if (MyVideoPlayer.FLOOR == MyVideoPlayer.SECOND) {
                MyVideoPlayerManager.instance().getCurrentTexture().returnVerticalScreen();
                Log.d("退出横屏", "退出横屏");
                return;
            } else {
                Log.d("退出程序", "退出程序");
                SJCVideoManager.instance().getMediaPlayer().stop();
                SJCVideoManager.instance().getMediaPlayer().release();
                MyVideoPlayerManager.instance().getCurrentTexture().VIDEOSTATE = MyVideoPlayerManager.instance().getCurrentTexture().VIDEOPAUSE;
                SJCVideoManager.instance().setMediaPlayer(null);
                finish();
            }
        } else {
            finish();
        }
    }

}
