package com.saltosion.gladiator.collisionlisteners;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.util.Direction;

public class SwingHitboxListener implements CollisionListener {
	
	private ArrayList<Entity> hitEntities = new ArrayList<Entity>();
	private ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);
	private Entity source;
	
	public SwingHitboxListener(Entity source) {
		this.source = source;
	}

	@Override
	public void collision(Direction side, Entity host, Entity other) {
		if (other.equals(source) || hitEntities.contains(other)) {
			return; // These entities don't need to take damage
		}
		hitEntities.add(other);
		
		CCombat otherCombat = cm.get(other);
		if (otherCombat == null) {
			System.out.println("This entity doesn't have combat!");
			return;
		}
		int damage = cm.get(source).getDamage();
		otherCombat.health -= damage;
		System.out.println("Entity was hit for " + damage + " damage!");
	}

}
