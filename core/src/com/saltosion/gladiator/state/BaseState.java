package com.saltosion.gladiator.state;

import com.saltosion.gladiator.GladiatorBrawler;

public abstract class BaseState {

	/**
	 * This is private because it should really only be used to change states
	 * (for now)
	 */
	private static GladiatorBrawler mainClass;

	public abstract void create();

	public abstract void update(float deltaTime);

	public abstract void destroy();

	public void setState(BaseState newState) {
		mainClass.setState(newState);
	}

	public static void setMainClass(GladiatorBrawler mainClass) {
		BaseState.mainClass = mainClass;
	}

}
