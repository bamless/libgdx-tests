package com.bizio.newgame.game.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

public class WaterRenderer implements Disposable {
	
	//for now the water color is constant because i'm lazy
	private static final Color WATER_COLOR = new Color(0.6f, 0.8f, 1, 1);
	
	private WaterShader waterShader;
	
	private float timedelta;
	private Texture displacement;

	public WaterRenderer() {
		waterShader = new WaterShader();
		displacement = new Texture(Gdx.files.internal("waterdisplacement.png"));
		displacement.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		displacement.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	}

	public void render(SpriteBatch batch, Water water) {
		timedelta += Gdx.graphics.getDeltaTime();
		
		ShaderProgram oldShader = batch.getShader();
		Color oldColor = batch.getColor();
		batch.setShader(waterShader);
		
		batch.begin();
		
		//sets the water shader uniforms
		waterShader.setTimeDelta(timedelta);
		displacement.bind(1);
		waterShader.setDisplacementTextureUnit(1);
		water.getTexture().bind(0);
		waterShader.setTextureUnit(0);
		
		//batch.setColor(0.250980392f, 0.643137255f, 0.874509804f, 1f);
		batch.setColor(WATER_COLOR);
		
		batch.draw(water.getTexture(), water.getX(), water.getY(), water.getWidth(), water.getHeight(), 0, 0, 1, 1);
		
		batch.end();
		
		batch.setColor(oldColor);
		batch.setShader(oldShader);
	}

	@Override
	public void dispose() {
		waterShader.dispose();
		displacement.dispose();
	}
	
}
