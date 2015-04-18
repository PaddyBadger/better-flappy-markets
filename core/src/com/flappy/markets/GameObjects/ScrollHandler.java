package com.flappy.markets.GameObjects;

import com.flappy.markets.GameWorld.GameWorld;
import com.flappy.markets.STHelpers.AssetLoader;

public class ScrollHandler {
	
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3, pipe4, pipe5;
	private GameWorld gameWorld;
	
	public static final int SCROLL_SPEED = -59;
	public static final int PIPE_GAP = 15;
	
	public ScrollHandler(GameWorld gameWorld, float yPos) {
		this.gameWorld = gameWorld;
		frontGrass = new Grass(0, yPos + 10, 170, 5, SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), yPos + 10, 170, 5, SCROLL_SPEED);
		
		pipe1 = new Pipe(210,0,22,60,SCROLL_SPEED, yPos);
		pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
		pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe4 = new Pipe(pipe3.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe5 = new Pipe(pipe4.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);

    }
	
	public void update(float delta) {
		frontGrass.update(delta);
		backGrass.update(delta);
		pipe1.update(delta);
		pipe2.update(delta);
		pipe3.update(delta);
		
		if (pipe1.isScrolledLeft()) {
			pipe1.reset(pipe3.getTailX() + PIPE_GAP);
		} else if (pipe2.isScrolledLeft()) {
			pipe2.reset(pipe1.getTailX() + PIPE_GAP);
		} else if (pipe3.isScrolledLeft()) {
			pipe3.reset(pipe2.getTailX() + PIPE_GAP);
		} else if
	
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());
		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());
		}

	}
	
	public void stop() {
		frontGrass.stop();
		backGrass.stop();
		pipe1.stop();
		pipe2.stop();
		pipe3.stop();}
	
	public boolean collides(Bird bird) {
		if (!pipe1.isScored() && pipe1.getX() + (pipe1.getWidth() / 2) <bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe1.setScored(true);
			AssetLoader.coin.play();
		} else if (!pipe2.isScored() && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe2.setScored(true);
			AssetLoader.coin.play();
		} else if (!pipe3.isScored() && pipe3.getX() + (pipe3.getWidth() /2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe3.setScored(true);
			AssetLoader.coin.play();
		}
		
		return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
	}
	
	public void onRestart() {
		frontGrass.onRestart(0, SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
		pipe1.onRestart(210, SCROLL_SPEED);
		pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
		pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
		
	}
	
	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}
	
	public Grass getFrontGrass() {
		return frontGrass;
	}
	
	public Grass getBackGrass() {
		return backGrass;
	}
	
	public Pipe getPipe1() {
		return pipe1;
	}
	
	public Pipe getPipe2() {
		return pipe2;
	}
	public Pipe getPipe3() {
		return pipe3;
	}
	
}
