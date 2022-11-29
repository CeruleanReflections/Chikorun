package com.example.androidgame;
import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {

    public void update();
    public void draw(Canvas c);
    public void terminate();
    public void input(MotionEvent event);

}
