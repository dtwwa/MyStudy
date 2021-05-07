package com.animation_study.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.animation_study.utils.BarUtils;
import com.animation_study.utils.GeometryUtil;

/**
 * Created by css on 2017/10/24.
 */

public class GooView extends View {
    private Paint paint;

    public GooView(Context context) {
        super(context);
        init();
    }

    public GooView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        paint.setAntiAlias(true);//
        paint.setColor(Color.RED);
    }

    private float dragRadius = 30f;//拖拽圆的半径
    private float stickyRadius = 30f;//拖拽圆的半径
    private PointF dragCenter = new PointF(300, 300);//拖拽圆的圆心
    private PointF stickyCenter = new PointF(300, 300);//静态圆的圆心

    private PointF[] dragyPoint = {new PointF(300, 270), new PointF(300, 330)};
    private PointF[] stickyPoint = {new PointF(300, 270), new PointF(300, 330)};

    private PointF controlPoint = new PointF(375, 300);

    private double lineK;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //让画布往上移动

        canvas.translate(0, -BarUtils.getStatusBarHeight(getContext()));

        stickyRadius = getStickyRadius();

        float xOffset = dragCenter.x - stickyCenter.x;
        float yOffset = dragCenter.y - stickyCenter.y;
        if (xOffset != 0) {
            lineK = yOffset / xOffset;
        }
        dragyPoint = GeometryUtil.getIntersectionPoints(dragCenter, dragRadius, lineK);
        stickyPoint = GeometryUtil.getIntersectionPoints(stickyCenter, stickyRadius, lineK);

        //动态计算控制点
        controlPoint = GeometryUtil.getPointByPercent(dragCenter, stickyCenter, 0.618f);

        canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, paint);
        canvas.drawCircle(stickyCenter.x, stickyCenter.y, stickyRadius, paint);

        //绘制贝塞尔曲线 绘制链接部分
        if (!isDragOutoOfRange) {
            Path path = new Path();
            path.moveTo(stickyPoint[0].x, stickyPoint[0].y);//设置起点
            path.quadTo(controlPoint.x, controlPoint.y, dragyPoint[0].x, dragyPoint[0].y);//使用贝塞尔曲线链接
            path.lineTo(dragyPoint[1].x, dragyPoint[1].y);
            path.quadTo(controlPoint.x, controlPoint.y, stickyPoint[1].x, stickyPoint[1].y);//使用贝塞尔曲线链接
            path.lineTo(stickyPoint[0].x, stickyPoint[0].y);
            path.close();//默认会闭合， 所以不用调
            canvas.drawPath(path, paint);
        }

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(stickyCenter.x, stickyCenter.y, maxDistance, paint);
    }

    private float maxDistance = 225;

    private float getStickyRadius() {
        float centerDistance = GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter);
        float fraction = centerDistance / maxDistance;
        float radius = GeometryUtil.evaluateValue(fraction, dragRadius, 4);
        return radius;


    }

    private boolean isDragOutoOfRange = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        int activon = event.getAction();
        switch (activon) {
            case MotionEvent.ACTION_DOWN:
                dragCenter.set(x, y);
                if (GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter) > maxDistance) {
                    isDragOutoOfRange = true;
                } else {
                    isDragOutoOfRange = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                dragCenter.set(x, y);
                if (GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter) > maxDistance) {
                    isDragOutoOfRange = true;
                } else {
                    isDragOutoOfRange = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}
