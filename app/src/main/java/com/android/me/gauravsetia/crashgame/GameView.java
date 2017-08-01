package com.android.me.gauravsetia.crashgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by gaurav on 28/7/17.
 */

public class GameView extends SurfaceView implements Runnable {

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    private Thread gameThread = null;

    //Adding player to this code
    private Player player;

    // Objects to use drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //Adding enemies object array
    private Enemy[] enemies;

    private Boom boom;

    //Adding 3 enemies you may increase the size
    private int enemyCount = 3;
    //Adding an stars list
    private ArrayList<Star> stars = new
            ArrayList<Star>();

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //initialize player object
        player = new Player(context, screenX, screenY);

        // initilize drawing object
        surfaceHolder = getHolder();
        paint = new Paint();
        //adding 100 stars you may increase the number
        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }
        //initializing enemy object array
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }
        //initializing boom object
        boom = new Boom(context);
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    public void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        player.update();
        //setting boom outside the screen
        boom.setX(-250);
        boom.setY(-250);

        //Updating the stars with player speed
        for (Star s : stars) {
            s.update(player.getSpeed());
        }
        //updating the enemy coordinate with respect to player speed
        for (int i = 0; i < enemyCount; i++) {
            enemies[i].update(player.getSpeed());
            //if collision occurrs with player
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {
                //displaying boom at that location
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());
                //moving enemy outside the left edge
                enemies[i].setX(-200);
            }
        }
    }

    private void draw() {
        // checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the surface
            canvas = surfaceHolder.lockCanvas();
            // draw the background color for canvas
            canvas.drawColor(Color.BLACK);
            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);
            //drawing all stars
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }
            // drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint
            );
            //drawing the enemies
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }
            //drawing boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );
            // unlock the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            // stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {

        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                // when user presses on the screen
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                // when user press down from object
                player.setBoosting();
                break;
        }
        return true;
    }
}
