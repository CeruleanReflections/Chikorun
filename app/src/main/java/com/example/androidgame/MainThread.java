package com.example.androidgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static final int maxFPS = 30;
    private double avgFPS;
    private SurfaceHolder sh;
    private GamePanel gp;
    private boolean running;
    public static Canvas c;

    public MainThread(SurfaceHolder s, GamePanel g){
        super();
        this.sh = s;
        this.gp = g;
    }

    public void setRunning (boolean r){
        this.running = r;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / maxFPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / maxFPS;

        while (running) {
            startTime = System.nanoTime();
            c = null;

            try {
                c = this.sh.lockCanvas();
                synchronized (sh) {
                    this.gp.update();
                    this.gp.draw(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (c != null) {
                    try {
                        sh.unlockCanvasAndPost(c);
                    } catch (Exception e) {e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 10000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0)
                    this.sleep(waitTime);
            } catch (Exception e) {e.printStackTrace();}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == maxFPS) {
                avgFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
}