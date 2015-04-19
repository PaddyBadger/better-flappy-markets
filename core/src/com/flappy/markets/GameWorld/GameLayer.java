package com.flappy.markets.GameWorld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by norton on 4/18/15.
 */

public class GameLayer {

    public static final int SPRITE_WIDTH = 15;

    private float width;
    private float height;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public GameLayer(float width, float height) {
        this.camera = new OrthographicCamera();

        this.batch = new SpriteBatch();
        this.batch.disableBlending();

        this.camera.setToOrtho(true, width, height);
        this.camera.update();
        this.batch.setProjectionMatrix(camera.combined);
    }

    public void orient(float width, float height, float bottom, float scale){
        this.width = width;
        this.height = height;

        camera.setToOrtho(true, width, height);
        // shift left by half the width of the screen to center, then center the sprite by shifting half its width
        camera.position.set(camera.position.x - (width/2), camera.position.y + (bottom * scale), 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
    }

    public void start(){
        this.batch.begin();
        this.batch.disableBlending();
    }

    public void blend(){
        this.batch.enableBlending();
    }

    public void stop(){
        this.batch.end();
    }

    public String toString(){
        return "width x height : " + width + " x " + height + "(" + width/height + ")";
    }
}
