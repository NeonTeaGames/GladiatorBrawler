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
package com.saltosion.gladiator.level.premade;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CAI;
import com.saltosion.gladiator.level.Level;
import com.saltosion.gladiator.listeners.ai.BerserkerAI;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Global;
import java.util.ArrayList;

public class Round5Level implements Level {

	public Entity player;
	public ArrayList<Entity> enemies = new ArrayList<Entity>();

	@Override
	public String getLevelName() {
		return "Round 5";
	}

	@Override
	public boolean levelCleared() {
		for (Entity enemy : enemies) {
			if ((enemy.flags & Global.FLAG_ALIVE) == Global.FLAG_ALIVE) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean levelFailed() {
		return (player.flags & Global.FLAG_ALIVE) == 0;
	}

	@Override
	public void generate() {
		AppUtil.levelFactory.createLevelBase();
		player = AppUtil.entityFactory.createPlayer(new Vector2(0, 2), Direction.RIGHT);
		enemies.add(AppUtil.entityFactory.createEnemy(new Vector2(10, 2), Direction.LEFT,
				new CAI().setReactDistance(10f).setAIListener(new BerserkerAI())));
		enemies.add(AppUtil.entityFactory.createEnemy(new Vector2(-10, 2), Direction.RIGHT,
				new CAI().setReactDistance(10f).setAIListener(new BerserkerAI())));
	}

}
