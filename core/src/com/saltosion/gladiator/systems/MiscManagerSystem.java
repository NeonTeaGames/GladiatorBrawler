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
		for (int i=0; i<entities.size(); i++) {
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
