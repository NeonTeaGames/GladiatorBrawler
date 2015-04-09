package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.saltosion.gladiator.SpriteSequence;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.SpriteLoader;

public class RenderingSystem extends EntitySystem {

	private ComponentMapper<CRenderedObject> rom = ComponentMapper.getFor(CRenderedObject.class);
	private ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private ImmutableArray<Entity> entities;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Box2DDebugRenderer debugRenderer;
	private World world;
	
	public RenderingSystem(World world) {
		this.world = world;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		debugRenderer = new Box2DDebugRenderer();
		
		updateEntities(engine);
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1, 1);
	}
	
	public void setViewport(int width, int height) {
		camera.setToOrtho(false, width, height);
	}
	
	@Override
	public void update(float deltaTime) {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i=0; i<entities.size(); i++) {
			CRenderedObject renderedObject = rom.get(entities.get(i));
			SpriteSequence currSequence = renderedObject.getSequence(renderedObject.getCurrentSequence());
			int currFrame = (int) Math.floor(renderedObject.getCurrentFrame());
			Sprite currSprite = currSequence.getSprite(currFrame);
			
			CPhysics physics = pm.get(entities.get(i));
			
			int spriteHeight = currSprite.getRegionHeight();
			int spriteWidth = currSprite.getRegionWidth();
			
			currSprite.setPosition(physics.body.getPosition().x-spriteWidth/2, 
					physics.body.getPosition().y-spriteHeight/2);
			currSprite.setRotation(physics.body.getAngle());
			currSprite.draw(batch);
			
			float nextFrame = renderedObject.getCurrentFrame() + deltaTime*currSequence.getPlayspeed();
			renderedObject.setCurrentFrame(nextFrame%currSequence.frameCount());
		}		
		batch.end();
		
		debugRenderer.render(world, camera.combined);
	}
	
	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CRenderedObject.class, CPhysics.class));
	}

}
