package com.saltosion.gladiator.state;

import com.saltosion.gladiator.gui.creators.GUICreator;
import com.saltosion.gladiator.gui.creators.TestGUICreator;
import com.saltosion.gladiator.gui.nodes.ButtonNode;
import com.saltosion.gladiator.level.Level;
import com.saltosion.gladiator.level.TestLevel;
import com.saltosion.gladiator.util.AppUtil;

public class InGameState extends BaseState {

	private Level level;
	private GUICreator guiCreator;

	@Override
	public void create() {
		// Start from a clean slate
		AppUtil.engine.removeAllEntities();
		AppUtil.guiManager.clearGUI();

		level = new TestLevel();
		level.generate();

		guiCreator = new TestGUICreator();
		guiCreator.create();
	}

	@Override
	public void update(float deltaTime) {
	}

	@Override
	public void destroy() {
		// Clear all entities that are left as they are no longer needed
		AppUtil.engine.removeAllEntities();
		// Clear GUI so there's nothing leftover for the next state
		AppUtil.guiManager.clearGUI();
	}

}
