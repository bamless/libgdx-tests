package com.bizio.newgame.game.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class WaterShader extends ShaderProgram {
	
	public final static String VERT = "shaders/default.vert";
	private final static String FRAG = "shaders/water.frag";
	
	private static final String U_TIMEDELTA = "timedelta";
	private static final String U_TEXTURE= "u_texture";
	private static final String U_DISPLACEMENT = "u_displacement";

	public WaterShader() {
		super(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
		if(!this.isCompiled()) {
			throw new RuntimeException(this.getLog());
		}
	}
	
	public void setTimeDelta(float timedelta) {
		setUniformf(U_TIMEDELTA, timedelta);
	}
	
	public void setDisplacementTextureUnit(int unit){
		setUniformi(U_DISPLACEMENT, unit);
	}
	
	public void setTextureUnit(int unit) {
		setUniformi(U_TEXTURE, unit);
	}

}
