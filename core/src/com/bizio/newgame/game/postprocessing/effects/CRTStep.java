package com.bizio.newgame.game.postprocessing.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bizio.newgame.game.NewGame;
import com.bizio.newgame.game.postprocessing.PostProcessFBO;
import com.bizio.newgame.game.postprocessing.PostProcessingStep;

public class CRTStep implements PostProcessingStep {

	private PostProcessFBO crt;
	private PostProcessFBO fisheye;
	private CRTShader crtShader;
	private FishEyeShader fisheyeShader;

	public CRTStep() {
		crt = new PostProcessFBO(Format.RGB888, NewGame.getWidth(), NewGame.getHeight(), false);
		fisheye = new PostProcessFBO(Format.RGB888, NewGame.getWidth(), NewGame.getHeight(), false);
		crt.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		fisheye.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		crtShader = new CRTShader();
		fisheyeShader = new FishEyeShader();
	}

	@Override
	public PostProcessFBO doPostProcessing(SpriteBatch batch, Viewport viewport, PostProcessFBO scene) {
		batch.setShader(crtShader);
		crt.begin();
		batch.begin();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		scene.draw(batch);

		batch.end();
		crt.end();

		batch.setShader(fisheyeShader);
		batch.begin();
		fisheye.begin();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		crt.draw(batch);

		batch.end();
		fisheye.end();

		return fisheye;
	}

	@Override
	public void dispose() {
		crt.dispose();
		crtShader.dispose();
		fisheye.dispose();
		fisheyeShader.dispose();
	}

}
