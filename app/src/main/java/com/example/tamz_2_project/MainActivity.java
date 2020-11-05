package com.example.tamz_2_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.gameView = new GameView(this, display.widthPixels, display.heightPixels);
        setContentView(this.gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            this.gameView.pause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.gameView.resume();
    }

    public void showGames(View view) {
        Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_LONG).show();
    }
}