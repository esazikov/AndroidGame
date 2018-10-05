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

import java.util.List;
import java.util.Random;

import ru.geekbrains.androidgame.base.ActionListener;
import ru.geekbrains.androidgame.base.BaseScreen;
import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.pool.BulletPool;
import ru.geekbrains.androidgame.pool.EnemyPool;
import ru.geekbrains.androidgame.pool.ExplosionPool;
import ru.geekbrains.androidgame.sprites.Bullet;
import ru.geekbrains.androidgame.sprites.ButtonNewGame;
import ru.geekbrains.androidgame.sprites.Enemy;
import ru.geekbrains.androidgame.sprites.Explosion;
import ru.geekbrains.androidgame.sprites.MessageGameOver;
import ru.geekbrains.androidgame.sprites.MyAirCraft;
import ru.geekbrains.androidgame.sprites.Background;
import ru.geekbrains.androidgame.sprites.Cloud;
import ru.geekbrains.androidgame.utils.EnemiesEmitter;
import ru.geekbrains.androidgame.utils.Regions;


public class GameScreen extends BaseScreen implements ActionListener{

    private static final int CLOUD_COUNT = 32;

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonNewGame) startNewGame();
    }

    private enum State { PLAYING, GAME_OVER }

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

    State state;

    MessageGameOver messageGameOver;

    ButtonNewGame buttonNewGame;

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
        enemyShootSound = Gdx.audio.newSound(Gdx.files.internal("sound/gun5.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosions.mp3"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        myAirCraft = new MyAirCraft(atlas, bulletPool,explosionPool, myShootSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, enemyShootSound, myAirCraft);
        enemiesEmitter = new EnemiesEmitter(enemyPool, atlas, worldBounds);
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("sound/game.mp3"));
        musicGame.setLooping(true);
        musicGame.play();
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        startNewGame();
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
        explosionPool.updateActiveObjects(delta);
        bulletPool.updateActiveObjects(delta);
        if (myAirCraft.isDestroyed()) {
            state = State.GAME_OVER;
        }
        switch (state) {
            case PLAYING:
                myAirCraft.update(delta);
                enemyPool.updateActiveObjects(delta);
                enemiesEmitter.generateEnemies(delta);
                break;
            case GAME_OVER:
                break;
        }
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy: enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + myAirCraft.getHalfWidth();
            if (enemy.pos.dst2(myAirCraft.pos) < minDist * minDist) {
                enemy.destroy();
                enemy.boom();
                myAirCraft.damage(enemy.getBulletDamage() * 10);
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy: enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet: bulletList) {
                if (bullet.getOwner() != myAirCraft || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    bullet.destroy();
                    enemy.damage(bullet.getDamage());
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == myAirCraft || bullet.isDestroyed()) {
                continue;
            }
            if (myAirCraft.isBulletCollision(bullet)) {
                bullet.destroy();
                myAirCraft.damage(bullet.getDamage());
            }
        }

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
        if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
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
        buttonNewGame.resize(worldBounds);
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
        if (state == State.PLAYING) {
            myAirCraft.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            myAirCraft.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            myAirCraft.touchDown(touch, pointer);
        } else buttonNewGame.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            myAirCraft.touchUp(touch, pointer);
        } else buttonNewGame.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    private void startNewGame() {
        state = State.PLAYING;
        myAirCraft.startNewGame();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
    }


}
