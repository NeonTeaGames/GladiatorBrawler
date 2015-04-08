package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.components.CPosition;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.GlobalStrings;
import com.saltosion.gladiator.util.SpriteLoader;

public class GladiatorBrawler extends ApplicationAdapter {
	
	private Engine engine;
	
	private Entity player;
	
	@Override
	public void create () {
		// Initialize the Engine
		
		engine = new Engine();
		
		engine.addSystem(new RenderingSystem());
		
		engine.addEntityListener(Family.getFor(CRenderedObject.class, CPosition.class), 
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
		
		// Initialize player
		
		initializePlayer();
		
	}

	@Override
	public void render () {
		engine.update(Gdx.graphics.getDeltaTime());
	}
	
	public void initializePlayer() {
		player = new Entity();
		
		CRenderedObject renderedObject = new CRenderedObject();
		Sprite player1 = SpriteLoader.loadSprite(GlobalStrings.PLAYERIMG, 0, 0, 64, 64);
		Sprite player2 = SpriteLoader.loadSprite(GlobalStrings.PLAYERIMG, 1, 0, 64, 64);
		SpriteSequence sequence = new SpriteSequence(1).addSprite(player1).addSprite(player2);
		renderedObject.addSequence("Idle", sequence);
		renderedObject.setCurrentSequence("Idle");
		player.add(renderedObject);
		player.add(new CPosition());
		player.getComponent(CPosition.class).x = 50;
		player.getComponent(CPosition.class).y = 50;
		
		engine.addEntity(player);
	}
}
