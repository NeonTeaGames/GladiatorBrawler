package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.input.InputHandler;
import com.saltosion.gladiator.systems.PhysicsSystem;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;
import com.saltosion.gladiator.util.SpriteSequence;

public class GladiatorBrawler extends ApplicationAdapter {

	private Engine engine;
	private InputHandler inputHandler;

	private Entity player;

	@Override
	public void create() {
		// Initialize the Engine
		engine = new Engine();

		engine.addSystem(new PhysicsSystem());
		engine.addSystem(new RenderingSystem());
		engine.addEntityListener(new EntityListener() {
			@Override
			public void entityAdded(Entity entity) {
				PhysicsSystem ps = engine.getSystem(PhysicsSystem.class);
				ps.updateEntities(engine);
				RenderingSystem rs = engine.getSystem(RenderingSystem.class);
				rs.updateEntities(engine);
			}

			@Override
			public void entityRemoved(Entity entity) {
				PhysicsSystem ps = engine.getSystem(PhysicsSystem.class);
				ps.updateEntities(engine);
				RenderingSystem rs = engine.getSystem(RenderingSystem.class);
				rs.updateEntities(engine);
			}
		});

		// Initialize stuff in the world
		initializePlayer();
		initializeLevel();

		// Initialize input
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render() {
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
		player.add(new CPhysics().setSize(2, 4).setPosition(0, 5));

		engine.addEntity(player);

		AppUtil.player = player;
	}

	public void initializeLevel() {
		Entity ground = new Entity();

		Sprite groundSprite = SpriteLoader.loadSprite(Name.GROUNDIMG);
		CRenderedObject renderedObject = new CRenderedObject(groundSprite);
		ground.add(renderedObject);
		CPhysics physics = new CPhysics().setMovable(false).setGravityApplied(false).setDynamic(false)
				.setSize(groundSprite.getRegionWidth() * Global.SPRITE_SCALE,
						groundSprite.getRegionHeight() * Global.SPRITE_SCALE);
		physics.getPosition().set(new Vector2(0, -4));
		ground.add(physics);

		Sprite wallSprite = SpriteLoader.loadSprite(Name.WALLIMG);

		Entity wall0 = new Entity();
		CRenderedObject wall0RenderedObject = new CRenderedObject(wallSprite);
		CPhysics wall0Physics = new CPhysics().setMovable(false).setGravityApplied(false).setDynamic(false)
				.setSize(wallSprite.getRegionWidth() * Global.SPRITE_SCALE,
						wallSprite.getRegionHeight() * Global.SPRITE_SCALE);
		wall0Physics.getPosition().set(new Vector2(6, 0));
		wall0.add(wall0RenderedObject);
		wall0.add(wall0Physics);

		Entity wall1 = new Entity();
		CRenderedObject wall1RenderedObject = new CRenderedObject(wallSprite);
		CPhysics wall1Physics = new CPhysics().setMovable(false).setGravityApplied(false).setDynamic(false)
				.setSize(wallSprite.getRegionWidth() * Global.SPRITE_SCALE,
						wallSprite.getRegionHeight() * Global.SPRITE_SCALE);
		wall1Physics.getPosition().set(new Vector2(-6, 0));
		wall1.add(wall1RenderedObject);
		wall1.add(wall1Physics);

		engine.addEntity(ground);
		engine.addEntity(wall0);
		engine.addEntity(wall1);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		RenderingSystem rs = engine.getSystem(RenderingSystem.class);
		float aspectratio = ((float) width) / ((float) height);
		rs.setViewport((int) (AppUtil.VPHEIGHT_CONST * aspectratio), AppUtil.VPHEIGHT_CONST);
	}
}
