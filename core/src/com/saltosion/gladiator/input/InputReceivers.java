package com.saltosion.gladiator.input;

import java.util.HashMap;

import com.saltosion.gladiator.util.Name;

public class InputReceivers {
	public static HashMap<String, InputReceiver> inputreceivers = new HashMap<String, InputReceiver>();
	
	static {
		inputreceivers.put(Name.MOVE_LEFT, new IRMoveLeft());
		inputreceivers.put(Name.MOVE_RIGHT, new IRMoveRight());
	}
	
	public static InputReceiver getReceiver(String key) {
		return inputreceivers.get(key);
	}
}
