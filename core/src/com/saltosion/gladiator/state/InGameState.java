package com.saltosion.gladiator.state;

import com.saltosion.gladiator.gui.creators.GUICreator;
import com.saltosion.gladiator.gui.creators.InGameGUICreator;
import com.saltosion.gladiator.level.Level;
import com.saltosion.gladiator.level.TestLevel;
import com.saltosion.gladiator.util.AppUtil;

public class InGameState extends BaseState {

	private Level level;
	private InGameGUICreator guiCreator;

	private float timeSinceLevelChange;
	private final float levelChangeTextDelay = 2;
	private boolean levelChangeTextChanged = false;

	@Override
	public void create() {
		// Start from a clean slate
		AppUtil.engine.removeAllEntities();
		AppUtil.guiManager.clearGUI();

		level = new TestLevel();
		level.generate();
		timeSinceLevelChange = 0;

		guiCreator = new InGameGUICreator();
		guiCreator.create();
	}

	@Override
	public void update(float deltaTime) {
		if (timeSinceLevelChange < levelChangeTextDelay) {
			timeSinceLevelChange += deltaTime;
			if (!levelChangeTextChanged) {
				guiCreator.getLevelChangeText().setVisible(true);
				guiCreator.getLevelChangeText().setText(level.getLevelName());
				levelChangeTextChanged = true;
			}
		} else {
			guiCreator.getLevelChangeText().setVisible(false);
			levelChangeTextChanged = false;
		}
	}

	public void changeLevel(Level level) {
		AppUtil.engine.removeAllEntities();
		this.level = level;
		this.timeSinceLevelChange = 0;
	}

	@Override
	public void destroy() {
		// Clear all entities that are left as they are no longer needed
		AppUtil.engine.removeAllEntities();
		// Clear GUI so there's nothing leftover for the next state
		AppUtil.guiManager.clearGUI();
	}

}
