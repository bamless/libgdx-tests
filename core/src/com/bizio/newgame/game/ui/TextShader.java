package com.bizio.newgame.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TextShader extends ShaderProgram {
	
	public final static String VERT = "shaders/default.vert";
	private static final String FRAG = "shaders/font.frag";
	
	private static final String U_SCALE = "scale";

	public TextShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
	}
	
	public void setScale(float scale) {
		setUniformf(U_SCALE, scale);
	}

}
