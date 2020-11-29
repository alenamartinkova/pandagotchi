package com.example.tamz_2_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.tamz_2_project.FlightGame.FlightGameActivity;
import com.example.tamz_2_project.FoodGame.FoodGameActivity;
import com.example.tamz_2_project.PandaBan.PandaBanActivity;

public class GamesList extends AppCompatActivity {
    Intent myIntent;
    Boolean storeHealth = false;
    Boolean storeHappiness = false;
    Boolean storeLove = false;
    Boolean storeFun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        myIntent = getIntent();
        storeHealth = myIntent.getBooleanExtra("storeHealth", false);
        storeHappiness = myIntent.getBooleanExtra("storeHappiness", false);
        storeFun = myIntent.getBooleanExtra("storeFun", false);
        storeLove = myIntent.getBooleanExtra("storeLove", false);

        SharedPreferences sharedPref = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(storeHealth) {
            editor.putFloat("health", MainActivity.healthStorage);
            editor.apply();
        }

        if(storeHappiness) {
            editor.putFloat("happiness", MainActivity.happinessStorage);
            editor.apply();
        }

        if(storeLove) {
            editor.putFloat("love", MainActivity.loveStorage);
            editor.apply();
        }

        if(storeFun) {
            editor.putFloat("fun", MainActivity.funStorage);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.panda:
                this.myIntent = new Intent(this, MainActivity.class);
                startActivity(this.myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void chooseGame(View view) {
        switch(view.getId()) {
            case R.id.button:
                this.myIntent = new Intent(this, PandaBanActivity.class);
                this.myIntent.putExtra("level", '1');
                startActivity(this.myIntent);
                break;
            case R.id.button2:
                this.myIntent = new Intent(this, FlightGameActivity.class);
                startActivity(this.myIntent);
                break;
            case R.id.button3:
                this.myIntent = new Intent(this, FoodGameActivity.class);
                startActivity(this.myIntent);
                break;
        }
    }
}