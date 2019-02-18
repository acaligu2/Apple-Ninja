package com.example.cs441_project3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.transition.Explode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FruitManager {

    private ArrayList<Fruit> fruits;

    private int fruitLocation;
    private int playerSize;
    private int fruitHeight;
    private int color;

    private int score = 0;

    private int misses = 0;

    private Random rand = new Random();

    private BitmapFactory bf = new BitmapFactory();
    private Bitmap fruitImage2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.apple3);


    private long start;
    private long initialization;

    public FruitManager(int playerSize, int fruitLocation, int fruitHeight, int color){

        fruits = new ArrayList<>();

        this.fruitLocation = fruitLocation;
        this.playerSize = playerSize;
        this.fruitHeight = fruitHeight;
        this.color = color;

        start = initialization = System.currentTimeMillis();

        populateFruits();

    }

    public boolean collisionDetection(Player player){

        for(Fruit f : fruits){

            if(f.collisionDetection(player)){

                score++;

                fruits.remove(f);

                return true;

            }

        }

        return false;

    }

    private void populateFruits(){

        int currentY = -5 * Constants.SCREEN_HEIGHT / 4;

        while(currentY < 0){

            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerSize));

            fruits.add(new Fruit(fruitHeight, color, xStart, currentY, playerSize));
            currentY += fruitHeight + fruitLocation;

        }

    }

    public boolean update(){

        int timeElapsed = (int)(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        float speed = (float)(Math.sqrt(1 + (start - initialization)/ 1000.0)) * Constants.SCREEN_HEIGHT / 10000.0f;

        for(Fruit fruit : fruits){

            fruit.addYVal(speed * timeElapsed);

        }

        if(fruits.get(fruits.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){

            misses++;

            System.out.println(misses);

            //Game Over
            if(misses == 3){

                return true;

            }

            fruits.remove(fruits.get(fruits.size()-1));

        }
        int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerSize));
        fruits.add(0, new Fruit(fruitHeight, color, xStart,
                fruits.get(0).getRectangle().top - fruitHeight - fruitLocation,
                playerSize));

        return false;

    }

    public void draw(Canvas canvas){

        for(Fruit fruit : fruits){

            fruit.draw(canvas);
            canvas.drawBitmap(fruitImage2, null, fruit.getRectangle(), new Paint());

        }

        Paint p = new Paint();
        p.setTextSize(100);
        p.setColor(Color.BLUE);
        canvas.drawText("Score: " + score, 50, 50 + p.descent() - p.ascent(), p);

    }
}
