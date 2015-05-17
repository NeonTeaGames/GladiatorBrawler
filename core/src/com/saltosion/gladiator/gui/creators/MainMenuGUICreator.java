package com.saltosion.gladiator.gui.creators;

import com.badlogic.gdx.Gdx;
import com.saltosion.gladiator.gui.nodes.ButtonNode;
import com.saltosion.gladiator.gui.nodes.GUINode;
import com.saltosion.gladiator.gui.nodes.ImageNode;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class MainMenuGUICreator implements GUICreator {

	private boolean shouldPlay = false;

	@Override
	public void create() {
		ImageNode backgroundImg = new ImageNode("background_image",
				SpriteLoader.loadSprite(Name.MENU_BACKGROUND));
		backgroundImg.setPosition(.5f, .5f);
		AppUtil.guiManager.getRootNode().addChild(backgroundImg);

		GUINode menuNode = new GUINode("menu-node");
		menuNode.setPosition(-.5f, -.5f);
		backgroundImg.addChild(menuNode);

		ButtonNode playButton = new ButtonNode("play_button", SpriteLoader.loadSprite(Name.BUTTON_BIG),
				SpriteLoader.loadSprite(Name.BUTTON_BIG_HOVER)) {
					@Override
					public void pressed(int x, int y, int mouseButton) {
						playButtonPressSound();
					}

					@Override
					public void released(int x, int y, int mouseButton) {
						playButtonReleaseSound();
						shouldPlay = true;
					}
				};
		playButton.setPosition(0.4f, 0.5f);
		TextNode playButtonText = new TextNode("play_button_text", "Play");
		playButtonText.setPosition(-0.039f, 0.0175f);
		playButton.addChild(playButtonText);
		menuNode.addChild(playButton);
		
		ButtonNode quitButton = new ButtonNode("quit_button", SpriteLoader.loadSprite(Name.BUTTON_BIG),
				SpriteLoader.loadSprite(Name.BUTTON_BIG_HOVER)) {
					@Override
					public void pressed(int x, int y, int mouseButton) {
						playButtonPressSound();
					}

					@Override
					public void released(int x, int y, int mouseButton) {
						playButtonReleaseSound();
						Gdx.app.exit();
					}
				};
		quitButton.setPosition(0.4f, 0.35f);
		TextNode quitButtonText = new TextNode("quit_button_text", "Quit");
		quitButtonText.setPosition(-0.039f, 0.0175f);
		quitButton.addChild(quitButtonText);
		menuNode.addChild(quitButton);

		ImageNode titleImage = new ImageNode("title_image",
				SpriteLoader.loadSprite(Name.TITLE_LOGO));
		titleImage.setPosition(0.4f, 0.8f);
		menuNode.addChild(titleImage);

		ImageNode gplLogo = new ImageNode("gpl_logo",
				SpriteLoader.loadSprite(Name.GPLV3_LOGO));
		gplLogo.setPosition(0.3f, 0.15f);
		menuNode.addChild(gplLogo);

		ImageNode osiLogo = new ImageNode("osi_logo",
				SpriteLoader.loadSprite(Name.OSI_LOGO));
		osiLogo.setPosition(0.5f, 0.15f);
		menuNode.addChild(osiLogo);
	}

	public boolean shouldPlay() {
		return shouldPlay;
	}

}
