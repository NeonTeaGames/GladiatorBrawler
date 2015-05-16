package com.saltosion.gladiator.state;

import com.saltosion.gladiator.gui.creators.WinGUICreator;
import com.saltosion.gladiator.util.AppUtil;

public class WinState extends BaseState {

	private WinGUICreator guiCreator;

	@Override
	public void create() {
		// Start from a clean slate
		AppUtil.guiManager.clearGUI();

		guiCreator = new WinGUICreator();
		guiCreator.create();
	}

	@Override
	public void update(float deltaTime) {
		if (guiCreator.shouldReturn()) {
			setState(new MainMenuState());
		}
	}

	@Override
	public void destroy() {
		// Clear GUI so there's nothing leftover for the next state
		AppUtil.guiManager.clearGUI();
	}

}
