package ru.geekbrains.androidgame.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.base.AirCraft;
import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.pool.BulletPool;

public class Enemy extends AirCraft {

    private MyAirCraft myAirCraft;

    private Vector2 v0 = new Vector2();

    public Enemy(BulletPool bulletPool, Sound shootSound, MyAirCraft myAirCraft, Rect worldBounds) {
        super(bulletPool, shootSound);
        this.myAirCraft = myAirCraft;
        this.reloadInterval = 0.2f;
        this.v.set(v0);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer +=delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

    public void set(TextureRegion[] regions, Vector2 v0, TextureRegion bulletRegion, float bulletHeight,
                    float bulletVY, int bulletDamage, float reloadInterval, float height, int hp) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        reloadTimer = reloadInterval;
        v.set(v0);
    }
}
