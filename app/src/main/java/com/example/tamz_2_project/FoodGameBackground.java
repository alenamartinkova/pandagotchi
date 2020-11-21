package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FoodGameBackground {
    int x = 0, y;
    Bitmap background;

    FoodGameBackground(int screenX, int screenY, Resources res) {
        this.background = BitmapFactory.decodeResource(res, R.drawable.greenbg);
        this.background = Bitmap.createScaledBitmap(this.background, screenX, screenY, false);
    }
}
