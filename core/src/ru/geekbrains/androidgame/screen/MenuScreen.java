package ru.geekbrains.androidgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.Background;
import ru.geekbrains.androidgame.base.BaseScreen;
import ru.geekbrains.androidgame.math.Rect;

public class MenuScreen extends BaseScreen {

    Background background;
    Texture bg;
    Vector2 posBg;

    Vector2 velocity;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("map.jpg");
        posBg = new Vector2(0f, 0f);
        background = new Background(new TextureRegion(bg));

        velocity = new Vector2(0f, 0f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
        // position.add(velocity);
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        return super.touchDown(screenX, screenY, pointer, button);
    }
}




