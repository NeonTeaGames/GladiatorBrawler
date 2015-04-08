package com.saltosion.gladiator.components;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.SpriteSequence;

public class CRenderedObject extends Component {
	public HashMap<String, SpriteSequence> spritesequences = new HashMap<String, SpriteSequence>();
	public String currentSequence = "";
	public Sprite staticSprite = null; // If this is set, only this image will be shown as the entity
	public float currentframe = 0;
}
