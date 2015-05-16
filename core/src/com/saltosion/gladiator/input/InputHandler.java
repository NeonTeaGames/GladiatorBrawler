package com.saltosion.gladiator.input;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.saltosion.gladiator.gui.nodes.GUINode;
import com.saltosion.gladiator.gui.properties.ImageProperty;
import com.saltosion.gladiator.gui.properties.InteractiveProperty;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Log;
import com.saltosion.gladiator.util.Name;

public class InputHandler implements InputProcessor {

	public HashMap<Integer, String> keys = new HashMap<Integer, String>();

	private final Array<String> hoveredUIElements = new Array<String>();

	public InputHandler() {
		keys.put(Keys.A, Name.MOVE_LEFT);
		keys.put(Keys.D, Name.MOVE_RIGHT);
		keys.put(Keys.SPACE, Name.JUMP);
		keys.put(Keys.LEFT, Name.SWING_LEFT);
		keys.put(Keys.RIGHT, Name.SWING_RIGHT);
		keys.put(Keys.UP, Name.SWING_UP);
		keys.put(Keys.DOWN, Name.SWING_DOWN);
		keys.put(Keys.F2, Name.DEBUG);
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
		for (String id : hoveredUIElements) {
			GUINode node = AppUtil.guiManager.getNode(id);

			if (node instanceof InteractiveProperty) {
				((InteractiveProperty) node).pressed(screenX, screenY, button);
			} else {
				Log.error("Attempted to call 'pressed' on a non-interactive node!");
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (String id : hoveredUIElements) {
			GUINode node = AppUtil.guiManager.getNode(id);

			if (node instanceof InteractiveProperty) {
				((InteractiveProperty) node).released(screenX, screenY, button);
			} else {
				Log.error("Attempted to call 'released' on a non-interactive node!");
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		RenderingSystem rs = AppUtil.engine.getSystem(RenderingSystem.class);

		float x = (float) (screenX) / rs.screenWidth;
		float y = 1 - (float) (screenY) / rs.screenHeight;

		for (GUINode node : AppUtil.guiManager.getAllRecursiveChildren(AppUtil.guiManager.getRootNode())) {
			if (node instanceof ImageProperty) {
				Sprite s = ((ImageProperty) node).getImage();
				float height = (s.getHeight() * Global.SPRITE_SCALE) / AppUtil.VPHEIGHT_CONST;
				float width = (s.getWidth() * Global.SPRITE_SCALE) / (AppUtil.VPHEIGHT_CONST * rs.aspectratio);
				float x0 = node.getPosition().x - width / 2;
				float x1 = node.getPosition().x + width / 2;
				float y0 = node.getPosition().y - height / 2;
				float y1 = node.getPosition().y + height / 2;
				x += 0.0065f;
				if (node instanceof InteractiveProperty) {
					InteractiveProperty interactiveNode = (InteractiveProperty) node;

					if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
						if (hoveredUIElements.contains(node.getID(), false)) {
							continue;
						}
						hoveredUIElements.add(node.getID());
						interactiveNode.mouseEnter(screenX, screenY);
					} else {
						if (!hoveredUIElements.contains(node.getID(), false)) {
							continue;
						}
						hoveredUIElements.removeValue(node.getID(), false);
						interactiveNode.mouseLeave(screenX, screenY);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
