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
    private ExplosionPool explosionPool;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, MyAirCraft myAirCraft) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.myAirCraft = myAirCraft;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, shootSound, myAirCraft);
    }
}
