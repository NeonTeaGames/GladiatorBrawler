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
package com.saltosion.gladiator.input;

import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;

public class IRSwing implements InputReceiver {

	private final Direction dir;

	public IRSwing(Direction dir) {
		this.dir = dir;
	}

	@Override
	public boolean pressed() {
		CCombat combat = AppUtil.player.getComponent(CCombat.class);
		combat.inputs.put(dir, true);
		return true;
	}

	@Override
	public boolean released() {
		CCombat combat = AppUtil.player.getComponent(CCombat.class);
		combat.inputs.put(dir, false);
		return true;
	}

}
