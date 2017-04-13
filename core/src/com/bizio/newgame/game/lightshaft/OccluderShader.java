package com.bizio.newgame.game.lightshaft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class OccluderShader extends ShaderProgram {
	
	public final static String VERT = "shaders/default.vert";
	private final static String FRAG = "shaders/lightshaft.frag";
	
	private final static String U_COLOR = "color";

	public OccluderShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
		if(!isCompiled()) {
			throw new RuntimeException(getLog());
		}
	}
	
	public void setColor(Color color) {
		setUniformf(U_COLOR, color);
	}
	
}
