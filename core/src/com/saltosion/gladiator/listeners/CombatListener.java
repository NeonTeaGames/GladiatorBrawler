package com.saltosion.gladiator.listeners;

import com.badlogic.ashley.core.Entity;

public interface CombatListener {
	
	/**
	 * This is called when the entity dies.
	 * @param source The Entity that dealt the damage. Null if called without source.
	 * @param damageTaken The damage dealt, that killed the entity.
	 */
	public void died(Entity source, int damageTaken);
	
	/**
	 * This is called when the entity takes damage.
	 * @param source Source of the damage. Null if called without source.
	 * @param damageTaken Amout of damage taken.
	 */
	public void damageTaken(Entity source, int damageTaken);
}
