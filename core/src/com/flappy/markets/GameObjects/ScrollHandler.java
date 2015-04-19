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

//        building6 = new Building(building5.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);
//        building7 = new Building(building6.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);
//        building8 = new Building(building7.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);
//        building9 = new Building(building8.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);
//        building10 = new Building(building9.getTailX() + BUILDING_GAP, 0, 22, 120, SCROLL_SPEED, yPos);

        cloud1 = new Cloud(220, 0, 100, 30, CLOUD_SCROLL_SPEED);
        cloud2 = new Cloud(cloud1.getTailX() + CLOUD_GAP, 10, 60, 20, CLOUD_SCROLL_SPEED);
        cloud3 = new Cloud(cloud2.getTailX() + CLOUD_GAP, 15, 80, 35, CLOUD_SCROLL_SPEED);

//        poop1 = new Poop(0, 0, 5, 5, SCROLL_SPEED);
//        poop2 = new Poop(poop1.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop3 = new Poop(poop2.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop4 = new Poop(poop3.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop5 = new Poop(poop4.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop6 = new Poop(poop5.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop7 = new Poop(poop6.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop8 = new Poop(poop7.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop9 = new Poop(poop8.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
//        poop10 = new Poop(poop9.getTailX() + POOP_GAP, 0, 3, 3, SCROLL_SPEED);
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
//        poop1.update(delta);
//        poop2.update(delta);
//        poop3.update(delta);
//        poop4.update(delta);
//        poop5.update(delta);
//        poop6.update(delta);
//        poop7.update(delta);
//        poop8.update(delta);
//        poop9.update(delta);
//        poop10.update(delta);

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
//        else if (building6.isScrolledLeft()) {
//            building6.reset(building5.getTailX() + BUILDING_GAP);
//        } else if (building7.isScrolledLeft()) {
//            building7.reset(building6.getTailX() + BUILDING_GAP);
//        } else if (building8.isScrolledLeft()) {
//            building8.reset(building7.getTailX() + BUILDING_GAP);
//        } else if (building9.isScrolledLeft()) {
//            building9.reset(building8.getTailX() + BUILDING_GAP);
//        } else if (building10.isScrolledLeft()) {
//            building10.reset(building9.getTailX() + BUILDING_GAP);
//        }

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


//        if (poop1.getX() < 0) {
//            poop1.reset(poop10.getTailX() + POOP_GAP);
//        } else if (poop2.getX() < 0) {
//            poop2.reset(poop1.getTailX() + POOP_GAP);
//        } else if (poop3.getX() < 0) {
//            poop3.reset(poop3.getTailX() + POOP_GAP);
//        } else if (poop4.getX() < 0) {
//            poop4.reset(poop3.getTailX() + POOP_GAP);
//        } else if (poop5.getX() < 0) {
//            poop5.reset(poop4.getTailX() + POOP_GAP);
//        } else if (poop6.getX() < 0) {
//            poop6.reset(poop5.getTailX() + POOP_GAP);
//        } else if (poop7.getX() < 0) {
//            poop7.reset(poop6.getTailX() + POOP_GAP);
//        } else if (poop8.getX() < 0) {
//            poop8.reset(poop7.getTailX() + POOP_GAP);
//        } else if (poop9.getX() < 0) {
//            poop9.reset(poop8.getTailX() + POOP_GAP);
//        } else if (poop10.getX() < 0) {
//            poop10.reset(poop9.getTailX() + POOP_GAP);
//        }
    }
	
	public void stop() {
		backdrop1.stop();
		backdrop2.stop();
		building1.stop();
		building2.stop();
		building3.stop();
        building4.stop();
        building5.stop();
//        building6.stop();
//        building7.stop();
//        building8.stop();
//        building9.stop();
//        building10.stop();
        cloud1.stop();
        cloud2.stop();
        cloud3.stop();

    }
	
	public void onRestart() {
		backdrop1.onRestart(0, SCROLL_SPEED);
		backdrop2.onRestart(backdrop1.getTailX(), SCROLL_SPEED);

		building1.onRestart(220, SCROLL_SPEED);
		building2.onRestart(building1.getTailX() + BUILDING_GAP, SCROLL_SPEED);
		building3.onRestart(building2.getTailX() + BUILDING_GAP, SCROLL_SPEED);
        building4.onRestart(building3.getTailX() + BUILDING_GAP, SCROLL_SPEED);
        building5.onRestart(building4.getTailX() + BUILDING_GAP, SCROLL_SPEED);
//        building6.onRestart(building5.getTailX() + BUILDING_GAP, SCROLL_SPEED);
//        building7.onRestart(building6.getTailX() + BUILDING_GAP, SCROLL_SPEED);
//        building8.onRestart(building7.getTailX() + BUILDING_GAP, SCROLL_SPEED);
//        building9.onRestart(building8.getTailX() + BUILDING_GAP, SCROLL_SPEED);
//        building10.onRestart(building9.getTailX() + BUILDING_GAP, SCROLL_SPEED);

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
//    public Building getBuilding6() { return building6; }
//    public Building getBuilding7() { return building7; }
//    public Building getBuilding8() { return building8; }
//    public Building getBuilding9() { return building9; }
//    public Building getBuilding10() { return building10; }

    public Cloud getCloud1() { return cloud1; }
    public Cloud getCloud2() { return cloud2; }
    public Cloud getCloud3() { return cloud3; }

//   public Poop getPoop1() {
//
//        return poop1;
//    }
//
//    public Poop getPoop2() {
//
//        return poop2;
//    }
//
//    public Poop getPoop3() {
//
//        return poop3;
//    }
//
//    public Poop getPoop4() {
//
//        return poop4;
//    }
//
//    public Poop getPoop5() {
//
//        return poop5;
//    }
//
//    public Poop getPoop6() {
//
//        return poop6;
//    }
//
//    public Poop getPoop7() {
//
//        return poop7;
//    }
//
//    public Poop getPoop8() {
//
//        return poop8;
//    }
//
//    public Poop getPoop9() {
//
//        return poop9;
//    }
//
//    public Poop getPoop10() {
//
//        return poop10;
//    }
    }
