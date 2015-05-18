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

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class DummyAI implements AIListener {

	private static final ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private static final ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		CPhysics p0 = pm.get(host);
		CCombat c0 = cm.get(host);
		c0.inputs.put(Direction.UP, false);
		c0.inputs.put(Direction.DOWN, false);
		c0.inputs.put(Direction.LEFT, false);
		c0.inputs.put(Direction.RIGHT, false);
		for (Entity other : closeEntities) {
			CPhysics p1 = pm.get(other);

			if (p0.getPosition().x + p0.getSize().x / 3 < p1.getPosition().x - p1.getSize().x / 3) {
				c0.inputs.put(Direction.RIGHT, true);
			} else if (p0.getPosition().x - p0.getSize().x / 3 > p1.getPosition().x + p1.getSize().x / 3) {
				c0.inputs.put(Direction.LEFT, true);
			} else if (p0.getPosition().y + p0.getSize().y / 3 < p1.getPosition().y - p1.getSize().y / 3) {
				c0.inputs.put(Direction.UP, true);
			} else if (p0.getPosition().y - p0.getSize().y / 3 > p1.getPosition().y + p1.getSize().y / 3) {
				c0.inputs.put(Direction.DOWN, true);
			}
		}
	}

}
