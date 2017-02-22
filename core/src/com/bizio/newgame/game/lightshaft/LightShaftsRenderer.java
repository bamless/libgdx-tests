package com.bizio.newgame.game.lightshaft;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bizio.newgame.game.NewGame;
import com.bizio.newgame.game.entities.Renderable;
import com.bizio.newgame.game.entities.Sun;
import com.bizio.newgame.game.postprocessing.PostProcessFBO;

public class LightShaftsRenderer implements Disposable {

	private PostProcessFBO occludersFBO;
	private PostProcessFBO radialBlur;
	
	private OccluderShader occluderShader;
	private RadialBlurShader radialBlurShader;
	
	/** Projection matrix used during the blurring*/
	private Matrix4 blurProj;
	
	public LightShaftsRenderer(int downScalingFactor) {
		occludersFBO = new PostProcessFBO(Format.RGBA8888, NewGame.getWidth()/downScalingFactor, NewGame.getHeight()/downScalingFactor, false);
		radialBlur = new PostProcessFBO(Format.RGB888, NewGame.getWidth()/downScalingFactor, NewGame.getHeight()/downScalingFactor, false);
		occludersFBO.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		radialBlur.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		occluderShader = new OccluderShader();
		radialBlurShader = new RadialBlurShader();
		
		blurProj = new Matrix4().setToOrtho2D(0, 0, NewGame.getWidth(), NewGame.getHeight());
	}
	
	//TODO: subsitute SU
	public void renderOccluders(SpriteBatch batch, List<Renderable> occluders, Sun sun, Viewport viewPort) {
		// Renders the occluders in black over the white sun
		renderBlackOccluders(batch, occluders, sun);
		// Applies radial blur in order to obtain the "lightshaft" effect
		applyRadialBlur(batch, sun);
		
		// cleanup and reset operations
		viewPort.apply();
		batch.setProjectionMatrix(viewPort.getCamera().combined);
		batch.enableBlending();
		batch.setShader(null);
	}
	
	private void renderBlackOccluders(SpriteBatch batch, List<Renderable> occluders, Sun sun) {
		occludersFBO.begin();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		batch.setShader(occluderShader);
		batch.begin();
		
		occluderShader.setColor(sun.getColor());
		
		sun.render(batch);
		batch.flush();
		
		occluderShader.setColor(Color.BLACK);
		
		for(Renderable o : occluders) {
			o.render(batch);
		}
		
		batch.end();
		occludersFBO.end();
	}
	
	private void applyRadialBlur(SpriteBatch batch, Sun sun) {
		radialBlur.begin();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		batch.disableBlending();
		batch.setProjectionMatrix(blurProj);
		batch.setShader(radialBlurShader);
		batch.begin();
		radialBlurShader.setCenter(sun.getNormalizedScreenX(), sun.getNormalizedScreenY());
		
		occludersFBO.draw(batch);
		
		batch.end();
		radialBlur.end();
	}
	
	/** Renders the lightshafts using additive blending*/
	public void renderLightShafts(SpriteBatch batch) {
		int oldSrcFunc = batch.getBlendSrcFunc();
        int oldDstFunc = batch.getBlendDstFunc();
        Matrix4 oldProj = batch.getProjectionMatrix();
        batch.setProjectionMatrix(blurProj);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
				
		radialBlur.draw(batch);
		
		batch.setBlendFunction(oldSrcFunc, oldDstFunc);
		batch.setProjectionMatrix(oldProj);
	}
	
	@Override
	public void dispose() {
		occludersFBO.dispose();
		radialBlur.dispose();
		occluderShader.dispose();
		radialBlurShader.dispose();
	}

}
