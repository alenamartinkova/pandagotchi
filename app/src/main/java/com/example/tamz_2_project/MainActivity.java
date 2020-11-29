package com.example.tamz_2_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GameView gameView;
    Intent myIntent;

    public static float funStorage;
    public static float healthStorage;
    public static float loveStorage;
    public static float happinessStorage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPref = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        funStorage = sharedPref.getFloat("fun", 100);
        healthStorage = sharedPref.getFloat("health", 100);
        loveStorage = sharedPref.getFloat("love", 100);
        happinessStorage = sharedPref.getFloat("happiness", 100);

        this.gameView = new GameView(this, display.widthPixels, display.heightPixels);
        setContentView(this.gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            this.gameView.pause();
            storeSharedPref();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        funStorage = sharedPref.getFloat("fun", 100);
        healthStorage = sharedPref.getFloat("health", 100);
        loveStorage = sharedPref.getFloat("love", 100);
        happinessStorage = sharedPref.getFloat("happiness", 100);

        this.gameView.resume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.games:
                myIntent = new Intent(this, GamesList.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void storeSharedPref() {
        Log.d("health", "" + healthStorage);
        SharedPreferences sharedPref = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("health", healthStorage);
        editor.putFloat("fun", funStorage);
        editor.putFloat("love", loveStorage);
        editor.putFloat("happiness", happinessStorage);
        editor.apply();
    }
}