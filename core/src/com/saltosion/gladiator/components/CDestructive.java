package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;

public class CDestructive extends Component {
	
	public CDestructive(float time) {
		this.timeLeft = time;
	}
	
	public float timeLeft = 0;

}
