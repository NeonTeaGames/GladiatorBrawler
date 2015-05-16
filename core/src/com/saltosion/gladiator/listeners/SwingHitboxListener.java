package com.saltosion.gladiator.listeners;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.systems.CombatSystem;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Log;

public class SwingHitboxListener implements CollisionListener {

	private final ArrayList<Entity> hitEntities = new ArrayList<Entity>();
	private final ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);
	private final ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private final Entity source;
	private final Direction direction;

	public SwingHitboxListener(Entity source, Direction direction) {
		this.source = source;
		this.direction = direction;
	}

	@Override
	public void collision(Direction side, Entity host, Entity other) {
		if (other.equals(source) || hitEntities.contains(other)) {
			return; // These entities don't need to take damage
		}
		hitEntities.add(other);

		CCombat otherCombat = cm.get(other);
		if (otherCombat != null) {
			int damage = cm.get(source).getDamage();
			CombatSystem.dealDamage(source, other, damage);
		}

		CPhysics otherPhysics = pm.get(other);
		if (otherPhysics != null && otherPhysics.getCollisionListener() != null
				&& otherPhysics.getCollisionListener() instanceof SwingHitboxListener) {
			Log.info("Clash!");
			float x = 0;
			if (direction == Direction.LEFT) {
				x = -1;
			}
			if (direction == Direction.RIGHT) {
				x = 1;
			}
		}
	}

}
