package com.example.tamz_2_project.FlightGame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.tamz_2_project.FlightGame.Flight;
import com.example.tamz_2_project.R;

import java.util.Random;

public class Enemy {
    int x, y, width, height, enemyCounter = 1, speed = 15;
    Bitmap enemyOne, enemyTwo;
    boolean wasShot = false;
    Random rand = new Random();

    Enemy(Resources res, int screenY, int screenX) {
        this.enemyOne = BitmapFactory.decodeResource(res, R.drawable.enemy1);
        this.enemyTwo = BitmapFactory.decodeResource(res, R.drawable.enemy2);

        this.width = this.enemyOne.getWidth();
        this.height = this.enemyOne.getHeight();

        this.width = this.width / 14;
        this.height = this.height / 13;

        this.enemyOne = Bitmap.createScaledBitmap(this.enemyOne, this.width, this.height, false);
        this.enemyTwo = Bitmap.createScaledBitmap(this.enemyTwo, this.width, this.height, false);

        this.x = screenX - 100;
        this.y = this.rand.nextInt(screenY - 200);
    }

    Bitmap getEnemy() {
        if(this.enemyCounter == 1) {
            this.enemyCounter++;
            return this.enemyOne;
        } else {
            this.enemyCounter = 1;
            return this.enemyTwo;
        }
    }

    public boolean update(int screenX, int screenY, Random random, Flight flight) {
        this.x -= this.speed;

        if(this.x + this.width < 0) {
            if(!this.wasShot) {
                return true;
            }

            this.x = screenX;
            this.y = random.nextInt(screenY - this.height);
            this.wasShot = false;
        }

        if(Rect.intersects(this.getRect(), flight.getRect())) {
            return true;
        }

        return false;
    }

    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }
}
