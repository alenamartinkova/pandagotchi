package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.tamz_2_project.MainActivity.screenRatioX;
import static com.example.tamz_2_project.MainActivity.screenRatioY;

public class Bullet {
    int x, y, width, height;
    Bitmap bullet;

    Bullet(Resources res) {
        this.bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        this.width = bullet.getWidth();
        this.height = bullet.getHeight();

        this.width = (int) ((this.width / 4) * screenRatioX);
        this.height = (int) ((this.height / 4) * screenRatioY);

        this.bullet = Bitmap.createScaledBitmap(this.bullet, this.width, this.height, false);
    }

    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }
}
