package com.saltosion.gladiator.components;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.listeners.CombatListener;
import com.saltosion.gladiator.util.Direction;

public class CCombat extends Component {

	public int health = 0;
	private int maxHealth = 0;
	private int damage = 0;
	private float swingForce = 20f;
	private Vector2 swingsize = new Vector2(4, 3);
	private CombatListener combatListener;

	private Vector2 swinging = new Vector2();
	private float swingDuration = 0.4f;
	public float swingCdCounter = 0;

	public HashMap<Direction, Boolean> inputs = new HashMap<Direction, Boolean>();

	public CCombat() {
		this.inputs.put(Direction.UP, false);
		this.inputs.put(Direction.DOWN, false);
		this.inputs.put(Direction.LEFT, false);
		this.inputs.put(Direction.RIGHT, false);
	}

	public CCombat setBaseDamage(int basedmg) {
		this.damage = basedmg;
		return this;
	}

	/**
	 * Sets max health for entity and replenishes health.
	 *
	 * @param health
	 * @return
	 */
	public CCombat setHealth(int health) {
		this.health = health;
		this.maxHealth = health;
		return this;
	}

	public CCombat setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		return this;
	}

	public CCombat setSwingForce(float force) {
		this.swingForce = force;
		return this;
	}

	public CCombat setSwingCD(float cd) {
		this.swingDuration = cd;
		return this;
	}

	public CCombat setSwinging(Vector2 swingdir) {
		this.swinging = swingdir;
		return this;
	}

	public CCombat setSwingSize(Vector2 swingsize) {
		this.swingsize = swingsize;
		return this;
	}

	public CCombat setCombatListener(CombatListener listener) {
		this.combatListener = listener;
		return this;
	}

	public int getMaxHealth() {
		return this.maxHealth;
	}

	public int getDamage() {
		float minDmg = damage * 0.9f;
		float maxDmg = damage * 1.1f;
		int randomdamage = (int) (Math.random() * (maxDmg - minDmg) + minDmg);
		if (Math.random() > 0.7) {
			randomdamage *= 1.5;
		}
		return randomdamage;
	}

	public float getSwingForce() {
		return swingForce;
	}

	public float getSwingDuration() {
		return this.swingDuration;
	}

	public Vector2 getSwing() {
		return this.swinging;
	}

	public Direction getSwingDirection() {
		if (swinging.x > 0) {
			return Direction.RIGHT;
		} else if (swinging.x < 0) {
			return Direction.LEFT;
		} else if (swinging.y > 0) {
			return Direction.UP;
		} else if (swinging.y < 0) {
			return Direction.DOWN;
		}
		return null;
	}

	public Vector2 getSwingSize() {
		return this.swingsize;
	}

	public CombatListener getCombatListener() {
		return this.combatListener;
	}
}
