package ru.geekbrains.androidgame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.androidgame.base.Sprite;
import ru.geekbrains.androidgame.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setWidhtProportion(worldBounds.getWidth());
        pos.set(worldBounds.pos);
    }
}


