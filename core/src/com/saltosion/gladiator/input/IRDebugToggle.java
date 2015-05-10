package com.saltosion.gladiator.input;

import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;

/**
 *
 * @author Jens "Jeasonfire" Pitkänen <jeasonfire@gmail.com>
 */
public class IRDebugToggle implements InputReceiver {

	@Override
	public boolean pressed() {
		RenderingSystem renderingSystem = AppUtil.engine.getSystem(RenderingSystem.class);
		if (renderingSystem != null) {
			renderingSystem.setDebug(!renderingSystem.getDebug());
		}
		return true;
	}

	@Override
	public boolean released() {
		return false;
	}

}
