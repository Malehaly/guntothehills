package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static br.com.pyrodevir.guntothehills.Constants.charSizeX;
import static br.com.pyrodevir.guntothehills.Constants.screenx;

public class Weapon {
    private Texture texture;
    private Texture texture2;
    private Rectangle weaponRect;
    private int weaponType; //1:pistol, 2:machinegun, 3:shotgun, 4:flamethrower;
    private int ammo;
    private boolean selected = false;

    public Weapon(int weaponType, int ammo){
        texture = new Texture("weapon/weapon"+weaponType+".png");
        texture2 = new Texture("background/weaponbg2.png");
        float x = 0;
        switch (weaponType){
            case 1 : x = 0.125f*screenx; //pistol (x=2,5 - 1,25)
                break;
            case 2 : x = 0.375f*screenx; //machinegun (x=5 - 1,25)
                break;
            case 3 : x = 0.625f*screenx; //shotgun
                break;
            case 4 : x = 0.875f*screenx; //flamethrower
                break;
        }
        weaponRect = new Rectangle(x-charSizeX/2, 0, charSizeX, charSizeX);

        this.ammo = ammo;
        this.weaponType = weaponType;
        if (weaponType == 1){
            selected = true;
        }
    }

    public void draw(SpriteBatch batch){
        if (!selected){
            batch.draw(texture2, weaponRect.x, weaponRect.y, charSizeX, charSizeX);
            batch.draw(texture, weaponRect.x, weaponRect.y, charSizeX, charSizeX);
        } else {
            batch.draw(texture2, weaponRect.x-(0.15f*charSizeX), weaponRect.y-(0.15f*charSizeX), charSizeX *1.3f, charSizeX *1.3f);
            batch.draw(texture, weaponRect.x-(0.15f*charSizeX), weaponRect.y-(0.15f*charSizeX), charSizeX *1.3f, charSizeX *1.3f);
        }
    }

    public boolean isClicked(int x, int y){
        if ((ammo>0 || weaponType==1) && x>=weaponRect.x && x<=weaponRect.x+weaponRect.getWidth() && y<= charSizeX){
            return true;
        } else return false;
        //TODO sem ammo deixar cinza
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getWeaponType() { return weaponType; }

    public void setAmmo(int ammo) {        this.ammo = ammo;    }

    public void decreaseAmmo(int ammo) {        this.ammo -= ammo;    }

    public int getAmmo() {        return ammo;    }

    public void dispose(){
        texture.dispose();
        texture2.dispose();
    }
}
