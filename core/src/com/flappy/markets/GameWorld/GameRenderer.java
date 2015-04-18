package com.flappy.markets.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flappy.markets.GameObjects.Bird;
import com.flappy.markets.GameObjects.Grass;
import com.flappy.markets.GameObjects.Building;
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

    private int midPointY;
    private int gameHeight;

    private Bird bird;
    private Bird marketBird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Building building1, building2, building3, building4, building5;

    private TextureRegion grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;

    private class GameLayer {

        float width;
        float height;

        OrthographicCamera camera;
        SpriteBatch batch;

        private GameLayer() {
            this.camera = new OrthographicCamera();
            this.batch = new SpriteBatch();
            this.batch.disableBlending();
        }

        private void orient(float width, float height){
            this.width = width;
            this.height = height;

            camera.setToOrtho(true, width, height);
            batch.setProjectionMatrix(camera.combined);
        }

        public void start(){
            this.batch.begin();
        }

        public void stop(){
            this.batch.end();
        }

        public String toString(){
            return "width x height : " + width + " x " + height + "(" + width/height + ")";
        }
    }

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {

        myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        this.viewportRatio = VIEWPORT_WIDTH / gameHeight;

        this.hudLayer = new GameLayer();
        this.birdLayer = new GameLayer();

        this.birdLayer.orient(VIEWPORT_WIDTH, gameHeight);
        this.hudLayer.orient(VIEWPORT_WIDTH, gameHeight);

        layers.add(hudLayer);
        layers.add(birdLayer);

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
    }

    private void initAssets() {

        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

    private void drawGrass(GameLayer layer) {

        // Draw the grass
        layer.batch.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        layer.batch.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawBuildings(GameLayer layer, Building building) {

        layer.batch.draw(bar, building.getX(), building.getY() + building.getHeight(),
                building.getWidth(), midPointY + 66 - (building.getHeight()));
    }

    public void render(float runTime) {

        resizeToBird(birdLayer, bird, runTime);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        for(GameLayer l : layers){
//            l.start();
//        }

        birdLayer.start();
        birdLayer.batch.disableBlending();

        drawGrass(birdLayer);
        drawBuildings(birdLayer, building1);
        drawBuildings(birdLayer, building2);
        drawBuildings(birdLayer, building3);
        drawBuildings(birdLayer, building4);
        drawBuildings(birdLayer, building5);

        birdLayer.batch.enableBlending();

        System.out.println("birdLayer:" + birdLayer.toString());
        System.out.println("hudLayer:" + hudLayer.toString());

        if (bird.shouldntFlap()) {
            birdLayer.batch.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } else {
            birdLayer.batch.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

        birdLayer.batch.draw(birdMid, marketBird.getX(), marketBird.getY(),
                marketBird.getWidth() / 2.0f, marketBird.getHeight() / 2.0f,
                marketBird.getWidth(), marketBird.getHeight(), 1, 1, marketBird.getRotation());

        birdLayer.stop();

        hudLayer.start();
        drawHud();
        hudLayer.stop();

//        for(GameLayer l : layers){
//            l.stop();
//        }
    }

    private void drawHud() {
        if (myWorld.isReady()) {
            drawStart(hudLayer.batch);
        } else {

            if (myWorld.isGameOver() || myWorld.isHighScore()) {

                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(hudLayer.batch, "Game Over", 25, 56);
                    AssetLoader.font.draw(hudLayer.batch, "Game Over", 24, 55);

                    AssetLoader.shadow.draw(hudLayer.batch, "High Score:", 23, 106);
                    AssetLoader.font.draw(hudLayer.batch, "High Score", 22, 105);

                    String highScore = AssetLoader.getHighScore() + "";

                    AssetLoader.shadow.draw(hudLayer.batch, highScore, (136 / 2)
                            - (3 * highScore.length()), 128);

                    AssetLoader.font.draw(hudLayer.batch, highScore, (136 / 2)
                            - (3 * highScore.length() - 1), 127);

                } else {
                    AssetLoader.shadow.draw(hudLayer.batch, "High Score!", 19, 56);
                    AssetLoader.font.draw(hudLayer.batch, "High Score!", 18, 55);
                }

                AssetLoader.shadow.draw(hudLayer.batch, "Try Again?", 23, 76);
                AssetLoader.font.draw(hudLayer.batch, "Try Again?", 24, 75);

                drawScore(hudLayer.batch, myWorld.getScore());
            }

            drawScore(hudLayer.batch, myWorld.getScore());
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

    private void resizeToBird(GameLayer layer, Bird bird, float timestamp) {

        float birdY = bird.getY();

        float height = birdY + this.gameHeight;
        float width = Math.max(VIEWPORT_WIDTH, birdY + (height * this.viewportRatio));

        layer.orient(width, height);
    }

}
