package com.animation_study.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.animation_study.R;
import com.animation_study.custom.EventStudyViewGroup;

public class EventStudyActivity extends AppCompatActivity {

    private EventStudyViewGroup viewGroup;
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_study);
        viewGroup = (EventStudyViewGroup) findViewById(R.id.parent);
        cb = (CheckBox) findViewById(R.id.cb);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewGroup.setIntercept(b);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(getApplicationContext(), "activity得到事件", Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }
}
