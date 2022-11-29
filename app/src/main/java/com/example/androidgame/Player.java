package com.example.androidgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameObject{

    private Rect rectangle;
    private int color;
    private Animation idle;
    private Animation walk_left;
    private Animation walk_right;
    private AnimationManager anim;

    public Player(Rect r, int c){
        this.rectangle = r;
        this.color = c;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.idle);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft1);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft2);
        Bitmap walk3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft3);
        Bitmap walk4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft4);

        idle = new Animation(new Bitmap[]{idleImg}, 2);

        //LEFT
        walk_left = new Animation(new Bitmap[]{walk1, walk2, walk3, walk4}, 0.25f);

        //RIGHT
        Matrix m = new Matrix();
        m.preScale(-1,1);
        walk1 = Bitmap.createBitmap(walk1,0,0,walk1.getWidth(),walk1.getHeight(),m,false);
        walk2 = Bitmap.createBitmap(walk1,0,0,walk2.getWidth(),walk2.getHeight(),m,false);
        walk3 = Bitmap.createBitmap(walk1,0,0,walk3.getWidth(),walk3.getHeight(),m,false);
        walk4 = Bitmap.createBitmap(walk1,0,0,walk4.getWidth(),walk4.getHeight(),m,false);

        walk_right = new Animation(new Bitmap[]{walk1, walk2, walk3,walk4}, 0.25f);
        anim = new AnimationManager(new Animation[]{idle, walk_right, walk_left});

    }

    public Rect getRectangle(){
        return rectangle;
    }

    @Override
    public void draw(Canvas c) {
        //Paint p = new Paint();
        //p.setColor(color);
        //c.drawRect(rectangle, p);
        anim.draw(c, rectangle);
    }

    @Override
    public void update() {
        anim.update();
    }

    public void update(Point p){
        float oldleft = rectangle.left;
        rectangle.set(p.x - rectangle.width()/2, p.y - rectangle.height()/2, p.x + rectangle.width()/2, p.y + rectangle.height()/2);

        int state = 0;
        if (rectangle.left - oldleft > 5)
            state = 1;
        else if(rectangle.left - oldleft < -5)
            state = 2;

        anim.playAnimation(state);
        anim.update();
    }

}
