package com.saltosion.gladiator.input;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.saltosion.gladiator.util.Name;

public class InputHandler implements InputProcessor {
	
	public HashMap<Integer, String> keys = new HashMap<Integer, String>();

	public InputHandler() {
		keys.put(Keys.A, Name.MOVE_LEFT);
		keys.put(Keys.D, Name.MOVE_RIGHT);
		keys.put(Keys.SPACE, Name.JUMP);
		keys.put(Keys.LEFT, Name.SWING_LEFT);
		keys.put(Keys.RIGHT, Name.SWING_RIGHT);
		keys.put(Keys.UP, Name.SWING_UP);
		keys.put(Keys.DOWN, Name.SWING_DOWN);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (!keys.containsKey(keycode)) {
			return false;
		}
		String actionName = keys.get(keycode);
		return InputReceivers.getReceiver(actionName).pressed();
	}

	@Override
	public boolean keyUp(int keycode) {
		if (!keys.containsKey(keycode)) {
			return false;
		}
		String actionName = keys.get(keycode);
		return InputReceivers.getReceiver(actionName).released();
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
