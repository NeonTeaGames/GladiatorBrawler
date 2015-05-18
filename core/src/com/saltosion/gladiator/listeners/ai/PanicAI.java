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
package com.saltosion.gladiator.listeners.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.AIListener;
import com.saltosion.gladiator.util.Direction;
import java.util.ArrayList;

public class PanicAI implements AIListener {

	private float stateChange = 0;
	private final float stateChangeInterval;

	public PanicAI() {
		stateChangeInterval = 1;
	}

	public PanicAI(float stateChangeInterval) {
		this.stateChangeInterval = stateChangeInterval;
	}

	@Override
	public void react(ArrayList<Entity> closeEntities, Entity host) {
		stateChange += Gdx.graphics.getDeltaTime();
		if (stateChange > stateChangeInterval) {
			stateChange = 0;
		} else {
			return;
		}
		if (closeEntities.isEmpty()) {
			return;
		}

		CPhysics po = host.getComponent(CPhysics.class);
		CCombat co = host.getComponent(CCombat.class);
		po.movingLeft = false;
		po.movingRight = false;
		po.jumping = false;
		po.setMoveSpeed(16);
		co.inputs.put(Direction.UP, false);
		co.inputs.put(Direction.DOWN, false);
		co.inputs.put(Direction.RIGHT, false);
		co.inputs.put(Direction.LEFT, false);

		switch ((int) Math.ceil(Math.random() * 4)) {
			case 1:
				po.movingLeft = true;
				break;
			case 2:
				po.movingRight = true;
				break;
		}

		switch ((int) Math.ceil(Math.random() * 3)) {
			case 1:
				po.jumping = true;
				break;
		}

		switch ((int) Math.ceil(Math.random() * 8)) {
			case 1:
				co.inputs.put(Direction.UP, true);
				break;
			case 2:
				co.inputs.put(Direction.DOWN, true);
				break;
			case 3:
				co.inputs.put(Direction.RIGHT, true);
				break;
			case 4:
				co.inputs.put(Direction.LEFT, true);
				break;
		}

	}

}
