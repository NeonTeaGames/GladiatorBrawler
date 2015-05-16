package com.saltosion.gladiator.gui.nodes;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class GUINode {

	private final Vector2 position;
	private final ArrayList<GUINode> children;
	private String ID = "";
	private boolean visible = true;

	public GUINode(String ID) {
		this.ID = ID;
		this.position = new Vector2();
		this.children = new ArrayList<GUINode>();
	}

	public GUINode setPosition(float x, float y) {
		this.position.set(x, y);
		return this;
	}

	public GUINode setPosition(Vector2 pos) {
		this.position.set(pos);
		return this;
	}

	public GUINode setID(String ID) {
		this.ID = ID;
		return this;
	}

	public GUINode addChild(GUINode node) {
		this.children.add(node);
		return this;
	}

	public GUINode removeChild(String id) {
		for (GUINode child : this.children) {
			if (child.ID.equals(id)) {
				this.children.remove(child);
				break;
			}
		}
		return this;
	}

	public GUINode removeChild(GUINode node) {
		this.children.remove(node);
		return this;
	}

	public GUINode clearChildren() {
		this.children.clear();
		return this;
	}

	public ArrayList<GUINode> getChildren() {
		return this.children;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public String getID() {
		return this.ID;
	}

	public GUINode setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public boolean isVisible() {
		return this.visible;
	}

}
