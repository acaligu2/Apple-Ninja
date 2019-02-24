package com.example.cs441_project3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Player user;
    private Point userPoint;
    private FruitManager fruitManager;

    private boolean gameOver = false;

    public GamePanel(Context context){

        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        //Instantiate player
        user = new Player(new Rect(100,100,200,200), Color.argb(0, 0, 0,0));

        //Instantiate location of the player
        userPoint = new Point(150,150);
        user.update(userPoint);

        //Instantiate the fruit-managing class
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

    //Creates a new fruitManager and resets the location of the user
    public void resetGame(){

        userPoint = new Point(150,150);
        user.update(userPoint);

        fruitManager = new FruitManager(200, 200, 325, Color.argb(0,255,255,255));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){

            //User has tapped
            case MotionEvent.ACTION_DOWN:

                //Reset the game if ended
                if(gameOver){

                    resetGame();
                    gameOver = false;

                }

                break;

            //User has moved their finger, update the location of the rect
            case MotionEvent.ACTION_MOVE:

                userPoint.set((int)event.getX(), (int)event.getY());

        }

        return true;

    }

    public void update(){

        //Game is continuing
        if(!gameOver) {

            //Move the user to the new point
            user.update(userPoint);

            //Update the fruitManager
            boolean x = fruitManager.update();

            //Check if three fruits have been missed
            if (x) {

                gameOver = true;

            }

            boolean y = fruitManager.collisionDetection(user);

            //Check if bomb has been hit
            if (y) {

                gameOver = true;

            }
        }

    }

    @Override
    public void draw(Canvas canvas){

        super.draw(canvas);

        canvas.drawColor(Color.rgb(232, 200,179));

        user.draw(canvas);

        fruitManager.draw(canvas);

        //Set gameover screen
        if(gameOver){

            canvas.drawColor(Color.BLACK);

            Paint p = new Paint();
            p.setColor(Color.GREEN);
            p.setTextSize(150);
            canvas.drawText("Game Over - Tap to Restart", 450, 800, p);

        }

    }

}
