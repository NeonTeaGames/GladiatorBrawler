package com.saltosion.gladiator.listeners;

import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.util.Direction;

/**
 *
 * @author Jens "Jeasonfire" Pitkänen <jeasonfire@gmail.com>
 */
public interface CollisionListener {

    /**
     * This method will be called when host collides with other
     *
     * @param side The side which host is colliding other with. Eg. if host is
     * falling towards other, this argument will be CollisionSide.BOTTOM when
     * the collision happens.
     * @param host
     * @param other
     */
    public void collision(Direction side, Entity host, Entity other);

}
