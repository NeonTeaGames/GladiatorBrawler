/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.saltosion.gladiator.listeners;

import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.util.Direction;

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
