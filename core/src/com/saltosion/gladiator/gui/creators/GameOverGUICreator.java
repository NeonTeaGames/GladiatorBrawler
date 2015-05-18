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

import com.saltosion.gladiator.gui.nodes.ButtonNode;
import com.saltosion.gladiator.gui.nodes.GUINode;
import com.saltosion.gladiator.gui.nodes.ImageNode;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class GameOverGUICreator implements GUICreator {

	private boolean shouldReturn = false;

	@Override
	public void create() {
		ImageNode backgroundImg = new ImageNode("background_image",
				SpriteLoader.loadSprite(Name.MENU_BACKGROUND));
		backgroundImg.setPosition(.5f, .5f);
		AppUtil.guiManager.getRootNode().addChild(backgroundImg);

		GUINode menuNode = new GUINode("menu-node");
		menuNode.setPosition(-.5f, -.5f);
		backgroundImg.addChild(menuNode);

		TextNode gameOverText = new TextNode("game_over", "Game over");
		gameOverText.setPosition(0.315f, 0.6f);
		menuNode.addChild(gameOverText);

		ButtonNode playButton = new ButtonNode("return_button", SpriteLoader.loadSprite(Name.BUTTON_BIG),
				SpriteLoader.loadSprite(Name.BUTTON_BIG_HOVER)) {
					@Override
					public void pressed(int x, int y, int mouseButton) {
						playButtonPressSound();
					}

					@Override
					public void released(int x, int y, int mouseButton) {
						playButtonReleaseSound();
						shouldReturn = true;
					}
				};
		playButton.setPosition(0.4f, 0.4f);
		TextNode playButtonText = new TextNode("return_button_text", "Main Menu");
		playButtonText.setPosition(-0.09f, 0.0175f);
		playButton.addChild(playButtonText);
		menuNode.addChild(playButton);
	}

	public boolean shouldReturn() {
		return shouldReturn;
	}

}
