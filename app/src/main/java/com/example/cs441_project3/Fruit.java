package com.example.cs441_project3;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Fruit implements GameObject{

    private Rect rectangle;
    private int color;

    public Fruit(int rectHeight, int color, int startX, int startY, int playerSize){

        rectangle = new Rect(startX, startY, startX + playerSize, startY + rectHeight);

        this.color = color;

    }

    public Rect getRectangle(){

        return rectangle;

    }

    public void addYVal(float y){

        rectangle.top += y;
        rectangle.bottom += y;

    }

    public boolean collisionDetection(Player player){

        Rect cDetect = player.getRectangle();

        if(rectangle.contains(cDetect.left, cDetect.top) ||
            rectangle.contains(cDetect.right, cDetect.top) ||
            rectangle.contains(cDetect.left, cDetect.bottom) ||
            rectangle.contains(cDetect.right, cDetect.bottom)){ return true; }


        return false;
    }

    @Override
    public void draw(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);

    }

    @Override
    public void update(){



    }

}
