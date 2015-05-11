package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.gui.ButtonNode;
import com.saltosion.gladiator.gui.GUIManager;
import com.saltosion.gladiator.input.InputHandler;
import com.saltosion.gladiator.listeners.CombatListener;
import com.saltosion.gladiator.systems.CombatSystem;
import com.saltosion.gladiator.systems.MiscManagerSystem;
import com.saltosion.gladiator.systems.PhysicsSystem;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Log;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;
import com.saltosion.gladiator.util.SpriteSequence;

public class GladiatorBrawler extends ApplicationAdapter {

	private Engine engine;
	private GUIManager guiManager;
	private InputHandler inputHandler;

	private Entity player;

	@Override
	public void create() {
		// Initialize the Engine
		engine = new Engine();
		AppUtil.engine = engine;

		engine.addSystem(new PhysicsSystem());
		engine.addSystem(new RenderingSystem());
		engine.addSystem(new CombatSystem());
		engine.addSystem(new MiscManagerSystem());
		engine.addEntityListener(new EntityListener() {
			@Override
			public void entityAdded(Entity entity) {
				PhysicsSystem ps = engine.getSystem(PhysicsSystem.class);
				ps.updateEntities(engine);
				RenderingSystem rs = engine.getSystem(RenderingSystem.class);
				rs.updateEntities(engine);
				CombatSystem cs = engine.getSystem(CombatSystem.class);
				cs.updateEntities(engine);
				MiscManagerSystem mms = engine.getSystem(MiscManagerSystem.class);
				mms.updateEntities(engine);
			}

			@Override
			public void entityRemoved(Entity entity) {
				PhysicsSystem ps = engine.getSystem(PhysicsSystem.class);
				ps.updateEntities(engine);
				RenderingSystem rs = engine.getSystem(RenderingSystem.class);
				rs.updateEntities(engine);
				CombatSystem cs = engine.getSystem(CombatSystem.class);
				cs.updateEntities(engine);
				MiscManagerSystem mms = engine.getSystem(MiscManagerSystem.class);
				mms.updateEntities(engine);
			}
		});

		// Initialize GUI
		guiManager = new GUIManager();
		AppUtil.guiManager = this.guiManager;
		initializeTestGUI();
		
		// Initialize stuff in the world
		initializePlayer();
		initializeTestDummy();
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
		player.add(new CCombat().setBaseDamage(100).setHealth(1000));
		engine.addEntity(player);

		AppUtil.player = player;
	}
	
	public void initializeTestDummy() {
		Entity dummy = new Entity();
		CRenderedObject renderedObject = new CRenderedObject();
		Sprite player1 = SpriteLoader.loadSprite(Name.PLAYERIMG, 0, 0, 64, 64);
		Sprite player2 = SpriteLoader.loadSprite(Name.PLAYERIMG, 1, 0, 64, 64);
		SpriteSequence sequence = new SpriteSequence(1).addSprite(player1).addSprite(player2);
		renderedObject.addSequence("Idle", sequence);
		renderedObject.playAnimation("Idle");
		dummy.add(renderedObject);
		dummy.add(new CPhysics().setSize(2, 4).setPosition(-6, 5));
		dummy.add(new CCombat().setBaseDamage(100).setHealth(1000).setSwingCD(.5f).setCombatListener(
				new CombatListener() {
					@Override
					public void died(Entity source, int damageTaken) {
						System.out.println("Nooooo! I died! I will revenge this!");
					}

					@Override
					public void damageTaken(Entity source, int damageTaken) {
						System.out.println(String.format("I took %d damage! Damnit!", damageTaken));
					}
			
		}));
		engine.addEntity(dummy);
		dummy.getComponent(CCombat.class).inputs.put(Direction.UP, true);
	}

	public void initializeLevel() {
		Entity ground = new Entity();

		Sprite groundSprite = SpriteLoader.loadSprite(Name.GROUNDIMG);
		CRenderedObject renderedObject = new CRenderedObject(groundSprite);
		ground.add(renderedObject);
		CPhysics physics = new CPhysics().setMovable(false).setGravityApplied(false).setProcessCollisions(false)
				.setSize(groundSprite.getRegionWidth() * Global.SPRITE_SCALE,
						groundSprite.getRegionHeight() * Global.SPRITE_SCALE);
		physics.getPosition().set(new Vector2(0, -4));
		ground.add(physics);

		Sprite wallSprite = SpriteLoader.loadSprite(Name.WALLIMG);

		Entity wall0 = new Entity();
		CRenderedObject wall0RenderedObject = new CRenderedObject(wallSprite);
		CPhysics wall0Physics = new CPhysics().setMovable(false).setGravityApplied(false).setProcessCollisions(false)
				.setSize(wallSprite.getRegionWidth() * Global.SPRITE_SCALE,
						wallSprite.getRegionHeight() * Global.SPRITE_SCALE);
		wall0Physics.getPosition().set(new Vector2(6, 0));
		wall0.add(wall0RenderedObject);
		wall0.add(wall0Physics);

		Entity wall1 = new Entity();
		CRenderedObject wall1RenderedObject = new CRenderedObject(wallSprite);
		CPhysics wall1Physics = new CPhysics().setMovable(false).setGravityApplied(false).setProcessCollisions(false)
				.setSize(wallSprite.getRegionWidth() * Global.SPRITE_SCALE,
						wallSprite.getRegionHeight() * Global.SPRITE_SCALE);
		wall1Physics.getPosition().set(new Vector2(-6, 0));
		wall1.add(wall1RenderedObject);
		wall1.add(wall1Physics);

		engine.addEntity(ground);
		engine.addEntity(wall0);
		engine.addEntity(wall1);
	}
	
	public void initializeTestGUI() {
		Sprite img1 = SpriteLoader.loadSprite(Name.WALLIMG, 0, 0, 32, 64);
		Sprite img2 = SpriteLoader.loadSprite(Name.WALLIMG, 1, 0, 32, 64);
		System.out.println(img1.getRegionHeight() + " - " + img1.getRegionWidth());
		ButtonNode button = new ButtonNode("test-button", img1, img2) {
			@Override
			public void click(float x, float y, Input.Buttons mouseButton) {
				Log.info("I should never be pressed!");
			}
		};
		button.setPosition(0.12f, 0.5f);
		guiManager.getRootNode().addChild(button);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		RenderingSystem.screenHeight = height;
		RenderingSystem.screenWidth = width;
		RenderingSystem rs = engine.getSystem(RenderingSystem.class);
		float aspectratio = ((float) width) / ((float) height);
		rs.aspectratio = aspectratio;
		rs.setViewport((int) (AppUtil.VPHEIGHT_CONST * aspectratio), AppUtil.VPHEIGHT_CONST);
	}
}
