package com.example.tamz_2_project.FoodGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static com.example.tamz_2_project.FoodGame.FoodGameView.health;

public class Health {
    private int x, y;

    public Health(int screenX) {
        this.x = screenX / 2;
        this.y = 100;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        paint.setTextSize(60);
        if(health <= 0) {
            canvas.drawText("Health: 0", this.x - 140, this.y + 10, paint);
        } else {
            canvas.drawText("Health: " + health, this.x - 140, this.y + 10, paint);
        }
    }
}
