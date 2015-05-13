package com.saltosion.gladiator.listeners;

import com.badlogic.ashley.core.Entity;
import java.util.ArrayList;

public interface AIListener {

	/**
	 * @param closeEntities A list of entities that are close to the host entity
	 * than the host entity's reactDistance.
	 * @param host The host entity of this listener
	 */
	public void react(ArrayList<Entity> closeEntities, Entity host);

}
