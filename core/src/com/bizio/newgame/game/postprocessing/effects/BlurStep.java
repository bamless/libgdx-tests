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

/**
 * A {@link PostProcessingStep} that implements a fullscreen blur
 * 
 * @author fabrizio
 *
 */
public class BlurStep implements PostProcessingStep {

	/** Vertical blur framebuffer */
	private PostProcessFBO verticalBlur;
	/** Horizontal blur framebuffer */
	private PostProcessFBO horBlur;

	/** The shader that implements blurring */
	private BlurShader blurShader;

	/**
	 * @param downSamplingFactor
	 *            how strong the blur will be
	 */
	public BlurStep(int downSamplingFactor) {
		if (downSamplingFactor < 1)
			throw new IllegalArgumentException("downSamplingFactor must be >= 1");

		// creates the 2 needed framebuffers and set the filtering
		verticalBlur = new PostProcessFBO(Format.RGB888, NewGame.getWidth() / (downSamplingFactor / 4),
				NewGame.getHeight() / (downSamplingFactor / 4), false);
		horBlur = new PostProcessFBO(Format.RGB888, NewGame.getWidth() / downSamplingFactor,
				NewGame.getHeight() / downSamplingFactor, false);
		horBlur.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		// creates the blurshader and sets its initial values
		blurShader = new BlurShader();
		blurShader.begin();
		blurShader.setRadius(0.5f);
		blurShader.setResolution(NewGame.getWidth() / downSamplingFactor);
		blurShader.end();
	}

	@Override
	public PostProcessFBO doPostProcessing(SpriteBatch batch, Viewport viewport, PostProcessFBO scene) {
		batch.setShader(blurShader);

		verticalBlur.begin();
		batch.begin();
		blurShader.setDirection(0, 1);

		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		scene.draw(batch);

		batch.flush();
		verticalBlur.end();

		horBlur.begin();
		blurShader.setDirection(1, 0);

		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		verticalBlur.draw(batch);

		batch.end();
		horBlur.end();

		return horBlur;
	}

	@Override
	public void dispose() {
		verticalBlur.dispose();
		horBlur.dispose();
		blurShader.dispose();
	}

}
