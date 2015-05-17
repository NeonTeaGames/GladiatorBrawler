package com.saltosion.gladiator.state;

import com.saltosion.gladiator.gui.creators.IntroGUICreator;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class IntroState extends BaseState {

	private static final float SPLASH_DELAY = 4f;
	private IntroGUICreator guiCreator;
	private float currentTime;
	private boolean forceskip = false;

	@Override
	public void create() {
		// Start from a clean slate
		AppUtil.guiManager.clearGUI();

		SpriteLoader.preload();
		guiCreator = new IntroGUICreator();
		guiCreator.create();
		
		// Enable skipping intro for now
		AppUtil.inputHandler.setInputEnabled(Name.SKIP_INTRO, true);
	}

	@Override
	public void update(float deltaTime) {
		currentTime += deltaTime;
		if ((currentTime > SPLASH_DELAY) || forceskip) {
			boolean finished = guiCreator.nextSplashScreen();
			if (finished && SpriteLoader.loadedAllSprites) {
				setState(new MainMenuState());
			} else if (finished && !guiCreator.getLoadingText().isVisible()) {
				guiCreator.getLoadingText().setVisible(true);
				SpriteLoader.loadAll();
			} else if (!finished) {
				currentTime = 0;
			}
		}
		guiCreator.getSplash().getImage().setAlpha(getCurrentAlpha());
	}

	public float getCurrentAlpha() {
		float f = Math.max(0, Math.min(1, currentTime / SPLASH_DELAY));
		return f < 0.5 ? f * 2 : 1 - ((f - 0.5f) * 2f);
	}
	
	public void skipIntros() {
		forceskip = true;
	}

	@Override
	public void destroy() {
		// And disable it now !
		AppUtil.inputHandler.setInputEnabled(Name.SKIP_INTRO, false);
		
		// Clear GUI so there's nothing leftover for the next state
		AppUtil.guiManager.clearGUI();
	}

}
