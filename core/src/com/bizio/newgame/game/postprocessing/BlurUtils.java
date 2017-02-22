package com.bizio.newgame.game.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.bizio.newgame.game.postprocessing.effects.BlurShader;

public class BlurUtils {

	private BlurUtils() {
	}

	/**
	 * Gets a texture and returns a blurred texture in the form of a FrameBuffer, in retFbo.
	 * To get the texture from the frame buffer use {@link FrameBuffer#getColorBufferTexture()}.
	 * For optimal results the texture and the retFbo should have the same size.
	 * Also, the texture passed in text could be the retFbo's previous colorBufferTexture.
	 * 
	 * @param text the texture to be blurred
	 * @param radius the radius of the blur
	 * @param batch a {@link SpriteBatch}
	 * @param retFbo the fbo on which the blurred texture will be rendered
	 */
	public static void blurTexture(Texture text, float radius, SpriteBatch batch, FrameBuffer temp, FrameBuffer retFbo) {
		
		//creates a 2nd fbo for the two pass gaussian blur
		temp.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		// creates and initialize the blur shader
		BlurShader blurShader = new BlurShader();
		
		//initialize the viewmatrix and saves the old viewmatrix and shader
		Matrix4 camMat = new Matrix4();
		Matrix4 oldMat = batch.getProjectionMatrix();
		ShaderProgram oldShader = batch.getShader();
		
		//change the matrix for the rendering of the previous framebuffer on the 2nd framebuffer
		camMat.setToOrtho2D(0, 0, retFbo.getWidth(), retFbo.getHeight());
		batch.setProjectionMatrix(camMat);// appllying the matrix to the batch
		batch.setShader(blurShader);// setting the blur shader to the batch
		
		batch.disableBlending();

		//render the scene framebuffer to a second framebuffer with horizontal blur
		temp.begin();
		
		batch.begin();
		
		blurShader.setResolution(retFbo.getWidth()); // framebuffer res
		blurShader.setRadius(radius); // blur radius
		blurShader.setDirection(0, 1);// sets the blur on the x (horizontal)
		
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.draw(text, 0, 0);// draws the previous fbo with hor blur
		
		batch.flush();
		temp.end();

		// render the hor blur framebuffer back on the 1st framebuffer with
		// vertical blurring
		retFbo.begin();
		
		// clearing the 1st fbo form the previously rendered scene (if present)
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		blurShader.setDirection(1, 0);// vertical blurring
		batch.draw(temp.getColorBufferTexture(), 0, 0);
		
		batch.end();
		retFbo.end();

		// resetting the old batch shader and the old batch matrix
		batch.setShader(oldShader);
		batch.setProjectionMatrix(oldMat);
		blurShader.dispose();
		
		batch.enableBlending();
	}

}
