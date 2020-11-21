package com.example.tamz_2_project.PandaStats;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Statistics {
    int margin = 5;
    int height = 70;
    void draw(Canvas canvas);
    void update();
}
