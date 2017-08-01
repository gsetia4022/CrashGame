package com.android.me.gauravsetia.crashgame;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    private  GameView gameView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Getting Display object
        Display display = getWindowManager().getDefaultDisplay();

        // getting screen resolution into point object
        Point point = new Point();
        display.getSize(point);

        // initilize game view
        gameView = new GameView(this, point.x, point.y);

        // adding it to content view
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
