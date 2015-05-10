package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.physics.CollisionListener;

public class CPhysics extends Component {

	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Vector2 size = new Vector2();
	public float movespeed = 5f, jumpForce = 0.5f, gravity = 1f;
	public boolean grounded = true;
	public CollisionListener collisionListener = null;

	public boolean movable = true;
	public boolean gravityApplied = true;
	public boolean dynamic = true;
	public boolean ghost = false;

	// Movement (/input) vars
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean jumping = false;

	/**
	 * @param movable Toggles if the entity can move by itself
	 * @return Returns the instance this methdod was called from
	 */
	public CPhysics setMovable(boolean movable) {
		this.movable = movable;
		return this;
	}

	/**
	 * @param gravityApplied Toggles if the entity is affected by gravity
	 * @return Returns the instance this methdod was called from
	 */
	public CPhysics setGravityApplied(boolean gravityApplied) {
		this.gravityApplied = gravityApplied;
		return this;
	}

	/**
	 * @param dynamic Toggles if the entity processes collisions
	 * @return Returns the instance this methdod was called from
	 */
	public CPhysics setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
		return this;
	}

	/**
	 * @param ghost Toggles if the entity is affected by collisions (will call
	 * collision listener anyway if dynamic == true)
	 * @return Returns the instance this methdod was called from
	 */
	public CPhysics setGhost(boolean ghost) {
		this.ghost = ghost;
		return this;
	}

	public CPhysics setSize(float w, float h) {
		this.size.set(w, h);
		return this;
	}

	public CPhysics setPosition(float x, float y) {
		this.position.set(x, y);
		return this;
	}

	public CPhysics setCollisionListener(CollisionListener collisionListener) {
		this.collisionListener = collisionListener;
		return this;
	}

}
