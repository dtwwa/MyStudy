package com.animation_study.activity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.animation_study.activity.BaseActiytiy;

import java.io.IOException;

/**
 * 打开相机
 * Created by css on 2017/10/15.
 */

public class TextureViewActivity extends BaseActiytiy implements TextureView.SurfaceTextureListener {
    private TextureView myTexture;
    private Camera mCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTexture = new TextureView(this);
        myTexture.setSurfaceTextureListener(this);
        setContentView(myTexture);
    }

    @Override
    public Integer getLayout() {
        return null;
    }

    /**
     * surfaceTexture可用时调用
     * Available 中文 可用
     *
     * @param surface
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
        Camera.Size previeSize = mCamera.getParameters().getPreviewSize();
        myTexture.setLayoutParams(new FrameLayout.LayoutParams(
                previeSize.width, previeSize.height, Gravity.CENTER
        ));//设置布局
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();//开始预览
        myTexture.setRotation(100.0f);//TextureView可以旋转透明，补间动画
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();//停止预览
        mCamera.release();//释放资源
        mCamera = null;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
