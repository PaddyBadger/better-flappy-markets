package com.flappy.markets.STHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	
	public static Texture texture;
    public static Texture background;
    public static Texture buildings;
	public static TextureRegion grass;
	
	public static Animation birdAnimation;
	public static TextureRegion bird, birdDown, birdUp;
    public static TextureRegion marketBird, marketBirdDown, marketBirdUp;
	
	public static TextureRegion bar;

    public static TextureRegion cloud;

    public static TextureRegion backgroundTexture;

    public static TextureRegion building1, building2, building3, building4;

    public static TextureRegion smallPoop, bigPoop;
	
	public static Sound dead, flap, coin;
	
	public static BitmapFont font, shadow;
	
	public static Preferences prefs;
	
	public static void load() {
		
		prefs = Gdx.app.getPreferences("SwervyTaxi");
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
		
		texture = new Texture(Gdx.files.internal("data/flappymarkets.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		grass = new TextureRegion(texture, 0, 33, 170, 5);
		grass.flip(false,  true);
		
		birdDown = new TextureRegion(texture, 0, 100, 245, 200);
		birdDown.flip(false,  true);
		
		bird = new TextureRegion(texture, 300, 100, 245, 200);
		bird.flip(false,  true);
		
		birdUp = new TextureRegion(texture, 600, 100, 245, 200);
		birdUp.flip(false,  true);
		
		TextureRegion[] birds = { birdDown, bird, birdUp };
		birdAnimation = new Animation(0.06f, birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        cloud = new TextureRegion(texture, 0, 300, 600, 300);
        cloud.flip(false, true);


		bar = new TextureRegion(texture, 152, 0, 22, 30);
		bar.flip(false,  true);
		
		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
		
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

        background = new Texture(Gdx.files.internal("data/backgroundsunset.png"));
        background.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        backgroundTexture = new TextureRegion(background, 0, 200, 500, 575);
        backgroundTexture.flip(false, true);

        buildings = new Texture((Gdx.files.internal("data/buildings.png")));
        buildings.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        building1 = new TextureRegion(buildings, 0, 0, 80, 580);
        building1.flip(false,  true);

        building2 = new TextureRegion(buildings, 80, 0, 80, 580);
        building2.flip(false,  true);

        building3 = new TextureRegion(buildings, 160, 0, 100, 580);
        building3.flip(false,  true);

        building4 = new TextureRegion(buildings, 260, 0, 80, 580);
        building4.flip(false,  true);
	}
	
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}
	
	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}
	
	public static void dispose() {
		texture.dispose();
		
		dead.dispose();
		flap.dispose();
		coin.dispose();
		font.dispose();
		shadow.dispose();
	}
}
