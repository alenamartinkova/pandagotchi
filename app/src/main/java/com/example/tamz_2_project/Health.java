package com.example.tamz_2_project;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Health implements Statistics {
    private int width, x, y;

    Health(int screenX, int screenY) {
        this.width = (int) (screenX * 0.6);
        this.x = (int) (screenX * 0.25);
        this.y = (int) (screenY * 0.7);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        paint.setTextSize(40);
        canvas.drawText("Health: ", this.x - 140,(this.y - this.height/2) + 10, paint);

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
