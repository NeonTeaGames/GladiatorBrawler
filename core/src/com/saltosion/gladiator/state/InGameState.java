package com.saltosion.gladiator.state;

import com.saltosion.gladiator.gui.creators.InGameGUICreator;
import com.saltosion.gladiator.level.Level;
import com.saltosion.gladiator.level.premade.Round1Level;
import com.saltosion.gladiator.level.premade.Round2Level;
import com.saltosion.gladiator.util.AppUtil;

public class InGameState extends BaseState {

	/**
	 * Add new levels to this list
	 */
	private static final Level[] levels = {new Round1Level(), new Round2Level()};

	private Level level;
	private InGameGUICreator guiCreator;

	private int currentLevel = 0;
	private float timeSinceLevelChange;
	private final float levelChangeTextDelay = 2;
	private boolean levelChangeTextChanged = false;

	private float timeSinceLevelFailed;
	private final float levelFailedDelay = 1.5f;

	private float timeSinceLevelWon;
	private final float levelWonDelay = 1.5f;

	@Override
	public void create() {
		// Start from a clean slate
		AppUtil.engine.removeAllEntities();
		AppUtil.guiManager.clearGUI();

		level = levels[currentLevel];
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

		if (level.levelCleared()) {
			timeSinceLevelWon += deltaTime;
			if (timeSinceLevelWon > levelWonDelay) {
				currentLevel++;
				timeSinceLevelWon = 0;
				if (currentLevel >= levels.length) {
					setState(new WinState());
				} else {
					changeLevel(levels[currentLevel]);
				}
			}
		}

		if (level.levelFailed()) {
			timeSinceLevelFailed += deltaTime;
			if (timeSinceLevelFailed > levelFailedDelay) {
				setState(new GameOverState());
			}
		}
	}

	public void changeLevel(Level level) {
		AppUtil.engine.removeAllEntities();
		this.level = level;
		this.level.generate();
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
