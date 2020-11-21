package com.example.tamz_2_project.FlightGame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tamz_2_project.R;

public class FlightBackground {
    int x = 0, y;
    Bitmap background;

    FlightBackground(int screenX, int screenY, Resources res) {
        this.background = BitmapFactory.decodeResource(res, R.drawable.flightbackground);
        this.background = Bitmap.createScaledBitmap(this.background, screenX, screenY, false);
    }
}
