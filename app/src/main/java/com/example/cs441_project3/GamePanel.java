package com.example.cs441_project3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private boolean gameOver = false;
    private long gameOverTime;

    public GamePanel(Context context){

        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        user = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));

        userPoint = new Point(150,150);
        user.update(userPoint);

        fruitManager = new FruitManager(200, 200, 325, Color.argb(0,255,255,255));

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

    public void resetGame(){

        userPoint = new Point(150,150);
        user.update(userPoint);

        fruitManager = new FruitManager(200, 200, 325, Color.argb(0,255,255,255));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(gameOver && System.currentTimeMillis() - gameOverTime >= 3000){

                    resetGame();
                    gameOver = false;

                }

                break;

            case MotionEvent.ACTION_MOVE:

                userPoint.set((int)event.getX(), (int)event.getY());

        }

        return true;
        //return super.onTouchEvent(event);

    }

    public void update(){

        gameOverTime = System.currentTimeMillis();

        if(!gameOver) {

            user.update(userPoint);

            boolean x = fruitManager.update();

            if(x){

                gameOver = true;

            }

            boolean y = fruitManager.collisionDetection(user);

            if(y){

                gameOver = true;

            }

        }else{

            gameOver = false;

        }

    }

    @Override
    public void draw(Canvas canvas){

        super.draw(canvas);

        canvas.drawColor(Color.GRAY);

        user.draw(canvas);

        fruitManager.draw(canvas);

        if(gameOver){

            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(150);
            canvas.drawText("Game Over - Tap to Restart", 750, 800, p);

        }

    }

}
