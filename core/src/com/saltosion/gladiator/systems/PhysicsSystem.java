package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.util.Direction;

/**
 *
 * @author Jens "Jeasonfire" Pitkänen <jeasonfire@gmail.com>
 */
public class PhysicsSystem extends EntitySystem {

	private static final float MAX_VEL = 0.5f;
	private ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
	}

	@Override
	public void update(float deltaTime) {
		deltaTime = Math.min(deltaTime, 0.033f);
		for (int i = 0; i < entities.size(); i++) {
			CPhysics obj = pm.get(entities.get(i));

			// Apply movement
			obj.getPosition().add(obj.getVelocity());

			// Movement
			if (obj.isMovable()) {
				float move = 0;
				if (obj.movingLeft) {
					move--;
				}
				if (obj.movingRight) {
					move++;
				}
				obj.getVelocity().x = move * obj.getMovespeed() * deltaTime;
				if (obj.jumping && obj.isGrounded()) {
					obj.setGrounded(false);
					obj.getVelocity().y = obj.getJumpForce();
				}
			}

			// Gravity
			if (obj.isGravityApplied()) {
				obj.getVelocity().y -= obj.getGravity() * deltaTime;
			}

			obj.getVelocity().y = Math.max(Math.min(obj.getVelocity().y, MAX_VEL), -MAX_VEL);

			// Collisions
			if (obj.isProcessCollisions()) {
				for (int j = 0; j < entities.size(); j++) {
					if (i == j) {
						continue;
					}
					collision(entities.get(i), entities.get(j));
				}
			}
		}
	}

	public void collision(Entity entity0, Entity entity1) {
		CPhysics cp0 = pm.get(entity0);
		CPhysics cp1 = pm.get(entity1);

		float x00 = cp0.getPosition().x - cp0.getSize().x / 2;
		float x01 = cp0.getPosition().x + cp0.getSize().x / 2;
		float x10 = cp1.getPosition().x - cp1.getSize().x / 2;
		float x11 = cp1.getPosition().x + cp1.getSize().x / 2;
		float y00 = cp0.getPosition().y - cp0.getSize().y / 2;
		float y01 = cp0.getPosition().y + cp0.getSize().y / 2;
		float y10 = cp1.getPosition().y - cp1.getSize().y / 2;
		float y11 = cp1.getPosition().y + cp1.getSize().y / 2;

		boolean colliding = (x00 < x11) && (x01 > x10) && (y00 < y11) && (y01 > y10);

		if (!colliding) {
			return;
		}

		if (x00 <= x11 && Math.abs(x00 - x11) < (cp0.getSize().x + cp1.getSize().x) / 16) {
			// cp0's left side is colliding with cp1's right side
			if (!cp0.isGhost() && !cp1.isGhost()) {
				if (cp0.getVelocity().x < 0) {
					// cp0 is going left, stop
					cp0.getVelocity().x = 0;
				}
			}

			if (cp0.getCollisionListener() != null) {
				cp0.getCollisionListener().collision(Direction.LEFT, entity0, entity1);
			}
		}
		if (x01 > x10 && Math.abs(x01 - x10) < (cp0.getSize().x + cp1.getSize().x) / 16) {
			// cp0's right side is colliding with cp1's left side
			if (!cp0.isGhost() && !cp1.isGhost()) {
				if (cp0.getVelocity().x > 0) {
					// cp0 is going right, stop
					cp0.getVelocity().x = 0;
				}
			}

			if (cp0.getCollisionListener() != null) {
				cp0.getCollisionListener().collision(Direction.RIGHT, entity0, entity1);
			}
		}
		if (y00 <= y11 && Math.abs(y00 - y11) < (cp0.getSize().y + cp1.getSize().y) / 16) {
			// cp0's bottom side is colliding with cp1's top side
			if (!cp0.isGhost() && !cp1.isGhost()) {
				if (cp0.getVelocity().y < 0) {
					// cp0 is going down, stop
					cp0.getVelocity().y = 0;
				}
				cp0.setGrounded(true);
				cp0.getPosition().y += y11 - y00;
			}

			if (cp0.getCollisionListener() != null) {
				cp0.getCollisionListener().collision(Direction.DOWN, entity0, entity1);
			}
		}
		if (y01 > y10 && Math.abs(y01 - y10) < (cp0.getSize().y + cp1.getSize().y) / 16) {
			// cp0's top side is colliding with cp1's bottom side
			if (!cp0.isGhost() && !cp1.isGhost()) {
				if (cp0.getVelocity().y > 0) {
					// cp0 is going up, stop
					cp0.getVelocity().y = 0;
				}
			}

			if (cp0.getCollisionListener() != null) {
				cp0.getCollisionListener().collision(Direction.UP, entity0, entity1);
			}
		}
	}

	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CPhysics.class));
	}

}
