package com.example.tamz_2_project.PandaBan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tamz_2_project.GamesList;
import com.example.tamz_2_project.MainActivity;
import com.example.tamz_2_project.R;

import java.io.IOException;
import java.io.InputStream;

public class PandaBanActivity extends AppCompatActivity {
    PandaBanView pandaBanView;
    int newLx = 0;
    int newLy = -1;
    int heroX;
    int heroY;
    boolean wasCross = false;
    int[] newLevel;
    int counterLy = 0;
    char level = '1';
    InputStream input;
    AssetManager assetManager;
    Intent myIntent;
    Boolean storeLove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panda_ban);

        pandaBanView = findViewById(R.id.pandaBanView);
        assetManager = getAssets();

        myIntent = getIntent();
        level = myIntent.getCharExtra("level", '1');

        storeLove = myIntent.getBooleanExtra("storeLove", false);

        if(storeLove) {
            SharedPreferences sharedPref = getSharedPreferences("Stats", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putFloat("love", MainActivity.loveStorage);
            editor.apply();
        }

        try {
            input = assetManager.open("levels.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer);

            // if there is next level pick it
            if(text.indexOf(level) != -1) {
                // +2 because next is \n, so I wanna start from + 2
                text = text.substring(text.indexOf(level) + 2);
                //if no select first level
            } else {
                text = text.substring(text.indexOf('1') + 2);
                level = '1';
            }
            // -1 because i want the row before next level
            if(text.indexOf("L") != -1) {
                text = text.substring(0, text.indexOf("L"));
            }

            for (int i = 0; i < text.length(); i++) {
                if(text.charAt(i) == '\n') {
                    if(counterLy > newLy) {
                        newLy = counterLy;
                    }
                    newLx++;
                    counterLy = 0;
                } else {
                    counterLy++;
                }
            }

            counterLy = 0;

            String newText = "";
            for (int i = 0; i < text.length(); i++) {
                if(text.charAt(i) == '\n') {
                    for(int j = counterLy; j < newLy; j++) {
                        newText += ' ';
                    }
                    counterLy = 0;
                } else {
                    newText += text.charAt(i);
                    counterLy++;
                }
            }

            if(newText.indexOf('+') != -1) {
                wasCross = true;
                newText = newText.replace('+', '4');
            }


            newText = newText.replace('*', '5');
            newText = newText.replace('#', '1');
            newText = newText.replace(' ', '0');
            newText = newText.replace('.', '3');
            newText = newText.replace('$', '2');
            newText = newText.replace('@', '4');

            newLevel = new int[newLy * newLx];
            for (int i = 0; i < newLy * newLx; i++){
                if (newText.charAt(i) == '4') {
                    heroY = i / newLy;
                    heroX = i % newLy;
                }

                newLevel[i] = newText.charAt(i) - '0';
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pandaBanView.setLevel(newLevel, newLy, newLx, heroX, heroY, wasCross, level);
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
                myIntent = new Intent(this, PandaBanActivity.class);
                myIntent.putExtra("level", level);
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