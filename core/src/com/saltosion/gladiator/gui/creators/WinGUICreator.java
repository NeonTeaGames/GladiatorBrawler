package com.saltosion.gladiator.gui.creators;

import com.saltosion.gladiator.gui.nodes.ButtonNode;
import com.saltosion.gladiator.gui.nodes.GUINode;
import com.saltosion.gladiator.gui.nodes.ImageNode;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class WinGUICreator implements GUICreator {

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

		TextNode gameOverText = new TextNode("win", "You win!");
		gameOverText.setPosition(0.315f, 0.6f);
		menuNode.addChild(gameOverText);

		ButtonNode playButton = new ButtonNode("return_button", SpriteLoader.loadSprite(Name.BUTTON_BIG),
				SpriteLoader.loadSprite(Name.BUTTON_BIG_HOVER)) {
					@Override
					public void pressed(int x, int y, int mouseButton) {
					}

					@Override
					public void released(int x, int y, int mouseButton) {
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
