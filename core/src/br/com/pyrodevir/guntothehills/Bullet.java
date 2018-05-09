package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static br.com.pyrodevir.guntothehills.Constants.charSizeX;
import static br.com.pyrodevir.guntothehills.Constants.loadTextures;
import static br.com.pyrodevir.guntothehills.Constants.screenx;
import static br.com.pyrodevir.guntothehills.Constants.screeny;

public class Bullet {
    private Rectangle bulletRect;
    private Texture bullet;
    private int shotDirection;
    private float directionX = 0;
    private float directionY = 0;

    //weapontype 1 and 3
    public Bullet(int shotDirection){
        bullet = loadTextures.get(3);
        bulletRect = new Rectangle((screenx/2 -charSizeX/16), (screeny/2 +charSizeX/2.5f), charSizeX/8, charSizeX/8);
        this.shotDirection = shotDirection;
        directionBullet();
    }

    //weapontype 2
    public Bullet(int shotDirection, int distBullets){
        bullet = loadTextures.get(3);
        Random random = new Random();
        bulletRect = new Rectangle((screenx/2 -charSizeX/16) +random.nextInt(distBullets)-15,
                (screeny/2 +charSizeX/2.5f +random.nextInt(distBullets)-15), charSizeX/8, charSizeX/8);
        this.shotDirection = shotDirection;
        directionBullet();
    }

    public void draw(SpriteBatch batch){
        batch.draw(bullet, bulletRect.x, bulletRect.y,
        bulletRect.getWidth()/2, bulletRect.getHeight()/2,
        bulletRect.getWidth(), bulletRect.getHeight(), 4, 4, shotDirection,
        0,0,bullet.getWidth(), bullet.getHeight(), false, false);
    }

    public boolean update(float time){
        bulletRect.x += directionX * time;
        bulletRect.y += directionY * time;
        if (bulletRect.x-charSizeX/3>screenx || bulletRect.y-charSizeX/3>screeny
                || bulletRect.x+bulletRect.getWidth()+charSizeX/3<0 || bulletRect.y+bulletRect.getHeight()+charSizeX/3<0){
            return true;
        }
        return false;
    }

    public void directionBullet(){
        switch (shotDirection){
            case 0: directionY = -screenx;
                break;
            case 45: directionX = screenx/1.5f;
                directionY = -screenx/1.5f;
                break;
            case 90: directionX = screenx;
                break;
            case 135: directionX = screenx/1.5f;
                directionY = screenx/1.5f;
                break;
            case 180: directionY = screenx;
                break;
            case 225: directionX = -screenx/1.5f;
                directionY = screenx/1.5f;
                break;
            case 270: directionX = -screenx;
                break;
            case 315: directionX = -screenx/1.5f;
                directionY = -screenx/1.5f;
                break;
            //360, 405 and -45 added because of Shotgun formula on main;
            case 360: directionY = -screenx;
                break;
            case 405: directionX = screenx/1.5f;
                directionY = -screenx/1.5f;
                break;
            case -45: directionX = -screenx/1.5f;
                directionY = -screenx/1.5f;
                break;
        }
    }

    public Rectangle getBulletRect(){
        return bulletRect;
    }

    public void dispose(){
        bullet.dispose();
    }
}
