package ru.geekbrains.androidgame.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.androidgame.base.SpritesPool;
import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.sprites.Enemy;
import ru.geekbrains.androidgame.sprites.MyAirCraft;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private Sound shootSound;
    private MyAirCraft myAirCraft;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Sound shootSound, MyAirCraft myAirCraft, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.myAirCraft = myAirCraft;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, shootSound, myAirCraft, worldBounds);
    }
}
