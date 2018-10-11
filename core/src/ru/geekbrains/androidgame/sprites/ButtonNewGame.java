package ru.geekbrains.androidgame.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.androidgame.base.ActionListener;
import ru.geekbrains.androidgame.base.ScaledTouchUpButton;
import ru.geekbrains.androidgame.math.Rect;

public class ButtonNewGame extends ScaledTouchUpButton {

    public ButtonNewGame (TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("btn_new_game"), actionListener, 0.9f);
        setHeightProportion(0.10f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
