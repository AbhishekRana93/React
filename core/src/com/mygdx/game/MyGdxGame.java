package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MainMenuScreen;


public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	public int V_WIDTH = 800;
	public int V_HEIGHT = 480;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		screen.dispose();
	}
}