package com.example.tamz_2_project.FlightGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class FlightGameActivity extends AppCompatActivity {

    private FlightGameView flightGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.flightGameView = new FlightGameView(this, display.widthPixels, display.heightPixels);
        setContentView(this.flightGameView);
    }

   @Override
    protected void onPause() {
        super.onPause();
        try {
            this.flightGameView.pause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.flightGameView.resume();
    }
}