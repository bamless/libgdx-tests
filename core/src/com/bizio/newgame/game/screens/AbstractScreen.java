package com.bizio.newgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bizio.newgame.game.NewGame;

public class AbstractScreen implements Screen {

	protected OrthographicCamera camera;
	protected FitViewport viewPort;
	protected SpriteBatch batch;
	
	public AbstractScreen() {
		init();
	}

	private void init() {
		//create the spritebatch for rendering
		batch = new SpriteBatch();
						
		//creates the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, NewGame.getWidth(), NewGame.getHeight());
				
		//set the viewport
		viewPort = new FitViewport(NewGame.getWidth(), NewGame.getHeight(), camera);
		viewPort.apply();
		//set the matrix to the spritebatch
		batch.setProjectionMatrix(camera.combined);
	}

	
	public void update(float delta) {
	}
	
	public void draw() {
	}
	
	@Override
	public final void render(float delta) {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//update the matrix
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		update(delta);
		draw();
	}
	
	@Override
	public void resize(int width, int height) {
		viewPort.update(width, height);
	}
	
	public FitViewport getViewPort() {
		return viewPort;
	}
	
	
	@Override
	public void show() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}
	
	public SpriteBatch getSpriteBatch() {
		return batch;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	//Temporary: dispose when hiding the screen
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
	

}
