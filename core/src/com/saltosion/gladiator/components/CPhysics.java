package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class CPhysics extends Component {
	
	public Body body;
	public Vector2 velocity = new Vector2(0, 0);

}
