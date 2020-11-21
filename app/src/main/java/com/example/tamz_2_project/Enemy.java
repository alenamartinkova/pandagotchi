package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.example.tamz_2_project.MainActivity.screenRatioX;
import static com.example.tamz_2_project.MainActivity.screenRatioY;

public class Enemy {
    int x = 0, y, width, height, enemyCounter = 1, speed = 20;
    Bitmap enemyOne, enemyTwo;
    boolean wasShot = true;

    Enemy(Resources res) {
        this.enemyOne = BitmapFactory.decodeResource(res, R.drawable.enemy1);
        this.enemyTwo = BitmapFactory.decodeResource(res, R.drawable.enemy2);

        this.width = this.enemyOne.getWidth();
        this.height = this.enemyOne.getHeight();

        this.width = (int) ((this.width / 10) * screenRatioX);
        this.height = (int) ((this.height / 10) * screenRatioY);

        this.enemyOne = Bitmap.createScaledBitmap(this.enemyOne, this.width, this.height, false);
        this.enemyTwo = Bitmap.createScaledBitmap(this.enemyTwo, this.width, this.height, false);

        this.y = -this.height;
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

            this.speed++;
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
