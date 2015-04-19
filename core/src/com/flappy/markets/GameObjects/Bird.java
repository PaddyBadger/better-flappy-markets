package com.flappy.markets.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.flappy.markets.STHelpers.AssetLoader;
import com.flappy.markets.STHelpers.MarketDataProvider;

public class Bird {

    private long lastUpdate = 0;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private MarketDataProvider marketDataProvider;
    private float rotation;
    private int width;
    private int height;

    private Circle boundingCircle;

    private boolean isAlive;

    public Bird(float x, float y, int width, int height) {

        this.width = width;
        this.height = height;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();

        isAlive = true;

        marketDataProvider = new MarketDataProvider(241);
    }

    public void repositionX(float x){
        position.x = x;
    }

    public void slowUpdate(float delta) {
        long l = System.currentTimeMillis();
        long elapsed = l - lastUpdate;
        if(elapsed > 500) {
            lastUpdate = l;
            bouncyUpdate(delta);
        }
    }

    public void bouncyUpdate(float delta) {


        velocity.add(acceleration.cpy().scl(delta));

        // can't go faster than 200
        if (velocity.y > 200) {
            velocity.y = 200;
        }

        // can't go higher than ceiling
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }

        // move
        position.add(velocity.cpy().scl(delta));

        // collision -- TODO DELETE ME
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);


        // multi-porpoise
        // if moving vertically up, rotate counter clockwise until arc'd
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // if moving down fast or dead then rotate clockwise until vertical
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }
        }

        // zane hack
        // position.y = (float) marketDataProvider.get((int) Math.ceil(delta / 0.5));
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }

    public void onClick() {
        if (isAlive) {
            AssetLoader.flap.play();
            velocity.y = -140;
        }
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {

        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public void decelerate() {
        acceleration.y = 0;
    }


    public String toString(){
        return String.format("x:%s, y:%s", position.x, position.y);
    }

}
