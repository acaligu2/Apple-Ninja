package com.example.cs441_project3;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

//Sets running FPS and manages mainThread operations

public class MainThread extends Thread {

    public static final int MAXFPS = 30;

    private double avgFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean running){
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }

    @Override
    public void run(){

        long startTime;
        long time = 1000/MAXFPS;

        long waitTime;
        int frameCount = 0;

        long totalTime = 0;
        long targetTime = 1000/MAXFPS;

        while(running) {

            startTime = System.nanoTime();
            canvas = null;

            try {

                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {

                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {

                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

            }

            time = System.nanoTime() - startTime / 1000000;
            waitTime = targetTime - time;
            try{

                if(waitTime > 0){

                    this.sleep(waitTime);

                }

            }catch(Exception e){ e.printStackTrace(); }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == MAXFPS){

                avgFPS = 1000/((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(avgFPS);

            }

        }
    }

}
