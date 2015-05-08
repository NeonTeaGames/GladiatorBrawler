package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.saltosion.gladiator.components.CPhysics;

/**
 *
 * @author Jens "Jeasonfire" Pitkänen <jeasonfire@gmail.com>
 */
public class PhysicsSystem extends EntitySystem {

    private ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        updateEntities(engine);
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            CPhysics obj = pm.get(entities.get(i));

            // Movement
            if (obj.movable) {
                float move = 0;
                if (obj.movingLeft) {
                    move--;
                }
                if (obj.movingRight) {
                    move++;
                }
                obj.velocity.x = move * obj.movespeed * deltaTime;
                if (obj.jumping && obj.grounded) {
                    obj.grounded = false;
                    obj.velocity.y = obj.jumpForce;
                }
            }

            // Gravity
            if (obj.gravityApplied) {
                obj.velocity.y -= obj.gravity * deltaTime;
            }

            // Collisions
            if (obj.dynamic) {
                for (int j = 0; j < entities.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    CPhysics other = pm.get(entities.get(j));
                    collision(obj, other);
                }
            }

            // Apply movement
            obj.position.add(obj.velocity);
        }
    }

    public void collision(CPhysics cp0, CPhysics cp1) {
        float x00 = cp0.position.x - cp0.size.x / 2;
        float x01 = cp0.position.x + cp0.size.x / 2;
        float x10 = cp1.position.x - cp1.size.x / 2;
        float x11 = cp1.position.x + cp1.size.x / 2;
        float y00 = cp0.position.y - cp0.size.y / 2;
        float y01 = cp0.position.y + cp0.size.y / 2;
        float y10 = cp1.position.y - cp1.size.y / 2;
        float y11 = cp1.position.y + cp1.size.y / 2;

        boolean colliding = (x00 < x11) && (x01 > x10) && (y00 < y11) && (y01 > y10);

        if (!colliding) {
            return;
        }

        if (x00 <= x11 && Math.abs(x00 - x11) < (cp0.size.x + cp1.size.x) / 4) {
            // cp0's left side is colliding with cp1's right side
            if (cp0.velocity.x < 0) {
                // cp0 is going left, stop
                cp0.velocity.x = 0;
            }
        }
        if (x01 > x10 && Math.abs(x01 - x10) < (cp0.size.x + cp1.size.x) / 4) {
            // cp0's right side is colliding with cp1's left side
            if (cp0.velocity.x > 0) {
                // cp0 is going right, stop
                cp0.velocity.x = 0;
            }
        }
        if (y00 <= y11 && Math.abs(y00 - y11) < (cp0.size.y + cp1.size.y) / 4) {
            // cp0's bottom side is colliding with cp1's top side
            if (cp0.velocity.y < 0) {
                // cp0 is going down, stop
                cp0.velocity.y = 0;
            }
            cp0.grounded = true;
        }
        if (y01 > y10 && Math.abs(y01 - y10) < (cp0.size.y + cp1.size.y) / 4) {
            // cp0's top side is colliding with cp1's bottom side
            if (cp0.velocity.y > 0) {
                // cp0 is going up, stop
                cp0.velocity.y = 0;
            }
        }
    }

    public void updateEntities(Engine engine) {
        entities = engine.getEntitiesFor(Family.getFor(CPhysics.class));
    }

}
