package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class CPhysics extends Component {

    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public Vector2 size = new Vector2();
    public float movespeed = 5f, jumpForce = 0.3f, gravity = 1f;
    public boolean grounded = true;

    // Movable toggles if the entity can move by itself
    public boolean movable = true;
    // GravityApplied toggles if the entity is affected by gravity
    public boolean gravityApplied = true;
    // Dynamic toggles if the entity processes collisions
    public boolean dynamic = true;

    // Movement (/input) vars
    public boolean movingLeft = false;
    public boolean movingRight = false;
    public boolean jumping = false;

    public CPhysics setMovable(boolean movable) {
        this.movable = movable;
        return this;
    }

    public CPhysics setGravityApplied(boolean gravityApplied) {
        this.gravityApplied = gravityApplied;
        return this;
    }

    public CPhysics setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
        return this;
    }

    public CPhysics setSize(float w, float h) {
        this.size.set(w, h);
        return this;
    }

}
