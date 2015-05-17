package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class PanicAI implements AIListener {

	private float swingCd = 0;
	private final float swingInterval;

	public PanicAI() {
		swingInterval = 0.75f;
	}

	public PanicAI(float swingInterval) {
		this.swingInterval = swingInterval;
	}

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

		if (swingCd < swingInterval) {
			swingCd += Gdx.graphics.getDeltaTime();
		} else {
			swingCd = 0;
			switch ((int) Math.ceil(Math.random() * 6)) {
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

}
