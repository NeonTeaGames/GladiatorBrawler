package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
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
		
		engine.addEntityListener(Family.getFor(CRenderedObject.class), 
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
		Sprite staticplayer = SpriteLoader.loadSprite(GlobalStrings.STATICPLAYER);
		renderedObject.spritesequences.put("Idle", new SpriteSequence(staticplayer));
		renderedObject.currentSequence = "Idle";
		
		player.add(renderedObject);
		
		engine.addEntity(player);
	}
}
