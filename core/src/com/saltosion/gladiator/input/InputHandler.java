package com.saltosion.gladiator.input;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.saltosion.gladiator.gui.GUINode;
import com.saltosion.gladiator.gui.ImageNode;
import com.saltosion.gladiator.gui.InteractiveNode;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Name;

public class InputHandler implements InputProcessor {

	public HashMap<Integer, String> keys = new HashMap<Integer, String>();
	
	private Array<String> hoveredUIElements = new Array<String>();

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
			for (GUINode node : AppUtil.guiManager.getAllRecursiveChildren(AppUtil.guiManager.getRootNode())) {
				if (node.getID().equals(id)) {
					if (node instanceof InteractiveNode) {
						((InteractiveNode) node).pressed(screenX, screenY, button);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (String id : hoveredUIElements) {
			for (GUINode node : AppUtil.guiManager.getAllRecursiveChildren(AppUtil.guiManager.getRootNode())) {
				if (node.getID().equals(id)) {
					if (node instanceof InteractiveNode) {
						((InteractiveNode) node).released(screenX, screenY, button);
					}
				}
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
		float x = (float)(screenX)/RenderingSystem.screenWidth;
		float y = 1-(float)(screenY)/RenderingSystem.screenHeight;
		
		for (GUINode node : AppUtil.guiManager.getAllRecursiveChildren(AppUtil.guiManager.getRootNode())) {
			if (node instanceof ImageNode) {
				Sprite s = ((ImageNode) node).getImage();
				float height = (s.getHeight()*Global.SPRITE_SCALE)/AppUtil.VPHEIGHT_CONST;
				float width = (s.getWidth()*Global.SPRITE_SCALE)/(AppUtil.VPHEIGHT_CONST*RenderingSystem.aspectratio);
				float x0 = node.getPosition().x-width/2;
				float x1 = node.getPosition().x+width/2;
				float y0 = node.getPosition().y-height/2;
				float y1 = node.getPosition().y+height/2;
				x += 0.01f;
				if (node instanceof InteractiveNode) {
					InteractiveNode interactiveNode = (InteractiveNode) node;
					
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
