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

import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;

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
