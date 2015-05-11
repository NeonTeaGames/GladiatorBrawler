package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;
import com.saltosion.gladiator.listeners.AIListener;

/**
 *
 * @author Jens "Jeasonfire" Pitkänen <jeasonfire@gmail.com>
 */
public class CAI extends Component {

	private AIListener listener;
	private float reactDistance = 1;

	public CAI setReactDistance(float reactDistance) {
		this.reactDistance = reactDistance;
		return this;
	}

	public float getReactDistance() {
		return reactDistance;
	}

	public CAI setAIListener(AIListener listener) {
		this.listener = listener;
		return this;
	}

	public AIListener getAIListener() {
		return listener;
	}

}
