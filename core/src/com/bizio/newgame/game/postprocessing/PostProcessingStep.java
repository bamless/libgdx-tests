package com.bizio.newgame.game.postprocessing;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Interface for implementing a post processing effect to be used in the post process pipeline
 * 
 * @see PostProcessing
 * @author fabrizio
 *
 */
public interface PostProcessingStep extends Disposable {
	
	/**
	 * Perform post processing on the input fbo (presumably an fbo with the whole scene rendered on it).
	 * @param batch - the {@link SpriteBatch}
	 * @param viewport - The scene {@link Viewport}. can be also use to retrieve the camera.
	 * @param scene - the fbo of the scene, with all {@link PostProcessingStep} prior to this one
	 * in the post processing pipeline applied.
	 * 
	 * @return the fbo that has the result of the effect redered on itself
	 * 
	 * @see PostProcessing 
	 */
	PostProcessFBO doPostProcessing(SpriteBatch batch, Viewport viewport, PostProcessFBO scene);
	
}
