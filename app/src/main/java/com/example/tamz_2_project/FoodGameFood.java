package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.example.tamz_2_project.FoodGameView.health;

public class FoodGameFood {
    int x, y, width, height;
    Bitmap food;
    public static int foodSpeed = 20;
    Random rand = new Random();
    boolean eaten = false;

    public FoodGameFood(Resources res, int screenX) {
        int tmp = this.rand.nextInt(4);
        if(tmp == 0) {
            this.food = BitmapFactory.decodeResource(res, R.drawable.watermelon);
        } else if (tmp == 1) {
            this.food = BitmapFactory.decodeResource(res, R.drawable.blueberry);
        } else if (tmp == 2) {
            this.food = BitmapFactory.decodeResource(res, R.drawable.banana);
        } else {
            this.food = BitmapFactory.decodeResource(res, R.drawable.strawberry);
        }

        this.width = this.food.getWidth();
        this.height = this.food.getHeight();

        this.width = this.width / 6;
        this.height = this.height / 4;

        this.food = Bitmap.createScaledBitmap(this.food, this.width, this.height, false);

        this.y = 0;
        this.x = this.rand.nextInt(screenX - this.width);
    }

    public boolean update(int screenY, PandaFoodPlayer panda) {
        this.y += foodSpeed;

        if(this.y + this.height >= screenY) {
            health--;
            return true;
        }

        if (Rect.intersects(this.getRect(), panda.getRect())) {
            this.eaten = true;
            return false;
        }

        return false;
    }

    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }
}
