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
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Log;
import java.util.ArrayList;

public class RelentlessAI implements AIListener {

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		float dist = -1;
		Entity closestEntity = null;
		CPhysics p0 = host.getComponent(CPhysics.class);
		p0.movingRight = false;
		p0.movingLeft = false;

		for (Entity entity : closeEntities) {
			CPhysics p1 = entity.getComponent(CPhysics.class);
			float newDist = p0.getPosition().cpy().add(p1.getPosition().cpy().scl(-1)).len();
			if (dist == -1 || newDist < dist) {
				dist = newDist;
				closestEntity = entity;
			}
		}
		if (closestEntity == null) {
			return;
		}

		CPhysics p1 = closestEntity.getComponent(CPhysics.class);
		if (p0.getPosition().x + p0.getSize().x / 3 < p1.getPosition().x - p1.getSize().x / 3) {
			p0.movingRight = true;
		} else if (p0.getPosition().x - p0.getSize().x / 3 > p1.getPosition().x + p1.getSize().x / 3) {
			p0.movingLeft = true;
		}

		CCombat c0 = host.getComponent(CCombat.class);
		c0.inputs.put(Direction.RIGHT, false);
		c0.inputs.put(Direction.LEFT, false);
		c0.inputs.put(Direction.UP, false);
		c0.inputs.put(Direction.DOWN, false);
		if (dist < c0.getSwingSize().x + c0.getSwingSize().y) {
			if (p0.getPosition().x + p0.getSize().x / 3 < p1.getPosition().x - p1.getSize().x / 3) {
				c0.inputs.put(Direction.RIGHT, true);
			} else if (p0.getPosition().x - p0.getSize().x / 3 > p1.getPosition().x + p1.getSize().x / 3) {
				c0.inputs.put(Direction.LEFT, true);
			}
			if (p0.getPosition().y < p1.getPosition().y) {
				Log.info("asd");
				c0.inputs.put(Direction.UP, true);
			} else if (p0.getPosition().y > p1.getPosition().y) {
				Log.info("asd");
				c0.inputs.put(Direction.DOWN, true);
			}
		}
	}

}
