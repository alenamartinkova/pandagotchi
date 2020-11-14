package com.example.tamz_2_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.tamz_2_project.FlightGameView.screenRatioX;
import static com.example.tamz_2_project.FlightGameView.screenRatioY;

public class Flight {

    public boolean isGoingUp = false;
    int x, y, width, height, wingCounter = 0, toShoot = 0, shootCounter = 1;
    Bitmap flightOne, flightTwo, shootOne, shootTwo, shootThree, shootFour, shootFive, dead;
    private FlightGameView flightGameView;

    Flight(int screenY, Resources res, FlightGameView fgw) {
        this.flightGameView = fgw;

        // Plane
        this.flightOne = BitmapFactory.decodeResource(res, R.drawable.fly1);
        this.flightTwo = BitmapFactory.decodeResource(res, R.drawable.fly1);

        this.width = this.flightOne.getWidth();
        this.height = this.flightOne.getHeight();

        this.width = (int) ((this.width / 4) * screenRatioX);
        this.height = (int) ((this.height / 4) * screenRatioY);

        this.flightOne = Bitmap.createScaledBitmap(this.flightOne, this.width, this.height, false);
        this.flightTwo = Bitmap.createScaledBitmap(this.flightTwo, this.width, this.height, false);

        this.y = screenY / 2;
        this.x = (int) (64 * screenRatioX);

        // Shooting plane
        this.shootOne = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        this.shootTwo = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        this.shootThree = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        this.shootFour = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        this.shootFive = BitmapFactory.decodeResource(res, R.drawable.shoot5);

        this.shootOne = Bitmap.createScaledBitmap(this.shootOne, this.width, this.height, false);
        this.shootTwo = Bitmap.createScaledBitmap(this.shootTwo, this.width, this.height, false);
        this.shootThree = Bitmap.createScaledBitmap(this.shootThree, this.width, this.height, false);
        this.shootFour = Bitmap.createScaledBitmap(this.shootFour, this.width, this.height, false);
        this.shootFive = Bitmap.createScaledBitmap(this.shootFive, this.width, this.height, false);

        // Dead
        this.dead = BitmapFactory.decodeResource(res, R.drawable.dead);
        this.dead = Bitmap.createScaledBitmap(this.dead, this.width, this.height, false);

    }

    public Bitmap getFlight() {
        if (this.toShoot != 0) {
            if(this.shootCounter == 1) {
                this.shootCounter++;
                return this.shootOne;
            } else if(this.shootCounter == 2) {
                this.shootCounter++;
                return this.shootTwo;
            } else if(this.shootCounter == 3) {
                this.shootCounter++;
                return this.shootThree;
            } else if(this.shootCounter == 4) {
                this.shootCounter++;
                return this.shootFour;
            } else if(this.shootCounter == 5) {
                this.shootCounter = 1;
                this.toShoot--;
                this.flightGameView.newBullet();
                return this.shootFive;
            }
        }

        if(this.wingCounter == 0) {
            this.wingCounter++;
            return this.flightOne;
        } else {
            this.wingCounter--;
            return this.flightTwo;
        }
    }

    public void update(int screenY) {
        if(this.isGoingUp) {
            this.y -= 20 * screenRatioY;
        } else {
            this.y += 20 * screenRatioY;
        }

        if(this.y < 0) {
            this.y = 0;
        }

        if(this.y > screenY - this.height) {
            this.y = screenY - this.height;
        }
    }

    Rect getRect() {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }

    Bitmap getDead() {
        return this.dead;
    }
}
