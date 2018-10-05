package ru.geekbrains.androidgame.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.base.AirCraft;
import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.pool.BulletPool;
import ru.geekbrains.androidgame.pool.ExplosionPool;

public class Enemy extends AirCraft {

    private enum State {DESCENT, FIGHT}

    private MyAirCraft myAirCraft;

    private Vector2 v0 = new Vector2();
    private Vector2 descentV = new Vector2(0, -0.15f);

    private State state;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, MyAirCraft myAirCraft) {
        super(bulletPool, explosionPool, shootSound);
        this.myAirCraft = myAirCraft;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer +=delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom() < worldBounds.getBottom()) {
                    boom();
                    destroy();
                }
                break;
        }
    }

    public void set(TextureRegion[] regions, Vector2 v0, TextureRegion bulletRegion, float bulletHeight,
                    float bulletVY, int bulletDamage, float reloadInterval, float height, int hp, Rect worldBounds) {
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
        v.set(descentV);
        state = State.DESCENT;
        this.worldBounds = worldBounds;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }
}
