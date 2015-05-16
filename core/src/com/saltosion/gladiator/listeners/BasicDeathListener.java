package com.saltosion.gladiator.listeners;

import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.util.Global;

public class BasicDeathListener implements CombatListener {

	@Override
	public void died(Entity source, Entity target, int damageTaken) {
		target.flags &= ~Global.FLAG_ALIVE;
	}

	@Override
	public void damageTaken(Entity source, Entity target, int damageTaken) {
	}

}
