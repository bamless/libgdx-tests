package com.bizio.newgame.game.postprocessing;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bizio.newgame.game.NewGame;

public class PostProcessFBO extends FrameBuffer {

	public PostProcessFBO(Format format, int width, int height, boolean hasDepth) {
		super(format, width, height, hasDepth);
	}

	public void draw(SpriteBatch batch) {
		batch.draw(getColorBufferTexture(), 0, 0, NewGame.getWidth(), NewGame.getHeight(), 0, 0, 1, 1);
	}
	
}
