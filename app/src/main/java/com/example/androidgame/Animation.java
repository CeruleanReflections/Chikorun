package com.example.androidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying = false;
    public boolean isPlaying(){
        return isPlaying;
    }

    public void play(){
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop(){
        isPlaying = false;
    }

    private float frameTime;
    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime){
        this.frames = frames;
        frameIndex = 0;

        frameTime = animTime/frames.length;
        lastFrame = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, Rect position){
        if(!isPlaying)
            return;

        scaleRect(position);

        canvas.drawBitmap(frames[frameIndex], null, position, new Paint());
    }

    private void scaleRect(Rect rectangle){
        float whRatio = (float) frames[frameIndex].getWidth()/(float)frames[frameIndex].getHeight();
        if (rectangle.width() > rectangle.height())
            rectangle.left = rectangle.right - (int)(rectangle.height() * whRatio);
        else
            rectangle.top = rectangle.bottom - (int)(rectangle.width() * (1/whRatio));
    }

    public void update(){
        if (!isPlaying)
            return;

        if (System.currentTimeMillis() - lastFrame > frameTime*1000){
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }

    }
}
