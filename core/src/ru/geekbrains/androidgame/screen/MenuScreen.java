package ru.geekbrains.androidgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.androidgame.AirCraft;
import ru.geekbrains.androidgame.Background;
import ru.geekbrains.androidgame.Exit;
import ru.geekbrains.androidgame.base.BaseScreen;
import ru.geekbrains.androidgame.math.Rect;

public class MenuScreen extends BaseScreen {

    Background background;
    Texture bg;
    Vector2 posBg;

    Exit exit;
    Texture ex;
    Vector2 posEx;

    AirCraft airCraft;
    Texture air;
    Vector2 posAir;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("map.jpg");
        posBg = new Vector2(0f, 0f);
        background = new Background(new TextureRegion(bg));

        ex = new Texture("exit.png");
        posEx = new Vector2(0f, 0f);
        exit = new Exit(new TextureRegion(ex));

        air = new Texture("aircraft_small.jpg");
        posAir = new Vector2(0f, 0f);
        airCraft = new AirCraft(new TextureRegion(air));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        exit.draw(batch);
        airCraft.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        exit.resize(worldBounds);
        airCraft.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        ex.dispose();
        air.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        airCraft.touchDown(touch, pointer);
        exit.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }
}




