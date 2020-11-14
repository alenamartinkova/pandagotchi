package com.example.tamz_2_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlightGameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private FlightBackground firstBg, secondBg;
    private List<Bullet> bullets;
    private Enemy[] enemies;
    private Canvas canvas;
    private Paint paint;
    private Flight flight;
    private Random random;

    public FlightGameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenRatioX = 1920f / screenX;
        this.screenRatioY = 1920f / screenY;

        this.firstBg = new FlightBackground(screenX, screenY, getResources());
        this.secondBg = new FlightBackground(screenX, screenY, getResources());
        this.secondBg.x = screenX;
        this.screenX = screenX;
        this.screenY = screenY;

        this.flight = new Flight(screenY, getResources(), this);
        this.paint = new Paint();

        this.bullets = new ArrayList<>();
        this.enemies = new Enemy[4];
        createEnemies();

        this.random = new Random();
    }

    public FlightGameView(Context context) {
        super(context);
    }

    public FlightGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlightGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void run() {
        while(this.isPlaying) {
            update();
            draw();
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
        this.firstBg.x -= 10 * this.screenRatioX;
        this.secondBg.x -= 10 * this.screenRatioX;

        if(this.firstBg.x + this.firstBg.background.getWidth() < 0) {
            this.firstBg.x = this.screenX;
        }

        if(this.secondBg.x + this.secondBg.background.getWidth() < 0) {
            this.secondBg.x = this.screenX;
        }

        this.flight.update(this.screenY);

        List<Bullet> toDelete = new ArrayList<>();

        for(Bullet b : this.bullets) {
            if(b.x > screenX) {
                toDelete.add(b);
            } else {
                b.x += 50 * screenRatioX;
            }

            for (Enemy e : this.enemies) {
                if(Rect.intersects(e.getRect(), b.getRect())) {
                    e.x = -500;
                    b.x = this.screenX + 500;
                    e.wasShot = true;
                }
            }
        }

        for(Bullet b : toDelete) {
            this.bullets.remove(b);
        }

        for(Enemy e : this.enemies) {
            this.isGameOver = e.update(this.screenX, this.screenY, this.random, this.flight);

            if(this.isGameOver) return;
        }
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            this.canvas = getHolder().lockCanvas();
            this.canvas.drawBitmap(this.firstBg.background, this.firstBg.x, this.firstBg.y, this.paint);
            this.canvas.drawBitmap(this.secondBg.background, this.secondBg.x, this.secondBg.y, this.paint);

            if(this.isGameOver) {
                this.isPlaying = false;
                this.canvas.drawBitmap(this.flight.getDead(), this.flight.x, this.flight.y, this.paint);
                getHolder().unlockCanvasAndPost(this.canvas);
                return;
            }

            for(Enemy e : this.enemies) {
                this.canvas.drawBitmap(e.getEnemy(), e.x, e.y, this.paint);
            }

            this.canvas.drawBitmap(this.flight.getFlight(), this.flight.x, this.flight.y, this.paint);

            for(Bullet b : this.bullets) {
                this.canvas.drawBitmap(b.bullet, b.x, b.y, this.paint);
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
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < this.screenX / 2) {
                    this.flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                this.flight.isGoingUp = false;

                if(event.getX() > this.screenX / 2) {
                    this.flight.toShoot++;
                }
                break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = this.flight.x + this.flight.width;
        bullet.y = this.flight.y + (this.flight.height / 2);
        this.bullets.add(bullet);
    }

    public void createEnemies() {
        for(int i = 0; i < this.enemies.length; i++) {
            this.enemies[i] = new Enemy(getResources());
        }
    }
}
