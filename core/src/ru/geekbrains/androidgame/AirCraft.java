package ru.geekbrains.androidgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.base.Sprite;
import ru.geekbrains.androidgame.math.Rect;

public class AirCraft extends Sprite {

    public AirCraft(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {

        return super.touchDown(touch, pointer);
    }
}
