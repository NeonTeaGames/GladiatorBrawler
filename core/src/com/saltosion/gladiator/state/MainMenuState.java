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

import com.saltosion.gladiator.gui.creators.MainMenuGUICreator;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.AudioLoader;
import com.saltosion.gladiator.util.Name;

public class MainMenuState extends BaseState {

	private MainMenuGUICreator guiCreator;

	@Override
	public void create() {
		// Play music
		AppUtil.jukebox.playMusic(AudioLoader.getMusic(Name.MUSIC_THEME));
		AppUtil.jukebox.setMusicVolume(AppUtil.musicVolume);

		// Start from a clean slate
		AppUtil.guiManager.clearGUI();

		guiCreator = new MainMenuGUICreator();
		guiCreator.create();
	}

	@Override
	public void update(float deltaTime) {
		if (guiCreator.shouldPlay()) {
			setState(new InGameState());
		}
	}

	@Override
	public void destroy() {
		// Clear GUI so there's nothing leftover for the next state
		AppUtil.guiManager.clearGUI();
	}

}
