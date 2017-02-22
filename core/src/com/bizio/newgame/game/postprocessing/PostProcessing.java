package com.bizio.newgame.game.postprocessing;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bizio.newgame.game.NewGame;

/**
 * Class that implements a post processing pipeline.
 * 
 * @author fabrizio
 *
 */
public class PostProcessing implements Disposable {
	private List<PostProcessingStep> postProcessingPipeline = new ArrayList<PostProcessingStep>();
	private Matrix4 postProcProj;
	
	public PostProcessing() {
		postProcProj = new Matrix4().setToOrtho2D(0, 0, NewGame.getWidth(), NewGame.getHeight());
	}
	
	/**
	 * Applies, one after another, all the {@link PostProcessingStep} in postProcessingPipeline on
	 * the inpt fbo (presumably an fbo with the whole scene redered on it) and then draws it on 
	 * screen.
	 * 
	 * @param batch the {@link SpriteBatch}
	 * @param viewPort the used {@link Viewport}
	 * @param sceneFbo the FBO with the scene redered on it
	 */
	public void doPostProcessing(SpriteBatch batch, Viewport viewPort, PostProcessFBO sceneFbo) {
		ShaderProgram oldShader = batch.getShader();
		batch.setProjectionMatrix(postProcProj);
		batch.disableBlending();
		
		PostProcessFBO pipeFBO = sceneFbo;
		
		for(PostProcessingStep p : postProcessingPipeline) {
			pipeFBO = p.doPostProcessing(batch, viewPort, pipeFBO);
		}
		
		batch.setShader(oldShader);
		viewPort.apply();
		
		drawToScreen(batch, pipeFBO);
		
		batch.setProjectionMatrix(viewPort.getCamera().combined);
		batch.enableBlending();
	}
	
	private void drawToScreen(SpriteBatch batch, PostProcessFBO sceneFbo) {
		batch.begin();
		sceneFbo.draw(batch);
		batch.end();
	}

	public void setPostProcessingPipeLine(List<PostProcessingStep> postProcessingPipeLine) {
		if(this.postProcessingPipeline != null) dispose();
		this.postProcessingPipeline = postProcessingPipeLine;
	}
	
	public void addPostProcessingStep(PostProcessingStep... p) {
		for(int i = 0; i < p.length; i++) {
			postProcessingPipeline.add(p[i]);
		}
	}
		
	public void removePostProcessingStep(PostProcessingStep p) {
		postProcessingPipeline.remove(p);
	}
	
	@Override
	public void dispose() {
		for(PostProcessingStep p : postProcessingPipeline) {
			p.dispose();
		}
		postProcessingPipeline.clear();
	}

}
