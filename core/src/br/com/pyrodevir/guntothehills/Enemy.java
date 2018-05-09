package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static br.com.pyrodevir.guntothehills.Constants.charSizeX;
import static br.com.pyrodevir.guntothehills.Constants.loadTextures;
import static br.com.pyrodevir.guntothehills.Constants.screenx;
import static br.com.pyrodevir.guntothehills.Constants.screeny;

public class Enemy {
    private Texture[] textures;
    private float auxTextures;
    private Rectangle body;
    private int angleBorn;
    private int statusOfZombie = 0; //0-walk 1-attack 2-dying 3-(gone-remove).
    private boolean triggerDamage;
    private float directionX = 0;
    private float directionY = 0;

    //todo por constructor de mais inimigos - cachorros, 1 hunter, 1 zumbi de longe (acido)
    public Enemy(int angleBorn){
        Random random = new Random();
        auxTextures = random.nextInt(4) + 1;
        this.angleBorn = angleBorn;
        int[] position = positionBorn(angleBorn);
        body = new Rectangle(position[0] - charSizeX/4, position[1] +charSizeX/4, charSizeX/2, charSizeX/2);
        textures = new Texture[4];
        textures[0] = loadTextures.get(4);
        textures[1] = loadTextures.get(5);
        textures[2] = loadTextures.get(6);
        textures[3] = loadTextures.get(7);
        directionWalk();
    }

    public void draw(SpriteBatch batch){
        /*draw(Texture texture, float x, float y,
        float originX, float originY, float width, float height,
        float scaleX, float scaleY, float rotation,
        int srcX, int srcY, int srcWidth, int srcHeight,
        boolean flipX, boolean flipY)*/
        if (statusOfZombie == 0){
            batch.draw(textures[(int)auxTextures%4], body.x, body.y,
                    body.getWidth()/2, body.getHeight()/2,
                    body.getWidth(), body.getHeight(), 2, 2, angleBorn+180,
                    0,0,textures[0].getWidth(), textures[0].getHeight(), false, false);
        } else if (statusOfZombie == 1){
            batch.draw(textures[(int)auxTextures%3], body.x, body.y,
                    body.getWidth()/2, body.getHeight()/2,
                    body.getWidth(), body.getHeight(), 2, 2, angleBorn+180,
                    0,0,textures[0].getWidth(), textures[0].getHeight(), false, false);
        } else if (statusOfZombie == 2){
            batch.draw(textures[(int)auxTextures%3], body.x, body.y, body.getWidth(), body.getHeight());
        }


    }

    public boolean update(float time){
        if (statusOfZombie == 0){           //walking
            auxTextures += 4*time;
            body.x += directionX * time/15;
            body.y += directionY * time/15;
        } else if(statusOfZombie == 1){     //attacking
            if (auxTextures == 0){
                textures = new Texture[3];
                textures[0] = loadTextures.get(8);
                textures[1] = loadTextures.get(9);
                textures[2] = loadTextures.get(10);
            }
            auxTextures += 3*time;
            triggerDamage = false;
            if ((int)auxTextures >= 4){
                triggerDamage = true;
                auxTextures = 0.1f;
            }
        } else if (statusOfZombie == 2){    //dying
            if (auxTextures == 0){
                textures = new Texture[3];
                textures[0] = loadTextures.get(11);
                textures[1] = loadTextures.get(12);
                textures[2] = loadTextures.get(13);
            }
            auxTextures += 3*time/5;
            if ((int)auxTextures>=3){
                statusOfZombie = 3;
            }
        } else {                            //status 3 - after sprites of Dying
            return false;
        }
        return true;
    }

    //position that zombie is created
    public int[] positionBorn(int angleBorn){
        int xBorn = 0-charSizeX/2;
        int yBorn = 0;
        switch (angleBorn){
            case 0: xBorn = screenx/2;
                //yBorn = charSizeX/2; //because of WeapongBackground - avoid killing zombies off-screen.
                break;
            case 45: xBorn = screenx +charSizeX/2;
                yBorn = screeny/5 -charSizeX/2;
                break;
            case 90: xBorn = screenx +charSizeX/2;
                yBorn = screeny/2;
                break;
            case 135: xBorn = screenx +charSizeX/2;
                yBorn = 4*screeny/5 +charSizeX/2;
                break;
            case 180: xBorn = screenx/2;
                yBorn = screeny;
                break;
            case 225: yBorn = 4*screeny/5 +charSizeX/2;
                break;
            case 270: yBorn = screeny/2;
                break;
            case 315: yBorn = screeny/5 -charSizeX/2;
                break;
        }
        return new int[] {xBorn, yBorn};
    }

    //direction for zombie to walk
    public void directionWalk(){
        switch (angleBorn){
            case 0: directionY = screenx;
                break;
            case 45: directionX = -screenx/1.5f;
                directionY = screenx/1.5f;
                break;
            case 90: directionX = -screenx;
                break;
            case 135: directionX = -screenx/1.5f;
                directionY = -screenx/1.5f;
                break;
            case 180: directionY = -screenx;
                break;
            case 225: directionX = screenx/1.5f;
                directionY = -screenx/1.5f;
                break;
            case 270: directionX = screenx;
                break;
            case 315: directionX = screenx/1.5f;
                directionY = screenx/1.5f;
                break;
        }
    }

    public Rectangle getRectangle(){
        return body;
    }

    public int getStatusOfZombie() {
        return statusOfZombie;
    }

    public boolean isTriggerDamage() { return triggerDamage; }

    public void setStatusOfZombie(int statusOfZombie) {
        this.statusOfZombie = statusOfZombie;
    }

    public void setAuxTextures(float auxTextures) {
        this.auxTextures = auxTextures;
    }

    public void dispose(){
        for (Texture t: textures){
            t.dispose();
        }
    }
}
