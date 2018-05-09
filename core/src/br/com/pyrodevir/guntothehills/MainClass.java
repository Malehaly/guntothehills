package br.com.pyrodevir.guntothehills;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static br.com.pyrodevir.guntothehills.Constants.charSizeX;
import static br.com.pyrodevir.guntothehills.Constants.loadTextures;
import static br.com.pyrodevir.guntothehills.Constants.screenx;
import static br.com.pyrodevir.guntothehills.Constants.screeny;

public class MainClass extends ApplicationAdapter {

    //Achievements ideas:
    //kill 500 zombies; kill 1.000 zombies; kill 10.000 zombies;
    //won without miss bullets;
    //Gun to the Hills - finished the normal game;
    //Gladiator - won with 1 hp remaining;
    //Pro - won without losing life;
    //Noob - died one time;
    //Kid with fire issues - won without using flame;
    //Savage - won only using pistol;
    //Liked the easy way - won and gather more than 70% of baloons;
    //Map-Catcher - catch all maps;
    //Rambo - obtain the machine-gun; (350gold)
    //"Input Any FPS game here" - catch the shotgun; (350gold)
    //"akame ga kill char" - catch the flamethrower; (1100gold) - tip: botar vídeo em Ad pra ganhar 200gold
    //Help the Dev to buy a underwear - bought something from the store;
	private SpriteBatch batch;
    private Background bg;
    private BackgroundWeapons bgw;
    private Character character;
    private List<Enemy> enemies;
    private List<Weapon> weapons;
    private LinkedList<Bullet> bullets;
    private LinkedList<Flame> flames;
    private int lifePoints = 3;
    private int powerPoints = 3;
    private int goldPoints = 0;
    private int horde = 0;
    private Texture iconHorde;
    private Texture iconPoints;
    private String mode = "Play Mode Running"; //intro-game?, menu, play mode(normal, running, getReward, dead, winner), arena mode, rank, store, quit
    //play mode - Play the normal game, unlock new cenarios, weapons and achievements. Arm yourself and defeat the hordes of zombies while
    //you run to a safe place.
    //arena mode - Start with your achieved weapons and battle for the top of the rank with the highest level tier.
    //ranking - show ranks, achievements.
    //store - buy new skins (clothes, hud-skins), zombie-skins - can be bought with IRL money;

    private int mapLevel = 0; //0-intro, 1: hotel; lv2: street... muda sprite do bg, character, enemies, armas que vem, hp e pw recuperados..
    //private float counter = 5f;
    private float[] mapLevelTrigger = new float[]{0.8f, 0.65f, 0.55f, 0.45f, 0.35f, 0.30f, 0.25f, 0.25f, 0.2f, 0.2f};
    private int[] mapLevelHorde = new int[]{50,100,150,200,250,300,350,400,450,550};
    private float enemyTrigger = 1f;
    private int[] enemyAngle = new int[]{0,45,90,135,180,225,270,315};
    private int enemyAngleStore = 8;
    private BitmapFont font, fontPoints;
    private GlyphLayout layout = new GlyphLayout();
    private String[][] language; //English -> Play Mode, Arena, Choose One:,...
    //Portuguese -> Jogar, Modo Arena, Escolha um:,...

    //TODO por método para criar uns baloezinhos que da um bonus ao matar o zumbi com o icone do balao.
    // ex.: matar com pistola-> tiros infinitos por alguns segundos


	@Override
	public void create () {
        batch = new SpriteBatch();
        loadTextures = new ArrayList<Texture>();
        loadTextures.add(new Texture("character/character1.png"));  //0
        loadTextures.add(new Texture("character/character2.png"));
        loadTextures.add(new Texture("character/character3.png"));
        loadTextures.add(new Texture("weapon/bullet1.png"));    //3
        loadTextures.add(new Texture("zombies/zombie1.1.png")); //4
        loadTextures.add(new Texture("zombies/zombie1.2.png"));
        loadTextures.add(new Texture("zombies/zombie1.3.png"));
        loadTextures.add(new Texture("zombies/zombie1.4.png"));
        loadTextures.add(new Texture("zombies/zombie1.5.png")); //8
        loadTextures.add(new Texture("zombies/zombie1.6.png"));
        loadTextures.add(new Texture("zombies/zombie1.7.png"));
        loadTextures.add(new Texture("zombies/zombie1.8.png")); //11
        loadTextures.add(new Texture("zombies/zombie1.9.png"));
        loadTextures.add(new Texture("zombies/zombie1.10.png"));
        loadTextures.add(new Texture("weapon/flame1.png"));     //14
        loadTextures.add(new Texture("background/iconheart1.png")); //15
        loadTextures.add(new Texture("background/iconheart2.png"));
        loadTextures.add(new Texture("background/iconpower1.png")); //17
        loadTextures.add(new Texture("background/iconpower2.png"));

        bg = new Background();
        bgw = new BackgroundWeapons();
        character = new Character();
        weapons = new ArrayList<Weapon>();
        weapons.add(new Weapon(1, 0));
        weapons.add(new Weapon(2, 40*powerPoints));
        weapons.add(new Weapon(3, 15*powerPoints));
        weapons.add(new Weapon(4, 5*powerPoints));
        bullets = new LinkedList<Bullet>();
        flames = new LinkedList<Flame>();
        enemies = new ArrayList<Enemy>();
        iconHorde = new Texture("background/iconhorde1.png");
        iconPoints = new Texture("background/iconpoints1.png");
        fontConfiguration();
	}

	@Override
	public void render () {
        input();
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		draw();
		batch.end();
	}

    private void draw(){
        if (mode.equals("Menu")){ // ||mode equals rank, store, quit
            bg.draw(batch);
        } else if (mode.contains("Play Mode" ) || mode.equals("Arena Mode")) {
            bg.draw(batch);
            for (Enemy e : enemies) {
                e.draw(batch);
            }
            for (Bullet b : bullets) {
                b.draw(batch);
            }
            for (Flame f : flames) {
                f.draw(batch);
            }
            bgw.draw(batch);
            for (int i = 0; i < 5; i++){
                batch.draw(loadTextures.get(16), screenx*0.4f - screenx*0.1f*i, charSizeX*1.05f, charSizeX/2, charSizeX/2);
                batch.draw(loadTextures.get(18), screenx*0.95f - screenx*0.05f*i, charSizeX*1.05f, charSizeX/4, charSizeX/2);
                batch.draw(loadTextures.get(18), screenx*0.7f - screenx*0.05f*i, charSizeX*1.05f, charSizeX/4, charSizeX/2);
            }
            for (int i = 0; i < lifePoints; i++){
                batch.draw(loadTextures.get(15), 0 + screenx*0.1f*i, charSizeX*1.05f, charSizeX/2, charSizeX/2);
            }
            for (int i = 0; i < powerPoints; i++){
                batch.draw(loadTextures.get(17), screenx - screenx*0.05f*(i+1), charSizeX*1.05f, charSizeX/4, charSizeX/2);
            }
            for (Weapon w : weapons) {
                w.draw(batch);
                if (w.getWeaponType() != 1){
                    fontPoints.draw(batch, String.valueOf(w.getAmmo()), (0.175f*screenx + 0.25f*screenx*(w.getWeaponType()-1) - layoutWidth(fontPoints, String.valueOf(w.getAmmo()))/2), (screeny * 0.02f));
                }
            }
            character.draw(batch);
            batch.draw(iconHorde, 0, screeny * 0.93f, 0.31f * screenx, 0.07f * screeny);
            batch.draw(iconPoints, screenx * 0.69f, screeny * 0.93f, 0.31f * screenx, 0.07f * screeny);
            font.draw(batch, String.valueOf(horde), (screenx * 0.29f - layoutWidth(font, String.valueOf(horde))), (screeny * 0.984f));
            fontPoints.draw(batch, String.valueOf(goldPoints), (screenx * 0.98f - layoutWidth(fontPoints, String.valueOf(goldPoints))), (screeny * 0.975f));
        }
    }

    private void update(float time) {
        if (mode.contains("Play Mode") || mode.equals("Arena Mode")) {
            //char update and shot trigger
            //if update returns true, a bullet is created on the direction that char is moving on or facing
            if (character.update(time)) {
                for (Weapon w : weapons) {
                    if (w.isSelected()) {
                        switch (w.getWeaponType()) {
                            case 1:
                                bullets.add(new Bullet(character.getRotation()));
                                break;
                            case 2:
                                bullets.add(new Bullet(character.getRotation(), 30));
                                bullets.add(new Bullet(character.getRotation(), 30));
                                w.setAmmo(w.getAmmo()-2);
                                break;
                            case 3:
                                bullets.add(new Bullet(character.getRotation()));
                                bullets.add(new Bullet(character.getRotation() - 45));
                                bullets.add(new Bullet(character.getRotation() + 45));
                                w.setAmmo(w.getAmmo()-1);
                                break;
                            case 4:
                                flames.add(new Flame(character.getRotation()));
                                w.setAmmo(w.getAmmo()-1);
                                break;
                        }
                        if (w.getAmmo()<=0){
                            w.setSelected(false);
                            weapons.get(0).setSelected(true);
                        }
                    }
                }
                character.setShotTrigger(false);
            }

            //collision bullets vs zombies, or bullet trespass the screen;
            for (int i = 0; i < bullets.size(); i++) {
                if (bullets.get(i).update(time)) {
                    bullets.remove(i);
                    //TODO por conquista caso nenhuma bala seja perdida
                    i--;
                }
                if (i < 0) {
                    break;
                }
                for (Enemy e : enemies) {
                    if (i < 0) {
                        break;
                    }
                    if (Intersector.overlaps(bullets.get(i).getBulletRect(), e.getRectangle()) &&
                            e.getStatusOfZombie() < 2) {
                        e.setAuxTextures(0);
                        e.setStatusOfZombie(2); //zombie dying
                        horde--;
                        goldPoints++;
                        bullets.remove(i);
                        i--;
                    }
                }
            }

            //collision flame vs zombies, or flame finish frames;
            for (int i = 0; i < flames.size(); i++) {
                if (flames.get(i).update(time)) {
                    flames.remove(i);
                    i--;
                }
                if (i < 0) {
                    break;
                }
                for (Enemy e : enemies) {
                    if (i < 0) {
                        break;
                    }
                    if (Intersector.overlaps(flames.get(i).getFlameCollider(), e.getRectangle()) &&
                            e.getStatusOfZombie() < 2) {
                        e.setAuxTextures(0);
                        e.setStatusOfZombie(2); //zombie dying
                        horde--;
                        goldPoints++;
                    }
                }
            }

            //create zombies and map trigger
            //TODO por outros monstros
            if (horde > 0) {
                enemyTrigger -= time;
                if (enemyTrigger < 0) {
                    Random random = new Random();
                    int i = random.nextInt(8);
                    //disable the creation of a zombie on the same spot for a minor time
                    if (i == enemyAngleStore) {
                        if (i == 0 || i == 7) {
                            i = random.nextInt(6) + 1;
                        } else {
                            i++;
                        }
                    }
                    enemies.add(new Enemy(enemyAngle[i]));
                    enemyTrigger = mapLevelTrigger[mapLevel-1];
                    enemyAngleStore = i;
                }
                //TODO por modo playmodeReaward - assim pega as coisas do menuzinho e continua
            } else if (horde <= 0 && enemies.isEmpty() && !mode.equals("Play Mode Running")){
                mapLevel++;
                if(mapLevel < 11){
                    mode = "Play Mode Running";
                } else {
                    mode = "Play Mode Winner";
                }
            }

            //update of zombies
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).update(time)) { //zombie walking
                    if (enemies.get(i).getStatusOfZombie() == 0) {
                        if (Intersector.overlaps(character.getRectangle(), enemies.get(i).getRectangle())) {
                            enemies.get(i).setAuxTextures(0);
                            enemies.get(i).setStatusOfZombie(1); //zombie attacking
                        }
                    } else if (enemies.get(i).getStatusOfZombie() == 1 && enemies.get(i).isTriggerDamage() ) {
                        /*auxDamage -= time;
                        if (auxDamage <= 0) {
                            //TODO som "creck" ao perder vida
                            lifePoints--;
                            auxDamage = 1f;
                        }*/
                        lifePoints--;
                    }
                } else if (!enemies.get(i).update(time)) {
                    enemies.remove(i);
                }
            }
            if (lifePoints<=0){
                //modo vira -Play Mode Dead
                //TODO lose game - muda modo + tela cinza e um "You lose" em vermelho com sangue.
            }

            //change level, background and character sprite
            if (mode.equals("Play Mode Running")){
                //character.update(time); assim que arrumar sprites corrida
                if (mapLevel == 0){
                    mapLevel++;
                }
                if (bg.update(time, mapLevel)){
                    horde = mapLevelHorde[mapLevel-1];
                    mode = "Play Mode";
                }
            }
        }
    }

    private void input(){
        if (mode.equals("Menu")){ // ||mode equals rank, store, quit
            //TODO input para menu
        } else if (mode.equals("Play Mode" ) || mode.equals("Arena Mode")) {
            if (Gdx.input.justTouched()) {
                boolean touchedWeapon = false;
                int x = Gdx.input.getX();
                int y = screeny - Gdx.input.getY();

                //when weapon is selected, all others as disabled.
                for (Weapon w : weapons) {
                    if (w.isClicked(x, y)) {
                        touchedWeapon = true;
                        for (Weapon wn : weapons) {
                            wn.setSelected(false);
                        }
                        w.setSelected(true);
                        //TODO som - armas
                    }
                }
                if (!touchedWeapon) {
                    character.isPressed(x, y);
                }
            }
        }
    }

    //aux of create;
    private void fontConfiguration(){
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) (0.06f * screeny);
        parameter.color = new Color(Color.WHITE);
        font = generator.generateFont(parameter);

        parameter.size = (int) (0.03f * screeny);
        parameter.color = new Color(Color.GOLDENROD);
        fontPoints = generator.generateFont(parameter);

        generator.dispose();
    }

    //aux of font - auto-adjust on draw;
    private float layoutWidth(BitmapFont font, String text) {
        layout.reset();
        layout.setText(font, text);
        return layout.width;
    }

	@Override
	public void dispose () {
        bg.dispose();
        bgw.dispose();
        for (Enemy e:enemies){
            e.dispose();
        }
        for (Bullet b:bullets){
            b.dispose();
        }
        for (Flame f:flames){
            f.dispose();
        }
        for (Weapon w:weapons){
            w.dispose();
        }
        character.dispose();
        iconHorde.dispose();
        iconPoints.dispose();
        for (Texture t:loadTextures){
            t.dispose();
        }
        font.dispose();
        fontPoints.dispose();
		batch.dispose();
	}
}
