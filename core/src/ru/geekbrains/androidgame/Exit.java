package ru.geekbrains.androidgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.base.Sprite;
import ru.geekbrains.androidgame.math.Rect;

public class Exit extends Sprite {

    public Exit(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        pos.set(worldBounds.pos);
        setLeft(0.4f);
        setTop(0.5f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) Gdx.app.exit();
        return super.touchDown(touch, pointer);
    }
}
