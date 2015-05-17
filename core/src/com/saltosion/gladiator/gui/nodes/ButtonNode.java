package com.saltosion.gladiator.gui.nodes;

import com.saltosion.gladiator.gui.properties.ImageProperty;
import com.saltosion.gladiator.gui.properties.InteractiveProperty;
import com.saltosion.gladiator.gui.nodes.GUINode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.AudioLoader;
import com.saltosion.gladiator.util.Name;

public abstract class ButtonNode extends GUINode implements InteractiveProperty, ImageProperty {
	private final Sprite onHover;
	private final Sprite normal;
	private boolean hovered = false;
	
	public ButtonNode(String ID, Sprite onHover, Sprite normal) {
		super(ID);
		this.onHover = onHover; 
		this.normal = normal;
	}
	
	public void playButtonPressSound() {
		AppUtil.jukebox.playSound(AudioLoader.getSound(Name.SOUND_BUTTON_PRESS), 
				AppUtil.sfxVolume/2);
	}
	
	public void playButtonReleaseSound() {
		AppUtil.jukebox.playSound(AudioLoader.getSound(Name.SOUND_BUTTON_RELEASE), 
				AppUtil.sfxVolume/2);
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
