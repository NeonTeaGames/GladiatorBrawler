package com.saltosion.gladiator.input;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.util.AppUtil;

public class IRMoveRight implements InputReceiver {

	@Override
	public boolean pressed() {
		CPhysics physics = AppUtil.player.getComponent(CPhysics.class);
		physics.movingRight = true;
		return true;
	}

	@Override
	public boolean released() {
		CPhysics physics = AppUtil.player.getComponent(CPhysics.class);
		physics.movingRight = false;
		return true;
	}
}
