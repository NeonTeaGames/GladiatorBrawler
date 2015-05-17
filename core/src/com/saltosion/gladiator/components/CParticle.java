package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class CParticle extends Component {

	private final Vector2 position = new Vector2();
	private final Vector2 velocity = new Vector2();
	private final Vector2 gravity = new Vector2();
	private final Vector2 size = new Vector2(0.1f, 0.1f);
	private final Color color = new Color();
	private final float creationTime;
	private float decayTime = 1;

	public CParticle() {
		this.creationTime = System.currentTimeMillis();
	}

	public CParticle setPosition(float x, float y) {
		this.position.set(x, y);
		return this;
	}

	public CParticle setVelocity(float x, float y) {
		this.velocity.set(x, y);
		return this;
	}

	public CParticle setGravity(float x, float y) {
		this.gravity.set(x, y);
		return this;
	}

	public CParticle setSize(float x, float y) {
		this.size.set(x, y);
		return this;
	}

	public CParticle setColor(float r, float g, float b, float a) {
		this.color.set(r, g, b, a);
		return this;
	}

	public CParticle setDecayTime(float decayTime) {
		this.decayTime = decayTime;
		return this;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public Vector2 getVelocity() {
		return this.velocity;
	}

	public Vector2 getGravity() {
		return this.gravity;
	}

	public Vector2 getSize() {
		return this.size;
	}

	public Color getColor() {
		return this.color;
	}

	public float getCreationTime() {
		return this.creationTime;
	}

	public float getDecayTime() {
		return this.decayTime;
	}

}
