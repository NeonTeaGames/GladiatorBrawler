package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.SpriteLoader;

public class GladiatorBrawler extends ApplicationAdapter {
	
	private Engine engine;
	private World world;
	private float physics_accumulator = 0f;
	
	
	private Entity player;
	
	@Override
	public void create () {
		// Initializing Physics
		
		world = new World(new Vector2(0, -10), true);
		
		// Initialize the Engine
		
		engine = new Engine();
		
		engine.addSystem(new RenderingSystem(world));
		
		engine.addEntityListener(Family.getFor(), 
				new EntityListener() {
					@Override
					public void entityRemoved(Entity entity) {
						RenderingSystem rs = engine.getSystem(RenderingSystem.class);
						rs.updateEntities(engine);						
					}
					
					@Override
					public void entityAdded(Entity entity) {
						RenderingSystem rs = engine.getSystem(RenderingSystem.class);
						rs.updateEntities(engine);
						
					}
				});
		
		
		// Initialize stuff in the world
		
		initializePlayer();
		initializeTerrain();
		
	}

	@Override
	public void render () {
		engine.update(Gdx.graphics.getDeltaTime());
		physicsStep(Gdx.graphics.getDeltaTime());
	}
	
	private void physicsStep(float deltaTime) {
		float frameTime = Math.max(deltaTime, 0.25f);
		physics_accumulator += frameTime;
		if (physics_accumulator >= Global.PHYSICS_TIMESTEP) {
			world.step(Global.PHYSICS_TIMESTEP, 6, 2);
			physics_accumulator -= Global.PHYSICS_TIMESTEP;
		}
	}
	
	public void initializePlayer() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(5, 5);
		Body body = world.createBody(bodyDef);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(2, 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		Fixture fixture =  body.createFixture(fixtureDef);
		
		box.dispose();
		
		player = new Entity();
		
		CRenderedObject renderedObject = new CRenderedObject();
		Sprite player1 = SpriteLoader.loadSprite(Global.PLAYERIMG, 0, 0, 64, 64);
		Sprite player2 = SpriteLoader.loadSprite(Global.PLAYERIMG, 1, 0, 64, 64);
		SpriteSequence sequence = new SpriteSequence(1).addSprite(player1).addSprite(player2);
		renderedObject.addSequence("Idle", sequence);
		renderedObject.setCurrentSequence("Idle");
		player.add(renderedObject);
		player.add(new CPhysics());
		player.getComponent(CPhysics.class).body = body;
		
		engine.addEntity(player);
	}
	
	public void initializeTerrain() {
		BodyDef terrain = new BodyDef();
		Body terrainBody = world.createBody(terrain);
		PolygonShape terrainBox = new PolygonShape();
		terrainBox.setAsBox(20, 2);
		terrainBody.createFixture(terrainBox, 0);
		terrainBox.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		RenderingSystem rs = engine.getSystem(RenderingSystem.class);
		float aspectratio = ((float)width)/((float)height);
		rs.setViewport((int)(Global.VPHEIGHT_CONST*aspectratio), Global.VPHEIGHT_CONST);
	}
}
