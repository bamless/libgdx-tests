package com.bizio.newgame.game.lightshaft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class RadialBlurShader extends ShaderProgram {

	private static final String VERT = "shaders/radial.vert";
	private final static String FRAG = "shaders/radial.frag";
	
	private static final String U_CENTER = "cent";
	
	public RadialBlurShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
	}

	public void setCenter(float x, float y) {
		setUniformf(U_CENTER, x, y);
	}
	
}
