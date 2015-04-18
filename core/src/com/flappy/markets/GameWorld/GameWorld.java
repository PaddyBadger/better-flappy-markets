package com.flappy.markets.GameWorld;

import com.flappy.markets.GameObjects.Bird;
import com.flappy.markets.GameObjects.ScrollHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorld {
	private ScrollHandler scroller;
	private Bird bird;
    private Bird marketBird;

	private int score = 0;
	
	private GameState currentState;
	public int midPointY;
    private int k;
    private final List<BirdData> birdData;
    private final List<BirdData> marketBirdData;

    public enum GameState {
			READY, RUNNING, GAMEOVER, HIGHSCORE
		}
	
	
	public GameWorld(int midPointY) {
		currentState = GameState.READY;
		this.midPointY = midPointY;
		bird = new Bird(33, midPointY - 5, 17, 12);
        marketBird = new Bird(33, midPointY + 5, 17, 12);
		scroller = new ScrollHandler(this, midPointY + 66);

        int i = 2000;
        k = 0;
        birdData = new ArrayList<BirdData>();
        for (int j = 0; j < i; j ++) {
            birdData.add(new BirdData(20, j));
        }

        marketBirdData = new ArrayList<BirdData>();
        for (int j = 0; j < i; j ++) {
            marketBirdData.add(new BirdData(20, j));
        }

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

        k++;
		
		bird.update(delta, (int) birdData.get(k).x, (int) birdData.get(k).y);
        marketBird.update(delta, (int) marketBirdData.get(k).x, (int) birdData.get(k).y);
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

    private class BirdData {
        double x, y;
        private Random rand;

        private BirdData(int x, int y) {
            this.x = x;
            this.y = randInt(30, 50);
        }

        public int randInt(int min, int max) {
            rand = new Random();
            int randomNum = rand.nextInt((max - min) + 1) + min;
            return randomNum;
        }

    }

}
