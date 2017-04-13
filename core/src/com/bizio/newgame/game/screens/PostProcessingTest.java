package com.bizio.newgame.game.screens;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;
import com.bizio.newgame.game.NewGame;
import com.bizio.newgame.game.entities.Entity;
import com.bizio.newgame.game.entities.Renderable;
import com.bizio.newgame.game.entities.Sun;
import com.bizio.newgame.game.lightshaft.LightShaftsRenderer;
import com.bizio.newgame.game.postprocessing.PostProcessFBO;
import com.bizio.newgame.game.postprocessing.PostProcessing;
import com.bizio.newgame.game.postprocessing.effects.BlurStep;
import com.bizio.newgame.game.postprocessing.effects.CRTStep;
import com.bizio.newgame.game.postprocessing.effects.WarpStep;
import com.bizio.newgame.game.ui.TextLabel;
import com.bizio.newgame.game.ui.TextRenderer;

/**
 * Screen to test the post processing pipeline. 
 * WARNING: contains ugly-ass code
 * 
 * @author fabrizio
 *
 */
public class PostProcessingTest extends AbstractScreen {

	//ENTITIES
	private Entity bg;
	private Entity bg2;
	private Entity tree;
	private Sun sun;
	
	//MOUSE POS
	private Vector2 mousePos = new Vector2();
	
	//POSTPROCESSING STUFF
	private LightShaftsRenderer lightShafts;
	private CRTStep crtStep;
	private BlurStep blurStep;
	private WarpStep warpStep;
	private PostProcessFBO fbo;
	private PostProcessing pp;

	//TEXT
	private TextRenderer textRenderer;
	private TextLabel fps;
	private TextLabel drawCalls;
	private TextLabel keys;
	
	//FLAGS
	private boolean blur;
	private boolean crt;
	private boolean godrays = true;

	@Override
	public void show() {
		bg = new Entity("sky.jpg");
		bg2 = new Entity("bg1.jpg");
		bg2.setCoords(720, 0);
		tree = new Entity("tree.png");
		sun = new Sun(0, 0, camera);
		sun.setCoords(NewGame.getWidth() / 2 - sun.getWidth() / 2, NewGame.getHeight() / 2);
		//SUNSET sun.setColor(255/255f, 168/255f, 87/255f);
		
		lightShafts = new LightShaftsRenderer(4);

		fbo = new PostProcessFBO(Format.RGBA8888, NewGame.getWidth(), NewGame.getHeight(), false);
		fbo.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		pp = new PostProcessing();
		crtStep = new CRTStep();
		blurStep = new BlurStep(8);
		warpStep = new WarpStep();
		pp.addPostProcessingStep(warpStep);
		
		textRenderer = new TextRenderer();
		fps = new TextLabel("00", 0, NewGame.getHeight());
		fps.setScale(1.5f);
		fps.setColor(Color.GREEN);
		drawCalls = new TextLabel("", 0, 20);
		drawCalls.setScale(0.7f);
		drawCalls.setColor(Color.GREEN);
		keys = new TextLabel("L: toggle lightshaft  C: toggle CRT effect\nB: toggle blur"
				+ "           ARROWS: move around", 0, 100);
		keys.setColor(Color.GREEN);
	}

	@Override
	public void update(float delta) {
		sun.update();
		
		//update texts
		fps.setText(Gdx.graphics.getFramesPerSecond() + "");
		drawCalls.setText(String.format("draw calls: %d", batch.totalRenderCalls));
		batch.totalRenderCalls = 0;

		//move stuff
		mousePos.x = Gdx.input.getX();
		mousePos.y = Gdx.input.getY();
		viewPort.unproject(mousePos);
		tree.setCoords(mousePos.x - tree.getWidth() / 2, mousePos.y - tree.getHeight() / 2);
		
		checkInputs(delta);
	}
	
	/** Horrible code written in 10 seconds to check inputs */
	private void checkInputs(float delta) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			if(!blur)
				pp.addPostProcessingStep(blurStep);
			else
				pp.removePostProcessingStep(blurStep);
			blur = !blur;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
			if(!crt)
				pp.addPostProcessingStep(crtStep);
			else
				pp.removePostProcessingStep(crtStep);
			crt = !crt;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.L))
			godrays = !godrays;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3 * delta * 60, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3 * delta * 60, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 3 * delta * 60);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -3 * delta * 60);
		}
	}

	@Override
	public void draw() {
		//we need to render occluders outside of FBO, because the method uses an FBO itself
		if(godrays) 
			lightShafts.renderOccluders(batch, new ArrayList<Renderable>(Arrays.asList(tree)), sun, viewPort);
		
		//Render the whole scene in the FBO
		begin();
		
		renderEntities(bg, bg2, sun, tree);
		
		// Draw lightshafts
		if(godrays) lightShafts.renderLightShafts(batch);
		
		end();
		
		// Apply post processing
		pp.doPostProcessing(batch, viewPort, fbo);
		//render text
		textRenderer.render(batch, true, fps, drawCalls, keys);
	}
	
	private void renderEntities(Entity... entities) {
		for(Entity e : entities)
			e.render(batch);
	}

	private void begin() {
		fbo.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin();
	}
	
	private void end() {
		batch.end();
		fbo.end();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		bg.dispose();
		bg2.dispose();
		tree.dispose();
		fbo.dispose();
		pp.dispose();
		sun.dispose();
		textRenderer.dispose();
		lightShafts.dispose();
		crtStep.dispose();
		blurStep.dispose();
		warpStep.dispose();
	}
}
