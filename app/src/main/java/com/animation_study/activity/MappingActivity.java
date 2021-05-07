package com.animation_study.activity;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.widget.RelativeLayout;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;

/**
 * Created by css on 2017/10/13.
 */

public class MappingActivity extends BaseActiytiy {

    @Override
    public Integer getLayout() {
        return R.layout.activity_mapping;
    }

    @Override
    public void findView() {
        View view = new View(getApplicationContext()) {
            private Paint mPaint;

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                mPaint = new Paint();
                mPaint.setAntiAlias(true);          //抗锯齿
                mPaint.setColor(Color.RED);//画笔颜色
                mPaint.setStyle(Paint.Style.FILL);  //画笔风格
                mPaint.setTextSize(36);             //绘制文字大小，单位px
                mPaint.setStrokeWidth(5);           //画笔粗细

                canvas.drawColor(Color.BLUE);   //设置画布背景颜色
                canvas.drawCircle(100, 100, 100, mPaint);//画实心圆  前两个为圆心坐标，，3位半径
                canvas.drawRect(0, 200, 200, 300, mPaint);//画矩形  float left, float top, float right, float bottom, @NonNull Paint paint
                canvas.drawRoundRect(new RectF(250, 200, 450, 300), 15, 15, mPaint); //画圆角矩形

                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),
                        0, 400, mPaint);//画bitmap
                canvas.drawOval(new RectF(0, 600, 200, 900), mPaint); //画椭圆


                canvas.drawArc(new RectF(300, 0, 500, 200), 0, 90, true, mPaint);  //绘制弧形区域   true

                canvas.drawArc(new RectF(500, 0, 700, 200), 0, 90, false, mPaint);  //绘制弧形区域  false

                ////!!!!!!!!!!!!画多边形，   连路径!!!!!!!!!!!!//////////////
                Path path = new Path();
                int y = 900;
                path.moveTo(10, 10 + y); //移动到 坐标10,10
                path.lineTo(100, 50 + y);
                path.lineTo(200, 40 + y);
                path.lineTo(300, 20 + y);
                path.lineTo(200, 10 + y);
                path.lineTo(100, 70 + y);
                path.lineTo(50, 40 + y);
                path.close();
                canvas.drawPath(path, mPaint);
                y += 100;
                canvas.drawText("好好学习， 天天向上~", 50, 50 + y, mPaint);    //绘制文字
                y += 100;
                ////!!!!!!!!!!!!画文字，   连路径    沿着路径画文字!!!!!!!!!!!!//////////////
                Path path2 = new Path();
                path2.moveTo(50, 50 + y);
                path2.lineTo(100, 100 + y);
                path2.lineTo(200, 200 + y);
                path2.lineTo(300, 300 + y);
                path2.close();
                canvas.drawTextOnPath("好好学习， 天天向上~~", path2, 50, 50, mPaint);    //绘制文字

                myDraw(canvas);
            }

            private void myDraw(Canvas canvas) {

                mPaint.setStyle(Paint.Style.STROKE);//设置画笔空心
                canvas.translate(canvas.getWidth() / 2 + 100, 400); //将位置移动画纸的坐标点:150,150
                canvas.drawCircle(0, 0, 100, mPaint); //画圆圈

                //使用path绘制路径文字
                canvas.save();
                canvas.translate(-75, -75);
                Path path = new Path();
                path.addArc(new RectF(0, 0, 150, 150), -180, 180);
                Paint citePaint = new Paint(mPaint);
                citePaint.setTextSize(14);
                citePaint.setStrokeWidth(1);

                canvas.drawTextOnPath("绘制表盘~", path, 28, 0, citePaint);
                canvas.restore();

                Paint tmpPaint = new Paint(mPaint); //小刻度画笔对象
                tmpPaint.setStrokeWidth(1);

                float y = 100;
                int count = 60; //总刻度数

                for (int i = 0; i < count; i++) {
                    if (i % 5 == 0) {
                        canvas.drawLine(0f, y, 0, y + 12f, mPaint);
                        canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 25f, tmpPaint);

                    } else {
                        canvas.drawLine(0f, y, 0f, y + 5f, tmpPaint);
                    }
                    canvas.rotate(360 / count, 0f, 0f); //旋转画纸
                }

                //绘制指针
                tmpPaint.setColor(Color.GRAY);
                tmpPaint.setStrokeWidth(4);
                canvas.drawCircle(0, 0, 7, tmpPaint);
                tmpPaint.setStyle(Paint.Style.FILL);
                tmpPaint.setColor(Color.YELLOW);
                canvas.drawCircle(0, 0, 5, tmpPaint);
                canvas.drawLine(0, 10, 0, -65, mPaint);

            }
        };
        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(1000, 2000);
        view.setBackgroundColor(Color.YELLOW);
        addContentView(view, ll);


    }
}
