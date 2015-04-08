package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saltosion.gladiator.util.GlobalStrings;
import com.saltosion.gladiator.util.SpriteLoader;

public class GladiatorBrawler extends ApplicationAdapter {
	
	private Batch batch;
	private Sprite staticplayer;
	private OrthographicCamera camera;
	private Engine engine;
	
	@Override
	public void create () {
		engine = new Engine();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 384, 216);
		batch = new SpriteBatch();
		staticplayer = SpriteLoader.loadSprite(GlobalStrings.STATICPLAYER);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(staticplayer, 0, 0);
		batch.end();
	}
}
