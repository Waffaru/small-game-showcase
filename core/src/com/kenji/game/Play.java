package com.kenji.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;

import static com.kenji.game.Constants.warpPoints;

/**
 * Created by Waffaru on 21.6.2017.
 */


public class Play implements Screen {

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    Player player;
    Box2DDebugRenderer debugRenderer;
    SpriteBatch batch;
    Sprite playerSprite;
    private TextureAtlas characterNakedAtlas;
    private Animation<TextureRegion> animation;
    private float timePassed = 0;
    private float posX = 0;
    private float posY = 0;
    int count;

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

        // Warp points for different areas of the map.
        warpPoints = new ArrayList<Vector2>();
        warpPoints.add(new Vector2(0.4f,4.4f));
        warpPoints.add(new Vector2(3.316f,3.6f));
        warpPoints.add(new Vector2(3.3f,3.315f));
        warpPoints.add(new Vector2(6.54f,4.682f));
        warpPoints.add(new Vector2(7.216f,3.53f));
        warpPoints.add(new Vector2(0.433f,1.832f));
        warpPoints.add(new Vector2(1.026f,1.832f));
        warpPoints.add(new Vector2(3.29f,1.2f));


        batch = new SpriteBatch();
        CreateWorld.map = new TmxMapLoader().load("map.tmx");
        CreateWorld.buildWorld();
        debugRenderer = new Box2DDebugRenderer();


        //TODO: Change name of file


        //TODO: add unit scale if needed


        camera = new OrthographicCamera();
        //camera.setToOrtho(Constants.APP_WIDTH, Constants.APP_HEIGHT);
        camera.setToOrtho(false, Constants.APP_WIDTH * Constants.SCALE, Constants.APP_HEIGHT * Constants.SCALE);

        renderer = new OrthogonalTiledMapRenderer(CreateWorld.map, Constants.SCALE * 5);

        //player = new Player(0.2833f,4.266f);
        player = new Player(0.2833f,4.266f);

        characterNakedAtlas = new TextureAtlas(Gdx.files.internal("characterNaked.atlas"));
        animation = new Animation(1/10f, characterNakedAtlas.getRegions());

        for(Torch t : Torch.torches) {
            new box2dLight.PointLight(Constants.hander, 100, new Color(255,165,0,0.5f), 0.4f, t.getBody().getPosition().x, t.getBody().getPosition().y).setSoftnessLength(0f);
        }

        for(Candle t : Candle.candles) {
            new box2dLight.PointLight(Constants.hander, 100, new Color(255,165,0,0.4f), 0.4f, t.getBody().getPosition().x, t.getBody().getPosition().y).setSoftnessLength(0f);
        }

        new ConeLight(Constants.hander, 100, new Color(255,165,0,0.4f), 7f, Constants.rect.getX(), Constants.rect.getY(), -90f, 10f).setSoftnessLength(0f);
        new ConeLight(Constants.hander, 100, new Color(255,255,255,0.5f), 0.5f, 0.3f, 1.832f, 5f, 50f).setSoftnessLength(0f);

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //TODO: Tämä päällä ei mitään hajua mihin grafiikka lentää.
        batch.setProjectionMatrix(camera.combined);


        posX = player.getPlayerBody().getPosition().x;
        posY = player.getPlayerBody().getPosition().y;

        camera.update();
        renderer.setView(camera);

        moveCamera();
        player.movement();
        //renderer.render();

        //TODO: Fix numbers if wrong
        renderer.render(new int[]{0,1,2});


        batch.begin();

        timePassed += Gdx.graphics.getDeltaTime();

        for(Torch t : Torch.torches) {
            batch.draw(t.animate().getKeyFrame(timePassed,true), t.getBody().getPosition().x - 0.08f, t.getBody().getPosition().y - 0.06f,  32*(Constants.SCALE * 5), 32*(Constants.SCALE*5));
        }


        for(Candle t : Candle.candles) {
            batch.draw(t.animate().getKeyFrame(timePassed,true), t.getBody().getPosition().x - 0.08f, t.getBody().getPosition().y - 0.12f,  32*(Constants.SCALE * 5), 32*(Constants.SCALE*5));
        }

        batch.draw(player.animate().getKeyFrame(timePassed, true), posX - 0.08f, posY - 0.02f, 32*(Constants.SCALE * 5), 32*(Constants.SCALE*5));

        for(Tree t : Tree.trees) {
            batch.draw(t.animate().getKeyFrame(timePassed,true), t.getBody().getPosition().x - 0.08f, t.getBody().getPosition().y - 0.02f,  32*(Constants.SCALE * 5), 32*(Constants.SCALE*5));
        }

        for(Bird t : Bird.birdList) {
            t.move();
            batch.draw(t.animate().getKeyFrame(timePassed,true), t.getBody().getPosition().x - 0.08f, t.getBody().getPosition().y - 0.02f,  32*(Constants.SCALE * 5), 32*(Constants.SCALE*5));
        }


        batch.end();

        //debugRenderer.render(CreateWorld.world, camera.combined);
        //TODO: Render player


        renderer.render(new int[]{3,4,5,6});

        Constants.hander.setCombinedMatrix(camera);
        Constants.hander.updateAndRender();

        player.checkCollision();


        CreateWorld.world.step(1/60f, 6, 2);
        //moves player if they have touched a warp zone
        if(Constants.movePlayer) {
            player.getPlayerBody().setTransform(Constants.moveTo, player.getPlayerBody().getAngle());
            Constants.movePlayer = false;
        }


    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        /*camera.viewportWidth = width * Constants.SCALE;
        camera.viewportHeight = height * Constants.SCALE;
        camera.update();*/
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
        dispose();

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        CreateWorld.map.dispose();
        renderer.dispose();
        characterNakedAtlas.dispose();
    }


    private void moveCamera() {

        camera.position.set(player.getPlayerBody().getPosition().x,
                player.getPlayerBody().getPosition().y,
                0);

    }


}
