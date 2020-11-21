package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.example.tamz_2_project.FoodGameView.health;

public class FoodGameBadFood {
    int x, y, width, height;
    Bitmap food;
    public static int badFoodSpeed = 30;
    Random rand = new Random();

    public FoodGameBadFood(Resources res, int screenX) {
        this.food = BitmapFactory.decodeResource(res, R.drawable.rock);

        this.width = this.food.getWidth();
        this.height = this.food.getHeight();

        this.width = this.width / 6;
        this.height = this.height / 4;

        this.food = Bitmap.createScaledBitmap(this.food, this.width, this.height, false);

        this.y = 0;
        this.x = this.rand.nextInt(screenX - this.width);
    }

    public boolean update(int screenY, PandaFoodPlayer panda) {
        this.y += badFoodSpeed;

        if(this.y + this.height >= screenY) {
            return true;
        }

        if (Rect.intersects(this.getRect(), panda.getRect())) {
            health--;
            return true;
        }

        return false;
    }

    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }
}
