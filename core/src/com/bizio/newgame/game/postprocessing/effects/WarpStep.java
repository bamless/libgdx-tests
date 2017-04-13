package com.bizio.newgame.game.postprocessing.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bizio.newgame.game.NewGame;
import com.bizio.newgame.game.postprocessing.PostProcessFBO;
import com.bizio.newgame.game.postprocessing.PostProcessingStep;

public class WarpStep implements PostProcessingStep {
	private final static String VERT = "shaders/default.vert";
	private final static String FRAG = "shaders/warp.frag";
	private ShaderProgram warpShader;
	PostProcessFBO fbo;
	
	public WarpStep() {
		warpShader = new ShaderProgram(Gdx.files.internal(VERT), Gdx.files.internal(FRAG));
		if(!warpShader.isCompiled()) throw new RuntimeException(warpShader.getLog());
		fbo = new PostProcessFBO(Format.RGB888, NewGame.getWidth(), NewGame.getHeight(), false);
	}
	

	@Override
	public PostProcessFBO doPostProcessing(SpriteBatch batch, Viewport viewport, PostProcessFBO scene) {
		batch.setShader(warpShader);
		fbo.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin();
		scene.draw(batch);
		batch.end();
		fbo.end();
		return fbo;
	}

	@Override
	public void dispose() {
		warpShader.dispose();
		fbo.dispose();
	}
}
