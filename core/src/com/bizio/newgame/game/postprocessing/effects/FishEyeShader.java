package com.bizio.newgame.game.postprocessing.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class FishEyeShader extends ShaderProgram {

	private final static String VERT = "shaders/fisheye.vert";
	private final static String FRAG = "shaders/fisheye.frag";

	public FishEyeShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
		if (!isCompiled()) {
			throw new RuntimeException(getLog());
		}
	}

}
