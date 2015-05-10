package com.saltosion.gladiator.input;

import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Global;

public class IRSwing implements InputReceiver {
	
	private Direction dir;
	
	public IRSwing(Direction dir) {
		this.dir = dir;
	}

	@Override
	public boolean pressed() {
		CCombat combat = AppUtil.player.getComponent(CCombat.class);
		combat.inputs.put(dir, true);
		return true;
	}

	@Override
	public boolean released() {
		CCombat combat = AppUtil.player.getComponent(CCombat.class);
		combat.inputs.put(dir, false);
		return true;
	}

}
