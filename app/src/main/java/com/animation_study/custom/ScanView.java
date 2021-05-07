package com.animation_study.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by css on 2017/11/15.
 */

public class ScanView extends View {
    Paint paint;
    int ran = 10;
    Paint textPaint;

    public ScanView(Context context) {
        super(context);
        init();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new SweepGradient(100f, 100f, new int[]{0xffff0000, 0xffffffff}, null));
    }

    public ScanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("扫描中", 10, 300, textPaint);
        if (ran <= 350) {
            ran += 10;
        } else {
            ran = 10;
        }
        canvas.rotate(-ran, 100, 100);
        canvas.drawCircle(100, 100, 100, paint);
        postInvalidateDelayed(50);
    }
}
