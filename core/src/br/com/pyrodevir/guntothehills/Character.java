package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static br.com.pyrodevir.guntothehills.Constants.charSizeX;
import static br.com.pyrodevir.guntothehills.Constants.screenx;
import static br.com.pyrodevir.guntothehills.Constants.screeny;

public class Character {
    private int powerPoints = 3;
    private Texture[] frames; //por array pra mais sprites do char
    private Rectangle body; //collision
    private int modeCheck; //mode 0=entering game ; mode 1= menu ; mode 2= normal game; mode3=lose; mode 4=store;
    private int rotation = 0;
    private int newRotation = 0;
    private boolean shotTrigger = false;

    //TODO - mudar sprite com fase para sprite de corrida

    public Character (){
        body = new Rectangle(screenx/2 - charSizeX/4, screeny/2 +charSizeX/4, charSizeX/2, charSizeX/2);
        frames = new Texture[3];
        for(int i=1; i<=3; i++){
            frames[i-1] = new Texture("character/character"+i+".png");
        }
    }

    public void draw (SpriteBatch batch){
        /*draw(Texture texture, float x, float y,
        float originX, float originY, float width, float height,
        float scaleX, float scaleY, float rotation,
        int srcX, int srcY, int srcWidth, int srcHeight,
        boolean flipX, boolean flipY)*/
        batch.draw(frames[0], body.x, body.y, body.getWidth()/2, body.getHeight()/2,
                body.getWidth(), body.getHeight(), 2, 2, rotation,
                0,0,frames[0].getWidth(), frames[0].getHeight(), false, false);
    }

    public Boolean update(float time){
        //framesCount += 4*time;
        if (newRotation != rotation){
            rotationTick(time*(2+powerPoints)); //TODO por powerpoints o trigger da multiplicação - assim gira mais rápido com mais power;
        } else if (shotTrigger){
            return true;
        }
        return false;
    }

    public void isPressed(int x, int y){
        /*
        if (x<=screenx/3 && y<=(screeny/3)+charSizeX){ //bottom left
            newRotation = 315;
        } else if (x<=screenx/3 && (y<(2*screeny/3) && y>(screeny/3)+charSizeX/2)){ //middle left
            newRotation = 270;
        } else if (x<=screenx/3 && y>=(2*screeny/3)){ //top left
            newRotation = 225;
        } else if ((x>screenx/3 && x<2*screenx/3) && y>=(2*screeny/3)){ //middle top
            newRotation = 180;
        } else if (x>=2*screenx/3 && y>=(2*screeny/3)){ // top right
            newRotation = 135;
        } else if (x>=2*screenx/3 && y>(screeny/3)+charSizeX/2 && y<(2*screeny/3)){ //middle right
            newRotation = 90;
        } else if (x>=2*screenx/3 && y<=(screeny/3)+charSizeX){ // bottom right
            newRotation = 45;
        } else if ((x>screenx/3 && x<2*screenx/3) && y<=(screeny/3)+charSizeX){ // middle bottom
            newRotation = 0;
        }*/
        if (x<=screenx/2-charSizeX/2 && y<=(screeny/2)){ //bottom left
            newRotation = 315;
        } else if (x<=screenx/2-charSizeX/2 && (y<(screeny/2+charSizeX) && y>(screeny/2))){ //middle left
            newRotation = 270;
        } else if (x<=screenx/2-charSizeX/2 && y>=(screeny/2+charSizeX)){ //top left
            newRotation = 225;
        } else if ((x>screenx/2-charSizeX/2 && x<screenx/2+charSizeX/2) && y>=(screeny/2+charSizeX)){ //middle top
            newRotation = 180;
        } else if (x>=screenx/2+charSizeX/2 && y>=(screeny/2+charSizeX)){ // top right
            newRotation = 135;
        } else if (x>=screenx/2+charSizeX/2 && y>(screeny/2) && y<(screeny/2+charSizeX)){ //middle right
            newRotation = 90;
        } else if (x>=screenx/2+charSizeX/2 && y<=(screeny/2)){ // bottom right
            newRotation = 45;
        } else if ((x>screenx/2-charSizeX/2 && x<screenx/2+charSizeX/2) && y<=(screeny/2)){ // middle bottom
            newRotation = 0;
        }

        if (newRotation == rotation){
            shotTrigger = true;
        }
    }

    public void rotationTick(float time){
        if (rotation < newRotation && (newRotation - rotation) <= 180){
            rotation += (newRotation - rotation + 15) * time;
            if (newRotation-rotation<=2) {
                rotation = newRotation;
            }
        }else if (rotation < newRotation && (newRotation - rotation) > 180){
            rotation -= (360-newRotation+rotation) * time;
            if (rotation <= 0){
                rotation = 360;
            }
        }else if (rotation > newRotation && (rotation - newRotation) <= 180){
            rotation -= (rotation - newRotation) * time;
        }else if (rotation > newRotation && (rotation - newRotation) > 180){
            if (newRotation==0){ rotation+=(360-rotation+15)*time; }
            else { rotation += (newRotation + 70) * time; }
            if (rotation >= 360){
                rotation = 0;
            }
        }
        shotTrigger = true;
    }

    public int getRotation() {
        return rotation;
    }

    public Rectangle getRectangle(){
        return body;
    }

    public void setShotTrigger(boolean shotTrigger) {
        this.shotTrigger = shotTrigger;
    }

    public void setPowerPoints(int powerPoints) {
        this.powerPoints = powerPoints;
    }

    public void dispose(){
        for(int i=0; i <=2; i++){
            frames[i].dispose();
        }
    }
}
