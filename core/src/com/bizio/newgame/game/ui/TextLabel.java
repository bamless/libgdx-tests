package com.bizio.newgame.game.ui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TextLabel {
	
	private String text;
	private Vector2 coords;
	private float scale = 1;
	private Color color = new Color();
	
	public TextLabel(String text, Vector2 coords) {
		this.text = text;
		this.coords = coords;
		this.color.set(Color.BLACK);
	}
	
	public TextLabel(String text, float x, float y) {
		this(text, new Vector2(x, y));
	}
	
	public TextLabel(String text, Vector2 coords, float scale) {
		this(text, coords);
		this.scale = scale;
	}
	
	public TextLabel(String text, float x, float y, float scale) {
		this(text, x, y);
		this.scale = scale;
	}
	
	protected void render(SpriteBatch batch, BitmapFont font) {
		final float oldScale = font.getData().scaleX;
		
		font.getData().setScale(scale);
		font.setColor(color);
		
		font.draw(batch, text, getX(), getY());
		
		font.setColor(Color.WHITE);
		font.getData().setScale(oldScale);
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Vector2 getCoords() {
		return coords;
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
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

	public String getText() {
		return text;
	}

	public void setCoords(float x, float y) {
		this.coords.x = x;
		this.coords.y = y;
	}
	
}
