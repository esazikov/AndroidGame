package ru.geekbrains.androidgame.pool;

import ru.geekbrains.androidgame.base.SpritesPool;
import ru.geekbrains.androidgame.sprites.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
