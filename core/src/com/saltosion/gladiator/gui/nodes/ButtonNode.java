package com.saltosion.gladiator.gui.nodes;

import com.saltosion.gladiator.gui.properties.ImageProperty;
import com.saltosion.gladiator.gui.properties.InteractiveProperty;
import com.saltosion.gladiator.gui.nodes.GUINode;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class ButtonNode extends GUINode implements InteractiveProperty, ImageProperty {
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
