package com.bizio.newgame.game.ui;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.bizio.newgame.game.NewGame;

public class TextRenderer implements Disposable {

	private TextShader textShader;
	private BitmapFont font; // For now the font is fixed because i'm lazy
	private Matrix4 fixedProj; // Fixed projection for rendering text uneffected by camera
	
	// Used in an hack to increase perfomance
	private float lastScale;
	
	public TextRenderer() {
		textShader = new TextShader();
		Texture region = new Texture(Gdx.files.internal("fonts/candara.png"));
		region.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font = new BitmapFont(Gdx.files.internal("fonts/candara.fnt"), new TextureRegion(region));
		
		fixedProj = new Matrix4().setToOrtho2D(0, 0, NewGame.getWidth(), NewGame.getHeight());
	}
	
	public void render(SpriteBatch batch, TextLabel text, boolean fixed) {
		ShaderProgram oldShader = batch.getShader();
		Matrix4 oldProj = batch.getProjectionMatrix();
		
		if(fixed) batch.setProjectionMatrix(fixedProj);
		batch.setShader(textShader);
		
		batch.begin();
		textShader.setScale(text.getScale());
		
		text.render(batch, font);
		
		batch.end();
		batch.setShader(oldShader);
		if(fixed) batch.setProjectionMatrix(oldProj);
	}
	
	public void render(SpriteBatch batch, boolean fixed, TextLabel... texts) {
		render(batch, Arrays.asList(texts), fixed);
	}
	

	public void render(SpriteBatch batch, List<TextLabel> texts, boolean fixed) {
		ShaderProgram oldShader = batch.getShader();
		Matrix4 oldProj = batch.getProjectionMatrix();
		
		if(fixed) batch.setProjectionMatrix(fixedProj);
		batch.setShader(textShader);
		batch.begin();
		
		for(TextLabel text : texts) {
			if(lastScale != text.getScale() && lastScale > 0) 
				batch.flush();
			textShader.setScale(text.getScale());
			lastScale = text.getScale();
			text.render(batch, font);
		}
		
		batch.end();
		batch.setShader(oldShader);
		if(fixed) batch.setProjectionMatrix(oldProj);
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	@Override
	public void dispose() {
		textShader.dispose();
		font.dispose();
	}
	
}
