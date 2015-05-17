package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class ScaredAI implements AIListener {

	private static final ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private static final ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		CCombat c0 = cm.get(host);
		CPhysics p0 = pm.get(host);

		p0.movingLeft = false;
		p0.movingRight = false;
		c0.inputs.put(Direction.UP, false);
		c0.inputs.put(Direction.DOWN, false);
		c0.inputs.put(Direction.LEFT, false);
		c0.inputs.put(Direction.RIGHT, false);

		Direction dir = Direction.DOWN;
		if (Math.random() < 0.5) {
			dir = Direction.UP;
		}
		if (Math.random() < 2 * Gdx.graphics.getDeltaTime()) {
			c0.inputs.put(dir, true);
		}
		for (Entity other : closeEntities) {
			CPhysics p1 = pm.get(other);

			if (p0.getPosition().x + p0.getSize().x / 3 < p1.getPosition().x - p1.getSize().x / 3) {
				p0.movingLeft = true;
			} else if (p0.getPosition().x - p0.getSize().x / 3 > p1.getPosition().x + p1.getSize().x / 3) {
				p0.movingRight = true;
			}
		}
	}

}
