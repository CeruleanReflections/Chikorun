package com.example.androidgame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class SceneManager {

    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager(){
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    public void input(MotionEvent event){
        scenes.get(ACTIVE_SCENE).input(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    };

    public void draw(Canvas c){
        scenes.get(ACTIVE_SCENE).draw(c);
    };

}
