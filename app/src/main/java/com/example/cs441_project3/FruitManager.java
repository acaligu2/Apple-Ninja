package com.example.cs441_project3;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Random;

public class FruitManager {

    //Used to assign images to the fruit rectangles
    private static BitmapFactory bf = new BitmapFactory();

    private static Bitmap FRUIT_IMAGE1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.apple1);
    private static Bitmap FRUIT_IMAGE2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.apple2);
    private static Bitmap FRUIT_IMAGE3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.apple3);
    private static Bitmap FRUIT_IMAGE4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.apple4);
    private static Bitmap FRUIT_IMAGE5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.apple5);
    private static Bitmap FRUIT_IMAGE6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.apple6);

    private static Bitmap BOMB_IMAGE = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.android1);

    //Instantiate sound clips
    private static MediaPlayer BOMB_NOISE = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.bomb);
    private static MediaPlayer MISSED = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.missed);
    private static MediaPlayer FRUIT1 = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.slice3);
    private static MediaPlayer FRUIT2 = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.slice4);

    private ArrayList<Fruit> fruits;  //Array of fruits currently on screen

    private int fruitLocation;        //Location of falling fruit
    private int playerSize;           //Size of the user's swipe space
    private int fruitHeight;          //Height of the fruit rectangle
    private int color;

    private int score = 0;            //Game score

    private int misses = 0;           //Number of fruit not sliced

    private Random rand = new Random();

    private long start;
    private long initialization;

    public FruitManager(int playerSize, int fruitLocation, int fruitHeight, int color){

        fruits = new ArrayList<>();

        this.fruitLocation = fruitLocation;
        this.playerSize = playerSize;
        this.fruitHeight = fruitHeight;
        this.color = color;

        start = initialization = System.currentTimeMillis();

        //Add fruit to the array
        populateFruits();

    }

    public boolean collisionDetection(Player player){

        //Check each fruit on screen for detection
        for(Fruit f : fruits){

            if(f.collisionDetection(player)){

                //Game over, hit a bomb
                if(f.getType() == 6){

                    BOMB_NOISE.start();
                    return true;

                }

                //Determine a noise to play for slicing the fruit
                int val = rand.nextInt(2 - 1) + 1;

                if(val == 1){ FRUIT1.start(); }
                else{ FRUIT2.start(); }

                //Increment score
                score += f.getScoreVal();

                //Remove from the arraylist
                fruits.remove(f);

            }

        }

        return false;

    }

    //Assigns next falling fruit to a random type
    //Based on percentage rarity
    private int determineFruitType(){

        int val = rand.nextInt(100 - 1) + 1;
        int type = -1;

        if(1 <= val && val <= 30){
            type = 1;
        }else if(30 < val && val <= 60) {
            type = 2;
        }else if(60 < val && val <= 75) {
            type = 3;
        }else if(75 < val && val <= 85) {
            type = 4;
        }else if(85 < val && val <= 90) {
            type = 5;
        }else if(90 < val && val <= 99) {
            type = 6;
        }else if(val == 100) {
            type = 7;
        }

        return type;
    }

    private void populateFruits(){

        //Starting Y position for the fruit
        int currentY = -5 * Constants.SCREEN_HEIGHT / 4;

        while(currentY < 0){

            //Determine where horizontally to place the fruit
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerSize));

            //Determines which type of fruit is spawned
            int type = determineFruitType();

            //Add to the array list
            fruits.add(new Fruit(fruitHeight, color, xStart, currentY, playerSize, type));
            currentY += fruitHeight + fruitLocation;

        }

    }

    public boolean update(){

        int timeElapsed = (int)(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        //Determine fall speed of the fruit
        //Value gets larger as the game progresses
        float speed = (float)(Math.sqrt(1 + (start - initialization)/ 1000.0)) * Constants.SCREEN_HEIGHT / 10000.0f;

        //Add y value to each fruit on the screen
        for(Fruit fruit : fruits){

            fruit.addYVal(speed * timeElapsed);

        }

        //Fruit has made it to the bottom of the screen, add a strike to the count
        if(fruits.get(fruits.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){


            //Fruit isn't a bomb, mark a penalty
            if(fruits.get(fruits.size()-1).getType() != 6) {

                MISSED.start();
                misses++;
            }

            //Game Over
            if(misses == 3){ return true; }

            //Remove from the array list
            fruits.remove(fruits.get(fruits.size()-1));

        }

        //Add a new fruit to be spawned
        int type = determineFruitType();

        int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerSize));

        fruits.add(0, new Fruit(fruitHeight, color, xStart,
                fruits.get(0).getRectangle().top - fruitHeight - fruitLocation,
                playerSize, type));

        return false;

    }

    public void draw(Canvas canvas){

        Bitmap photo = null;

        for(Fruit fruit : fruits){

            fruit.draw(canvas);

            //Assigns correct image to fruit based on type
            switch(fruit.getType()){

                case 1:
                    photo = FRUIT_IMAGE1;
                    break;
                case 2:
                    photo = FRUIT_IMAGE2;
                    break;
                case 3:
                    photo = FRUIT_IMAGE3;
                    break;
                case 4:
                    photo = FRUIT_IMAGE4;
                    break;
                case 5:
                    photo = FRUIT_IMAGE5;
                    break;
                case 6:
                    photo = BOMB_IMAGE;
                    break;
                case 7:
                    photo = FRUIT_IMAGE6;
                    break;
            }

            canvas.drawBitmap(photo, null, fruit.getRectangle(), new Paint());

        }

        //Represents score
        Paint p = new Paint();
        p.setTextSize(100);
        p.setColor(Color.BLACK);
        canvas.drawText("Score: " + score, 50, 50 + p.descent() - p.ascent(), p);

        //Number of misses
        if(misses == 1) {
            Paint p1 = new Paint();
            p1.setTextSize(100);
            p1.setFakeBoldText(true);
            p1.setColor(Color.RED);
            canvas.drawText("X", 50, 200 + p1.descent() - p1.ascent(), p1);
        }else if(misses == 2){
            Paint p1 = new Paint();
            p1.setTextSize(100);
            p1.setFakeBoldText(true);
            p1.setColor(Color.RED);
            canvas.drawText("X X", 50, 200 + p1.descent() - p1.ascent(), p1);
        }else if(misses == 3){
            Paint p1 = new Paint();
            p1.setTextSize(100);
            p1.setFakeBoldText(true);
            p1.setColor(Color.RED);
            canvas.drawText("X X X", 50, 200 + p1.descent() - p1.ascent(), p1);
        }

    }
}
