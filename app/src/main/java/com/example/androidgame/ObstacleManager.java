package com.example.androidgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ObstacleManager {

    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private ArrayList<Obstacle> obstacles;
    private long init_time;
    private int score;

    public ObstacleManager(int pG, int oG, int oH, int color){
        this.playerGap = pG;
        this.obstacleGap = oG;
        this.obstacleHeight = oH;
        this.color = color;
        this.score = 0;

        startTime = init_time = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        populateObstacle();
    }

    private void populateObstacle() {

        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while (currY < 0){
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public boolean playerCollide(Player p){
        for (Obstacle ob : obstacles){
            if (ob.playerCollide(p))
                return true;
        }
        return false;
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) (Math.sqrt(1 + (startTime - init_time)/2500)) * Constants.SCREEN_HEIGHT/10000.0f;
        for (Obstacle ob : obstacles){
            ob.addY(speed*elapsedTime);
        }
        if (obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart,obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size()-1);
            score++;
        }
    }

    public void draw(Canvas c){
        for (Obstacle ob : obstacles)
            ob.draw(c);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        c.drawText("" + score, Constants.SCREEN_WIDTH/5,Constants.SCREEN_HEIGHT/10, paint);
    }


}
