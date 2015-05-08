package com.saltosion.gladiator.input;

import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.util.AppUtil;

public class IRJump implements InputReceiver {

	@Override
	public boolean pressed() {
		CPhysics physics = AppUtil.player.getComponent(CPhysics.class);
		physics.jumping = true;
		return true;
	}

	@Override
	public boolean released() {
		CPhysics physics = AppUtil.player.getComponent(CPhysics.class);
		physics.jumping = false;
		physics.grounded = true;
		return true;
	}

}
