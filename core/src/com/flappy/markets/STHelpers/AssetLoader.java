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
    public static Texture marketbirdTexture;

	public static Animation birdAnimation;
    public static Animation marketBirdAnimation;

    public static TextureRegion bird, birdDown, birdUp;
    public static TextureRegion marketBird, marketBirdDown, marketBirdUp;
	
	public static TextureRegion bar;

    public static TextureRegion cloud;

    public static TextureRegion backgroundTexture;

    public static TextureRegion building1, building2, building3, building4, buildingBackdrop;

    public static TextureRegion smallPoop, bigPoop;
	
	public static Sound dead, flap, coin, ending;
	
	public static BitmapFont font, shadow;
	
	public static Preferences prefs;
	
	public static void load() {
		
		prefs = Gdx.app.getPreferences("SwervyTaxi");
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}

        makeBirdAndCloudsAndPoop();

		bar = new TextureRegion(texture, 152, 0, 22, 30);
		bar.flip(false,  true);
		
		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        ending = Gdx.audio.newSound(Gdx.files.internal("data/GameMusicWithEnding.wav"));
		
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
		
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

        makeBackground();
        makeBuildings();
        makeMarketBird();
	}

    private static void makeMarketBird() {
        marketbirdTexture = new Texture(Gdx.files.internal("data/marketbarry.png"));
        marketbirdTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        marketBirdDown = new TextureRegion(marketbirdTexture, 300, 0, 245, 200);
        marketBirdDown.flip(false, true);

        marketBird = new TextureRegion(marketbirdTexture, 0, 0, 245, 200);
        marketBird.flip(false, true);

        marketBirdUp = new TextureRegion(marketbirdTexture, 600, 0, 245, 200);
        marketBirdUp.flip(false, true);

        TextureRegion[] birds = { marketBirdDown, marketBird, marketBirdUp };
        marketBirdAnimation = new Animation(0.06f, birds);
        marketBirdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    private static void makeBuildings() {
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

        buildingBackdrop = new TextureRegion(buildings, 340, 120, 740, 460);
        buildingBackdrop.flip(false, true);
    }

    private static void makeBackground() {
        background = new Texture(Gdx.files.internal("data/backgroundsunset.png"));
        background.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        backgroundTexture = new TextureRegion(background, 0, 200, 500, 575);
        backgroundTexture.flip(false, true);
    }

    private static void makeBirdAndCloudsAndPoop() {
        texture = new Texture(Gdx.files.internal("data/flappymarkets.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

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

        bigPoop = new TextureRegion(texture, 0, 0, 50, 50);
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
        ending.dispose();
		font.dispose();
		shadow.dispose();
	}
}
