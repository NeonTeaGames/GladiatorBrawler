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

public interface CombatListener {

	/**
	 * This is called when the entity dies.
	 *
	 * @param source The Entity that dealt the damage. Null if called without
	 * source.
	 * @param target The target of the damage. Should never be null.
	 * @param damageTaken The damage dealt, that killed the entity.
	 */
	public void died(Entity source, Entity target, int damageTaken);

	/**
	 * This is called when the entity takes damage.
	 *
	 * @param source Source of the damage. Null if called without source.
	 * @param target The target of the damage. Should never be null.
	 * @param damageTaken Amout of damage taken.
	 */
	public void damageTaken(Entity source, Entity target, int damageTaken);
}
