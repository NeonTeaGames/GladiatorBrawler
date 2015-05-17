
package com.saltosion.gladiator.input;

import com.saltosion.gladiator.GladiatorBrawler;
import com.saltosion.gladiator.state.IntroState;
import com.saltosion.gladiator.util.Log;

public class IRSkipIntros implements InputReceiver {

	@Override
	public boolean pressed() {
		if (GladiatorBrawler.currentState instanceof IntroState) {
			((IntroState) GladiatorBrawler.currentState).skipIntros();
		} else {
			Log.error("Tried to skip intros when introstate is not active!");
		}
		return true;
	}

	@Override
	public boolean released() {
		return true;
	}
	
}
