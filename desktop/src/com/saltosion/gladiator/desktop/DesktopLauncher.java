package com.saltosion.gladiator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.saltosion.gladiator.GladiatorBrawler;
import com.saltosion.gladiator.util.GlobalStrings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = GlobalStrings.GAME_NAME;
		config.width = 720;
		config.height = 480;
		new LwjglApplication(new GladiatorBrawler(), config);
	}
}
