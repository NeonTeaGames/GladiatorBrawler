package com.saltosion.gladiator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.saltosion.gladiator.GladiatorBrawler;
import com.saltosion.gladiator.util.Global;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Global.GAME_NAME;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new GladiatorBrawler(), config);
	}
}
