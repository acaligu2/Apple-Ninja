package com.example.cs441_project3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Fruit implements GameObject{

    private Rect rectangle;
    private int color;
    private int scoreVal;

    private int fruitType; //Type of the fruit (1 - 7)

    public Fruit(int rectHeight, int color, int startX, int startY, int playerSize, int type){

        rectangle = new Rect(startX, startY, startX + playerSize, startY + rectHeight);

        this.color = color;

        this.fruitType = type;

        switch(type){

            //White Logo
            case 1:
                scoreVal = 1;
                break;
            //Gray Logo
            case 2:
                scoreVal = 2;
                break;
            //Black Logo
            case 3:
                scoreVal = 3;
                break;
            //Blue Logo
            case 4:
                scoreVal = 4;
                break;
            //Teal & Purple Logo
            case 5:
                scoreVal = 5;
                break;
            //Android Logo
            case 6:
                scoreVal = -1;
                break;
            //Rainbow Logo
            case 7:
                scoreVal = 10;
                break;
        }

    }

    public int getScoreVal(){
        return scoreVal;
    }

    public int getType(){

        return fruitType;

    }

    public Rect getRectangle(){

        return rectangle;

    }

    //Represents falling fruit
    public void addYVal(float y){

        rectangle.top += y;
        rectangle.bottom += y;

    }

    //Returns true if the player swipe has crossed the fruit
    public boolean collisionDetection(Player player){

        return Rect.intersects(rectangle, player.getRectangle());

    }

    @Override
    public void draw(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);

    }

    @Override
    public void update(){ }

}
