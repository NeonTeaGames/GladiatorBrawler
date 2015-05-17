/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saltosion.gladiator.gui.nodes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.gui.properties.ImageProperty;

/**
 *
 * @author somersby
 */
public class ImageNode extends GUINode implements ImageProperty {
	
	private Sprite image;
	
	public ImageNode(String ID, Sprite image) {
		super(ID);
		this.image = image;
	}

	@Override
	public Sprite getImage() {
		return this.image;
	}
	
}
