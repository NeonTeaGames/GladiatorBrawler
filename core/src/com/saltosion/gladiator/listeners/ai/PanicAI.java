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
