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

import java.util.HashMap;

import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Name;

public class InputReceivers {

	public static final HashMap<String, InputReceiver> inputreceivers = new HashMap<String, InputReceiver>();

	static {
		inputreceivers.put(Name.MOVE_LEFT, new IRMoveLeft());
		inputreceivers.put(Name.MOVE_RIGHT, new IRMoveRight());
		inputreceivers.put(Name.JUMP, new IRJump());
		inputreceivers.put(Name.SWING_LEFT, new IRSwing(Direction.LEFT));
		inputreceivers.put(Name.SWING_RIGHT, new IRSwing(Direction.RIGHT));
		inputreceivers.put(Name.SWING_UP, new IRSwing(Direction.UP));
		inputreceivers.put(Name.SWING_DOWN, new IRSwing(Direction.DOWN));
		inputreceivers.put(Name.DEBUG, new IRDebugToggle());
		inputreceivers.put(Name.SKIP_INTRO, new IRSkipIntros());
	}

	public static InputReceiver getReceiver(String key) {
		return inputreceivers.get(key);
	}
}
