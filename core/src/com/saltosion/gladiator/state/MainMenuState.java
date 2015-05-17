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
