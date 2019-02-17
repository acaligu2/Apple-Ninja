package com.example.cs441_project3;

import android.graphics.Canvas;

import java.util.ArrayList;

public class FruitManager {

    private ArrayList<Fruit> fruits;

    private int fruitLocation;
    private int playerSize;
    private int fruitHeight;
    private int color;

    private long start;

    public FruitManager(int playerSize, int fruitLocation, int fruitHeight, int color){

        fruits = new ArrayList<>();
        this.fruitLocation = fruitLocation;
        this.playerSize = playerSize;
        this.fruitHeight = fruitHeight;
        this.color = color;

        start = System.currentTimeMillis();

        populateFruits();

    }

    private void populateFruits(){

        int currentY = -5 * Constants.SCREEN_HEIGHT / 4;

        while(currentY < 0){

            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerSize));

            fruits.add(new Fruit(fruitHeight, color, xStart, currentY, playerSize));
            currentY += fruitHeight + fruitLocation;

        }

    }

    public void update(){

        int timeElapsed = (int)(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        float speed = Constants.SCREEN_HEIGHT / 10000.0f;

        for(Fruit fruit : fruits){

            fruit.addYVal(speed * timeElapsed);

        }

        if(fruits.get(fruits.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerSize));
            fruits.add(0, new Fruit(fruitHeight, color, xStart,
                    fruits.get(0).getRectangle().top - fruitHeight - fruitLocation,
                    playerSize));

            fruits.remove(fruits.size()-1);
        }

    }

    public void draw(Canvas canvas){

        for(Fruit fruit : fruits){

            fruit.draw(canvas);

        }

    }
}
