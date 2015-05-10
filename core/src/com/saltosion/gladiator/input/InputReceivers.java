package com.saltosion.gladiator.input;

import java.util.HashMap;

import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Name;

public class InputReceivers {
	public static HashMap<String, InputReceiver> inputreceivers = new HashMap<String, InputReceiver>();
	
	static {
		inputreceivers.put(Name.MOVE_LEFT, new IRMoveLeft());
		inputreceivers.put(Name.MOVE_RIGHT, new IRMoveRight());
		inputreceivers.put(Name.JUMP, new IRJump());
		inputreceivers.put(Name.SWING_LEFT, new IRSwing(Direction.LEFT));
		inputreceivers.put(Name.SWING_RIGHT, new IRSwing(Direction.RIGHT));
		inputreceivers.put(Name.SWING_UP, new IRSwing(Direction.UP));
		inputreceivers.put(Name.SWING_DOWN, new IRSwing(Direction.DOWN));
	}
	
	public static InputReceiver getReceiver(String key) {
		return inputreceivers.get(key);
	}
}
