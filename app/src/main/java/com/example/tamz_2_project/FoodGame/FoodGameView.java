package com.example.tamz_2_project.FoodGame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import static com.example.tamz_2_project.FoodGame.FoodGameFood.foodSpeed;
import com.example.tamz_2_project.GamesList;

public class FoodGameView extends SurfaceView implements Runnable {
    private boolean isPlaying;
    private Thread thread;
    private Canvas canvas;
    private Paint paint;
    private PandaFoodPlayer pandaFoodPlayer;
    private FoodGameBackground bg;
    private int screenX, screenY;
    private FoodGameFood food;
    private FoodGameFood foodSecond;
    private FoodGameBadFood badFood;
    private boolean isGameOver = false;
    public static int health = 5;
    private Health healthStat;
    private FoodGameActivity activity;

    public FoodGameView(Context context) {
        super(context);
    }

    public FoodGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FoodGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FoodGameView(FoodGameActivity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        this.pandaFoodPlayer = new PandaFoodPlayer(getResources(), screenX, screenY);
        this.paint = new Paint();
        this.isPlaying = true;
        this.bg = new FoodGameBackground(screenX, screenY, getResources());
        this.screenX = screenX;
        this.screenY = screenY;
        this.food = new FoodGameFood(getResources(), this.screenX, 0);
        this.foodSecond = new FoodGameFood(getResources(), this.screenX, 200);
        this.badFood = new FoodGameBadFood(getResources(), this.screenX);
        this.healthStat = new Health(this.screenX);
        health = 5;
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
        badFoodUpdateAll();

        // update good food
        goodFoodUpdateAll();

        // update panda
        this.pandaFoodPlayer.update(this.screenX);

        // if not create new good food
        if(this.food.eaten) {
           foodSpeed++;
            this.food = new FoodGameFood(getResources(), this.screenX, 0);
        }

        // if not create new good food
        if(this.foodSecond.eaten) {
            foodSpeed++;
            this.foodSecond = new FoodGameFood(getResources(), this.screenX, 0);
        }

        if(this.isGameOver && health == 0) {
            return;
        }
    }

    private void draw() throws InterruptedException {
        if(getHolder().getSurface().isValid()) {
            this.canvas = getHolder().lockCanvas();
            this.canvas.drawBitmap(this.bg.background, this.bg.x, this.bg.y, this.paint);
            this.canvas.drawBitmap(this.pandaFoodPlayer.pandaPlayer, this.pandaFoodPlayer.x, this.pandaFoodPlayer.y, this.paint);
            this.canvas.drawBitmap(this.food.food, this.food.x, this.food.y, this.paint);
            this.canvas.drawBitmap(this.foodSecond.food, this.foodSecond.x, this.foodSecond.y, this.paint);
            this.canvas.drawBitmap(this.badFood.food, this.badFood.x, this.badFood.y, this.paint);
            this.healthStat.draw(this.canvas);

            if(this.isGameOver && health == 0) {
                this.isPlaying = false;
                waitBeforeExit();
                getHolder().unlockCanvasAndPost(this.canvas);
                return;
            }

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

    private void badFoodUpdateAll() {
        this.isGameOver = this.badFood.update(this.screenY, this.pandaFoodPlayer);

        // check if game is over
        if(this.isGameOver && health == 0) {
            return;
        } else if (this.isGameOver && health > 0) {
            this.badFood = new FoodGameBadFood(getResources(), this.screenX);
        }
    }

    private void goodFoodUpdateAll() {
        this.isGameOver = this.food.update(this.screenY, this.pandaFoodPlayer);

        // check if game is over
        if(this.isGameOver && health == 0) {
            return;
        } else if (this.isGameOver && health > 0) {
            foodSpeed += 5;
            this.food = new FoodGameFood(getResources(), this.screenX, 0);
        }

        // update good food
        this.isGameOver = this.foodSecond.update(this.screenY, this.pandaFoodPlayer);

        // check if game is over
        if(this.isGameOver && health == 0) {
            return;
        } else if (this.isGameOver && health > 0) {
            this.foodSecond = new FoodGameFood(getResources(), this.screenX, 0);
        }
    }

    private void waitBeforeExit() throws InterruptedException {
        Thread.sleep(3000);
        this.activity.startActivity(new Intent(this.activity, GamesList.class));
        this.activity.finish();
    }

}
