package com.example.tamz_2_project.FlightGame;

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

public class FlightGameActivity extends AppCompatActivity {
    Intent myIntent;
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
                myIntent = new Intent(this, FlightGameActivity.class);
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