package com.animation_study.custom;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.animation_study.R;
import com.animation_study.utils.ViewUtil;

/**
 * Created by css on 2017/10/23.
 */

public class ProgressView extends View {

    private Point point;
    private int height;
    private int width;
    private int process = 0;
    private int solidWidth = 42;
    private int solidHeight = 20;
    private int w = 0;
    //滑块整体向左移动17dp, 不然会出去， 不知道为什么
    private int yidongLeft = 13;

    public ProgressView(Context context) {
        this(context, null);
    }


    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        point = ViewUtil.getScreenSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
//        width = ViewUtil.px2dip(width);
//        solidWidth = ViewUtil.px2dip(solidWidth);
        w = (width - solidWidth) / 100;
//        solidWidth = ViewUtil.dip2px(solidWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.gray));
        paint.setAntiAlias(true);
        //倒数第二， 三个是四个角的弧度
        canvas.drawRect(0, height / 2 - (ViewUtil.dip2px(4) / 2), point.x, height / 2 + (ViewUtil.dip2px(4) / 2), paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(process * w - ViewUtil.dip2px(yidongLeft), height / 2 - (ViewUtil.dip2px(solidHeight) / 2), process * w + ViewUtil.dip2px(solidWidth - yidongLeft), height / 2 + (ViewUtil.dip2px(solidHeight) / 2), paint);
        paint.setColor(getResources().getColor(R.color.borrow));
        paint.setTextSize(getResources().getDimension(R.dimen.textsize));
        canvas.drawText(process + "%", process * w + ViewUtil.dip2px(2 - yidongLeft), ViewUtil.dip2px(solidHeight + 1), paint);

    }

    public void setProcess(int process) {
        this.process = process;
    }
}
