package com.google.android.exoplayer2.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;


public class VideoView extends SurfaceView {
    private Activity activity;
    private SurfaceCallback surfaceCallback;

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(mCallback);
        getHolder().setFormat(PixelFormat.RGBA_8888);
    }

    @SuppressWarnings("deprecation")
    public void initialize(Activity activity, SurfaceCallback surfaceCallback) {
        this.activity = activity;
        this.surfaceCallback = surfaceCallback;
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void setSurfaceLayout(int videoWidth, int videoHeight) {
//        ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        int windowWidth = Screen.SCREEN_WIDTH_PX;
//        int windowHeight = Screen.SCREEN_HEIGHT_PX;
//        float windowRatio = windowWidth / (float) windowHeight;
//        layoutParams.width = windowWidth;
//        layoutParams.height = windowHeight;
//        setLayoutParams(layoutParams);
//        getHolder().setFixedSize(videoWidth, videoHeight);
    }

    public void setVideoLayout(int videoWidth, int videoHeight) {
        setSurfaceLayout(videoWidth, videoHeight);
    }

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            holder.setKeepScreenOn(true);
            if (surfaceCallback != null)
                surfaceCallback.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (surfaceCallback != null)
                surfaceCallback.onSurfaceCreated(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (surfaceCallback != null)
                surfaceCallback.onSurfaceDestroyed(holder);
        }
    };


    public interface SurfaceCallback {
        public void onSurfaceCreated(SurfaceHolder holder);

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height);

        public void onSurfaceDestroyed(SurfaceHolder holder);
    }
}
