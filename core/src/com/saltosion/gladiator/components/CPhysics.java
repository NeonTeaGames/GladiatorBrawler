package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.collisionlisteners.CollisionListener;

public class CPhysics extends Component {

	private final Vector2 position = new Vector2();
	private final Vector2 velocity = new Vector2();
	private final Vector2 size = new Vector2();
	private float movespeed = 5f, jumpForce = 0.5f, gravity = 1f;
	private CollisionListener collisionListener = null;

	private boolean movable = true;
	private boolean gravityApplied = true;
	private boolean processCollisions = true;
	private boolean ghost = false;
	private boolean grounded = true;

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
	 * @param processCollisions Toggles if the entity processes collisions
	 * @return Returns the instance this methdod was called from
	 */
	public CPhysics setProcessCollisions(boolean processCollisions) {
		this.processCollisions = processCollisions;
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

	public CPhysics setSize(Vector2 size) {
		this.size.set(size);
		return this;
	}

	public CPhysics setPosition(float x, float y) {
		this.position.set(x, y);
		return this;
	}

	public CPhysics setPosition(Vector2 pos) {
		this.position.set(pos);
		return this;
	}

	public CPhysics setMoveSpeed(float movespeed) {
		this.movespeed = movespeed;
		return this;
	}

	public CPhysics setJumpForce(float jumpForce) {
		this.jumpForce = jumpForce;
		return this;
	}

	public CPhysics setGravity(float gravity) {
		this.gravity = gravity;
		return this;

	}

	public CPhysics setCollisionListener(CollisionListener collisionListener) {
		this.collisionListener = collisionListener;
		return this;
	}

	public CPhysics setGrounded(boolean grounded) {
		this.grounded = grounded;
		return this;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public Vector2 getVelocity() {
		return this.velocity;
	}

	public Vector2 getSize() {
		return this.size;
	}

	public float getMovespeed() {
		return this.movespeed;
	}

	public float getJumpForce() {
		return this.jumpForce;
	}

	public float getGravity() {
		return this.gravity;
	}

	public CollisionListener getCollisionListener() {
		return this.collisionListener;
	}

	public boolean isMovable() {
		return this.movable;
	}

	public boolean isGravityApplied() {
		return this.gravityApplied;
	}

	public boolean isProcessCollisions() {
		return this.processCollisions;
	}

	public boolean isGhost() {
		return this.ghost;
	}

	public boolean isGrounded() {
		return this.grounded;
	}

}
