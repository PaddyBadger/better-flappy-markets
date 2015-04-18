package com.flappy.markets.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.flappy.markets.GameObjects.Bird;
import com.flappy.markets.GameObjects.Grass;
import com.flappy.markets.GameObjects.Building;
import com.flappy.markets.GameObjects.ScrollHandler;
import com.flappy.markets.STHelpers.AssetLoader;

public class GameRenderer {

    public static final int VIEWPORT_WIDTH = 136;
    private final float viewportRatio;
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

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

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {

        myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        this.viewportRatio = VIEWPORT_WIDTH / gameHeight;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, VIEWPORT_WIDTH, gameHeight);

        batcher = new SpriteBatch();

        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

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

    private void drawGrass() {

        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawBuildings(Building building) {

        batcher.draw(bar, building.getX(), building.getY() + building.getHeight(),
                building.getWidth(), midPointY + 66 - (building.getHeight()));
    }

    public void render(float runTime) {

        System.out.println(runTime);

        // black background to prevent flickering
        resizeToBird(bird, runTime);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);

        shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, 204);

        shapeRenderer.setColor(84 / 255.0f, 84 / 255.0f, 84 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();

        drawGrass();
        drawBuildings(building1);
        drawBuildings(building2);
        drawBuildings(building3);
        drawBuildings(building4);
        drawBuildings(building5);

        batcher.enableBlending();

        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } else
            batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());

        batcher.draw(birdMid, marketBird.getX(), marketBird.getY(),
                marketBird.getWidth() / 2.0f, marketBird.getHeight() / 2.0f,
                marketBird.getWidth(), marketBird.getHeight(), 1, 1, marketBird.getRotation());

        if (myWorld.isReady()) {
            AssetLoader.shadow.draw(batcher, "Start", (136 / 2)
                    - (42 - 1), 75);

            AssetLoader.font.draw(batcher, "Start", (136 / 2)
                    - (42 - 1), 75);
        } else {

            if (myWorld.isGameOver() || myWorld.isHighScore()) {

                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                    AssetLoader.font.draw(batcher, "Game Over", 24, 55);

                    AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
                    AssetLoader.font.draw(batcher, "High Score", 22, 105);

                    String highScore = AssetLoader.getHighScore() + "";

                    AssetLoader.shadow.draw(batcher, highScore, (136 / 2)
                            - (3 * highScore.length()), 128);

                    AssetLoader.font.draw(batcher, highScore, (136 / 2)
                            - (3 * highScore.length() - 1), 127);

                } else {
                    AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
                    AssetLoader.font.draw(batcher, "High Score!", 18, 55);
                }

                AssetLoader.shadow.draw(batcher, "Try Again?", 23, 76);
                AssetLoader.font.draw(batcher, "Try Again?", 24, 75);

                String score = myWorld.getScore() + "";
                AssetLoader.shadow.draw(batcher, score, (136 / 2)
                        - (3 * score.length()), 12);
                AssetLoader.font.draw(batcher, score, (136 / 2)
                        - (3 * score.length() - 1), 11);
            }

            String score = myWorld.getScore() + "";
            AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2)
                    - (3 * score.length()), 12);
            AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2)
                    - (3 * score.length() - 1), 11);
        }
        batcher.end();
    }
    private void resizeToBird(Bird bird, float timestamp) {

        float birdY = bird.getY();

        float height = birdY + this.gameHeight;
        float width = Math.max(VIEWPORT_WIDTH, birdY + (height * this.viewportRatio));

        System.out.println("width x height : " + width + " x " + height + "(" + width/height + ")");

        cam.setToOrtho(true, width, height);
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

}
