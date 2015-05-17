package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.Entity;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class PanicAI implements AIListener {

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		CPhysics po = host.getComponent(CPhysics.class);
		CCombat co = host.getComponent(CCombat.class);
		co.inputs.put(Direction.UP, false);
		co.inputs.put(Direction.DOWN, false);
		co.inputs.put(Direction.RIGHT, false);
		co.inputs.put(Direction.LEFT, false);
		if (closeEntities.isEmpty()) {
			po.jumping = false;
			return;
		}

		if (po.isGrounded()) {
			po.jumping = true;
		}

		switch ((int) Math.ceil(Math.random() * 4)) {
			default:
			case 1:
				co.inputs.put(Direction.LEFT, true);
				break;
			case 2:
				co.inputs.put(Direction.RIGHT, true);
				break;
			case 3:
				co.inputs.put(Direction.DOWN, true);
				break;
			case 4:
				co.inputs.put(Direction.UP, true);
				break;
		}
	}

}
