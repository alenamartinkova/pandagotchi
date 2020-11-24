package com.example.tamz_2_project.FoodGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.tamz_2_project.GamesList;
import com.example.tamz_2_project.PandaBan.PandaBanActivity;
import com.example.tamz_2_project.R;

public class FoodGameActivity extends AppCompatActivity {
    Intent myIntent;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu_in_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                myIntent = new Intent(this, FoodGameActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.back:
                myIntent = new Intent(this, GamesList.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}