package com.flappy.markets.GameWorld;

import com.badlogic.gdx.math.Rectangle;
import com.flappy.markets.GameObjects.Bird;
import com.flappy.markets.GameObjects.ScrollHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorld {
    private final ArrayList<BirdData> marketBirdData;

    private int k;

    private ScrollHandler scroller;

    private CameraMan cameraMan;

	private Bird bird;
    private Bird marketBird;

    List<Bird> birds = new ArrayList<Bird>();

    public final static int MIN_BIRD_SPREAD = 15; // m - We don't zoom any closer than as if the birds were N meters apart

    public List<Bird> getBirds() {
        return birds;
    }

    private Rectangle ground;

	private int score = 0;

	private GameState currentState;
	public int midPointY;

    public enum GameState {
			READY, RUNNING, GAMEOVER, HIGHSCORE
		}


	public GameWorld(int midPointY) {
		currentState = GameState.READY;
		this.midPointY = midPointY;
		bird = new Bird(6, midPointY - 5, 17, 12);
        marketBird = new Bird(6, midPointY + 5, 17, 12);
		scroller = new ScrollHandler(this, midPointY + 66);

        int i = 2000;
        k = 0;

        marketBirdData = new ArrayList<BirdData>();
        for (int j = 0; j < i; j ++) {
            marketBirdData.add(new BirdData(20, j));
        }

        this.birds.add(marketBird);
        this.birds.add(bird);

        // cameraman needs birds
        this.cameraMan = new CameraMan(this);
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

		bird.bouncyUpdate(delta);
        marketBird.slowUpdate(delta);
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

    public CameraMan getCameraMan() {
        return cameraMan;
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

    public float getBirdSpread(){
        float spread = maxBirdAltitude() - minBirdAltitude();
        return Math.max(MIN_BIRD_SPREAD, spread);
    }

    public float maxBirdAltitude(){
        float max = birds.get(0).getY();

        for(Bird b : birds){
            if(b.getY() > max){
                max = b.getY();
            }
        }
        return max;
    }

    public float minBirdAltitude(){
        float min = birds.get(0).getY();

        for(Bird b : birds){
            if(b.getY() < min){
                min = b.getY();
            }
        }
        return min;
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
