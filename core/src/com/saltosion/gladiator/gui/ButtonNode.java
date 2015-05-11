package com.saltosion.gladiator.gui;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class ButtonNode extends GUINode implements InteractiveNode, ImageNode {
	private final Sprite onHover;
	private final Sprite normal;
	private boolean hovered = false;
	
	public ButtonNode(String ID, Sprite onHover, Sprite normal) {
		super(ID);
		this.onHover = onHover; 
		this.normal = normal;
	}

	@Override
	public void mouseEnter(float x, float y) {
		hovered = true;
	}

	@Override
	public void mouseLeave(float x, float y){
		hovered = false;
	}

	@Override
	public Sprite getImage() {
		if (hovered) {
			return onHover;
		}
		return normal;
	}
}
