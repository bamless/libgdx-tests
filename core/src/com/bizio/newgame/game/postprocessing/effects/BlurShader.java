package com.bizio.newgame.game.postprocessing.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class BlurShader extends ShaderProgram {
	
	private static final String VERT = "shaders/blur.vert";
	private static final String FRAG = "shaders/blur.frag";
	
	private static final String U_RESOLUTION = "resolution";
	private static final String U_RADIUS = "radius";
	private static final String U_DIR = "dir";

	public BlurShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
	}
	
	public void setResolution(float res) {
		setUniformf(U_RESOLUTION, res);
	}
	
	public void setRadius(float radius) {
		setUniformf(U_RADIUS, radius);
	}
	
	public void setDirection(float x, float y) {
		setUniformf(U_DIR, new Vector2(x, y));
	}

}
