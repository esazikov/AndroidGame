package ru.geekbrains.androidgame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.geekbrains.androidgame.base.ActionListener;
import ru.geekbrains.androidgame.sprites.Background;
import ru.geekbrains.androidgame.base.BaseScreen;
import ru.geekbrains.androidgame.math.Rect;
import ru.geekbrains.androidgame.sprites.ButtonExit;
import ru.geekbrains.androidgame.sprites.ButtonPlay;
import ru.geekbrains.androidgame.sprites.Cloud;
import ru.geekbrains.androidgame.utils.Regions;

public class MenuScreen extends BaseScreen implements ActionListener{

    private static final int CLOUD_COUNT = 48;

    Background background;
    Texture bg;
    TextureAtlas atlas;

    ButtonExit buttonExit;
    ButtonPlay buttonPlay;

    Cloud[] cloud;
    TextureRegion[] cloudTextures;
    Random random = new Random();

    Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("sound/menu.mp3"));


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/menuAtlas.pack");
        bg = new Texture("map.png");
        background = new Background(new TextureRegion(bg));
        buttonExit = new ButtonExit(atlas, this);
        buttonPlay = new ButtonPlay(atlas, this);
        cloud = new Cloud[CLOUD_COUNT];
        cloudTextures = Regions.split(atlas.findRegion("cloud"),4,1,4);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i] = new Cloud(cloudTextures[random.nextInt(4)]);
        }
        musicMenu.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < cloud.length; i++) {
            cloud[i].resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        musicMenu.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonExit) {
            Gdx.app.exit();
        } else if (src == buttonPlay) {
            game.setScreen(new GameScreen(game));
        }
    }
}




