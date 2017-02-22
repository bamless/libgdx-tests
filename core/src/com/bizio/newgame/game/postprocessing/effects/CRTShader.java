package com.bizio.newgame.game.postprocessing.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class CRTShader extends ShaderProgram {

	public static final String VERT = "shaders/crt.vert";
	public final static String FRAG = "shaders/crt.frag";

	public CRTShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
		if (!isCompiled()) {
			throw new RuntimeException(getLog());
		}
	}

}
