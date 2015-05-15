package com.saltosion.gladiator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.saltosion.gladiator.gui.GUIManager;
import com.saltosion.gladiator.input.InputHandler;
import com.saltosion.gladiator.level.EntityFactory;
import com.saltosion.gladiator.state.BaseState;
import com.saltosion.gladiator.state.MainMenuState;
import com.saltosion.gladiator.systems.AISystem;
import com.saltosion.gladiator.systems.CombatSystem;
import com.saltosion.gladiator.systems.MiscManagerSystem;
import com.saltosion.gladiator.systems.PhysicsSystem;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Log;

public class GladiatorBrawler extends ApplicationAdapter {

	private Engine engine;
	private EntityFactory entityFactory;
	private GUIManager guiManager;
	private InputHandler inputHandler;

	private BaseState currentState;

	@Override
	public void create() {
		Log.info("Starting up the game");

		// Initialize the Engine
		engine = new Engine();
		AppUtil.engine = engine;
		setupSystems();

		// Initialize the EntityFactory
		entityFactory = new EntityFactory();
		AppUtil.entityFactory = entityFactory;

		// Initialize GUI
		guiManager = new GUIManager();
		AppUtil.guiManager = this.guiManager;

		// Initialize input
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);

		// Initialize states
		BaseState.setMainClass(this);
		setState(new MainMenuState());

		Log.info("Successfully started the game.");
	}

	private void setupSystems() {
		engine.addSystem(new PhysicsSystem());
		engine.addSystem(new RenderingSystem());
		engine.addSystem(new CombatSystem());
		engine.addSystem(new MiscManagerSystem());
		engine.addSystem(new AISystem());
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
				AISystem ais = engine.getSystem(AISystem.class);
				ais.updateEntities(engine);
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
				AISystem ais = engine.getSystem(AISystem.class);
				ais.updateEntities(engine);
			}
		});
	}

	@Override
	public void render() {
		engine.update(Gdx.graphics.getDeltaTime());
		currentState.update(Gdx.graphics.getDeltaTime());
	}

	public void setState(BaseState newState) {
		if (newState != null) {
			if (currentState != null) {
				currentState.destroy();
			}
			newState.create();
			currentState = newState;
		} else {
			Log.error("Tried to set state to null!");
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		RenderingSystem rs = engine.getSystem(RenderingSystem.class);
		rs.screenHeight = height;
		rs.screenWidth = width;
		float aspectratio = ((float) width) / ((float) height);
		rs.aspectratio = aspectratio;
		rs.setViewport((int) (AppUtil.VPHEIGHT_CONST * aspectratio), AppUtil.VPHEIGHT_CONST);
	}

	@Override
	public void dispose() {
		if (currentState != null) {
			currentState.destroy();
		}
		AppUtil.engine.getSystem(RenderingSystem.class).dispose();
	}
}
