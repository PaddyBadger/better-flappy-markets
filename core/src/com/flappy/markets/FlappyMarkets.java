package com.flappy.markets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappy.markets.STHelpers.AssetLoader;
import com.flappy.markets.Screens.GameScreen;

public class FlappyMarkets extends Game {
	SpriteBatch batch;
	Texture img;

    @Override
    public void create() {
        System.out.println("STGame Created!");
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
