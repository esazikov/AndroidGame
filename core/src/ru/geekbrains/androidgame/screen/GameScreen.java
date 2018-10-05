package ru.geekbrains.androidgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.geekbrains.androidgame.base.BaseScreen;
import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.pool.BulletPool;
import ru.geekbrains.androidgame.pool.EnemyPool;
import ru.geekbrains.androidgame.pool.ExplosionPool;
import ru.geekbrains.androidgame.sprites.Explosion;
import ru.geekbrains.androidgame.sprites.MyAirCraft;
import ru.geekbrains.androidgame.sprites.Background;
import ru.geekbrains.androidgame.sprites.Cloud;
import ru.geekbrains.androidgame.utils.EnemiesEmitter;
import ru.geekbrains.androidgame.utils.Regions;


public class GameScreen extends BaseScreen {

    private static final int CLOUD_COUNT = 32;

    Background background;
    Texture bg;
    TextureAtlas atlas;
    MyAirCraft myAirCraft;
    BulletPool bulletPool;


    Cloud[] cloud;
    TextureRegion[] cloudTextures;
    Random random = new Random();

    Music musicGame;
    Sound myShootSound;
    Sound enemyShootSound;
    Sound explosionSound;

    EnemyPool enemyPool;
    EnemiesEmitter enemiesEmitter;
    ExplosionPool explosionPool;

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
        bulletPool = new BulletPool();
        myShootSound = Gdx.audio.newSound(Gdx.files.internal("sound/gun5.wav"));
       // myShootSound.setVolume();
        enemyShootSound = Gdx.audio.newSound(Gdx.files.internal("sound/gun5.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosions.mp3"));
        myAirCraft = new MyAirCraft(atlas, bulletPool, myShootSound);
        enemyPool = new EnemyPool(bulletPool, enemyShootSound, myAirCraft, worldBounds);
        enemiesEmitter = new EnemiesEmitter(enemyPool, atlas, worldBounds);
        explosionPool = new ExplosionPool(atlas, explosionSound);
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("sound/game.mp3"));
        musicGame.setLooping(true);
        musicGame.play();
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
        myAirCraft.update(delta);
        bulletPool.updateActiveObjects(delta);
        enemyPool.updateActiveObjects(delta);
        enemiesEmitter.generateEnemies(delta);
        explosionPool.updateActiveObjects(delta);
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].draw(batch);
        }
        myAirCraft.draw(batch);
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].resize(worldBounds);
        }
        myAirCraft.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        myAirCraft.dispose();
        musicGame.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                Explosion explosion = explosionPool.obtain();
                explosion.set(0.15f, worldBounds.pos);
                break;
        }
        myAirCraft.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        myAirCraft.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        myAirCraft.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        myAirCraft.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
