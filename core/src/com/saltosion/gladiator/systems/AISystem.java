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
import com.saltosion.gladiator.components.CAI;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import java.util.ArrayList;

public class AISystem extends EntitySystem {

	private static final ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private static final ComponentMapper<CAI> aim = ComponentMapper.getFor(CAI.class);
	private ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
	}

	@Override
	public void update(float deltaTime) {
		// Loop through all entities that have CCombat
		for (int i = 0; i < entities.size(); i++) {
			// If current entity doesn't have CAI, skip current entity
			if (aim.get(entities.get(i)) == null) {
				continue;
			}

			CPhysics cp0 = pm.get(entities.get(i));
			CAI cai = aim.get(entities.get(i));

			// React
			ArrayList<Entity> reactEntities = new ArrayList<Entity>();
			// Loop through all entities with CCombat
			for (int j = 0; j < entities.size(); j++) {
				if (i == j) {
					continue;
				}
				CPhysics cp1 = pm.get(entities.get(j));
				float x = cp1.getPosition().x - cp0.getPosition().x;
				float y = cp1.getPosition().y - cp0.getPosition().y;
				float len = (float) Math.sqrt(x * x + y * y);
				if (len <= cai.getReactDistance()) {
					reactEntities.add(entities.get(j));
				}
			}
			AIListener listener = cai.getAIListener();
			if (listener != null) {
				listener.react(reactEntities, entities.get(i));
			}
		}
	}

	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CCombat.class));
	}
}
