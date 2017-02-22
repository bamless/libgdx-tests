package com.bizio.newgame.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * A generic entity that has a position and texture, used for testing purposes
 * 
 * @author fabrizio
 *
 */
public class Entity implements Renderable, Disposable {
	protected Texture texture;
	private Vector2 coords = new Vector2();
	
	public Entity(String texturePath, float x, float y) {
		this(new Texture(texturePath), x, y);
	}
	
	public Entity(String texturePath) {
		this(texturePath, 0, 0);
	}
	
	public Entity(Texture texture) {
		this(texture, 0, 0);
	}
	
	public Entity(Texture texture, float x, float y) {
		this.texture = texture;
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		coords.set(x, y);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, coords.x, coords.y);
	}
	
	public void setX(float x) {
		coords.x = x;
	}
	
	public void setY(float y) {
		coords.y = y;
	}
	
	public float getX() {
		return coords.x;
	}
	
	public float getY() {
		return coords.y;
	}
	
	public void setCoords(Vector2 coords) {
		this.coords = coords;
	}
	
	public void setCoords(float x, float y) {
		coords.x = x;
		coords.y = y;
	}
	
	public Vector2 getCoords() {
		return coords;
	}
	
	public int getHeight() {
		return texture.getHeight();
	}
	
	public int getWidth() {
		return texture.getWidth();
	}
	
	public Texture getTexture() {
		return texture;
	}

	@Override
	public void dispose() {
		texture.dispose();
	}

}
