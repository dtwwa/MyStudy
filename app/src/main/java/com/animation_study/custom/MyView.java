package com.animation_study.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyView extends View {


    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Bitmap mBitmap;
    private Canvas mCanvas;

    private Paint mPaint;
    private Path mPath;
    private int mLastX;
    private int mLastY;
    private int widht;
    private int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widht = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        mBitmap = Bitmap.createBitmap(widht, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画路径到位图
        drawPath();
        //在画板上画位图
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void drawPath() {
        mCanvas.drawPath(mPath, mPaint);
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); //结合处为圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 设置转弯处为圆角
        mPaint.setStrokeWidth(20);   // 设置画笔宽度
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();//触摸动作
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                mPath.lineTo(x - 1, y - 1);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if (dx > 3 || dy > 3) {
                    mPath.lineTo(x, y);
                }
                mLastX = x;
                mLastY = y;
                break;
        }
        invalidate();


        return true;
    }

    public void clear() {
        if (!mBitmap.isRecycled()) {
            mBitmap.recycle();
        }

        mBitmap = Bitmap.createBitmap(widht, height, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        mPath = new Path();
        invalidate();
    }

    public void save() {
        FileOutputStream out = null;
        String path = Environment.getExternalStorageDirectory().getPath() + "/css绘图/";
        File file1 = new File(path);
        if (!file1.exists()) {              //如果不存在，那就建立这个文件夹
            file1.mkdirs();
        }

        File file = new File(path, System.currentTimeMillis() + ".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名

        try {
            out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            Toast.makeText(getContext(), "保存成功 放在根目录/css绘图下", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("路径", path);
    }

}