package com.flappy.markets.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.flappy.markets.GameWorld.GameRenderer;
import com.flappy.markets.GameWorld.GameWorld;
import com.flappy.markets.STHelpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;

	private float runTime;
	
	public GameScreen() {
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		int midPointY = (int) (gameHeight / 2);

		world = new GameWorld(midPointY);
		renderer = new GameRenderer(world, (int) gameHeight, midPointY);
		
		Gdx.input.setInputProcessor(new InputHandler(world));
	}

	@Override
	public void render(float delta) {
		runTime += delta;

        if(runTime > 60){
            world.currentState = GameWorld.GameState.GAMEOVER;
        }
`
		world.update(delta);
		renderer.render(runTime);
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("GameScreen - resizing");
		
	}

	@Override
	public void show() {
		System.out.println("GameScreen - show called");
		
	}

	@Override
	public void hide() {
		System.out.println("GameScreen - hide called");
		
	}

	@Override
	public void pause() {
		System.out.println("GameScreen - pause called");
		
	}

	@Override
	public void resume() {
		System.out.println("GameScreen - resume called");
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
