package com.example.tamz_2_project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Love implements Statistics {
    private int width, x, y;

    Love(int screenX, int screenY) {
        this.width = (int) (screenX * 0.6);
        this.x = screenX / 5;
        this.y = (int) (screenY * 0.75);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // Border
        canvas.drawRect(this.x, this.y - this.height, this.x + this.width, this.y, paint);

        // Health
        int healthWidth = this.width - 2 * this.margin;
        int healthHeight = this.height - 2 * this.margin;
        int healthLeft = this.x + this.margin;
        int healthRight = healthLeft + healthWidth;
        int healthBottom = this.y - this.margin;
        int healthTop = healthBottom - healthHeight;

        paint.setColor(Color.GREEN);
        canvas.drawRect(healthLeft, healthTop, healthRight, healthBottom, paint);
    }
}
