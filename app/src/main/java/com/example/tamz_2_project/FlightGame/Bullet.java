package com.example.tamz_2_project.FlightGame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.tamz_2_project.R;

public class Bullet {
    int x, y, width, height;
    Bitmap bullet;

    Bullet(Resources res) {
        this.bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        this.width = bullet.getWidth();
        this.height = bullet.getHeight();

        this.width = this.width / 4;
        this.height = this.height / 4;

        this.bullet = Bitmap.createScaledBitmap(this.bullet, this.width, this.height, false);
    }

    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }
}
