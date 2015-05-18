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
package com.saltosion.gladiator.gui.creators;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.gui.nodes.ImageNode;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class IntroGUICreator implements GUICreator {

	// These sprites should be loaded before the initialization of this class
	private final Sprite[] splashScreens = {SpriteLoader.loadSprite(Name.SPLASH_SALTOSION),
		SpriteLoader.loadSprite(Name.SPLASH_LIBGDX)};
	private int currentSplash = 0;
	private ImageNode splash;
	private TextNode loadingText;

	@Override
	public void create() {
		splash = new ImageNode("splash_image", splashScreens[currentSplash]);
		splash.setPosition(0.5f, 0.5f);
		AppUtil.guiManager.getRootNode().addChild(splash);

		loadingText = new TextNode("loading_text", "Loading");
		loadingText.setPosition(0.43f, 0.5f);
		loadingText.setVisible(false);
		AppUtil.guiManager.getRootNode().addChild(loadingText);
	}

	/**
	 * @return Returns true if there is no next splash screen
	 */
	public boolean nextSplashScreen() {
		currentSplash++;
		if (currentSplash >= splashScreens.length) {
			return true;
		}
		splash.setImage(splashScreens[currentSplash]);
		return false;
	}

	public int getCurrentSplashIndex() {
		return currentSplash;
	}

	public ImageNode getSplash() {
		return splash;
	}

	public TextNode getLoadingText() {
		return loadingText;
	}

}
