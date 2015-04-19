package com.flappy.markets.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flappy.markets.GameObjects.Bird;
import com.flappy.markets.GameObjects.Building;
import com.flappy.markets.GameObjects.Cloud;
import com.flappy.markets.GameObjects.Grass;
import com.flappy.markets.GameObjects.ScrollHandler;
import com.flappy.markets.STHelpers.AssetLoader;

import java.util.ArrayList;
import java.util.List;

public class GameRenderer {

    public static final int VIEWPORT_WIDTH = 136;
    private final float viewportRatio;
    private GameWorld myWorld;

    private List<GameLayer> layers = new ArrayList<GameLayer>();

    private GameLayer hudLayer;
    private GameLayer birdLayer;
    private GameLayer cloudLayer;
    private GameLayer landLayer;

    private int midPointY;
    private int gameHeight;

    private Bird bird;
    private Bird marketBird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Building building1, building2, building3, building4, building5;
    private Cloud cloud1, cloud2, cloud3;

    private TextureRegion grass;
    private Animation birdAnimation;
    private TextureRegion bar;
    private TextureRegion cloudImage;


    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {

        this.myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        this.viewportRatio = VIEWPORT_WIDTH / (float) gameHeight;

        this.hudLayer = new GameLayer(VIEWPORT_WIDTH, gameHeight);
        this.birdLayer = new GameLayer(VIEWPORT_WIDTH, gameHeight);
        this.cloudLayer = new GameLayer(VIEWPORT_WIDTH, gameHeight);
        this.landLayer = new GameLayer(VIEWPORT_WIDTH, gameHeight);

        layers.add(hudLayer);
        layers.add(birdLayer);
        layers.add(cloudLayer);

        initGameObjects();
        initAssets();
    }

    private void initGameObjects() {

        bird = myWorld.getBird();
        marketBird = myWorld.getMarketBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        building1 = scroller.getBuilding1();
        building2 = scroller.getBuilding2();
        building3 = scroller.getBuilding3();
        building4 = scroller.getBuilding4();
        building5 = scroller.getBuilding5();
        cloud1 = scroller.getCloud1();
        cloud2 = scroller.getCloud2();
        cloud3 = scroller.getCloud3();
    }

    private void initAssets() {

        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        bar = AssetLoader.bar;
        cloudImage = AssetLoader.cloud;
    }

    private void drawGrass(GameLayer layer) {

        // Draw the grass
        layer.getBatch().draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        layer.getBatch().draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawBuildings(GameLayer layer, Building building) {

        layer.getBatch().draw(bar, building.getX(), building.getY() + building.getHeight(),
                building.getWidth(), midPointY + 66 - (building.getHeight()));
    }

   
    private void drawCloudLayer(GameLayer cloudLayer, Cloud cloud) {

        cloudLayer.getBatch().draw(cloudImage, cloud.getX(), cloud.getY() + cloud.getHeight(),
                cloud.getWidth(), cloud.getHeight());
    }
    
    /**
     * @param runTime used to keyframe into sprites to allow them to change visible states
     */
    public void render(float runTime) {

        // magic.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(202 / 255f, 227 / 255f, 246 / 255f, 1);
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
        
        final CameraMan cameraMan = myWorld.getCameraMan();
        cameraMan.update(runTime);

        float bottom = cameraMan.getBottom();
        float top = cameraMan.getTop();

        float height = top - bottom;
        float width = height * this.viewportRatio;

        // System.out.println(String.format("width:%s, height:%s", width, height));

        cloudLayer.start();
        cloudLayer.blend();

        drawCloudLayer(cloudLayer, cloud1);
        drawCloudLayer(cloudLayer, cloud2);
        drawCloudLayer(cloudLayer, cloud3);
        cloudLayer.stop();
        
        birdLayer.orient(width, height, bottom);
        
        landLayer.start();
        drawGrass(landLayer);
        drawBuildings(landLayer, building1);
        drawBuildings(landLayer, building2);
        drawBuildings(landLayer, building3);
        drawBuildings(landLayer, building4);
        drawBuildings(landLayer, building5);
        landLayer.stop();

        birdLayer.start();
        birdLayer.blend();
        drawBird(bird, runTime);
        drawBird(marketBird, runTime);
        birdLayer.stop();

        hudLayer.start();
        drawHud();
        hudLayer.stop();
    }

    private void drawBird(Bird b, float runTime) {
        birdLayer.getBatch().draw(birdAnimation.getKeyFrame(runTime), b.getX(), b.getY(),
                b.getWidth() / 2.0f, b.getHeight() / 2.0f,
                b.getWidth(), b.getHeight(), 1, 1, b.getRotation());
    }

    private void drawHud() {

        SpriteBatch hudBatch = hudLayer.getBatch();

        if (myWorld.isReady()) {
            drawStart(hudBatch);
        } else {

            if (myWorld.isGameOver() || myWorld.isHighScore()) {

                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(hudBatch, "Game Over", 25, 56);
                    AssetLoader.font.draw(hudBatch, "Game Over", 24, 55);

                    AssetLoader.shadow.draw(hudBatch, "High Score:", 23, 106);
                    AssetLoader.font.draw(hudBatch, "High Score", 22, 105);

                    String highScore = AssetLoader.getHighScore() + "";

                    AssetLoader.shadow.draw(hudBatch, highScore, (136 / 2)
                            - (3 * highScore.length()), 128);

                    AssetLoader.font.draw(hudBatch, highScore, (136 / 2)
                            - (3 * highScore.length() - 1), 127);

                } else {
                    AssetLoader.shadow.draw(hudBatch, "High Score!", 19, 56);
                    AssetLoader.font.draw(hudBatch, "High Score!", 18, 55);
                }

                AssetLoader.shadow.draw(hudBatch, "Try Again?", 23, 76);
                AssetLoader.font.draw(hudBatch, "Try Again?", 24, 75);

                drawScore(hudBatch, myWorld.getScore());
            }

            drawScore(hudBatch, myWorld.getScore());
        }
    }

    private void drawStart(SpriteBatch batch) {
        AssetLoader.shadow.draw(batch, "Start", (136 / 2) - (42 - 1), 75);
        AssetLoader.font.draw(batch, "Start",   (136 / 2) - (42 - 1), 75);
    }

    private void drawScore(SpriteBatch batch, int score) {
        String scoreString = myWorld.getScore() + "";

        AssetLoader.shadow.draw(batch, scoreString, (136 / 2) - (3 * scoreString.length()), 12);
        AssetLoader.font.draw(batch,   scoreString,   (136 / 2) - (3 * scoreString.length() - 1), 11);
    }


}
