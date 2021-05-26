package com.animation_study.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.animation_study.R;

/**
 * Created by css on 2017/11/29.
 */

public class ParticleMotionView extends View {

    Bitmap bitmap;
    Rect rect1;
    Rect rect2;
    Paint paint;

    public ParticleMotionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.have);
        rect1 = new Rect(100, 100, 100, 100);
        paint = new Paint();

    }

    public ParticleMotionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParticleMotionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, rect1,rect1, paint);

    }
}
