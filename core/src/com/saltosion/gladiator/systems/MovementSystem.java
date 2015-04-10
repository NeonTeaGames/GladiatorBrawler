package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CPhysics;

public class MovementSystem extends EntitySystem {

	private ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private ImmutableArray<Entity> entities;
	
	private final Vector2 RIGHT = new Vector2(1, 0);
	private final float MOVEMENT_SPEED = 850;
	private final float MAX_MOVEMENT_SPEED = 10;
	
	private Vector2 newVel = new Vector2(0, 0);
	private Vector2 bodyVel = new Vector2(0, 0);
	
	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
	}
	
	@Override
	public void update(float deltaTime) {
		for (int i=0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			CPhysics physics = pm.get(e);
			
			newVel.setZero();
			if (physics.movingRight) {
				newVel.add(RIGHT);
			}
			if (physics.movingLeft) {
				newVel.add(RIGHT).scl(-1);
			}
			newVel.scl(MOVEMENT_SPEED);
			bodyVel.set(physics.body.getLinearVelocity());
			bodyVel.y = 0;
			newVel.sub(bodyVel.scl(50));
			System.out.println(newVel);
			physics.body.applyLinearImpulse(newVel, physics.body.getPosition(), true);
			/**
			if (Math.abs(bodyVel.x) < MAX_MOVEMENT_SPEED) {
				physics.body.applyLinearImpulse(newVel, physics.body.getPosition(), true);
			}
			*/
		}
	}
	
	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CPhysics.class));
	}

}
