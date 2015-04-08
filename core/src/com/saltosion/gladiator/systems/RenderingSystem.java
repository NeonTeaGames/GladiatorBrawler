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
import com.saltosion.gladiator.components.CPosition;
import com.saltosion.gladiator.components.CRenderedObject;

public class RenderingSystem extends EntitySystem {
	
	private ImmutableArray<Entity> entities;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	public final int VPHEIGHT_CONST = 252;
	
	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 448, 252);
	}
	
	public void setViewport(int width, int height) {
		camera.setToOrtho(false, width, height);
	}
	
	@Override
	public void update(float deltaTime) {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (int i=0; i<entities.size(); i++) {
			CRenderedObject renderedObject = entities.get(i).getComponent(CRenderedObject.class);
			SpriteSequence currSequence = renderedObject.getSequence(renderedObject.getCurrentSequence());
			int currFrame = (int) Math.floor(renderedObject.getCurrentFrame());
			Sprite currSprite = currSequence.getSprite(currFrame);
			
			CPosition position = entities.get(i).getComponent(CPosition.class);
			
			batch.draw(currSprite, position.x, position.y);
			
			float nextFrame = renderedObject.getCurrentFrame() + deltaTime*currSequence.getPlayspeed();
			renderedObject.setCurrentFrame(nextFrame%currSequence.frameCount());
		}
		
		batch.end();
	}
	
	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CRenderedObject.class, CPosition.class));
	}

}
