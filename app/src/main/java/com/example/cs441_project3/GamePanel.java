package com.example.cs441_project3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Player user;
    private Point userPoint;
    private FruitManager fruitManager;

    public GamePanel(Context context){

        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        user = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));

        userPoint = new Point(150,150);

        fruitManager = new FruitManager(200, 200, 200, Color.BLACK);

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){



    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        boolean retry = true;

        while(retry){

            try{

                thread.setRunning(false);
                thread.join();

            }catch(Exception e){

                e.printStackTrace();

            }

            retry = false;

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                userPoint.set((int)event.getX(), (int)event.getY());

        }

        return true;
        //return super.onTouchEvent(event);

    }

    public void update(){

        user.update(userPoint);

        fruitManager.update();

    }

    @Override
    public void draw(Canvas canvas){

        super.draw(canvas);

        canvas.drawColor(Color.GRAY);

        user.draw(canvas);

        fruitManager.draw(canvas);

    }

}
