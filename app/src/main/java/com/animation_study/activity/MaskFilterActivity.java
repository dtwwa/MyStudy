package com.animation_study.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;

import com.animation_study.R;
import com.animation_study.activity.BaseActiytiy;

/**
 * Created by css on 2017/10/14.
 */

public class MaskFilterActivity extends BaseActiytiy {
    LinearLayout ll;

    @Override
    public Integer getLayout() {
        return R.layout.activity_mask_filter;
    }

    @Override
    public void findView() {
        ll = (LinearLayout) findViewById(R.id.ll_root);
        View view = new View(getApplicationContext()) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                BlurMaskFilter bmf = null;
                Paint paint = new Paint();
                paint.setAntiAlias(true);//抗锯齿
                paint.setColor(Color.RED);//画笔颜色
                paint.setStyle(Paint.Style.FILL);//画布风格，，填充
                paint.setTextSize(68);
                paint.setStrokeWidth(5);//画笔粗细
                /*
                    BlurMaskFilter(10f,BlurMaskFilter.Blur.NORMAL);
                    我们可以控制的就是这两个参数：
                    第一个参数：指定模糊边缘的半径；
                    第二个参数：指定模糊的风格，可选值有：
                    BlurMaskFilter.Blur.NORMAL：内外模糊
                    BlurMaskFilter.Blur.OUTER：外部模糊
                    BlurMaskFilter.Blur.INNER：内部模糊
                    BlurMaskFilter.Blur.SOLID：内部加粗，外部模糊
                 */
                bmf = new BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL);
                paint.setMaskFilter(bmf);
                canvas.drawText("好好学习， 天天向上~", 100, 100, paint);
                bmf = new BlurMaskFilter(10f, BlurMaskFilter.Blur.OUTER);
                paint.setMaskFilter(bmf);
                canvas.drawText("好好学习， 天天向上~", 100, 200, paint);
                bmf = new BlurMaskFilter(10f, BlurMaskFilter.Blur.INNER);
                paint.setMaskFilter(bmf);
                canvas.drawText("好好学习， 天天向上~", 100, 300, paint);
                bmf = new BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID);
                paint.setMaskFilter(bmf);
                canvas.drawText("好好学习， 天天向上~", 100, 400, paint);

                setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速
            }
        };
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500);
        view.setLayoutParams(lp1);
        view.setBackgroundColor(Color.YELLOW);
        ll.addView(view);

        View view1 = new View(getApplicationContext()) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                float[] direction = new float[]{1, 1, 3};   // 设置光源的方向
                float light = 0.4f;     //设置环境光亮度
                float specular = 8;     // 定义镜面反射系数
                float blur = 3.0f;      //模糊半径

                //EmbossMaskFilter(浮雕效果)
                EmbossMaskFilter emboss = new EmbossMaskFilter(direction, light, specular, blur);

                Paint paint = new Paint();
                paint.setAntiAlias(true);          //抗锯齿
                paint.setColor(Color.BLUE);//画笔颜色
                paint.setStyle(Paint.Style.FILL);  //画笔风格
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setTextSize(120);             //绘制文字大小，单位px
                paint.setStrokeWidth(8);           //画笔粗细
                paint.setMaskFilter(emboss);

                paint.setMaskFilter(emboss);
                canvas.drawText("好好学习， 天天向上~", 50, 100, paint);


                setLayerType(View.LAYER_TYPE_SOFTWARE, null);     //关闭硬件加速
            }
        };
        view1.setLayoutParams(lp1);
        view1.setBackgroundColor(Color.GRAY);
        ll.addView(view1);

        View view2 = new View(getApplicationContext()) {
            /**
             * drawXxx方法族：以一定的坐标值在当前画图区域画图，另外图层会叠加，
             即后面绘画的图层会覆盖前面绘画的图层。
             clipXXX方法族：在当前的画图区域裁剪(clip)出一个新的画图区域，这个
             画图区域就是canvas对象的当前画图区域了。比如：clipRect(new Rect())，
             那么该矩形区域就是canvas的当前画图区域
             getXxx方法族：获得与Canvas相关一些值，比如宽高，屏幕密度等。
             save()，restore()，saveLayer()，restoreToCount()等保存恢复图层的方法
             translate(平移)，scale(缩放)，rotate(旋转)，skew(倾斜)
             *
             * @param canvas
             */
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                Rect rect = new Rect(50, 0, 150, 50);
                canvas.translate(200, 200);
                for (int i = 0; i < 36; i++) {
                    canvas.rotate(10);//旋转画布
                    canvas.drawRect(rect, paint);
                }
            }
        };
        view2.setLayoutParams(lp1);
        view2.setBackgroundColor(Color.GRAY);
        ll.addView(view2);

        View view3 = new View(getApplicationContext()) {

            private Bitmap mBitmap;
            private Rect mRect = new Rect();
            private PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG);
            private Paint mPaint = new Paint();
            private Path mPath = new Path();

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
                mPaint.setAntiAlias(true);// 抗锯尺
                if (mBitmap == null) {
                    return;
                }
                mRect.set(0, 0, getWidth(), getHeight());
                canvas.save();
                canvas.setDrawFilter(pdf);
                mPath.addCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredHeight() / 2, Path.Direction.CCW);
                canvas.clipPath(mPath, Region.Op.REPLACE);
                canvas.drawBitmap(mBitmap, null, mRect, mPaint);
                canvas.restore();
            }
        };
        view3.setLayoutParams(lp1);
        view3.setBackgroundColor(Color.GRAY);
        ll.addView(view3);


    }
}
