package com.example.androidgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

public class GameplayScene implements Scene{

    private Rect r = new Rect();
    private Player player;
    private Point player_position;
    private ObstacleManager obstacleManager;

    private boolean moving_player = false;
    private boolean gameover = false;
    private long gameOverTime;

    public GameplayScene(){
        player = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));
        player_position = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(player_position);
        obstacleManager = new ObstacleManager(200, 350, 75,Color.BLACK);
    }

    @Override
    public void update() {
        if (!gameover) {
            player.update(player_position);
            obstacleManager.update();

            if (obstacleManager.playerCollide(player)){
                gameover = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }


    @Override
    public void draw(Canvas c) {
        c.drawColor(Color.WHITE);
        player.draw(c);
        obstacleManager.draw(c);

        if (gameover){
            Paint paint = new Paint();
            paint.setTextSize(120);
            paint.setColor(Color.RED);
            drawCenterText(c, paint, "Game Over! Premi per riprovare");
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    public void reset(){
        player_position = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(player_position);
        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
        moving_player = false;
    }

    @Override
    public void input(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!gameover && player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    moving_player = true;
                if (gameover && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameover = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (moving_player && !gameover)
                    player_position.set((int)event.getX(), (int)event.getY());
                break;

            case MotionEvent.ACTION_UP:
                moving_player = false;
                break;
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}