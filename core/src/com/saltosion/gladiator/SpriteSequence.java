package com.saltosion.gladiator;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteSequence {
	
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private float defaultPlayspeed = 1;
	
	/**
	 * A static single image.
	 * @param sprite
	 */
	public SpriteSequence(Sprite sprite) {
		sprites.add(sprite);
		defaultPlayspeed = 0;
	}
	
	public SpriteSequence(float playspeed, ArrayList<Sprite> sprites) {
		this.defaultPlayspeed = playspeed;
		if (sprites != null) {
			this.sprites = sprites;
		}
	}
	
	public Sprite getSprite(int index) {
		return sprites.get(index);
	}
	
	public float getPlayspeed() {
		return defaultPlayspeed;
	}

}
