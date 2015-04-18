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
	public static TextureRegion grass;
	
	public static Animation birdAnimation;
	public static TextureRegion bird, birdDown, birdUp;
	
	public static TextureRegion skullUp, skullDown, bar;
	
	public static Sound dead, flap, coin;
	
	public static BitmapFont font, shadow;
	
	public static Preferences prefs;
	
	public static void load() {
		
		prefs = Gdx.app.getPreferences("SwervyTaxi");
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
		
		texture = new Texture(Gdx.files.internal("data/st.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		grass = new TextureRegion(texture, 0, 33, 170, 5);
		grass.flip(false,  true);
		
		birdDown = new TextureRegion(texture, 0, 0, 54, 29);
		birdDown.flip(false,  true);
		
		bird = new TextureRegion(texture, 0, 1, 54, 29);
		bird.flip(false,  true);
		
		birdUp = new TextureRegion(texture, 0, 0, 54, 29);
		birdUp.flip(false,  true);
		
		TextureRegion[] birds = { birdDown, bird, birdUp };
		birdAnimation = new Animation(0.06f, birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		skullUp = new TextureRegion(texture, 152, 0, 22, 12);
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false,  true);
		
		bar = new TextureRegion(texture, 152, 0, 22, 30);
		bar.flip(false,  true);
		
		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
		
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);
		
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
