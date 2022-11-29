package com.example.androidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    private SceneManager manager;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new MainThread(getHolder(), this);

        manager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder h, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder sh){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while(true){
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch(Exception e) {e.printStackTrace();}
            retry = false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        manager.input(event);
        return true;
        //return super.onTouchEvent(m);
    }

    public void update(){
        manager.update();
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        manager.draw(c);
    }

}

