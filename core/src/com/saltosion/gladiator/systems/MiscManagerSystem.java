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
package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.saltosion.gladiator.components.CDestructive;
import com.saltosion.gladiator.util.AppUtil;

public class MiscManagerSystem extends EntitySystem {

	private ComponentMapper<CDestructive> dm = ComponentMapper.getFor(CDestructive.class);
	private ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			CDestructive des = dm.get(e);

			if (des.timeLeft > 0) {
				des.timeLeft -= deltaTime;
			} else {
				AppUtil.engine.removeEntity(e);
			}
		}
	}

	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CDestructive.class));
	}

}
