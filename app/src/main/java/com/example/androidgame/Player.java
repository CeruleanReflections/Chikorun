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
    private Animation idle;
    private Animation walk_left;
    private Animation walk_right;
    private Animation up;
    private AnimationManager anim;

    public Player(Rect r, int c){
        this.rectangle = r;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.idle);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft1);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft2);
        Bitmap walk3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft3);
        Bitmap walk4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walkleft4);
        Bitmap up1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.up1);
        Bitmap up2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.up2);
        Bitmap up3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.up3);
        Bitmap up4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.up4);

        idle = new Animation(new Bitmap[]{idleImg}, 2);

        //LEFT
        walk_left = new Animation(new Bitmap[]{walk1, walk2, walk3, walk4}, 0.25f);

        //RIGHT
        Matrix m = new Matrix();
        m.preScale(-1,1);
        Bitmap walk_r1 = Bitmap.createBitmap(walk1,0,0,walk1.getWidth(),walk1.getHeight(),m,false);
        Bitmap walk_r2 = Bitmap.createBitmap(walk2,0,0,walk2.getWidth(),walk2.getHeight(),m,false);
        Bitmap walk_r3 = Bitmap.createBitmap(walk3,0,0,walk3.getWidth(),walk3.getHeight(),m,false);
        Bitmap walk_r4 = Bitmap.createBitmap(walk4,0,0,walk4.getWidth(),walk4.getHeight(),m,false);

        walk_right = new Animation(new Bitmap[]{walk_r1, walk_r2, walk_r3,walk_r4}, 0.25f);
        up = new Animation(new Bitmap[]{up1, up2, up3, up4},0.25f);

        anim = new AnimationManager(new Animation[]{idle, walk_right, walk_left, up});

    }

    public Rect getRectangle(){
        return rectangle;
    }

    @Override
    public void draw(Canvas c) {
        anim.draw(c, rectangle);
    }

    @Override
    public void update() {
        anim.update();
    }

    public void update(Point p){
        float oldLeft = rectangle.left;
        rectangle.set(p.x - rectangle.width()/2, p.y - rectangle.height()/2, p.x + rectangle.width()/2, p.y + rectangle.height()/2);

        int state = 0;

        if (rectangle.left - oldLeft > 0)
            state = 1;
        else if(rectangle.left - oldLeft < -10)
            state = 2;
        else if (rectangle.left == oldLeft)
            state = 3;

        anim.playAnimation(state);
        anim.update();
    }

}
