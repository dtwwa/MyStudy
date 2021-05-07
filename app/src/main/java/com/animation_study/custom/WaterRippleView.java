package com.animation_study.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by css on 2017/10/24.
 */

public class WaterRippleView extends View {

    private int width;
    private int height;
    private Point startPoint;
    private Paint mPaint;
    private Path path;
    private Paint circlePaint;
    private int widthCT;
    private boolean isras = false;

    public WaterRippleView(Context context) {
        this(context, null);
    }

    public WaterRippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        path = new Path();
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getViewSize(800, widthMeasureSpec);
        height = getViewSize(400, heightMeasureSpec);
        //初始化的时候将起点移至屏幕外一个周期
        widthCT = width / 2;
        startPoint = new Point(-cycle * 4, height / 2 + widthCT);
    }

    /*sin曲线 1/4个周期的宽度*/
    private int cycle = 200;
    /*sin曲线振幅的高度*/
    private int waveHeight = 110;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //裁剪画布为圆形
        Path circlePath = new Path();
        circlePath.addCircle(width / 2, height / 2, width / 2, Path.Direction.CW);
        canvas.clipPath(circlePath);
        canvas.drawPaint(circlePaint);
        canvas.drawCircle(width / 2, height / 2, width / 2, circlePaint);

        path.moveTo(startPoint.x, startPoint.y);
        int j = 1;
        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                path.quadTo(startPoint.x + (cycle * j), startPoint.y + waveHeight,
                        startPoint.x + (cycle * 2) * i, startPoint.y);
            } else {
                path.quadTo(startPoint.x + (cycle * j), startPoint.y - waveHeight,
                        startPoint.x + (cycle * 2) * i, startPoint.y);//曲线点
            }
            j += 2;
        }

        path.lineTo(width, height);
        path.lineTo(startPoint.x, height);
        path.lineTo(startPoint.x, startPoint.y);
        path.close();
        canvas.drawPath(path, mPaint);

//判断是不是平移完了一个周期
        if (startPoint.x + 40 >= 0) {
            //满了一个周期则恢复默认起点继续平移
            startPoint.x = -cycle * 4;
        }
//每次波形的平移量 40
        startPoint.x += 40;

        if (startPoint.y <= height / 2 - widthCT) {
//            startPoint.y = height / 2 + widthCT;
            isras = true;
        }
        if (startPoint.y >= height / 2 + widthCT) {
//            startPoint.y = height / 2 + widthCT;
            isras = false;
        }


        if (!isras) {
            startPoint.y -= 10;
        } else {
            startPoint.y += 10;
        }

//        Log.d("sss", rand + "");
//        waveHeight = rand;
        postInvalidateDelayed(100);//延时刷新
        path.reset();
    }

    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY: //如果是固定的大小，那就不要去改变它
                viewSize = size;
                break;
        }

        return viewSize;
    }
}
