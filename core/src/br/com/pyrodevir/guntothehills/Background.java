package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static br.com.pyrodevir.guntothehills.Constants.backgroundVel;
import static br.com.pyrodevir.guntothehills.Constants.screenx;
import static br.com.pyrodevir.guntothehills.Constants.screeny;

public class Background {
    private Texture texture1;
    private Texture texture2;
    private float posx1;
    private float posx2;
    private int levelCheck = 0;
    private int levelNext = 1;

    public Background(){
        texture1 = new Texture("background/bg"+levelCheck+".png");
        texture2 = new Texture("background/bg"+levelNext+".png");

        posx1 = 0;
        posx2 = screenx*1.2f;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture1, posx1, 0, screenx*1.2f, screeny);
        batch.draw(texture2, posx2, 0, screenx*1.2f, screeny);
    }

    //update triggered while playmodeRunning is on.
    public boolean update(float time, int level){
        if (levelCheck < level) {
            posx1 += time * backgroundVel;
            posx2 += time * backgroundVel;

            if (posx1 + screenx*1.2f <= 0) {
                posx1 = screenx*1.2f;
                posx2 = 0;
                levelCheck++;
                levelNext++;
                texture1.dispose();
                if(levelNext <= 10){
                    texture1 = new Texture("background/bg"+levelNext+".png");
                }
                return true;
            }
            if (posx2 + screenx*1.2f <= 0) {
                posx2 = screenx*1.2f;
                posx1 = 0;
                levelCheck++;
                levelNext++;
                texture2.dispose();
                if(levelNext <= 10) {
                    texture2 = new Texture("background/bg" + levelNext + ".png");
                }
                return true;
            }
        }
        return false;
    }

    public void dispose(){
        texture1.dispose();
        texture2.dispose();
    }
}

