package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class BerserkerAI implements AIListener {

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
		if (dist < c0.getSwingSize().x) {
			if (Math.random() < 0.5) {
				switch ((int) Math.ceil(Math.random() * 4)) {
					default:
					case 1:
						c0.inputs.put(Direction.RIGHT, true);
						break;
					case 2:
						c0.inputs.put(Direction.LEFT, true);
						break;
					case 3:
						c0.inputs.put(Direction.UP, true);
						break;
					case 4:
						c0.inputs.put(Direction.DOWN, true);
						break;
				}
			} else if (p0.getPosition().x + p0.getSize().x / 3 < p1.getPosition().x - p1.getSize().x / 3) {
				c0.inputs.put(Direction.RIGHT, true);
			} else if (p0.getPosition().x - p0.getSize().x / 3 > p1.getPosition().x + p1.getSize().x / 3) {
				c0.inputs.put(Direction.LEFT, true);
			} else if (p0.getPosition().y < p0.getPosition().y) {
				c0.inputs.put(Direction.UP, true);
			} else if (p0.getPosition().y > p0.getPosition().y) {
				c0.inputs.put(Direction.DOWN, true);
			}
		}
	}

}
