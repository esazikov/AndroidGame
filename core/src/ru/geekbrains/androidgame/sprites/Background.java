package ru.geekbrains.androidgame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.base.Sprite;
import ru.geekbrains.androidgame.math.Rect;

public class Background extends Sprite {

    private Vector2 v = new Vector2();

    public Background(TextureRegion region) {
        super(region);
        v.set(0.0f, -0.002f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setWidhtProportion(worldBounds.getWidth());
        setBottom(worldBounds.getBottom());
//        pos.set(worldBounds.getWidth(),worldBounds.getTop()-this.getHeight());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }
}


