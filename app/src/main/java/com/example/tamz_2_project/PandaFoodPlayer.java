package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.tamz_2_project.MainActivity.screenRatioX;
import static com.example.tamz_2_project.MainActivity.screenRatioY;

public class PandaFoodPlayer {
    int x, y, width, height;
    boolean moveRight = false, moveLeft = false;
    Bitmap pandaPlayer;

    public PandaFoodPlayer(Resources res, int screenX, int screenY) {
        this.pandaPlayer = BitmapFactory.decodeResource(res, R.drawable.pandafood);

        this.width = this.pandaPlayer.getWidth();
        this.height = this.pandaPlayer.getHeight();

        this.width = (int) ((this.width / 4) * screenRatioX);
        this.height = (int) ((this.height / 3) * screenRatioY);

        this.pandaPlayer = Bitmap.createScaledBitmap(this.pandaPlayer, this.width, this.height, false);

        this.y = (int) (screenY * 0.8);
        this.x = (int) (screenX * 0.37);
    }

    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }

}
