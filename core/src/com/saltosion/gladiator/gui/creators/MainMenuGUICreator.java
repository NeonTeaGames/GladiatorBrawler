package com.saltosion.gladiator.gui.creators;

import com.saltosion.gladiator.gui.nodes.ButtonNode;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class MainMenuGUICreator implements GUICreator {

	private boolean shouldPlay = false;

	@Override
	public void create() {
		TextNode titleText = new TextNode("game_title", "Gladiator Brawler Game Thing!");
		titleText.setPosition(0.23f, 0.8f);
		AppUtil.guiManager.getRootNode().addChild(titleText);

		ButtonNode playButton = new ButtonNode("play_button", SpriteLoader.loadSprite(Name.BUTTON_BIG),
				SpriteLoader.loadSprite(Name.BUTTON_BIG_HOVER)) {
					@Override
					public void pressed(int x, int y, int mouseButton) {
					}

					@Override
					public void released(int x, int y, int mouseButton) {
						shouldPlay = true;
					}
				};
		playButton.setPosition(0.5f, 0.5f);
		TextNode playButtonText = new TextNode("play_button_text", "Play");
		playButtonText.setPosition(-0.0325f, 0.0175f);
		playButton.addChild(playButtonText);
		AppUtil.guiManager.getRootNode().addChild(playButton);
	}

	public boolean shouldPlay() {
		return shouldPlay;
	}

}
