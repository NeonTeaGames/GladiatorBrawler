package com.saltosion.gladiator.components;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.SpriteSequence;

public class CRenderedObject extends Component {
	private HashMap<String, SpriteSequence> spritesequences = new HashMap<String, SpriteSequence>();
	private String currentSequence = "";
	private float currentframe = 0;
	
	public CRenderedObject() {}
	
	/**
	 * Can be used if the Rendered Object is a single static image always.
	 * @param sprite
	 */
	public CRenderedObject(Sprite sprite) {
		spritesequences.put("Idle", new SpriteSequence(sprite));
		currentSequence = "Idle";
	}
	
	public void addSequence(String key, SpriteSequence sequence) {
		spritesequences.put(key, sequence);
	}
	
	public void setCurrentSequence(String sequence) {
		this.currentSequence = sequence;
	}
	
	public void setCurrentFrame(float frame) {
		this.currentframe = frame;
	}
	
	public SpriteSequence getSequence(String key) {
		return spritesequences.get(key);
	}
	
	public float getCurrentFrame() {
		return currentframe;
	}
	
	public String getCurrentSequence() {
		return currentSequence;
	}
	
}
