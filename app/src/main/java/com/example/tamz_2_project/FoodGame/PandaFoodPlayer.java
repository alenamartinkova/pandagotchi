package com.example.tamz_2_project.FoodGame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.tamz_2_project.R;

public class PandaFoodPlayer {
    int x, y, width, height;
    boolean moveRight = false, moveLeft = false;
    Bitmap pandaPlayer;

    public PandaFoodPlayer(Resources res, int screenX, int screenY) {
        this.pandaPlayer = BitmapFactory.decodeResource(res, R.drawable.pandafood);

        this.width = this.pandaPlayer.getWidth();
        this.height = this.pandaPlayer.getHeight();

        this.width = this.width / 4;
        this.height = this.height / 3;

        this.pandaPlayer = Bitmap.createScaledBitmap(this.pandaPlayer, this.width, this.height, false);

        this.y = (int) (screenY * 0.8);
        this.x = (int) (screenX * 0.37);
    }

    public void update(int screenX) {
        if(this.moveLeft) {
            this.x -= 18;
        }

        if(this.moveRight) {
            this.x += 18;
        }

        if(this.x <= 0) {
            this.x = 0;
        }

        if(this.x + this.width >= screenX) {
            this.x = screenX - this.width;
        }
    }
    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }

}
