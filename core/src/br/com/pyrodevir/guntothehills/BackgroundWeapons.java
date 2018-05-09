package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static br.com.pyrodevir.guntothehills.Constants.charSizeX;
import static br.com.pyrodevir.guntothehills.Constants.screenx;

public class BackgroundWeapons {
    private Texture bgWeapons;

    public BackgroundWeapons(){
        bgWeapons = new Texture("background/weaponbg1.png");
    }

    public void draw(SpriteBatch batch){
        batch.draw(bgWeapons, 0, 0, screenx, charSizeX);
    }

    public void dispose(){
        bgWeapons.dispose();
    }
}
