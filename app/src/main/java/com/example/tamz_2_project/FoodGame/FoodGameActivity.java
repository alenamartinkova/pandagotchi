package com.example.tamz_2_project.FoodGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class FoodGameActivity extends AppCompatActivity {

    private FoodGameView foodGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.foodGameView = new FoodGameView(this, display.widthPixels, display.heightPixels);
        setContentView(this.foodGameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            this.foodGameView.pause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.foodGameView.resume();
    }
}