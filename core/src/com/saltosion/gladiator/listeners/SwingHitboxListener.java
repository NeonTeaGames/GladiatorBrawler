package com.saltosion.gladiator.listeners;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.systems.CombatSystem;
import com.saltosion.gladiator.util.AppUtil;
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
			return;
		}
		int damage = cm.get(source).getDamage();		
		CombatSystem.dealDamage(source, other, damage);
	}

}
