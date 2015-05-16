package com.saltosion.gladiator.gui.creators;

import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;

public class InGameGUICreator implements GUICreator {

	private TextNode levelChangeText;

	@Override
	public void create() {
		levelChangeText = new TextNode("Level-Change-Text", "Round X");
		levelChangeText.setPosition(0.435f, 0.5f);
		AppUtil.guiManager.getRootNode().addChild(levelChangeText);
	}

	public TextNode getLevelChangeText() {
		return this.levelChangeText;
	}

}
