package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.example.tamz_2_project.FlightGameView.screenRatioX;
import static com.example.tamz_2_project.FlightGameView.screenRatioY;

public class Enemy {
    int x = 0, y, width, height, enemyCounter = 1, speed = 20;
    Bitmap enemyOne, enemyTwo, enemyThree, enemyFour;
    boolean wasShot = false;

    Enemy(Resources res) {
        this.enemyOne = BitmapFactory.decodeResource(res, R.drawable.enemy1);
        this.enemyTwo = BitmapFactory.decodeResource(res, R.drawable.enemy2);
        this.enemyThree = BitmapFactory.decodeResource(res, R.drawable.enemy3);
        this.enemyFour = BitmapFactory.decodeResource(res, R.drawable.enemy4);

        this.width = this.enemyOne.getWidth();
        this.height = this.enemyOne.getHeight();

        this.width = (int) ((this.width / 6) * screenRatioX);
        this.height = (int) ((this.height / 6) * screenRatioY);

        this.enemyOne = Bitmap.createScaledBitmap(this.enemyOne, this.width, this.height, false);
        this.enemyTwo = Bitmap.createScaledBitmap(this.enemyTwo, this.width, this.height, false);
        this.enemyThree = Bitmap.createScaledBitmap(this.enemyThree, this.width, this.height, false);
        this.enemyFour = Bitmap.createScaledBitmap(this.enemyFour, this.width, this.height, false);

        this.y = -this.height;
    }

    Bitmap getEnemy() {
        if(this.enemyCounter == 1) {
            this.enemyCounter++;
            return this.enemyOne;
        } else if(this.enemyCounter == 2) {
            this.enemyCounter++;
            return this.enemyTwo;
        } else if(this.enemyCounter == 3) {
            this.enemyCounter++;
            return this.enemyThree;
        } else {
            this.enemyCounter = 1;
            return this.enemyFour;
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
