package com.animation_study.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.animation_study.R;

/**
 * Created by css on 2017/10/13.
 */

public class BitmapActivity extends BaseActiytiy {
    private ImageView img_bg;
    private Bitmap mBitmap;

    /**
     * 从资源中获取位图的方式有两种：通过BitmapDrawable或者BitmapFactory
     *
     * @return
     */
    @Override
    public Integer getLayout() {
        return R.layout.activity_bitmap;
    }

    @Override
    public void initData() {
        //BitmapDrawable方法：
        BitmapDrawable bmpMeizi = null;
        bmpMeizi = new BitmapDrawable(getResources().openRawResource(R.raw.test));

        if (bmpMeizi != null) {
            mBitmap = bmpMeizi.getBitmap();
        }

        //BitmapFactory方法：  工厂类
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        if (mBitmap != null) {
            img_bg.setImageBitmap(mBitmap);
        }

     /*4.获取Bitmap的相关信息：
        这个，只要我们获取了Bitmap对象，就可以调用相关方法来获取对应的参数了，getByteCount获得大小，
        getHeight和getWidth这些~这里就不写了，自己查文档！
        */

    }

    @Override
    public void findView() {
        img_bg = (ImageView) findViewById(R.id.img_bg);
    }

    /**
     * 抠图
     *
     * @param v
     */
    public void kou(View v) {

        Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, 100, 100);
        img_bg.setImageBitmap(bitmap);
    }

    /**
     * 缩放
     *
     * @param v
     */
    public void scale(View v) {
//两个int值为缩放后的宽高
        Bitmap bitmap = Bitmap.createScaledBitmap(mBitmap, 50, 50, true);
        img_bg.setImageBitmap(bitmap);
    }
}
