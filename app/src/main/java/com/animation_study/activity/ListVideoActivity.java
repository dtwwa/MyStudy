package com.animation_study.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.animation_study.R;
import com.animation_study.utils.Constant;
import com.animation_study.video.SuperVideoView;

import java.util.ArrayList;

/**
 * 列表播放视频 ， 问题太多
 * <p>
 * Created by css on 2017/10/15.
 */

public class ListVideoActivity extends Activity {
    ArrayList<String> listVideoPaths;
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video1);
        initData();
        findView();
    }

    public void findView() {
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ListVideoAdapter());
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

    class ListVideoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listVideoPaths.size();
        }

        @Override
        public String getItem(int position) {
            return listVideoPaths.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder holder;
            if (convertView == null) {//listView的复用
                convertView = new View(getApplicationContext());

            }
            convertView = View.inflate(getApplicationContext(), R.layout.item_list_video, null);//打气筒加载布局
            holder = new MyHolder(convertView);
            holder.tv_title.setText(getItem(position));
            holder.lv_video.setVideoPath(getItem(position));
            return convertView;
        }

        private class MyHolder {
            TextView tv_title;
            SuperVideoView lv_video;

            MyHolder(View v) {
                tv_title = (TextView) v.findViewById(R.id.tv_title1);
                lv_video = (SuperVideoView) v.findViewById(R.id.lv_video1);
                lv_video.register(ListVideoActivity.this);//设置宿主
            }
        }

    }

}
