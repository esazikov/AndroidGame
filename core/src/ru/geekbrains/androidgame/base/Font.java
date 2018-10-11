package ru.geekbrains.androidgame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Font extends BitmapFont {

    public Font(String fontFile, String imageFile) {
        super(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false, false);

    }

    public GlyphLayout draw (Batch batch, CharSequence str, float x, float y, int align) {
        return super.draw(batch, str, x, y, 0f, align, false);
    }

    public void setFontSize(float worldSize) {
        getData().setScale(worldSize / getCapHeight());
    }
}
