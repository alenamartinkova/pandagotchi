package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FlightBackground {
    int x = 0, y;
    Bitmap background;

    FlightBackground(int screenX, int screenY, Resources res) {
        this.background = BitmapFactory.decodeResource(res, R.drawable.background);
        this.background = Bitmap.createScaledBitmap(this.background, screenX, screenY, false);
    }
}
