package com.bizio.newgame.game.water;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bizio.newgame.game.entities.Renderable;
import com.bizio.newgame.game.postprocessing.BlurUtils;

public class Water implements Disposable {
	private static final int DOWNSAMPLING_FACTOR = 1;
	
	private float x;
	private float y;
	private int width;
	private int height;
	
	private Matrix4 waterMat = new Matrix4();
	
	private FrameBuffer refractionFbo;
	private FrameBuffer blurFbo;
	
	public Water(Vector2 coords, int width, int height) {
		this(coords.x, coords.y, width, height);
	}
	
	public Water(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		refractionFbo = new FrameBuffer(Format.RGBA8888, width/DOWNSAMPLING_FACTOR, height/DOWNSAMPLING_FACTOR, false);
		blurFbo = new FrameBuffer(Format.RGBA8888, width/DOWNSAMPLING_FACTOR, height/DOWNSAMPLING_FACTOR, false);
		waterMat.setToOrtho2D(x, y, width, height);	
	}
	
	public void renderRefraction(SpriteBatch batch, List<Renderable> refractors, Viewport vp) {
		//create matrix to render only the scene inside the water bounds
		waterMat.setToOrtho2D(x, y, width, height);		
		//apply the matrix to the batch
		batch.setProjectionMatrix(waterMat);
		
		//render the whole scene to framebuffer
		refractionFbo.begin();
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		///leave a 20px transparent top edge for water deformation
		ScissorStack.pushScissors(new Rectangle(x, y, width, height/DOWNSAMPLING_FACTOR - 20/DOWNSAMPLING_FACTOR));
		batch.begin();
		for(Renderable r : refractors) {
			r.render(batch);
		}
		batch.end();
		ScissorStack.popScissors();
		refractionFbo.end();
		
		//blurs the rendered scene
		BlurUtils.blurTexture(refractionFbo.getColorBufferTexture(), 2, batch, blurFbo, refractionFbo);
		
		//resets the batch matrices
		batch.setProjectionMatrix(vp.getCamera().combined);
		vp.apply();
	}
	
	public Texture getTexture() {
		return refractionFbo.getColorBufferTexture();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void dispose() {
		refractionFbo.dispose();
		blurFbo.dispose();
	}

}
