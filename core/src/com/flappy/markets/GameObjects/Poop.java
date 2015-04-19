package com.flappy.markets.GameObjects;

/**
 * Created by paddy on 4/19/15.
 */
public class Poop extends Scrollable {
    public Poop(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }
}
