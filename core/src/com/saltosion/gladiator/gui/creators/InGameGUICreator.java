package com.saltosion.gladiator.gui.creators;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.gui.nodes.ButtonNode;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Log;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class InGameGUICreator implements GUICreator {

	private TextNode levelChangeText;

	@Override
	public void create() {
		Sprite img1 = SpriteLoader.loadSprite(Name.WALLIMG, 0, 0, 32, 64);
		Sprite img2 = SpriteLoader.loadSprite(Name.WALLIMG, 1, 0, 32, 64);
		ButtonNode button = new ButtonNode("test-button", img1, img2) {
			@Override
			public void pressed(int x, int y, int mouseButton) {
				Log.info("I should never be pressed against my will!");

			}

			@Override
			public void released(int x, int y, int mouseButton) {
				Log.info("And now I was even released! Blasphemy!");

			}
		};
		button.setPosition(0.12f, 0.5f);
		AppUtil.guiManager.getRootNode().addChild(button);

		TextNode text = new TextNode("test-text", "Test!");
		text.setPosition(0.8f, 0.5f);
		AppUtil.guiManager.getRootNode().addChild(text);

		levelChangeText = new TextNode("Level-Change-Text", "Level change");
		levelChangeText.setPosition(0.4f, 0.5f);
		AppUtil.guiManager.getRootNode().addChild(levelChangeText);
	}

	public TextNode getLevelChangeText() {
		return this.levelChangeText;
	}

}
