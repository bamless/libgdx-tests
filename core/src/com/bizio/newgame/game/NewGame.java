package com.bizio.newgame.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.bizio.newgame.game.screens.InitScreen;
import com.bizio.newgame.game.screens.PostProcessingTest;
import com.bizio.newgame.game.screens.WaterTestScreen;

public class NewGame extends Game {
	//TODO: remove
	private static int width;
	private static int height;
	
	public NewGame(int width, int height) {
		super();
		NewGame.width = width;
		NewGame.height = height;
	}
	
	@Override
	public void create() {
		//TODO: remove
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.ESCAPE)
					Gdx.app.exit();
				if(keycode == Keys.NUM_1)
					setScreen(new WaterTestScreen());
				if(keycode == Keys.NUM_2)
					setScreen(new PostProcessingTest());
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(pointer == 1)
					setScreen(new WaterTestScreen());
				if(pointer == 2)
					setScreen(new PostProcessingTest());
				return false;
			}
		});
		
		setScreen(new InitScreen());
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

}
