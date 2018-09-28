package ru.geekbrains.androidgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.geekbrains.androidgame.base.BaseScreen;
import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.sprites.AirCraft;
import ru.geekbrains.androidgame.sprites.Background;
import ru.geekbrains.androidgame.sprites.Cloud;
import ru.geekbrains.androidgame.utils.Regions;


public class GameScreen extends BaseScreen {

    private static final int CLOUD_COUNT = 32;

    Background background;
    Texture bg;
    TextureAtlas atlas;
    AirCraft airCraft;


    Cloud[] cloud;
    TextureRegion[] cloudTextures;
    Random random = new Random();

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("map.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        cloud = new Cloud[CLOUD_COUNT];
        cloudTextures = Regions.split(atlas.findRegion("cloud"),4,1,4);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i] = new Cloud(cloudTextures[random.nextInt(4)]);
        }
        airCraft = new AirCraft(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].update(delta);
        }
        airCraft.update(delta);
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {

    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].draw(batch);
        }
        airCraft.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].resize(worldBounds);
        }
        airCraft.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        airCraft.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        airCraft.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        airCraft.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        airCraft.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
