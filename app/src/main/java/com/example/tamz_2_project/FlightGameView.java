package com.example.tamz_2_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class FlightGameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private FlightBackground firstBg, secondBg;
    private Canvas canvas;
    private Paint paint;

    public FlightGameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenRatioX = 1920f / screenX;
        this.screenRatioY = 1920f / screenY;

        this.firstBg = new FlightBackground(screenX, screenY, getResources());
        this.secondBg = new FlightBackground(screenX, screenY, getResources());

        this.secondBg.x = screenX;
        this.screenX = screenX;
        this.screenY = screenY;
        this.paint = new Paint();
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
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            this.canvas = getHolder().lockCanvas();
            this.canvas.drawBitmap(this.firstBg.background, this.firstBg.x, this.firstBg.y, this.paint);
            this.canvas.drawBitmap(this.secondBg.background, this.secondBg.x, this.secondBg.y, this.paint);

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
}
