package com.flappy.markets.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flappy.markets.GameObjects.Backdrop;
import com.flappy.markets.GameObjects.Bird;
import com.flappy.markets.GameObjects.Building;
import com.flappy.markets.GameObjects.Cloud;
import com.flappy.markets.GameObjects.ScrollHandler;
import com.flappy.markets.STHelpers.AssetLoader;

import java.text.NumberFormat;
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
    private GameLayer backgroundLayer;

    private int midPointY;
    private int gameHeight;

    private Bird bird;
    private Bird marketBird;
    private ScrollHandler scroller;
    private Backdrop backdrop1, backdrop2;
    private Building building1, building2, building3, building4, building5;
    private Cloud cloud1, cloud2, cloud3;

    private TextureRegion buildingBackdrop;
    private TextureRegion background;
    private TextureRegion buildingTexture1, buildingTexture2, buildingTexture3, buildingTexture4;
    private Animation birdAnimation;
    private Animation marketBirdAnimation;
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
        this.backgroundLayer = new GameLayer(VIEWPORT_WIDTH, gameHeight);

        layers.add(hudLayer);
        layers.add(birdLayer);
        layers.add(cloudLayer);
        layers.add(backgroundLayer);

        initGameObjects();
        initAssets();
    }

    private void initGameObjects() {

        bird = myWorld.getBird();
        marketBird = myWorld.getMarketBird();
        scroller = myWorld.getScroller();
        backdrop1 = scroller.getBackdrop1();
        backdrop2 = scroller.getBackdrop2();
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

        buildingBackdrop = AssetLoader.buildingBackdrop;
        birdAnimation = AssetLoader.birdAnimation;
        marketBirdAnimation = AssetLoader.marketBirdAnimation;
        cloudImage = AssetLoader.cloud;
        background = AssetLoader.backgroundTexture;
        buildingTexture1 = AssetLoader.building1;
        buildingTexture2= AssetLoader.building2;
        buildingTexture3 = AssetLoader.building3;
        buildingTexture4 = AssetLoader.building4;
    }

    private void drawBackground(GameLayer layer) {
        layer.getBatch().draw(background, 0, 0);
    }

    private void drawBuildings(GameLayer layer, Building building, TextureRegion buildingTexture) {

        layer.getBatch().draw(buildingTexture, building.getX(), building.getY() + building.getHeight(),
                building.getWidth(), midPointY + 66 - (building.getHeight()));
    }

   
    private void drawCloudLayer(GameLayer cloudLayer, Cloud cloud) {

        cloudLayer.getBatch().draw(cloudImage, cloud.getX(), cloud.getY() + cloud.getHeight(),
                cloud.getWidth(), cloud.getHeight());

        cloudLayer.getBatch().draw(buildingBackdrop, backdrop1.getX(), backdrop1.getY(),
                backdrop1.getWidth(), backdrop1.getHeight());
        cloudLayer.getBatch().draw(buildingBackdrop, backdrop2.getX(), backdrop2.getY(),
                backdrop2.getWidth(), backdrop2.getHeight());
    }
    
    /**
     * @param runTime used to keyframe into sprites to allow them to change visible states
     */
    public void render(float runTime) {

        // magic.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_CULL_FACE);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);

        backgroundLayer.start();
        backgroundLayer.blend();
        drawBackground(backgroundLayer);
        backgroundLayer.stop();
        
        final CameraMan cameraMan = myWorld.getCameraMan();
        cameraMan.update(runTime);

        float bottom = cameraMan.getBottom();
        float top = cameraMan.getTop();

        float height = top - bottom;
        float width = height * this.viewportRatio;

        // System.out.println(String.format("width:%s, height:%s", width, height));

        cloudLayer.start();
        cloudLayer.blend();
        birdLayer.orient(width, height, bottom, 1f);
        landLayer.orient(width, height, bottom, 0.4f);

        drawCloudLayer(cloudLayer, cloud1);
        drawCloudLayer(cloudLayer, cloud2);
        drawCloudLayer(cloudLayer, cloud3);
        cloudLayer.stop();

        landLayer.start();
        landLayer.blend();
        drawBuildings(landLayer, building1, buildingTexture1);
        drawBuildings(landLayer, building2, buildingTexture2);
        drawBuildings(landLayer, building3, buildingTexture3);
        drawBuildings(landLayer, building4, buildingTexture4);
        drawBuildings(landLayer, building5, buildingTexture2);
        landLayer.stop();

        birdLayer.start();
        birdLayer.blend();
        drawBird(bird, runTime, birdAnimation);
        drawBird(marketBird, runTime, marketBirdAnimation);
        birdLayer.stop();

        hudLayer.start();
        drawHud();
        hudLayer.stop();
    }

    private void drawBird(Bird b, float runTime, Animation birdAnimation) {
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
                drawSignal(hudBatch, myWorld.getSignal());
            }

            drawScore(hudBatch, myWorld.getScore());
            drawSignal(hudBatch, myWorld.getSignal());
        }
    }

    private void drawStart(SpriteBatch batch) {
        AssetLoader.shadow.draw(batch, "Start", (136 / 2) - (42 - 1), 75);
        AssetLoader.font.draw(batch, "Start",   (136 / 2) - (42 - 1), 75);
    }

    private void drawScore(SpriteBatch batch, double score) {
        final NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        String scoreString = defaultFormat.format(score);

        AssetLoader.shadow.draw(batch, scoreString, (136 / 2) - (3 * scoreString.length()), 12);
        AssetLoader.font.draw(batch,   scoreString,   (136 / 2) - (3 * scoreString.length() - 1), 11);
    }

    private void drawSignal(SpriteBatch batch, double score) {

        String scoreString = "HOLD";
        if (score > 0) {
            scoreString = "BUY";
        } else if (score < 0) {
            scoreString = "SELL";
        }

        AssetLoader.shadow.draw(batch, scoreString, 50, 200);
        AssetLoader.font.draw(batch, scoreString, 50, 200);
    }
}
