package com.animation_study.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.animation_study.MyStaticMediaplay;
import com.animation_study.R;
import com.animation_study.custom.MyTextureView;
import com.animation_study.utils.Constant;

import java.util.ArrayList;

/**
 * Created by css on 2017/10/16.
 */

public class RecycleTextureActivity extends Activity {
    private RecyclerView recyclerView;
    private ArrayList<String> listVideoPaths;
    public static LinearLayoutManager layoutManager;
    private int firstView = 0;
    private int lasttView = 0;
    private static int positionView = 0;
    static RecycleTextureActivity recycleTextureActivity;

    public static RecycleTextureActivity getRecycleTextureActivity() {
        return recycleTextureActivity;

    }


    public static LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public static int getPositionView() {

        return positionView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy_texture);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        initData();
        recycleTextureActivity = this;

        //设置布局管理器， 长时间不用容易忘
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyAdapter());

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstView = layoutManager.findFirstVisibleItemPosition();
                lasttView = layoutManager.findLastVisibleItemPosition();
                if (positionView < firstView || positionView > lasttView) {//判断item不可见状态
                    MyStaticMediaplay.getInstance().getMediaPlayer().pause();
                }
            }
        });
    }

    private void initData() {
        listVideoPaths = new ArrayList<>();
        String path = Constant.strVideoUrl;
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
        listVideoPaths.add(path);
        listVideoPaths.add(path);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getApplicationContext()).inflate(R.layout.item_recy_texture, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyStaticMediaplay.getInstance().getMediaPlayer().isPlaying()) {
                        MyStaticMediaplay.getInstance().getMediaPlayer().stop();
                    }
                    MyStaticMediaplay.getInstance().getMediaPlayer().reset();
                    holder.bar.setVisibility(View.VISIBLE);
                    holder.textureView.setmMediaPlayer(MyStaticMediaplay.getInstance().getMediaPlayer());
                    holder.textureView.setPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            holder.bar.setVisibility(View.GONE);
                            holder.textureView.startPlay();
                            positionView = position;
                        }
                    });
                    holder.textureView.setUrl(listVideoPaths.get(position));
                }
            });


        }

        @Override
        public int getItemCount() {
            return listVideoPaths.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            MyTextureView textureView;
            Button button;
            ProgressBar bar;
            View view;

            public MyViewHolder(View itemView) {
                super(itemView);
                textureView = (MyTextureView) itemView.findViewById(R.id.my_textureview);
                button = (Button) itemView.findViewById(R.id.bt_recycle_paly);
                bar = (ProgressBar) itemView.findViewById(R.id.pb_waiting);
                view = itemView;
            }
        }

    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (MyStaticMediaplay.getInstance().getMediaPlayer() != null) {
            MyStaticMediaplay.getInstance().getMediaPlayer().stop();
            MyStaticMediaplay.getInstance().getMediaPlayer().release();
            MyStaticMediaplay.getInstance().setMediaPlayer(null);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (MyStaticMediaplay.getInstance().getMediaPlayer() != null) {
            MyStaticMediaplay.getInstance().getMediaPlayer().start();
//            mMediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (MyStaticMediaplay.getInstance().getMediaPlayer() != null) {
            MyStaticMediaplay.getInstance().getMediaPlayer().pause();
//            mMediaPlayer = null;
        }
    }
}
