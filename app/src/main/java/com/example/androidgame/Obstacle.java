package com.example.androidgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject{

    private Rect rectangle;
    private Rect rectangle2;
    private int  color;

    public Rect getRectangle(){
        return rectangle;
    }

    public Obstacle(int rectHeight, int col, int startX, int startY, int playerGap){
        this.color = col;
        rectangle = new Rect(0, startY, startX, startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap, startY, Constants.SCREEN_WIDTH, startY+rectHeight);
    }

    public void addY(float Y){
        rectangle.top += Y;
        rectangle.bottom += Y;
        rectangle2.top += Y;
        rectangle2.bottom += Y;
    }

    public boolean playerCollide(Player p){
        return Rect.intersects(rectangle, p.getRectangle()) || Rect.intersects(rectangle2, p.getRectangle());
    }


    @Override
    public void draw(Canvas c) {
        Paint p = new Paint();
        p.setColor(color);
        c.drawRect(rectangle, p);
        c.drawRect(rectangle2, p);
    }

    @Override
    public void update() {

    }
}
