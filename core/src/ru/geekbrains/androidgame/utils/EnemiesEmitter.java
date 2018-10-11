package ru.geekbrains.androidgame.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.math.Rnd;
import ru.geekbrains.androidgame.pool.EnemyPool;
import ru.geekbrains.androidgame.sprites.Enemy;

public class EnemiesEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.15f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.035f;
    private static final float ENEMY_SMALL_BULLET_VY = - 0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.045f;
    private static final float ENEMY_MEDIUM_BULLET_VY = - 0.25f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.15f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.055f;
    private static final float ENEMY_BIG_BULLET_VY = - 0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_BIG_HP = 10;

    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMediumRegion;
    private final TextureRegion[] enemyBigRegion;

    private final Vector2 enemySmallV = new Vector2(0f, -0.1f);
    private final Vector2 enemyMediumV = new Vector2(0f, -0.07f);
    private final Vector2 enemyBigV = new Vector2(0f, -0.02f);

    private final EnemyPool enemyPool;

    private float generateInterval = 3.5f;
    private float generateTimer;

    private Rect worldBounds;

    private TextureRegion bulletRegion;

    private int level = 1;

    public EnemiesEmitter(EnemyPool enemyPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyPool = enemyPool;
        TextureRegion textureRegion0 = atlas.findRegion("enemy");
        this.enemySmallRegion = Regions.split(textureRegion0,1,2,2);
        TextureRegion textureRegion1 = atlas.findRegion("enemyMed");
        this.enemyMediumRegion = Regions.split(textureRegion1,1,2,2);
        TextureRegion textureRegion2 = atlas.findRegion("enemyBig");
        this.enemyBigRegion = Regions.split(textureRegion2,1,2,2);
        this.bulletRegion = atlas.findRegion("enemyRocket");
        this.worldBounds = worldBounds;
    }

    public void generateEnemies(float delta, int frags) {
        level = frags / 3 + 1;
        generateTimer += delta;
        if ( generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
            enemy.set(enemySmallRegion, enemySmallV, bulletRegion, ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY, ENEMY_SMALL_BULLET_DAMAGE * level, ENEMY_SMALL_RELOAD_INTERVAL,
                    ENEMY_SMALL_HEIGHT, ENEMY_SMALL_HP * level, worldBounds);
            } else if (type < 0.8f) {
                enemy.set(enemyMediumRegion, enemyMediumV, bulletRegion, ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY, ENEMY_MEDIUM_BULLET_DAMAGE * level, ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT, ENEMY_MEDIUM_HP * level, worldBounds);
            } else {
                enemy.set(enemyBigRegion, enemyBigV, bulletRegion, ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY, ENEMY_BIG_BULLET_DAMAGE * level, ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT, ENEMY_BIG_HP * level, worldBounds);
            }

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
