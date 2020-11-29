package com.example.tamz_2_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private Pandagotchi pandagotchi;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        this.pandagotchi = new Pandagotchi(getResources(), this.screenX, this.screenY);
        this.pandagotchi.x = this.screenX / 2 - 180;
        this.pandagotchi.y = this.screenY / 5;

        this.paint = new Paint();
    }

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }

    }

    public void update() {
        this.pandagotchi.update();
    }

    public void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            this.pandagotchi.draw(canvas, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void sleep() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() throws InterruptedException {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
