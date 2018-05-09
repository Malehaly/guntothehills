package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static br.com.pyrodevir.guntothehills.Constants.charSizeX;
import static br.com.pyrodevir.guntothehills.Constants.loadTextures;
import static br.com.pyrodevir.guntothehills.Constants.screenx;
import static br.com.pyrodevir.guntothehills.Constants.screeny;

public class Flame {
    private Rectangle flameRect;
    private Rectangle flameCollider;
    private Texture frame;
    private float auxFrames = 0;
    private int shotDirection;
    private boolean flip;
    private float auxHeight;
    private float directionX = 0;
    private float directionY = 0;

    public Flame(int shotDirection){
        frame = loadTextures.get(14);
        flameRect = new Rectangle((screenx/2 -charSizeX/16), (screeny/2 +charSizeX/2.5f), charSizeX/8, charSizeX/8);
        flameCollider = new Rectangle((screenx/2 -charSizeX/16), (screeny/2 +charSizeX/2.5f), charSizeX/8, charSizeX/8);
        this.shotDirection = shotDirection;
        directionFlame();
    }

    public void draw(SpriteBatch batch){
        batch.draw(frame, flameRect.x, flameRect.y,
                flameRect.getWidth()/2, flameRect.getHeight()/2,
                flameRect.getWidth(), auxHeight, 5, 6, shotDirection,
                0,0,frame.getWidth(), frame.getHeight(), flip, true);
    }

    public boolean update(float time){
        auxFrames += 2*time;
        auxHeight = (-flameRect.getHeight()-charSizeX/8)*auxFrames;

        //small delay compared to flame sprite, but feels more real - like burning.
        flameCollider.x += directionX * time/2;
        flameCollider.y += directionY * time/2;
        flameRect.x += directionX * time/10;
        flameRect.y += directionY* time/10;

        //flip X on the draw - make flame move
        if ((int)auxFrames%2 == 0){
            flip = false;
        } else {
            flip = true;
        }

        if (flameCollider.x>screenx || flameCollider.y>screeny
                || flameCollider.x+flameCollider.getWidth()<0 || flameCollider.y+flameCollider.getHeight()<0+charSizeX){
            return true;
        }
        return false;
    }

    public void directionFlame() {
        switch (shotDirection) {
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
        }
    }

    public Rectangle getFlameCollider(){
        return flameCollider;
    }

    public void dispose(){ frame.dispose(); }
}

