package com.flappy.markets.GameWorld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by norton on 4/18/15.
 */

public class GameLayer {

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

    public GameLayer() {
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();
        this.batch.disableBlending();
    }

    public void orient(float width, float height){
        this.width = width;
        this.height = height;

        camera.setToOrtho(true, width, height);
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
