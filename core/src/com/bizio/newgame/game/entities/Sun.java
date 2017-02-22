package com.bizio.newgame.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bizio.newgame.game.NewGame;

/**
 * TODO: erase and redo
 * 
 * @author fabrizio
 *
 */
public class Sun extends Entity {
	private static final String TEXTURE_PATH = "transpSun.png";
	
	private Camera cam;
	private Color color = new Color(1, 1, 1, 1);
	private Vector2 worldCoords = new Vector2();

	public Sun(float x, float y, Camera cam) {
		super(TEXTURE_PATH);
		this.cam = cam;
		worldCoords.x = x;
		worldCoords.y = y;
		setCoords(x + cam.position.x - NewGame.getWidth()/2, y + cam.position.y - NewGame.getHeight()/2);
	}
	
	public void update() {
		setCoords(worldCoords.x, worldCoords.y);
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, getX(), getY());
	}
	
	public void setColor(float r, float g, float b) {
		color.r = r;
		color.g = g;
		color.b = b;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public void setCoords(float x, float y) {
		super.setCoords(x + cam.position.x - NewGame.getWidth()/2, y + cam.position.y - NewGame.getHeight()/2);
		worldCoords.x = x;
		worldCoords.y = y;
	}
	
	@Override
	public void setCoords(Vector2 coords) {
		this.setCoords(coords.x, coords.y);
	}
	
	public float getNormalizedScreenX() {
		return (worldCoords.x + getWidth()/2) / NewGame.getWidth();
	}
	
	public float getNormalizedScreenY() {
		return (worldCoords.y + getHeight()/2) / NewGame.getHeight();
	}
	
	@Override
	public void setX(float x) {
		this.setCoords(x, getY());
	}
	
	@Override
	public void setY(float y) {
		this.setCoords(getX(), y);
	}

}
