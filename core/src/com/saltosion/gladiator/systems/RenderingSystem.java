package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.saltosion.gladiator.SpriteSequence;
import com.saltosion.gladiator.components.CRenderedObject;

public class RenderingSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 384, 216);
	}
	
	@Override
	public void update(float deltaTime) {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (int i=0; i<entities.size(); i++) {
			CRenderedObject renderedObject = entities.get(i).getComponent(CRenderedObject.class);
			SpriteSequence currSequence = renderedObject.spritesequences.get(renderedObject.currentSequence);
			int currFrame = (int) Math.floor(renderedObject.currentframe);
			Sprite currSprite = currSequence.getSprite(currFrame);
			batch.draw(currSprite, 0, 0);
		}
		
		batch.end();
	}
	
	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CRenderedObject.class));
	}

}
