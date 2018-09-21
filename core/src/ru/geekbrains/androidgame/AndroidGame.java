package ru.geekbrains.androidgame;

import com.badlogic.gdx.Game;

import ru.geekbrains.androidgame.screen.MenuScreen;

public class AndroidGame extends Game {

	
	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}

}
