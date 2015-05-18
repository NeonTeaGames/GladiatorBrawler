/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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

	public ImageNode setImage(Sprite image) {
		this.image = image;
		return this;
	}

}
