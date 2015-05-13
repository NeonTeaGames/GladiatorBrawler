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
