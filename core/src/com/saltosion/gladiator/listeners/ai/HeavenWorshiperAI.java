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
package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class HeavenWorshiperAI implements AIListener {

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		CCombat co = host.getComponent(CCombat.class);
		co.inputs.put(Direction.UP, true);
	}

}
