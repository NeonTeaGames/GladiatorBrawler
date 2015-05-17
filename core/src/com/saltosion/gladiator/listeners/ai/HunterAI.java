package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class HunterAI implements AIListener {

	private static final ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private static final ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);

	private float lastRush = 0;
	private final float rushInterval;

	public HunterAI() {
		this.rushInterval = 1f;
	}

	public HunterAI(float rushInterval) {
		this.rushInterval = rushInterval;
	}

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		lastRush += Gdx.graphics.getDeltaTime();
		if (lastRush > rushInterval) {
			lastRush = 0;
		} else {
			return;
		}

		CCombat c0 = cm.get(host);
		CPhysics p0 = pm.get(host);

		p0.movingLeft = false;
		p0.movingRight = false;
		c0.inputs.put(Direction.UP, false);
		c0.inputs.put(Direction.DOWN, false);
		c0.inputs.put(Direction.LEFT, false);
		c0.inputs.put(Direction.RIGHT, false);

		for (Entity other : closeEntities) {
			CPhysics p1 = pm.get(other);

			if (p0.getPosition().x + p0.getSize().x / 3 < p1.getPosition().x - p1.getSize().x / 3) {
				p0.movingRight = true;
				c0.inputs.put(Direction.RIGHT, true);
			} else if (p0.getPosition().x - p0.getSize().x / 3 > p1.getPosition().x + p1.getSize().x / 3) {
				p0.movingLeft = true;
				c0.inputs.put(Direction.LEFT, true);
			}
		}
	}

}
