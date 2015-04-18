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
import com.flappy.markets.GameObjects.Pipe;
import com.flappy.markets.GameObjects.ScrollHandler;
import com.flappy.markets.STHelpers.AssetLoader;

public class GameRenderer {

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
    private Pipe pipe1, pipe2, pipe3, pipe4, pipe5;

    private TextureRegion grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {

        myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);

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
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
        pipe4 = scroller.getPipe4();
        pipe5 = scroller.getPipe5();
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

        private void drawSkulls() {
            // might still be handy

            batcher.draw(skullUp, pipe1.getX() - 1,
                    pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
            batcher.draw(skullDown, pipe1.getX() - 1,
                    pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

            batcher.draw(skullUp, pipe2.getX() - 1,
                    pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
            batcher.draw(skullDown, pipe2.getX() - 1,
                    pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

            batcher.draw(skullUp, pipe3.getX() - 1,
                    pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
            batcher.draw(skullDown, pipe3.getX() - 1,
                    pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
        }

        private void drawBuildings() {
            batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                    pipe1.getHeight());
            batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                    pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

            batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                    pipe2.getHeight());
            batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                    pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

            batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                    pipe3.getHeight());
            batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                    pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));

            batcher.draw(bar, pipe4.getX(), pipe4.getY(), pipe4.getWidth(),
                    pipe4.getHeight());
            batcher.draw(bar, pipe4.getX(), pipe4.getY() + pipe4.getHeight() + 45,
                    pipe4.getWidth(), midPointY + 66 - (pipe4.getHeight() + 45));

            batcher.draw(bar, pipe5.getX(), pipe5.getY(), pipe5.getWidth(),
                    pipe5.getHeight());
            batcher.draw(bar, pipe5.getX(), pipe5.getY() + pipe5.getHeight() + 45,
                    pipe5.getWidth(), midPointY + 66 - (pipe5.getHeight() + 45));
        }

    public void render(float runTime) {

        // black background to prevent flickering

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
        		drawBuildings();

        batcher.enableBlending();

        //		drawSkulls();

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

}
