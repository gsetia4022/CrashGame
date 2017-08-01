package com.android.me.gauravsetia.crashgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by gaurav on 28/7/17.
 */

public class Player {

    //Bitmap to get character from image
    private Bitmap bitmap;

    // Cordinates
    private  int x;
    private  int y;

    //mention speed of character
    private int speed = 0;

    // variable to track if the ship is boosting or not
    private boolean boosting;

    // Gravity value to give gravity effects to ship
    private final int GRAVITY = -10;

    // controlling y cordinates so that ship won't go outside the screen
    private int maxY;
    private int minY;

    //limit the bounds of ship speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private Rect detectCollision;

    // constructor
    public Player(Context context, int screenX, int screenY){
        x=75;
        y=50;
        speed =1;
        //getting bitmap from resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        //calculating maxY
        maxY = screenY - bitmap.getHeight();

        //top edge's y point is 0 so min y will always be zero
        minY = 0;
        //setting boosting value to false initially
        boosting = false;
        //initializing rect object
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    // Setting boosting true
    public void setBoosting() {
        boosting = true;
    }

    // Setting boosting false
    public void stopBoosting() {
        boosting = false;
    }

    // Method to update cordinate of character
    public void update(){
        //updating x cordinates
        //if the ship is boosting
        if (boosting) {
            //speeding up the ship
            speed += 2;
        } else {
            //slowing down if not boosting
            speed -= 5;
        }
        //controlling the top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        //if the speed is less than min speed
        //controlling it so that it won't stop completely
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }
        //moving the ship down
        y -= speed + GRAVITY;

        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }
        //adding top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
