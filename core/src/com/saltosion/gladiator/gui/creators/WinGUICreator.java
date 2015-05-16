package com.saltosion.gladiator.gui.creators;

import com.saltosion.gladiator.gui.nodes.ButtonNode;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class WinGUICreator implements GUICreator {

	private boolean shouldReturn = false;

	@Override
	public void create() {
		TextNode titleText = new TextNode("Game Title", "Gladiator Brawler Game Thing!");
		titleText.setPosition(0.23f, 0.8f);
		AppUtil.guiManager.getRootNode().addChild(titleText);

		TextNode gameOverText = new TextNode("Win", "You win!");
		gameOverText.setPosition(0.415f, 0.6f);
		AppUtil.guiManager.getRootNode().addChild(gameOverText);

		ButtonNode playButton = new ButtonNode("Return Button", SpriteLoader.loadSprite(Name.BUTTON_BIG),
				SpriteLoader.loadSprite(Name.BUTTON_BIG_HOVER)) {
					@Override
					public void pressed(int x, int y, int mouseButton) {
					}

					@Override
					public void released(int x, int y, int mouseButton) {
						shouldReturn = true;
					}
				};
		playButton.setPosition(0.5f, 0.4f);
		TextNode playButtonText = new TextNode("Return Button Text", "Main Menu");
		playButtonText.setPosition(-0.09f, 0.0175f);
		playButton.addChild(playButtonText);
		AppUtil.guiManager.getRootNode().addChild(playButton);
	}

	public boolean shouldReturn() {
		return shouldReturn;
	}

}
