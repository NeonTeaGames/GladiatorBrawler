package com.saltosion.gladiator.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.gui.GUIManager;
import com.saltosion.gladiator.level.EntityFactory;

public class AppUtil {

	public static Entity player;
	public static Engine engine;
	public static EntityFactory entityFactory;
	public static GUIManager guiManager;

	public static final int VPHEIGHT_CONST = 24;

	public static final Vector2 JUMP_FORCE = new Vector2(0, 12000);
}
