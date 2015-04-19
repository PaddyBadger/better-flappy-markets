package com.flappy.markets.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.flappy.markets.STHelpers.AssetLoader;
import com.flappy.markets.STHelpers.MarketDataProvider;
import com.flappy.markets.STHelpers.MarketPriceTimeCoordinator;
import com.flappy.markets.STHelpers.PortfolioTimeCoordinator;

public class Bird {

    private long lastUpdate = 0;


    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private MarketPriceTimeCoordinator marketPriceTimeCoordinator;
    private PortfolioTimeCoordinator portfolioTimeCoordinator;
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

        marketPriceTimeCoordinator = new MarketPriceTimeCoordinator(0, 500, 241);
        portfolioTimeCoordinator = new PortfolioTimeCoordinator(15.92, 0, 500, 500, 241);
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

        // marketBird vs regular bird hack
        if (lastUpdate > 0) {
            marketPriceTimeCoordinator.incrementTime((long) (delta * 1000));
            position.y = (float) marketPriceTimeCoordinator.getCurrentPrice() * -60 + 1000;
        } else {
            // change between buy and sell every 5 seconds
            portfolioTimeCoordinator.incrementTime((long) (delta * 1000), (System.currentTimeMillis()/1000) % 10 > 5);
            position.y = (float) portfolioTimeCoordinator.getCurrentPortfolioValue() * -60 + 1000;
        }

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
