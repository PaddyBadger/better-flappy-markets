package com.flappy.markets.GameObjects;

import com.flappy.markets.GameWorld.GameWorld;

public class ScrollHandler {
	
	private Backdrop backdrop1, backdrop2;
	private Building building1, building2, building3, building4, building5;
	private GameWorld gameWorld;
    private Cloud cloud1, cloud2, cloud3;
    private Poop poop1, poop2, poop3, poop4, poop5, poop6, poop7, poop8, poop9, poop10;
	
	public static final int SCROLL_SPEED = -59;
    public static final int CLOUD_SCROLL_SPEED = -30;
	public static final int BUILDING_GAP = 25;
    public static final int CLOUD_GAP = 30;
	
	public ScrollHandler(GameWorld gameWorld, float yPos) {
		this.gameWorld = gameWorld;
		backdrop1 = new Backdrop(0, yPos - 10, 170, 130, CLOUD_SCROLL_SPEED);
		backdrop2 = new Backdrop(backdrop1.getTailX(), yPos - 10, 170, 130, CLOUD_SCROLL_SPEED);
		
		building1 = new Building(210, 0, 22, 120,SCROLL_SPEED, yPos);
		building2 = new Building(building1.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);
		building3 = new Building(building2.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);
        building4 = new Building(building3.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);
        building5 = new Building(building4.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);

        cloud1 = new Cloud(220, 0, 100, 30, CLOUD_SCROLL_SPEED);
        cloud2 = new Cloud(cloud1.getTailX() + CLOUD_GAP, 10, 60, 20, CLOUD_SCROLL_SPEED);
        cloud3 = new Cloud(cloud2.getTailX() + CLOUD_GAP, 15, 80, 35, CLOUD_SCROLL_SPEED);

//        poop1 = new Poop();
//        poop2 = new Poop();
//        poop3 = new Poop();
//        poop4 = new Poop();
//        poop5 = new Poop();
//        poop6 = new Poop();
//        poop7 = new Poop();
//        poop8 = new Poop();
//        poop9 = new Poop();
//        poop10 = new Poop();
    }
	
	public void update(float delta) {
		backdrop1.update(delta);
		backdrop2.update(delta);
		building1.update(delta);
		building2.update(delta);
		building3.update(delta);
        building4.update(delta);
        building5.update(delta);
        cloud1.update(delta);
        cloud2.update(delta);
        cloud3.update(delta);
		
		if (building1.isScrolledLeft()) {
			building1.reset(building5.getTailX() + BUILDING_GAP);
		} else if (building2.isScrolledLeft()) {
			building2.reset(building1.getTailX() + BUILDING_GAP);
		} else if (building3.isScrolledLeft()) {
			building3.reset(building2.getTailX() + BUILDING_GAP);
		} else if (building4.isScrolledLeft()) {
            building4.reset(building3.getTailX() + BUILDING_GAP);
        } else if (building5.isScrolledLeft()) {
            building5.reset(building4.getTailX() + BUILDING_GAP);
        }

        if (cloud1.isScrolledLeft()) {
            cloud1.reset(cloud3.getTailX() + CLOUD_GAP);
        } else if (cloud2.isScrolledLeft()) {
            cloud2.reset(cloud1.getTailX() + CLOUD_GAP);
        } else if (cloud3.isScrolledLeft()) {
            cloud3.reset(cloud2.getTailX() + CLOUD_GAP);
        }
	
		if (backdrop1.isScrolledLeft()) {
			backdrop1.reset(backdrop2.getTailX());
		} else if (backdrop2.isScrolledLeft()) {
			backdrop2.reset(backdrop1.getTailX());
		}



	}
	
	public void stop() {
		backdrop1.stop();
		backdrop2.stop();
		building1.stop();
		building2.stop();
		building3.stop();
        building4.stop();
        building5.stop();
        cloud1.stop();
        cloud2.stop();
        cloud3.stop();

    }

//TODO: figure out scoring / market diff @ these points
//	public boolean collides(Bird bird) {
//		if (!pipe1.isScored() && pipe1.getX() + (pipe1.getWidth() / 2) <bird.getX() + bird.getWidth()) {
//			addScore(1);
//			pipe1.setScored(true);
//			AssetLoader.coin.play();
//		} else if (!pipe2.isScored() && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX() + bird.getWidth()) {
//			addScore(1);
//			pipe2.setScored(true);
//			AssetLoader.coin.play();
//		} else if (!pipe3.isScored() && pipe3.getX() + (pipe3.getWidth() /2) < bird.getX() + bird.getWidth()) {
//			addScore(1);
//			pipe3.setScored(true);
//			AssetLoader.coin.play();
//		} else if (!pipe4.isScored() && pipe4.getX() + (pipe4.getWidth() /2) < bird.getX() + bird.getWidth()) {
//            addScore(1);
//            pipe4.setScored(true);
//            AssetLoader.coin.play();
//        } else if (!pipe5.isScored() && pipe5.getX() + (pipe5.getWidth() /2) < bird.getX() + bird.getWidth()) {
//            addScore(1);
//            pipe5.setScored(true);
//            AssetLoader.coin.play();
//        }
//
//		return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
//	}
	
	public void onRestart() {
		backdrop1.onRestart(0, SCROLL_SPEED);
		backdrop2.onRestart(backdrop1.getTailX(), SCROLL_SPEED);
		building1.onRestart(210, SCROLL_SPEED);
		building2.onRestart(building1.getTailX() + BUILDING_GAP, SCROLL_SPEED);
		building3.onRestart(building2.getTailX() + BUILDING_GAP, SCROLL_SPEED);
        building4.onRestart(building3.getTailX() + BUILDING_GAP, SCROLL_SPEED);
        building5.onRestart(building4.getTailX() + BUILDING_GAP, SCROLL_SPEED);
        cloud1.onRestart(220, CLOUD_SCROLL_SPEED);
        cloud2.onRestart(cloud1.getTailX() + CLOUD_GAP, CLOUD_SCROLL_SPEED);
        cloud3.onRestart(cloud2.getTailX() + CLOUD_GAP, CLOUD_SCROLL_SPEED);
		
	}
	
	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}
	
	public Backdrop getBackdrop1() {
		return backdrop1;
	}
	public Backdrop getBackdrop2() {
		return backdrop2;
	}
	
	public Building getBuilding1() {
		return building1;
	}
	public Building getBuilding2() {
		return building2;
	}
	public Building getBuilding3() {
		return building3;
	}
    public Building getBuilding4() { return building4; }
    public Building getBuilding5() { return building5; }

    public Cloud getCloud1() { return cloud1; }
    public Cloud getCloud2() { return cloud2; }
    public Cloud getCloud3() { return cloud3; }
}
