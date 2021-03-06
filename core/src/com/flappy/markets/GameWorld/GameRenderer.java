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
//    private List<Poop> pooplist = new ArrayList<Poop>();
//    private Poop poop1, poop2, poop3, poop4, poop5, poop6, poop7, poop8, poop9, poop10;
    private ScrollHandler scroller;
    private Backdrop backdrop1, backdrop2;
    private Building building1, building2, building3, building4, building5;
    private Cloud cloud1, cloud2, cloud3;

    private TextureRegion buildingBackdrop;
    private TextureRegion background;
    private TextureRegion buildingTexture1, buildingTexture2, buildingTexture3, buildingTexture4;

    private TextureRegion gameOverRegion;

    private Animation birdAnimation;
    private Animation marketBirdAnimation;
    private TextureRegion bar;
    private TextureRegion cloudImage;
  //  private TextureRegion poopTexture;


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
//        building6 = scroller.getBuilding6();
//        building7 = scroller.getBuilding7();
//        building8 = scroller.getBuilding8();
//        building9 = scroller.getBuilding9();
//        building10 = scroller.getBuilding10();

        cloud1 = scroller.getCloud1();
        cloud2 = scroller.getCloud2();
        cloud3 = scroller.getCloud3();
//        poop1 = scroller.getPoop1();
//        poop2 = scroller.getPoop2();
//        poop3 = scroller.getPoop3();
//        poop4 = scroller.getPoop4();
//        poop5 = scroller.getPoop5();
//        poop6 = scroller.getPoop6();
//        poop7 = scroller.getPoop7();
//        poop8 = scroller.getPoop8();
//        poop9 = scroller.getPoop9();
//        poop10 = scroller.getPoop10();
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

        gameOverRegion = AssetLoader.gameOver;
    }

    private void drawBackground(GameLayer layer) {
        layer.getBatch().draw(background, 0, 0);
    }

    private void drawBuildings(GameLayer layer, Building building, TextureRegion buildingTexture) {

        layer.getBatch().draw(buildingTexture, building.getX(), building.getY() + building.getHeight(),
                building.getWidth(), gameHeight - (building.getHeight()));
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
        // landLayer.orient(width, height, bottom, 0.005f);

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
//        drawPoop(bird, poop1);
//        drawPoop(marketBird, poop1);
//        drawPoop(bird, poop2);
//        drawPoop(marketBird, poop2);
//        drawPoop(bird, poop3);
//        drawPoop(marketBird, poop3);
//        drawPoop(bird, poop4);
//        drawPoop(marketBird, poop4);
//        drawPoop(bird, poop5);
//        drawPoop(marketBird, poop5);
//        drawPoop(bird, poop6);
//        drawPoop(marketBird, poop6);
//        drawPoop(bird, poop7);
//        drawPoop(marketBird, poop7);
//        drawPoop(bird, poop8);
//        drawPoop(marketBird, poop8);
//        drawPoop(bird, poop9);
//        drawPoop(marketBird, poop9);
//        drawPoop(bird, poop10);
//        drawPoop(marketBird, poop10);
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

//    private void drawPoop(Bird b, Poop poop) {
//        landLayer.getBatch().draw(poopTexture, b.getX(), b.getY(), poop.getWidth(), poop.getHeight());
//    }

    private void drawHud() {

        SpriteBatch hudBatch = hudLayer.getBatch();

        if (myWorld.isReady()) {
            drawStart(hudBatch);
        } else {

            if (myWorld.isGameOver()) {

                final NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
                String scoreString = defaultFormat.format(myWorld.getScore());

                hudBatch.draw(gameOverRegion,
                        10,
                        40,
                        gameOverRegion.getRegionWidth() / 4,
                        gameOverRegion.getRegionHeight() / 4);

                AssetLoader.shadow.draw(hudBatch, scoreString, 75 - (3 * scoreString.length()), 65);
                AssetLoader.font.draw(hudBatch,   scoreString,   75 - (3 * scoreString.length() - 1), 67);


// AssetLoader.font.draw(hudBatch, "", 24, 55);
// AssetLoader.shadow.draw(hudBatch, "Game Over", 25, 56);
            } else {
                drawScore(hudBatch, myWorld.getScore());
                drawSignal(hudBatch, myWorld.getSignal());
            }
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
