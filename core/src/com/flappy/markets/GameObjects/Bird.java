package com.flappy.markets.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.flappy.markets.STHelpers.AssetLoader;
import com.flappy.markets.STHelpers.MarketDataProvider;
import com.flappy.markets.STHelpers.MarketPriceTimeCoordinator;
import com.flappy.markets.STHelpers.PortfolioTimeCoordinator;
import com.flappy.markets.STHelpers.UpcomingValue;
import com.flappy.markets.STHelpers.ValueTimeCoordinator;

public class Bird {

    private long lastUpdate = 0;


    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private ValueTimeCoordinator valueTimeCoordinator;

    private float rotation;
    private int width;
    private int height;

    private boolean isAlive;

    private static final float FLAP_VELOCITY = -140;

    public Bird(float x, float y, int width, int height, ValueTimeCoordinator valueTimeCoordinator) {

        this.width = width;
        this.height = height;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        acceleration = new Vector2(0, 460);

        isAlive = true;

        this.valueTimeCoordinator = valueTimeCoordinator;
    }

    public void repositionX(float x){
        position.x = x;
    }

    public void slowUpdate(float delta) {
        long l = System.currentTimeMillis();
        long elapsed = l - lastUpdate;
        lastUpdate = l;
        bouncyUpdate(delta);
    }

    public void bouncyUpdate(float delta) {


        velocity.add(acceleration.cpy().scl(delta));

        // can't go faster than 200
        if (velocity.y > 200) {
            velocity.y = 200;
        }


        // move
        position.add(velocity.cpy().scl(delta));

        // multi-porpoise
        // if moving vertically up, rotate counter clockwise until arc'd
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        UpcomingValue upcomingValue = valueTimeCoordinator.getUpcomingValue();

        if (shouldFlap(position.y, (float) upcomingValue.getValue() / -1000 + 1000, upcomingValue.getTimeRemaining()/1000)) {
            AssetLoader.flap.play();
            velocity.y = FLAP_VELOCITY;
        }

        // if moving down fast or dead then rotate clockwise until vertical
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
        }

//        position.y = (float) valueTimeCoordinator.getCurrentValue() * -60 + 1000;
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }

    public void onClick() {
//        if (isAlive) {
//            AssetLoader.flap.play();
//            velocity.y = FLAP_VELOCITY;
//        }
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

    private boolean shouldFlap(float altitude, float targetAltitude, float elapsedTime) {
        float expectedFlapAltitude = altitude + altitudeChange(FLAP_VELOCITY, elapsedTime);
        return targetAltitude < expectedFlapAltitude; //due to reverse axis
    }

    private float altitudeChange(float initialVelocity, float timeElapsed) {
        return (initialVelocity * timeElapsed) + ((float) 0.5 * acceleration.y * timeElapsed * timeElapsed);
    }

}
