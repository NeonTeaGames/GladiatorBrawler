package com.saltosion.gladiator.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.gui.GUIManager;
import com.saltosion.gladiator.input.InputHandler;
import com.saltosion.gladiator.level.EntityFactory;
import com.saltosion.gladiator.level.LevelFactory;

public class AppUtil {

	public static Entity player;
	public static Engine engine;
	public static Jukebox jukebox;
	public static EntityFactory entityFactory;
	public static LevelFactory levelFactory;
	public static GUIManager guiManager;
	public static InputHandler inputHandler;

	public static final int VPHEIGHT_CONST = 24;
	
	public static float sfxVolume = 0.3f;
	public static float musicVolume = 0.7f;

	public static final Vector2 JUMP_FORCE = new Vector2(0, 12000);
}
