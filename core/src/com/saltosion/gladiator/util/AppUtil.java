/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
