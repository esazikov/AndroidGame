package ru.geekbrains.androidgame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.pool.BulletPool;
import ru.geekbrains.androidgame.sprites.Bullet;

public class AirCraft extends Sprite {

    protected Vector2 v = new Vector2();

    protected Rect worldBounds;

    protected Vector2 bulletV = new Vector2();
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected int bulletDamage;

    private Sound shootSound;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    public AirCraft(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, Sound shootSound) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.bulletHeight = 0.03f;
        this.bulletDamage = 1;
    }

    public AirCraft(BulletPool bulletPool, Sound shootSound) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
        shootSound.play();
    }

}
