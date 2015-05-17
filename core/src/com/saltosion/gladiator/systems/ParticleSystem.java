package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.saltosion.gladiator.components.CParticle;
import com.saltosion.gladiator.util.AppUtil;

public class ParticleSystem extends EntitySystem {

	private static final ComponentMapper<CParticle> pm = ComponentMapper.getFor(CParticle.class);
	private ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); i++) {
			CParticle particle = pm.get(entities.get(i));
			particle.getPosition().add(particle.getVelocity().cpy().scl(deltaTime));
			particle.getVelocity().add(particle.getGravity().cpy().scl(deltaTime));
			if (System.currentTimeMillis() - particle.getCreationTime() > particle.getDecayTime()) {
				AppUtil.engine.removeEntity(entities.get(i));
			}
		}
	}

	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CParticle.class));
	}

}
