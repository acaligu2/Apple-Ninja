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

        return Rect.intersects(rectangle, player.getRectangle());

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
