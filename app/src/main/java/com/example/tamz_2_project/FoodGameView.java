package com.example.tamz_2_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class FoodGameView extends SurfaceView implements Runnable {
    private boolean isPlaying;
    private Thread thread;
    private Canvas canvas;
    private Paint paint;
    private PandaFoodPlayer pandaFoodPlayer;
    private FoodGameBackground bg;
    private int screenX, screenY;
    private FoodGameFood food;
    private FoodGameBadFood badFood;
    private boolean isGameOver = false;
    public static int health = 3;

    public FoodGameView(Context context) {
        super(context);
    }

    public FoodGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FoodGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FoodGameView(Context context, int screenX, int screenY) {
        super(context);
        this.pandaFoodPlayer = new PandaFoodPlayer(getResources(), screenX, screenY);
        this.paint = new Paint();
        this.isPlaying = true;
        this.bg = new FoodGameBackground(screenX, screenY, getResources());
        this.screenX = screenX;
        this.screenY = screenY;
        this.food = new FoodGameFood(getResources(), this.screenX);
        this.badFood = new FoodGameBadFood(getResources(), this.screenX);
    }

    @Override
    public void run() {
        while(this.isPlaying) {
            update();
            try {
                draw();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sleep();
        }
    }

    public void resume() {
        this.isPlaying = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void pause() throws InterruptedException {
        try {
            this.isPlaying = false;
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        // update bad food
        this.isGameOver = this.badFood.update(this.screenY, this.pandaFoodPlayer);

        // check if game is over
        if(this.isGameOver && health == 0) {
            return;
        } else if (this.isGameOver && health > 0) {
            this.badFood.badFoodSpeed++;
            this.badFood = new FoodGameBadFood(getResources(), this.screenX);
        }

        // update good food
        this.isGameOver = this.food.update(this.screenY, this.pandaFoodPlayer);

        // check if game is over
        if(this.isGameOver && health == 0) {
            return;
        } else if (this.isGameOver && health > 0) {
            this.food.foodSpeed += 3;
            this.food = new FoodGameFood(getResources(), this.screenX);
        }


        // update panda
        this.pandaFoodPlayer.update(this.screenX);

        // if not create new good food
        if(this.food.eaten) {
            this.food.foodSpeed++;
            this.food = new FoodGameFood(getResources(), this.screenX);
        }
    }

    private void draw() throws InterruptedException {
        if(getHolder().getSurface().isValid()) {
            this.canvas = getHolder().lockCanvas();
            this.canvas.drawBitmap(this.bg.background, this.bg.x, this.bg.y, this.paint);
            this.canvas.drawBitmap(this.pandaFoodPlayer.pandaPlayer, this.pandaFoodPlayer.x, this.pandaFoodPlayer.y, this.paint);
            this.canvas.drawBitmap(this.food.food, this.food.x, this.food.y, this.paint);
            this.canvas.drawBitmap(this.badFood.food, this.badFood.x, this.badFood.y, this.paint);

            getHolder().unlockCanvasAndPost(this.canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(xTouch < this.screenX / 2) {
                    this.pandaFoodPlayer.moveRight = false;
                    this.pandaFoodPlayer.moveLeft = true;
                } else {
                    this.pandaFoodPlayer.moveRight = true;
                    this.pandaFoodPlayer.moveLeft = false;
                }

                break;

            case MotionEvent.ACTION_UP:
                this.pandaFoodPlayer.moveRight = false;
                this.pandaFoodPlayer.moveLeft = false;
                break;
        }
        return true;
    }
}
