package com.example.tamz_2_project.PandaBan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.tamz_2_project.GamesList;
import com.example.tamz_2_project.MainActivity;
import com.example.tamz_2_project.R;

public class PandaBanView extends View {
    Bitmap[] bmp;

    int lx;
    int ly;

    int width;
    int height;

    int heroX;
    int heroY;

    boolean wasCross = false;
    char levelName;
    Intent myIntent;

    private int level[] = {
            1,1,1,1,1,1,1,1,1,0,
            1,0,0,0,0,0,0,0,1,0,
            1,0,2,3,3,2,1,0,1,0,
            1,0,1,3,2,3,2,0,1,0,
            1,0,2,3,3,2,4,0,1,0,
            1,0,1,3,2,3,2,0,1,0,
            1,0,2,3,3,2,1,0,1,0,
            1,0,0,0,0,0,0,0,1,0,
            1,1,1,1,1,1,1,1,1,0,
            0,0,0,0,0,0,0,0,0,0
    };

    public PandaBanView(Context context) {
        super(context);
        init(context);
    }

    public PandaBanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PandaBanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[6];

        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.box);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
        bmp[5] = BitmapFactory.decodeResource(getResources(), R.drawable.boxok);
    }

    public void setLevel(int[] newLevel, int newLy, int newLx, int newHeroX, int newHeroY, boolean cross, char levelNum) {
        heroX = newHeroX;
        heroY = newHeroY;
        lx = newLx;
        ly = newLy;
        level = new int[lx * ly];
        wasCross = cross;
        levelName = levelNum;
        System.arraycopy(newLevel, 0, level, 0, newLevel.length);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            {
                float xTouch = event.getX();
                float yTouch = event.getY();

                float realWidth = width * ly;
                float realHeight = height * lx;

                // RIGHT
                if((xTouch >= realWidth * 0.75) && (yTouch > realHeight / 4) && (yTouch < realHeight * 0.75)) {
                    // tile without cross
                    if(canMoveRightEmpty()) {
                        level[heroY * ly + heroX + 1] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX++;
                        wasCross = false;
                        // tile with cross
                    } else if(canMoveRightCross()) {
                        level[heroY * ly + heroX + 1] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX++;
                        wasCross = true;
                        // tile with box
                    } else if(canMoveBoxRightEmpty()) {
                        level[heroY * ly + heroX + 1] = 4;
                        level[heroY * ly + heroX + 2] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX++;
                        wasCross = false;
                    } else if(canMoveBoxRightCross()) {
                        level[heroY * ly + heroX + 1] = 4;
                        level[heroY * ly + heroX + 2] = 5;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX++;
                        wasCross = false;
                    } else if (greenBoxNextRightEmpty()) {
                        level[heroY * ly + heroX + 1] = 4;
                        level[heroY * ly + heroX + 2] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX++;
                        wasCross = true;
                    } else if (greenBoxNextRightCross()) {
                        level[heroY * ly + heroX + 1] = 4;
                        level[heroY * ly + heroX + 2] = 5;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX++;
                        wasCross = true;
                    }
                    // LEFT
                } else if((xTouch <= realWidth / 4) && (yTouch > realHeight / 4) && (yTouch < realHeight * 0.75)){
                    // tile without cross
                    if(canMoveLeftEmpty()) {
                        level[heroY * ly + heroX - 1] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX--;
                        wasCross = false;
                        // tile with cross
                    } else if(canMoveLeftCross()) {
                        level[heroY * ly + heroX - 1] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX--;
                        wasCross = true;
                        // tile with box
                    } else if(canMoveBoxLeftEmpty()) {
                        level[heroY * ly + heroX - 1] = 4;
                        level[heroY * ly + heroX - 2] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX--;
                        wasCross = false;
                    } else if(canMoveBoxLeftCross()) {
                        level[heroY * ly + heroX - 1] = 4;
                        level[heroY * ly + heroX - 2] = 5;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX--;
                        wasCross = false;
                    } else if (greenBoxNextLeftEmpty()) {
                        level[heroY * ly + heroX - 1] = 4;
                        level[heroY * ly + heroX - 2] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX--;
                        wasCross = true;
                    } else if (greenBoxNextLeftCross()) {
                        level[heroY * ly + heroX - 1] = 4;
                        level[heroY * ly + heroX - 2] = 5;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroX--;
                        wasCross = true;
                    }
                    //UP
                } else if(yTouch <= realHeight / 4) {
                    // tile without cross
                    if(canMoveUpEmpty()) {
                        level[(heroY - 1) * ly + heroX] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY--;
                        wasCross = false;
                        // tile with cross
                    } else if(canMoveUpCross()) {
                        level[(heroY - 1) * ly + heroX] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY--;
                        wasCross = true;
                        // tile with box
                    } else if(canMoveBoxUpEmpty()) {
                        level[(heroY - 1) * ly + heroX] = 4;
                        level[(heroY - 2) * ly + heroX] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY--;
                        wasCross = false;
                    } else if(canMoveBoxUpCross()) {
                        level[(heroY - 1) * ly + heroX] = 4;
                        level[(heroY - 2) * ly + heroX] = 5;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY--;
                        wasCross = false;
                    } else if (greenBoxNextUpEmpty()) {
                        level[(heroY - 1) * ly + heroX] = 4;
                        level[(heroY - 2) * ly + heroX] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY--;
                        wasCross = true;
                    } else if (greenBoxNextUpCross()) {
                        level[(heroY - 1) * ly + heroX] = 4;
                        level[(heroY - 2) * ly + heroX] = 3;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY--;
                        wasCross = true;
                    }
                    // DOWN
                } else if(yTouch >= realHeight * 0.75){
                    // tile without cross
                    if(canMoveDownEmpty()) {
                        level[(heroY + 1) * ly + heroX] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY++;
                        wasCross = false;
                        // tile with cross
                    } else if(canMoveDownCross()) {
                        level[(heroY + 1) * ly + heroX] = 4;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY++;
                        wasCross = true;
                        // tile with box
                    } else if(canMoveBoxDownEmpty()) {
                        level[(heroY + 1) * ly + heroX] = 4;
                        level[(heroY + 2) * ly + heroX] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY++;
                        wasCross = false;
                    } else if (canMoveBoxDownCross()) {
                        level[(heroY + 1) * ly + heroX] = 4;
                        level[(heroY + 2) * ly + heroX] = 5;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY++;
                        wasCross = false;
                    } else if (greenBoxNextDownEmpty()) {
                        level[(heroY + 1) * ly + heroX] = 4;
                        level[(heroY + 2) * ly + heroX] = 2;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY++;
                        wasCross = true;
                    } else if (greenBoxNextDownCross()) {
                        level[(heroY + 1) * ly + heroX] = 4;
                        level[(heroY + 2) * ly + heroX] = 5;
                        level[heroY * ly + heroX] = (wasCross) ? 3 : 0;
                        heroY++;
                        wasCross = true;
                    }
                }

                invalidate();
                break;
            }
        }

        // if there is no box on wrong place
        if(!contains(level, 2)){
            Toast.makeText(getContext(), "GOOD JOB", Toast.LENGTH_LONG).show();
            levelName = (char) (levelName + 1);
            myIntent = new Intent(getContext(), PandaBanActivity.class);
            myIntent.putExtra("level", levelName);
            MainActivity.loveStorage += 10;

            if(MainActivity.loveStorage >= 100) {
                MainActivity.loveStorage = 100;
            }

            myIntent.putExtra("storeLove", true);
            getContext().startActivity(myIntent);
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / ly;
        height = h / lx;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                canvas.drawBitmap(bmp[level[i*ly + j]], null, new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
            }
        }
    }

    protected static boolean contains(final int[] array, final int v) {
        boolean result = false;
        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
    }

    protected boolean canMoveRightEmpty() {
        return (level[heroY * ly + heroX + 1] == 0);
    }

    protected boolean canMoveRightCross() {
        return (level[heroY * ly + heroX + 1] == 3);
    }

    protected boolean canMoveBoxRightEmpty() {
        return (level[heroY * ly + heroX + 1] == 2 && level[heroY * ly + heroX + 2] == 0);
    }

    protected boolean canMoveBoxRightCross() {
        return (level[heroY * ly + heroX + 1] == 2 && level[heroY * ly + heroX + 2] == 3);
    }

    protected boolean canMoveLeftEmpty() {
        return (level[heroY * ly + heroX - 1] == 0);
    }

    protected boolean canMoveLeftCross() {
        return (level[heroY * ly + heroX - 1] == 3);
    }

    protected boolean canMoveBoxLeftEmpty() {
        return (level[heroY * ly + heroX - 1] == 2 && level[heroY * ly + heroX - 2] == 0);
    }

    protected boolean canMoveBoxLeftCross() {
        return (level[heroY * ly + heroX - 1] == 2 && level[heroY * ly + heroX - 2] == 3);
    }

    protected boolean canMoveUpEmpty() {
        return (level[(heroY - 1) * ly + heroX] ==  0);
    }

    protected boolean canMoveUpCross() {
        return (level[(heroY - 1) * ly + heroX] == 3);
    }

    protected boolean canMoveBoxUpEmpty() {
        return (level[(heroY - 1) * ly + heroX] == 2 && level[(heroY - 2) * ly + heroX] == 0);
    }

    protected boolean canMoveBoxUpCross() {
        return (level[(heroY - 1) * ly + heroX] == 2 && level[(heroY - 2) * ly + heroX] == 3);
    }

    protected boolean canMoveDownEmpty() {
        return (level[(heroY + 1) * ly + heroX] ==  0);
    }

    protected boolean canMoveDownCross() {
        return (level[(heroY + 1) * ly + heroX] == 3);
    }

    protected boolean canMoveBoxDownEmpty() {
        return (level[(heroY + 1) * ly + heroX] == 2 && level[(heroY + 2) * ly + heroX] == 0);
    }

    protected boolean canMoveBoxDownCross() {
        return (level[(heroY + 1) * ly + heroX] == 2 && level[(heroY + 2) * ly + heroX] == 3);
    }

    protected boolean greenBoxNextDownEmpty() {
        return (level[(heroY + 1) * ly + heroX] == 5 && level[(heroY + 2) * ly + heroX] == 0);
    }

    protected boolean greenBoxNextDownCross() {
        return (level[(heroY + 1) * ly + heroX] == 5 && level[(heroY + 2) * ly + heroX] == 3);
    }

    protected boolean greenBoxNextUpEmpty() {
        return (level[(heroY - 1) * ly + heroX] == 5 && level[(heroY - 2) * ly + heroX] == 0);
    }

    protected boolean greenBoxNextUpCross() {
        return (level[(heroY - 1) * ly + heroX] == 5 && level[(heroY - 2) * ly + heroX] == 3);
    }

    protected boolean greenBoxNextLeftEmpty() {
        return (level[heroY * ly + heroX - 1] == 5 && level[heroY * ly + heroX - 2] == 0);
    }

    protected boolean greenBoxNextLeftCross() {
        return (level[heroY * ly + heroX - 1] == 5 && level[heroY * ly + heroX - 2] == 3);
    }

    protected boolean greenBoxNextRightEmpty() {
        return (level[heroY * ly + heroX + 1] == 5 && level[heroY * ly + heroX + 2] == 0);
    }

    protected boolean greenBoxNextRightCross() {
        return (level[heroY * ly + heroX + 1] == 5 && level[heroY * ly + heroX + 2] == 3);
    }
}
