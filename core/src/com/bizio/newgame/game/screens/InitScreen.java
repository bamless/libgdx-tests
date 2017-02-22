package com.bizio.newgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.bizio.newgame.game.NewGame;
import com.bizio.newgame.game.ui.TextLabel;
import com.bizio.newgame.game.ui.TextRenderer;

/**
 * Random init screen
 * 
 * @author fabrizio
 *
 */
public class InitScreen extends AbstractScreen {
	
	private TextRenderer textRenderer;
	private TextLabel press;
	
	public InitScreen() {
		textRenderer = new TextRenderer();

		String pressStr;
		switch(Gdx.app.getType()) {
			case Android:
				pressStr = "2 finger tap for water test\n3 finger tap for post process test";
		       	break;
			case Desktop:
				pressStr = "Press 1 for water test\nPress 2 for post process test";
				break;
			default:
				pressStr = "Platform not currently supported";
				break;
		}
		press = new TextLabel(pressStr, 0, 0);
		press.setColor(Color.WHITE);
		
		//center the text
		GlyphLayout layout = new GlyphLayout(textRenderer.getFont(), press.getText());
		press.setCoords(NewGame.getWidth()/2 - layout.width/2, NewGame.getHeight()/2 + layout.height);
	}
	
	@Override
	public void draw() {
		textRenderer.render(batch, false, press);
	}

	@Override
	public void dispose() {
		super.dispose();
		textRenderer.dispose();
	}
}
