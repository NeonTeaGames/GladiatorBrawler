package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class HeavenWorshiperAI implements AIListener {

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		CCombat co = host.getComponent(CCombat.class);
		co.inputs.put(Direction.UP, true);
	}

}
