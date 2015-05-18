/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.saltosion.gladiator.state;

import com.saltosion.gladiator.gui.creators.InGameGUICreator;
import com.saltosion.gladiator.level.Level;
import com.saltosion.gladiator.level.premade.Round1Level;
import com.saltosion.gladiator.level.premade.Round2Level;
import com.saltosion.gladiator.level.premade.Round3Level;
import com.saltosion.gladiator.level.premade.Round4Level;
import com.saltosion.gladiator.level.premade.Round5Level;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.AudioLoader;
import com.saltosion.gladiator.util.Name;

public class InGameState extends BaseState {

	/**
	 * Add new levels to this list
	 */
	private static final Level[] levels = {new Round1Level(), new Round2Level(),
		new Round3Level(), new Round4Level(), new Round5Level()};

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
		// Play music
		AppUtil.jukebox.playMusic(AudioLoader.getMusic(Name.MUSIC_BATTLE));
		AppUtil.jukebox.setMusicVolume(AppUtil.musicVolume / 2);

		// Start from a clean slate
		AppUtil.engine.removeAllEntities();
		AppUtil.guiManager.clearGUI();

		level = levels[currentLevel];
		level.generate();
		timeSinceLevelChange = 0;

		guiCreator = new InGameGUICreator();
		guiCreator.create();

		// Activate inputs
		AppUtil.inputHandler.setInputEnabled(Name.JUMP, true);
		AppUtil.inputHandler.setInputEnabled(Name.MOVE_LEFT, true);
		AppUtil.inputHandler.setInputEnabled(Name.MOVE_RIGHT, true);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_DOWN, true);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_LEFT, true);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_RIGHT, true);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_UP, true);
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

		// Deactivate inputs
		AppUtil.inputHandler.setInputEnabled(Name.JUMP, false);
		AppUtil.inputHandler.setInputEnabled(Name.MOVE_LEFT, false);
		AppUtil.inputHandler.setInputEnabled(Name.MOVE_RIGHT, false);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_DOWN, false);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_LEFT, false);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_RIGHT, false);
		AppUtil.inputHandler.setInputEnabled(Name.SWING_UP, false);

		// Clear all entities that are left as they are no longer needed
		AppUtil.engine.removeAllEntities();
		// Clear GUI so there's nothing leftover for the next state
		AppUtil.guiManager.clearGUI();
	}

}
