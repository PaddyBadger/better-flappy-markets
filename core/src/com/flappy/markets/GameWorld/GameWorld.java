package com.flappy.markets.GameWorld;

import com.flappy.markets.GameObjects.Bird;
import com.flappy.markets.GameObjects.ScrollHandler;

public class GameWorld {
	private ScrollHandler scroller;
	private Bird bird;
    private Bird marketBird;

	private int score = 0;

	private GameState currentState;
	public int midPointY;

    public enum GameState {
			READY, RUNNING, GAMEOVER, HIGHSCORE
		}


	public GameWorld(int midPointY) {
		currentState = GameState.READY;
		this.midPointY = midPointY;
		bird = new Bird(33, midPointY - 5, 17, 12);
        marketBird = new Bird(33, midPointY + 5, 17, 12);
		scroller = new ScrollHandler(this, midPointY + 66);
    }

	public void update(float delta) {
		switch (currentState) {
		case READY:
			updateReady(delta);
			break;

		case RUNNING:
			default:
				updateRunning(delta);
				break;
		}
	}

	private void updateReady(float delta) {

	}

	public void updateRunning(float delta) {

		if (delta > .05f) {
			delta = .05f;
		}

		bird.update(delta, 50, midPointY);
     //   marketBird.update(delta);
		scroller.update(delta);
	}

	public Bird getBird() {
		return bird;
	}

    public Bird getMarketBird() {
        return marketBird;
    }

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void restart() {
		currentState = GameState.READY;
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		currentState = GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}
}
