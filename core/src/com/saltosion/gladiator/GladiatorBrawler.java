package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.input.InputHandler;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;
import com.saltosion.gladiator.util.SpriteSequence;

public class GladiatorBrawler extends ApplicationAdapter {
	
	private Engine engine;
	private InputHandler inputHandler;
	
	private float physics_accumulator = 0f;
	
	private Entity player;
	
	@Override
	public void create () {
		
		// Initialize the Engine
		
		engine = new Engine();
		
		engine.addSystem(new RenderingSystem());
		
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
		
		// Initialize input
		
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render () {
		engine.update(Gdx.graphics.getDeltaTime());
	}
	
	public void initializePlayer() {
		
		player = new Entity();
		
		CRenderedObject renderedObject = new CRenderedObject();
		Sprite player1 = SpriteLoader.loadSprite(Name.PLAYERIMG, 0, 0, 64, 64);
		Sprite player2 = SpriteLoader.loadSprite(Name.PLAYERIMG, 1, 0, 64, 64);
		SpriteSequence sequence = new SpriteSequence(1).addSprite(player1).addSprite(player2);
		renderedObject.addSequence("Idle", sequence);
		renderedObject.playAnimation("Idle");
		player.add(renderedObject);
		player.add(new CPhysics());
		
		engine.addEntity(player);
		
		AppUtil.player = player;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		RenderingSystem rs = engine.getSystem(RenderingSystem.class);
		float aspectratio = ((float)width)/((float)height);
		rs.setViewport((int)(AppUtil.VPHEIGHT_CONST*aspectratio), AppUtil.VPHEIGHT_CONST);
	}
}
