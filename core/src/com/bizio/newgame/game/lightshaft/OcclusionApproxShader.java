package com.bizio.newgame.game.lightshaft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class OcclusionApproxShader extends ShaderProgram {

	public final static String VERT = "shaders/default.vert";
	private final static String FRAG = "shaders/occlusion.frag";
	
	private static final String U_CENTER = "cent";
	
	public OcclusionApproxShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
	}

	public void setCenter(float x, float y) {
		setUniformf(U_CENTER, x, y);
	}
	
}
