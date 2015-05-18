/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
