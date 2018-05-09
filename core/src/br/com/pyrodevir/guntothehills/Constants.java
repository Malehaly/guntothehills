package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class Constants {
    public static int screenx = Gdx.graphics.getWidth();
    public static int screeny = Gdx.graphics.getHeight();

    public static List<Texture> loadTextures;

    public static float backgroundVel = -0.2f*screenx;

    public static int charSizeX = (int)(0.2*screenx);
}
