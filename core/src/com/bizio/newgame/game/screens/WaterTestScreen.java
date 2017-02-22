package com.bizio.newgame.game.screens;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.bizio.newgame.game.NewGame;
import com.bizio.newgame.game.entities.Entity;
import com.bizio.newgame.game.entities.Renderable;
import com.bizio.newgame.game.ui.TextLabel;
import com.bizio.newgame.game.ui.TextRenderer;
import com.bizio.newgame.game.water.Water;
import com.bizio.newgame.game.water.WaterRenderer;

public class WaterTestScreen extends AbstractScreen {

	// Entities and water
	private Entity bg;
	private Entity gdx;
	private Water water;
	
	// Water renderer
	private WaterRenderer waterRenderer;
	
	// Text
	private TextRenderer textrenderer;
	private TextLabel fps;
	
	private Vector2 mouseCoords = new Vector2();
	
	@Override
	public void show() {
		waterRenderer = new WaterRenderer();
		textrenderer = new TextRenderer();
		
		// entities and water
		bg = new Entity("bg1.jpg");
		gdx = new Entity("badlogic.jpg");
		water = new Water(0, 0, 720, 300);
		
		fps = new TextLabel("00", 0, NewGame.getHeight());
		fps.setScale(1.5f);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		// update flag
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			renderWater = !renderWater;
		}
		
		fps.setText(Gdx.graphics.getFramesPerSecond()+"");
		
		// move the gdx logo to the pos of the mouse
		mouseCoords.x = Gdx.input.getX();
		mouseCoords.y = Gdx.input.getY();
		mouseCoords = viewPort.unproject(mouseCoords);
		mouseCoords.x -= gdx.getWidth()/2;
		mouseCoords.y -= gdx.getHeight()/2;
		gdx.setCoords(mouseCoords);
		
	}
	
	private boolean renderWater = true;
	
	@Override
	public void draw() {		
		batch.begin();
		
		batch.disableBlending();
		bg.render(batch);
		batch.enableBlending();
		
		gdx.render(batch);
		batch.end();
		
		//renders the refraction to the water
		water.renderRefraction(batch,  new ArrayList<Renderable>(Arrays.asList(bg, gdx)), viewPort);
		//renders the water
		waterRenderer.render(batch, water);
		
		textrenderer.render(batch, true, fps);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		bg.dispose();
		gdx.dispose();
		water.dispose();
		waterRenderer.dispose();
		textrenderer.dispose();
	}

}
